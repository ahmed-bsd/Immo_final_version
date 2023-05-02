package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.CategorieAnnouncement;
import tn.esprit.spring.entities.Insurance;

import java.util.Date;
import java.util.List;


@Repository
public interface InsurranceRepository extends JpaRepository<Insurance, Long> {

public Insurance findByIdInsurrance(Long id);


    List<Insurance> findByDateExpirationBefore(Date date);
}
