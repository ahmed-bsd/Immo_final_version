package tn.esprit.spring.serviceInterface;

import tn.esprit.spring.entities.Comment;

import java.util.List;

public interface ICommentService {
    Comment addComment(Comment d);

    //}
    Comment updateComment(Comment s);

    void deleteComment(Long id);

    List<Comment> retrieveAllComment();

    Comment retrieveComment(Long id);
}
