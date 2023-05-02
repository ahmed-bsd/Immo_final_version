package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.ResponseDTO;

import java.util.List;

@Repository
public interface ResponseDTORepository extends JpaRepository<ResponseDTO, Long> {
     public List<ResponseDTO> findByRegion (String region);
}
