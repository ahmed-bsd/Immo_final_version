package tn.esprit.spring.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javassist.bytecode.ByteArray;
import lombok.AllArgsConstructor;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.CategorieAnnouncement;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Insurance;
import tn.esprit.spring.repository.InsurranceRepository;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailSenderService {
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    InsurranceRepository insurranceRepository;

    @Autowired
    PDFGeneratorService pdfGeneratorService;
    public List<Insurance> findExpiringInsurancess() {
        Date tomorrow = Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return insurranceRepository.findByDateExpirationBefore(tomorrow);
    }

    public void sendEmaill(String email, String subject, String text) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ahmedbhd97@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Mail sent");
    }
    public void sendEmailWithQRCode(Contrat contrat) throws MessagingException, IOException, WriterException, DocumentException {
        MimeMessage message = mailSender.createMimeMessage();

        // Set the recipients, subject, and body of the email
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(contrat.getEmail()));
        message.setSubject("Your contrat" );

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Please find your contrat attached.", "application/pdf");

        // Generate the QR code
        String qrText = "Company name: " + contrat.getCompanyName() + "\nFunction: " + contrat.getFunction();
        Multipart multipartt = new MimeMultipart();
        Multipart multipart = new MimeMultipart();
        ByteArrayDataSource   pdfDataSource = new ByteArrayDataSource(generatePdf(contrat), "application/pdf");
        ByteArrayOutputStream qrOutputStream = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(pdfDataSource.getContentType(), BarcodeFormat.QR_CODE, 200, 200);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", qrOutputStream);
        byte[] qrBytes = qrOutputStream.toByteArray();

        // Attach the PDF and QR code to the email

        MimeBodyPart pdfAttachment = new MimeBodyPart();
        pdfAttachment.addHeader("Content-Type", "application/pdf");
        pdfAttachment.addHeader("Content-Disposition", "attachment; filename=contrat.pdf");
        pdfAttachment.setDataHandler(new DataHandler((DataSource) pdfDataSource));
        multipart.addBodyPart(pdfAttachment);

        ByteArrayDataSource qrDataSource = new ByteArrayDataSource(qrBytes, "image/png");
        MimeBodyPart qrAttachment = new MimeBodyPart();
        qrAttachment.addHeader("Content-Type", "image/png");
        qrAttachment.addHeader("Content-Disposition", "attachment; filename=qr.png");
        qrAttachment.setDataHandler(new DataHandler(qrDataSource));
        multipart.addBodyPart(qrAttachment);

        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        // Send the email
        mailSender.send(message);
    }


    private byte[] generatePDF(Contrat contrat) throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfGeneratorService.export( contrat);
        return baos.toByteArray();
    }
    public byte[] generatePdf(Contrat c) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Add content to the PDF document here
        Paragraph title = new Paragraph("Contrat information");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add the contract fields to the PDF document
        document.add(new Paragraph("ID: " + c.getIdContrat()));
        document.add(new Paragraph("Company name: " + c.getCompanyName()));
        document.add(new Paragraph("Function: " + c.getFunction()));
        document.add(new Paragraph("Email: " + c.getEmail()));
        document.add(new Paragraph("Type de contrat: " + c.getTypeContrat().toString()));

        document.close();

        // Return the PDF document as a byte array
        return  (baos.toByteArray());
    }

}