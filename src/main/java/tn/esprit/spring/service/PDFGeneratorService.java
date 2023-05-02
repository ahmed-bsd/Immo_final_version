package tn.esprit.spring.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Contrat;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PDFGeneratorService {
    public void export( Contrat contrat) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new ByteArrayOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("This is a title.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph(
                "CompanyName: " + contrat.getCompanyName() + "\n" +
                        "Function: " + contrat.getFunction() + "\n" +
                        "Email: " + contrat.getEmail() + "\n"
                , fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }
}
