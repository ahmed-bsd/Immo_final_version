package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.HousePricing;

import java.util.List;

@Repository
public interface HousingPricingRepository extends JpaRepository<HousePricing, Long> {
     List<HousePricing> findByGovernorate (String gov);
}
