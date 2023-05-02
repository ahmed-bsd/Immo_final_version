package tn.esprit.spring.serviceInterface;

import tn.esprit.spring.entities.Publication;

import java.util.List;

public interface IPublicationService {

    Publication retrievePublication(Long id);


    Publication addPublication(Publication d);

    Publication updatePublication(Publication s);

    void deletePublication(Long id);

    List<Publication> retrieveAllPublication();


    List<Publication> searchPublications(String title, String content);


    List<Publication> getPublicationsByLikes();

}
