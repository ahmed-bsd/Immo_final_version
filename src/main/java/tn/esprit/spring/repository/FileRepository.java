package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.entities.File;

public interface FileRepository extends JpaRepository<File, Long> {
}

