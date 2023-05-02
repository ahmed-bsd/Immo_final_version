package tn.esprit.spring.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.spring.config.TwilioConfig;
import tn.esprit.spring.entities.AlertMessage;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.repository.OfferRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.IOfferService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class OfferService implements IOfferService {
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;

    @Value("${twilio.phone.number}")
    private String phoneNumber;



    /*public void sendSMS(UserA user, AlertMessage alertMessage) {
        Message.creator(new PhoneNumber(user.getPhone()),
                new PhoneNumber(phoneNumber),
                alertMessage.getMessage()).create();
    }*/

    public void archiveOffre(){
            List<Offer> offres = offerRepository.findAll();
        Date curDate = new Date();
        for(Offer sub : offres){
            Date expDate= sub.getExpirationDate();
            int diffInDays = (int) ((expDate.getTime() - curDate.getTime())
                    / (1000 * 60 * 60 * 24));
            System.out.println(diffInDays);
            if(diffInDays==0){
                sub.setArchive(false);
                offerRepository.save(sub);
                System.out.println(sub.isArchive());
            }
        }
    }

    public Offer addOffer(Offer offer) {

        offerRepository.save(offer);

        List<UserA>users =userRepository.findAll();
        AlertMessage alertMessage=new AlertMessage("immo project PIdev Test add Offer !!");
        for(UserA u: users){
            System.out.println(u.getPhone());
            String phone="+216"+u.getPhone().toString();
            Message.creator(new PhoneNumber(phone),
                    new PhoneNumber(phoneNumber),
                    alertMessage.getMessage()).create();
        }

        return offerRepository.save(offer);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public void deletetOffer(Long id) {
        Offer offer= offerRepository.findById(id).get();
        offerRepository.delete(offer);
    }
}
