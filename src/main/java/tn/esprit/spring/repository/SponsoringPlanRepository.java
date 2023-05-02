package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.SponsoringPlan;


@Repository
public interface SponsoringPlanRepository extends JpaRepository<SponsoringPlan, Long> {



}
