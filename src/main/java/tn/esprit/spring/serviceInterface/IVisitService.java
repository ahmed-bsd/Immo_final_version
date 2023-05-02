package tn.esprit.spring.serviceInterface;

import tn.esprit.spring.entities.Visit;

import java.util.List;

public interface IVisitService {

    public Visit addVisit(Visit visit);

    public Visit addAndAssignVisitToAnnouncementAndUser(Visit v ,Long idAnnouncement , Long idUser);
    public void AssignVisitToAnnouncementAndUser (Long idVisit,  Long idAnnouncement, Long idUser);
    public List<Visit> getAllVisit();

    public void deletetVisit(Long id);

    public String  statistique();
    public void AlertDateVisit();
}
