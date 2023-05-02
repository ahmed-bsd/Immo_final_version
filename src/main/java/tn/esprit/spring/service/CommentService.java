package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.repository.CommentRepository;
import tn.esprit.spring.repository.PublicationRepository;
import tn.esprit.spring.serviceInterface.ICommentService;

import java.util.List;

@Service
public class CommentService implements ICommentService {


    @Autowired
    PublicationRepository publicationRepository;
    @Autowired
    CommentRepository commentRepository;

    @Override

    public Comment addComment(Comment d) {
        commentRepository.save(d);
        return d;
    }


    @Override
    public Comment updateComment(Comment s) {

        return commentRepository.save(s);
    }

    @Override
    public void deleteComment(Long id) {
        // TODO Auto-generated method stub
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> retrieveAllComment() {

        List<Comment> comment = (List<Comment>) commentRepository.findAll();
        return comment;
    }

    @Override
    public Comment retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment;
    }


}
