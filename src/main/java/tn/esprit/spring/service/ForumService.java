package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Forum;
import tn.esprit.spring.repository.ForumRepository;
import tn.esprit.spring.serviceInterface.IForumService;

import java.util.List;

@Service
public class ForumService implements IForumService {


    @Autowired

    ForumRepository forumRepository;

    @Override
    public Forum addForum(Forum d) {
        forumRepository.save(d);
        return d;
    }

    @Override
    public Forum updateForum(Forum s) {
        return forumRepository.save(s);
    }


    @Override
    public void deleteForum(Long id) {
        forumRepository.deleteById(id);
    }

    @Override
    public List<Forum> retrieveAllForum() {
        List<Forum> forum = forumRepository.findAll();
        return forum;
    }

    @Override
    public Forum retrieveForum(Long id) {
        Forum forum = forumRepository.findById(id).orElse(null);
        return forum;
    }


}

