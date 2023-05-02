package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.repository.CommentRepository;
import tn.esprit.spring.repository.PublicationRepository;
import tn.esprit.spring.serviceInterface.ICommentService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController @RequestMapping("/api")
public class CommentController {

    @Autowired
    ICommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;


    @PostMapping("/public/pubs/{pubId}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable Long pubId, @RequestBody Comment comment) {
        Publication publication = publicationRepository.findById(pubId).orElseThrow(() -> new EntityNotFoundException("Pub with id " + pubId + " not found"));
        comment.setPublication(publication);
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }


    @GetMapping("/public/retrieve-all-comments")
    @ResponseBody
    public List<Comment> getComment() {
        List<Comment> listComment = commentService.retrieveAllComment();
        return listComment;
    }


    @PutMapping("/public/modify-comment")
    @ResponseBody
    public Comment modifyComment(@RequestBody Comment s) {
        return commentService.updateComment(s);

    }


    @DeleteMapping("/public/remove-comment/{comment-id}")
    @ResponseBody
    public void removeComment(@PathVariable("comment-id") Long idComment) {

        commentService.deleteComment(idComment);
        System.out.println("Deleted successfuly");
    }


    @GetMapping("/public/comment/{comment-id}")
    public Comment getPub(@PathVariable("comment-id") Long id) {
        return commentService.retrieveComment(id);
    }


}

