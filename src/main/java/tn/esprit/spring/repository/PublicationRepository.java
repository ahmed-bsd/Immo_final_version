package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Publication;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByTitleContainingAndContentContaining(String title, String content);

    List<Publication> findAllByOrderByLikesDesc();

    List<String> badWords = Arrays.asList("bad", "vulgar", "offensive", "inappropriate");


}
