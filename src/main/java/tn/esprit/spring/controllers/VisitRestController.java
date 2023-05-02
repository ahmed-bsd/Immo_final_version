package tn.esprit.spring.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Visit;
import tn.esprit.spring.repository.VisitRepository;
import tn.esprit.spring.serviceInterface.IVisitService;

import java.util.List;

@RestController
@Api(tags = "visit")
@RequestMapping("api/user/client/visit")
public class VisitRestController {

    @Autowired
    IVisitService visitService;
    @Autowired
    VisitRepository visitRepository;


    @PostMapping("/add/visit")
    @ResponseBody
    public Visit addVisit(@RequestBody Visit visit) {
        Visit v= visitService.addVisit(visit);
        return v;
    }
    @PostMapping("/AddAndAssignvisitToUserAndAnnouncement/{idAnnouncement}/{idUser}")
    @ResponseBody
    public Visit AddAndAssignvisitToUserAndAnnouncement(@RequestBody Visit visit ,
                                                  @PathVariable("idAnnouncement") Long idAnnouncement ,
                                                  @PathVariable("idUser") Long idUser ) {
        Visit v= visitService.addAndAssignVisitToAnnouncementAndUser(visit, idAnnouncement,idUser);
        return v;
    }

    @ApiOperation(value = "assign visit To User And Announcement")
    @PutMapping("/assignvisitToUserAndAnnouncement/{idVisit}/{idAnnouncement}/{idUser}")
    public void AssignvisitToUserAndAnnouncement(@PathVariable("idVisit") Long idVisit,
                                                 @PathVariable("idAnnouncement") Long idAnnouncement ,
                                                 @PathVariable("idUser") Long idUser) {
        visitService.AssignVisitToAnnouncementAndUser(idVisit,idAnnouncement,idUser);

    }

    @GetMapping("/retrieve/allvisit")
    @ResponseBody
    public List<Visit> getVisits() {
        return visitService.getAllVisit();
    }

    @GetMapping("/visitRegion")
    @ResponseBody
    public String getVisitsRegion() {
        return visitService.statistique();
    }


    @DeleteMapping("/delete/visit/{id}")
    public void deleteVisit(@PathVariable("id") Long id) {

        visitService.deletetVisit(id);
    }

    @GetMapping("/sms")
    public void smsnotif(){
        visitService.AlertDateVisit();
    }
}
