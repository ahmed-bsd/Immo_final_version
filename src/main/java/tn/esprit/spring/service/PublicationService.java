package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.repository.ForumRepository;
import tn.esprit.spring.repository.PublicationRepository;
import tn.esprit.spring.serviceInterface.IPublicationService;

import java.util.List;

@Service
public class PublicationService implements IPublicationService {


    @Autowired
    PublicationRepository publicationRepository;
    @Autowired
    ForumRepository forumRepository;


    @Override

    public Publication addPublication(Publication d) {
        publicationRepository.save(d);
        return d;
    }

    @Override
    public Publication updatePublication(Publication s) {

        return publicationRepository.save(s);
    }

    @Override
    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }

    @Override
    public List<Publication> retrieveAllPublication() {

        List<Publication> publication = (List<Publication>) publicationRepository.findAll();
        return publication;
    }

    @Override
    public Publication retrievePublication(Long id) {
        Publication publication = publicationRepository.findById(id).orElse(null);
        return publication;
    }

    public List<Publication> searchPublications(String title, String content) {
        return publicationRepository.findByTitleContainingAndContentContaining(title, content);
    }


    public List<Publication> getPublicationsByLikes() {

        return publicationRepository.findAllByOrderByLikesDesc();
    }


}

