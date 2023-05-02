package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.Visit;

import java.util.List;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    @Query(value = "SELECT count(user_iduser) FROM subscription where offer_id_offer = :id  AND user_iduser = :idUser", nativeQuery = true)
    int countUsedSubscriptionByOffer(@Param("id") Long id,@Param("idUser") Long idUser);

    @Query(value = "SELECT * FROM subscription where offer_id_offer = :id ", nativeQuery = true)
    List<Subscription> findSubscriptionByOffer(@Param("id") Long id);

    @Query(value = "SELECT COUNT(subscription_type) FROM subscription where subscription_type = :type", nativeQuery = true)
    int getMostPopularTypeSub(@Param("type") String type);

    @Query(value = "SELECT * FROM subscription WHERE subscription_type = :type", nativeQuery = true)
    List<Subscription> findSubscriptionBySubscriptionType(@Param("type") String type);
}
