package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Insurance;
import tn.esprit.spring.repository.InsurranceRepository;
import tn.esprit.spring.service.ContratService;
import tn.esprit.spring.service.EmailSenderService;
import tn.esprit.spring.service.InsuranceService;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "Insurance")
@RequestMapping("api/dashboard/insurance")
public class RestControllerInsurance {
    @Autowired
    ContratService contratService;

    @Autowired
    InsuranceService insuranceService;
    @Autowired
    InsurranceRepository insurranceRepository;
    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/addInsurance")
    @ResponseBody
    public void addInsurance(@RequestBody Insurance insurance){
        insuranceService.addInsurance(insurance);
    }
    @GetMapping("/listInsurance")
    public List<Insurance> listInsurance(){
         List<Insurance> insurances =  insuranceService.ListInsurance();
        return insurances;
    }

    @DeleteMapping("/deleteInsurance/{id}")
    @ResponseBody
    public void deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
    }

@PutMapping("/updateInsurance/{id}")
@ResponseBody

    public  Insurance UpdateInsurance(@RequestBody Insurance insurance , @PathVariable Long id){
            insuranceService.updateInsurance(id , insurance);
        return  insurance;
    }

    @GetMapping("/jeudiPayee")
    @Scheduled(cron = "0 56 11 * * *")
    public void sendExpirationEmails() throws MessagingException {
        // Get insurances expiring today or tomorrow
        List<Insurance> expiringInsurances = emailSenderService.findExpiringInsurancess();

        for (Insurance insurance : expiringInsurances) {
            // Get email address from insurance
            String email = insurance.getEmail();

            // Send email
            String subject = "Your insurance is expiring soon";
            String text = "Dear " + insurance.getInsurranceName() + ",\n\n"
                    + "Your insurance is expiring soon. Please log in to our website to renew your insurance.\n\n"
                    + "Thank you for choosing our insurance service.\n\n"
                    + "Sincerely,\n"
                    + "The Insurance Team";
            emailSenderService.sendEmaill(email, subject, text);
        }
    }



}
