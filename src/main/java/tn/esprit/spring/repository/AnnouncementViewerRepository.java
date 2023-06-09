package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.AnnouncementViewer;


@Repository
public interface AnnouncementViewerRepository extends JpaRepository<AnnouncementViewer, Long> {


}
