package tn.esprit.spring.serviceInterface;

import tn.esprit.spring.entities.Forum;

import java.util.List;

public interface IForumService {

    Forum addForum(Forum d);

    Forum updateForum(Forum s);

    void deleteForum(Long id);

    List<Forum> retrieveAllForum();

    Forum retrieveForum(Long id);

}
