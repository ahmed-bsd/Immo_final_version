package tn.esprit.spring.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.AnnoucementRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VisitRepository;
import tn.esprit.spring.serviceInterface.IVisitService;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class VisitService implements IVisitService {

    @Autowired
    VisitRepository visitRepository;
    @Autowired
    AnnoucementRepository annoucementRepository;
    @Autowired
    UserRepository userRepository;
    @Value("+12765829206")
    private String phoneNumber;

    public static final String ACCOUNT_SID = "AC89798ba8d46b4c972a197cc774d221d8";
    public static final String AUTH_TOKEN = "21d46bdc8f44748f15beb1ae394f3dc0";
    @Autowired
    private UserRepository userARepository;

    @Override
    public Visit addVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit addAndAssignVisitToAnnouncementAndUser(Visit v, Long idAnnouncement, Long idUser) {

        Announcement announcement = annoucementRepository.findById(idAnnouncement).orElse(null);
        UserA user = userRepository.findById(idUser).orElse(null);
        //Affectation avec annonce : visite c'est le parent
        v.setAnnouncement(announcement);
        //Affectation user : visite c'est le parent
        v.setUserA(user);
        visitRepository.save(v);
        return v;
    }

    @Override
    public void AssignVisitToAnnouncementAndUser (Long idVisit,  Long idAnnouncement, Long idUser) {
        Visit visit = visitRepository.findById(idVisit).get();
        Announcement announcement = annoucementRepository.findById(idAnnouncement).get();
        UserA user = userRepository.findById(idUser).get();
        visit.setUserA(user);
        visit.setAnnouncement(announcement);

        visitRepository.save(visit);

    }

    @Override
    public List<Visit> getAllVisit() {
        return visitRepository.findAll();
    }

    @Override
    public void deletetVisit(Long id) {
        Visit visit= visitRepository.findById(id).get();
        visitRepository.delete(visit);

    }
    public int getMax( int a,  int b, int c) {
        int max = a;
        if (b > max) {
            max = b;
        }
        if (c > max) {
            max = c;
        }
        return max;
    }



    //rajaana akethr  type d'annulation des visite w  men type d'annulation rjaana les régions
    @Override
    public String statistique() {
        List<String> reList = new ArrayList<>();
        String MostFrequent = null;

        //liste des visites par le type d'annulation circulation
        List<Visit> circulation = visitRepository.retrieveVisitbyCircuation();
        //liste des visites par le type d'annulation maladie
        List<Visit> maladie = visitRepository.retrieveVisitbyMALADIE();
        //liste des visites par le type d'annulation urgence
        List<Visit> urgence = visitRepository.retrieveVisitbyUrgence();

        //retourner size des listes
        int size1 = circulation.size();
       int size2 = maladie.size();
        int size3 = urgence.size();

        //pour retourner la liste eli aandha akethr type d'annulation
        int maxsize = getMax(size1, size2, size3);

        if (maxsize == size1) {
            for (Visit v : circulation) {
                String reg = v.getAnnouncement().getRegion();
                reList.add(reg);
                System.out.println(reList);
                String mostFrequent = reList.stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .get()
                        .getKey();

                System.out.println("L'élément le plus fréquent est : " + mostFrequent);

                MostFrequent = mostFrequent;
            }

        }

        else if (maxsize == size2) {
            for (Visit v : maladie) {
                String reg = v.getAnnouncement().getRegion();
                reList.add(reg);
                System.out.println(reList);
                String mostFrequent = reList.stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .get()
                        .getKey();

                System.out.println("L'élément le plus fréquent est : " + mostFrequent);

                MostFrequent = mostFrequent;


            }


        }

        else if (maxsize == size3) {
            for (Visit v : urgence) {
                String reg = v.getAnnouncement().getRegion();
                reList.add(reg);
                System.out.println(reList);
                String mostFrequent = reList.stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .get()
                        .getKey();

                System.out.println("L'élément le plus fréquent est : " + mostFrequent);
                MostFrequent = mostFrequent;

            }
            }
        return MostFrequent ;


    }

    ////////SMS//////
  /**  @Override
    //Every minute
    @Scheduled(cron = "0 * * ? * *")
    public void AlertDateVisit() {
    Long id= Long.valueOf(1);
        UserA user = userARepository.findById(id).get();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        AlertMessage alertMessage=new AlertMessage("test");
       Message message= Message.creator(new PhoneNumber(user.getPhone()),
                new PhoneNumber(phoneNumber),
                alertMessage.getMessage()).create();

        System.out.println("Message SID: " + message.getSid());
    }*/

    //envoyer une notification avant la date du visite

    @Override
    //Every minute
    @Scheduled(cron = "0 * * ? * *")
    public void AlertDateVisit() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //la date actuelle
        Date curDate = new Date();
        List<Visit> Visits = visitRepository.findAll();
        List<Long> expirationSoonList = new ArrayList<>();
            for (Visit v : Visits) {
               UserA user= v.getUserA();
               System.out.println(user);
                //date des visites
                Date date2 = v.getVisitDate();
              // AlertMessage alertMessage=new AlertMessage("Don't forget your visit : " + date2);
                int diffInDays = (int) ((date2.getTime()-curDate.getTime())
                        / (1000 * 60 * 60 * 24));
                if (diffInDays == 1) {
                    expirationSoonList.add(v.getIdvisit());
                }
               /* Message message= Message.creator(new PhoneNumber(user.getPhone()),
                        new PhoneNumber(phoneNumber),
                        alertMessage.getMessage()).create();
                System.out.println("Message SID: " + message.getSid());*/
            }
        System.out.println("Don't forget your visit : " + expirationSoonList);
        }


}
