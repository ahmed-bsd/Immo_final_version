package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Investisment;

import java.util.List;

@Repository
public interface InvestismentRepository extends JpaRepository<Investisment,Long> {

    @Query(value = "SELECT * FROM investisment e WHERE e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type", nativeQuery = true)
    List<Investisment> findInvestismentByCategoryAndCityAndRegion(@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);

  //  @Query(value = "SELECT * FROM investisment e WHERE e.category =:category AND e.city =:city AND e.region =:region AND e.type =:type",nativeQuery = true)
   // List<Investisment> findInvestismentByCategoryAndCityAndRegion(@Param("category") String category,@Param("city") String city,@Param("region") String region,@Param("type") String type);
    @Query(value = "Select * FROM investisment i WHERE i.type LIKE 'À Vendre' ",nativeQuery = true)
    List<Investisment> findByTypeVente();
    @Query(value = "Select * FROM investisment i WHERE i.type LIKE 'À Louer' ",nativeQuery = true)
    List<Investisment> findByTypeVLocation();

    @Query(value = "SELECT DISTINCT * FROM investisment i WHERE i.price < :price AND (i.category='Appartements' OR i.category='Maisons et Villas') " +
            "GROUP BY price ORDER BY price desc LIMIT 3",nativeQuery = true)
    List<Investisment> findLessPrices(@Param("price") float price);

    @Query(value="SELECT MAX(price) FROM investisment e WHERE e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type ",nativeQuery = true)
    Float findMaxInvest(@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);
    @Query(value="SELECT MIN(price) FROM investisment e WHERE e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type ",nativeQuery = true)
    Float findMinInvest(@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);

    @Query(value="SELECT AVG(price) FROM investisment e WHERE e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type ",nativeQuery = true)
    Float average(@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);

    // **************Appart :

  @Query(value="SELECT MAX(price) FROM investisment e WHERE e.roomsCount = :roomsCount AND e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type ",nativeQuery = true)
  Float findMaxInvestA(@Param("roomsCount") String roomsCount,@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);

  @Query(value="SELECT MIN(price) FROM investisment e WHERE e.roomsCount = :roomsCount AND e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type ",nativeQuery = true)
  Float findMinInvestA(@Param("roomsCount") String roomsCount,@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);

  @Query(value="SELECT AVG(price) FROM investisment e WHERE e.roomsCount = :roomsCount AND e.category = :category AND e.city = :city AND e.region = :region AND e.type = :type ",nativeQuery = true)
  Float averageA(@Param("roomsCount") String roomsCount,@Param("category") String category, @Param("city") String city, @Param("region") String region, @Param("type") String type);






}
