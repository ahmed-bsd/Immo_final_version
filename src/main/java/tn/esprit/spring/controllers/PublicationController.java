package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.File;
import tn.esprit.spring.entities.Forum;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.exceptions.BadWordException;
import tn.esprit.spring.exceptions.InvalidInputException;
import tn.esprit.spring.exceptions.MaxSizeExceededException;
import tn.esprit.spring.exceptions.ResourceNotFoundException;
import tn.esprit.spring.repository.*;
import tn.esprit.spring.serviceInterface.IForumService;
import tn.esprit.spring.serviceInterface.IPublicationService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static tn.esprit.spring.repository.PublicationRepository.badWords;

@RestController
@RequestMapping("/api")
public class PublicationController {
    @Autowired
    IForumService forumService;
    @Autowired
    IPublicationService publicationService;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userARepository;


    /*@PostMapping("/forums/{forumId}/publication")
    public ResponseEntity<Publication> addPublication(@PathVariable Long forumId, @RequestBody Publication publication) {
        Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new EntityNotFoundException("Forum with id " + forumId + " not found"));
        publication.setForum(forum);
        Publication savedPublication = publicationRepository.save(publication);
        return ResponseEntity.ok(savedPublication);
    }*/


    @GetMapping("/public/retrieve-all-publication")
    @ResponseBody
    public List<Publication> getPublication() {
        List<Publication> listPublication = publicationService.retrieveAllPublication();
        return listPublication;
    }


    @PutMapping("/public/{forumId}/posts/{postId}")
    public Publication updatePublication(@PathVariable Long forumId, @PathVariable Long postId, @RequestBody Publication updatedPublication) {
        return forumRepository.findById(forumId)
                .map(forum -> {
                    Publication post = publicationRepository.findById(postId)
                            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

                    post.setTitle(updatedPublication.getTitle());
                    post.setContent(updatedPublication.getContent());

                    Publication savedPost = publicationRepository.save(post);

                    return savedPost;
                }).orElseThrow(() -> new ResourceNotFoundException("Forum", "id", forumId));
    }


    @DeleteMapping("/public/remove-publication/{publication-id}")
    @ResponseBody
    public void removePublication(@PathVariable("publication-id") Long idPublication) {

        publicationService.deletePublication(idPublication);
        System.out.println("Deleted successfuly");
    }


    @GetMapping("/public/publication/{publication-id}")
    @CrossOrigin
    public Publication getPub(@PathVariable("publication-id") Long id) {
        return publicationService.retrievePublication(id);
    }


    @PutMapping("/public/pub/{id}/like")
    public Publication like(@PathVariable Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publication not found"));

        publication.incrementLikes();
        return publicationRepository.save(publication);
    }

    @PutMapping("/public/pub/{id}/dislike")
    public Publication dislike(@PathVariable Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publication not found"));

        publication.incrementDislikes();
        return publicationRepository.save(publication);
    }


    @GetMapping("/public/publications/search")
    public ResponseEntity<List<Publication>> searchPublications(@RequestParam(required = false) String title,
                                                                @RequestParam(required = false) String content) {
        List<Publication> publications = publicationService.searchPublications(title, content);
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/dashboard/publications/likes")
    public List<Publication> getPublicationsByLikes() {
        return publicationService.getPublicationsByLikes();
    }


    @PostMapping("/public/forums/{forumId}/publication")
    public ResponseEntity<Publication> createPublicationWithAttachment(@PathVariable Long forumId,
                                                                       @RequestParam("file") MultipartFile file, @ModelAttribute @Valid Publication publication,
                                                                       BindingResult result) {
        if (publication.containsBadWord(badWords)) {
            throw new BadWordException("The publication contains a bad word.");
        }
        if (result.hasErrors()) {
            throw new InvalidInputException(result.getFieldErrors());
        }
        if (publication.getTitle().length() > 20) {
            throw new MaxSizeExceededException("Title exceeds maximum length of 20 characters");
        }
        try {
            Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new EntityNotFoundException("Forum with id " + forumId + " not found"));
            publication.setForum(forum);

            if (!file.isEmpty()) {
                // get the filename and extension
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));

                // create a new filename to prevent overwriting existing files
                String newFileName = UUID.randomUUID().toString() + fileExtension;

                // write the file to the publications directory
                Path path = Paths.get("" + newFileName);
                Files.write(path, file.getBytes());

                // create a new File entity and set its attributes
                publication.setAttachments(new ArrayList<>());
                File fileEntity = new File();
                fileEntity.setFilename(fileName);
                fileEntity.setFiletype(file.getContentType());
                fileEntity.setFileSize(file.getSize());
                fileEntity.setAttachmentUrl(path.toString());
                fileEntity.setPublication(publication);

                // add the file entity to the publication
                publication.getAttachments().add(fileEntity);
            }

            Publication savedPublication = publicationRepository.save(publication);
            return ResponseEntity.ok(savedPublication);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file", ex);
        }
    }



    @PatchMapping("/public/publications/{publicationId}/unlike")
    public ResponseEntity<Publication> unlikePublication(@PathVariable Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new EntityNotFoundException("Publication with id " + publicationId + " not found"));
        publication.decrementLikes();
        Publication savedPublication = publicationRepository.save(publication);
        return ResponseEntity.ok(savedPublication);
    }

    @PatchMapping("/public/publications/{publicationId}/undislike")
    public ResponseEntity<Publication> undislikePublication(@PathVariable Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new EntityNotFoundException("Publication with id " + publicationId + " not found"));
        publication.decrementDislikes();
        Publication savedPublication = publicationRepository.save(publication);
        return ResponseEntity.ok(savedPublication);
    }




    @GetMapping("/dashboard/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        // Get the count of each entity
        long forumCount = forumRepository.count();
        long publicationCount = publicationRepository.count();
        long commentCount = commentRepository.count();
        long userACount = userARepository.count();

        // Calculate the average number of comments per publication
        double avgCommentsPerPublication = commentRepository.count() / (double) publicationRepository.count();

        // Find the most active user (the one with the most comments)

        // Create a map to hold the statistics
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("forumCount", forumCount);
        statistics.put("publicationCount", publicationCount);
        statistics.put("commentCount", commentCount);
        statistics.put("userACount", userACount);
        statistics.put("avgCommentsPerPublication", avgCommentsPerPublication);

        return ResponseEntity.ok(statistics);
    }
}
