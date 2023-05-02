package tn.esprit.spring.controllers;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.service.ContratService;
import tn.esprit.spring.service.EmailSenderService;
import tn.esprit.spring.service.PDFGeneratorService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@Api(tags = "Contrat")
@RequestMapping("api/user/client/Contrat")
public class RestControllerContrat {


    @Autowired
    ContratService contratService;
    @Autowired
    ContratRepository contratRepository;



    @PostMapping("/addContrat/{idAnn}/{idInsurance}")
    @ResponseBody
    public void addContrat (@PathVariable Long idAnn , @PathVariable Long idInsurance, @RequestBody Contrat contrat  ){
        contratService.addContratInsurance(idAnn,idInsurance ,contrat );
    }

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping(value = "/export/{idContrat}", produces = MediaType.APPLICATION_PDF_VALUE )
    public ResponseEntity<InputStreamResource> exportContratToPDF(@PathVariable Long idContrat) {
        try {
            Contrat contrat = getContratById(idContrat);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            // Send the email with the PDF and QR code attachment
            emailService.sendEmailWithQRCode(contrat);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=contrat.pdf");

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bais));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Contrat getContratById(Long idContrat ) {
        // Implement this method to retrieve the Contrat object from your database or data store
        // This is just an example
        Contrat c = contratRepository.findByIdContrat(idContrat);
        c.getIdContrat();
        c.getCompanyName();
        c.getFunction();
        c.getEmail();
        return c;
    }
    @Autowired
    private EmailSenderService emailService;
    @PostMapping("emailService")
    public ResponseEntity<String> createContrat(@RequestBody Contrat contrat) throws IOException, MessagingException, DocumentException, WriterException {
        // Save the contrat to the database
        contratRepository.save(contrat);

        // Send the email with the PDF and QR code attachment
        emailService.sendEmailWithQRCode(contrat);

        return ResponseEntity.ok("Contrat created successfully and email sent.");
    }
}