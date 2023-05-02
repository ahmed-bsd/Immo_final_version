package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Forum;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.repository.ForumRepository;
import tn.esprit.spring.repository.PublicationRepository;
import tn.esprit.spring.serviceInterface.IForumService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ForumController {
    @Autowired
    IForumService forumService;
    @Autowired
    ForumRepository forumRepository;
    @Autowired
    PublicationRepository publicationRepository;

    @PostMapping("/dashboard/add")

    @ResponseBody

    public Forum addForum(@RequestBody Forum d) {
        Forum forum = forumService.addForum(d);
        return forum;
    }

    @PutMapping("/dashboard/modify-forum")
    @ResponseBody
    public Forum modifyForum(@RequestBody Forum d) {
        return forumService.updateForum(d);

    }


    @DeleteMapping("/dashboard/remove-forum/{forum-id}")
    @ResponseBody
    public void removeForum(@PathVariable("forum-id") Long id) {

        forumService.deleteForum(id);
        System.out.println("Deleted successfuly");
    }

    @GetMapping("/public/retrieve-all-forums")
    @ResponseBody
    public List<Forum> getForum() {
        List<Forum> listForum = forumService.retrieveAllForum();
        return listForum;
    }

    @GetMapping("/dashboard/forum/{forum-id}")

    public Forum getProd(@PathVariable("forum-id") Long id) {
        return forumService.retrieveForum(id);
    }


    @GetMapping("/public/forums/sorted-by-publications")
    public ResponseEntity<List<Forum>> getAllForumsSortedByPublications() {
        List<Forum> forums = forumRepository.findAll();
        Collections.sort(forums, Comparator.comparingInt(f -> f.getPublication().size()));
        return ResponseEntity.ok(forums);
    }

    @GetMapping("/dashboard/forums/{forumId}/most-active-users")
    public ResponseEntity<List<UserA>> getMostActiveUsers(@PathVariable Long forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new EntityNotFoundException("Forum with id " + forumId + " not found"));

        List<Object[]> activeUsers = forumRepository.findMostActiveUsers();

        List<UserA> users = new ArrayList<>();
        for (Object[] result : activeUsers) {
            UserA user = (UserA) result[0];
            users.add(user);
        }

        return ResponseEntity.ok(users);
    }


}

