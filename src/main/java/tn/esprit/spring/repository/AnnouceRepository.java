package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Announcement;

import java.util.List;


@Repository
public interface AnnouceRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAnnouncementsBySponsoredIsTrueOrderBySponsoringWeightDesc();



}
