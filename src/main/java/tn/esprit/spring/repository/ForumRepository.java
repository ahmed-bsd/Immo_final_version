package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Forum;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Query("SELECT p.user, COUNT(p) as publicationCount FROM Publication p GROUP BY p.user ORDER BY publicationCount DESC")
    List<Object[]> findMostActiveUsers();



}
