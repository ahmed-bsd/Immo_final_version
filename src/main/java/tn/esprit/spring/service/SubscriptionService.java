package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.repository.OfferRepository;
import tn.esprit.spring.repository.SubscriptionRepository;
import tn.esprit.spring.serviceInterface.ISubscriptionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    OfferRepository offerRepository;

    public Subscription addSubscription(Subscription subscription) {
        System.out.println(subscription.getSubscriptionType());
        if(subscription.getSubscriptionType().equals("FREE")){
            subscription.setDescription("Publiez votre annonce et observez les résultats avant de payer. " +
                    "Pour répondre aux acheteurs, il faudra passer en abonnement mensuel ou forfait 3 mois.\n");

        }if (subscription.getSubscriptionType().equals("MOUNTHLY")) {
            subscription.setDescription(
                    "Publiez votre annonce avec 12 photos + vidéo et bénéficiez de tous nos outils. " +
                    "Interrompez l'abonnement en 1 clic, quand vous le souhaitez.\n" +
                    "* Visite virtuelle en option");

        }else {
            subscription.setDescription("Vous pensez que votre vente va prendre du temps ? Bénéficiez d'un tarif dégressif !  20% de remise  par rapport à l'abonnement.\n" +
                    "* Visite virtuelle en option");
        }
        System.out.println(subscription.getSubscriptionType());


        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public void deletetSubscription(Long id) {
      Subscription subscription = subscriptionRepository.findById(id).get();

          subscriptionRepository.delete(subscription);

    }

    public void archiveSubscription(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        Date curDate = new Date();
        for(Subscription sub : subscriptions){
            Date expDate= sub.getExpirationDate();
            int diffInDays = (int) ((expDate.getTime() - curDate.getTime())
                    / (1000 * 60 * 60 * 24));
            System.out.println(diffInDays);
            if(diffInDays==0){
                sub.setArchive(false);
                subscriptionRepository.save(sub);
                System.out.println(sub.isArchive());
            }
        }

    }


    public List<Subscription> getAllValidSubscriptions() {
        List<Subscription> list = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for(Subscription s : subscriptions){
            if(s.isArchive()==true)
                list.add(s);
        }return list;
    }

    public void assignSubscriptionToOffer (long subId, long offerId) throws Exception{
        Subscription subscription = subscriptionRepository.findById(subId).get();
        Offer offer = offerRepository.findById(offerId).get();
        Long idUser=subscription.getUserA().getIdUser();
        System.out.println("user id ==> "+idUser);
        int usedCount = subscriptionRepository.countUsedSubscriptionByOffer(offerId,idUser);
        int limitOfUse=offer.getLimitOfUse();
        System.out.println("limitofuse:"+limitOfUse+"\nusedCount=" +usedCount);
        if (limitOfUse > usedCount){
            subscription.setOffer(offer);
            subscriptionRepository.save(subscription);
        }else
            throw new Exception("Limite d'utilisation atteinte pour cette offre.");

    }
    public String getMostPopSub(String free,String mounthly, String pack) {

        int mostpop = subscriptionRepository.getMostPopularTypeSub(free);
        int mostpop2 = subscriptionRepository.getMostPopularTypeSub(mounthly);
        int mostpop3 = subscriptionRepository.getMostPopularTypeSub(pack);
        int max = getMax(mostpop3,mostpop2,mostpop);
        List<Subscription> subscriptionsFREE= subscriptionRepository.findSubscriptionBySubscriptionType(free);
        List<Subscription> subscriptionsMounth= subscriptionRepository.findSubscriptionBySubscriptionType(mounthly);
        List<Subscription> subscriptionsPackage= subscriptionRepository.findSubscriptionBySubscriptionType(pack);

        double revenue=0;
        if(max==mostpop){
            return "FREE :"+max
                    +"\nMOUNTHLY :"+mostpop2
                    +"\nPACKAGE : "+mostpop3
                    +"\n ==> most popular type of Subscription = FREE"
                    +"\nle revenue ="+revenue;

        }else if (max==mostpop2){
            for(Subscription s:subscriptionsMounth){
                if(s.getOffer()!=null){  // exple (100% - 20%)/100 ==> 0.8
                    revenue +=s.getPrice()* (100 - s.getOffer().getValue()) / 100;
                }else
                   revenue += s.getPrice();
            }
            return "MOUNTHLY :"+max
                    +"\nFREE :"+mostpop
                    +"\nPACKAGE : "+mostpop3
                    +"\n ==> most popular type of Subscription = MOUNTHLY"
                    +"\nle revenue ="+revenue;

        }else
            for(Subscription s:subscriptionsMounth){
                if(s.getOffer()!=null){
                    revenue +=s.getPrice()* (100 - s.getOffer().getValue()) / 100;
                }else
                    revenue += s.getPrice();
            }
            return "PACKAGE :"+max
                    +"\nFREE :"+mostpop
                    +"\nMOUNTHLY : "+mostpop2
                    +"\n ==> most popular type of Subscription = PACKAGE"
                    +"\nle revenue ="+revenue;
    }
    public int getMax(int num1, int num2, int num3) {
        int max = num1;
        if (num2 > max) {
            max = num2;
        }
        if (num3 > max) {
            max = num3;
        }
        return max;
    }
}
