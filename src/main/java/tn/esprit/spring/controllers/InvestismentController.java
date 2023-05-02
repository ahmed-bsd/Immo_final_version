package tn.esprit.spring.controllers;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Investisment;
import tn.esprit.spring.repository.InvestismentRepository;
import tn.esprit.spring.service.InvestismentService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public/investisement")
public class InvestismentController {

    @Autowired
    InvestismentRepository investismentRepository;

    @Autowired
    InvestismentService investismentService;

    @PostMapping("/upload")
    public String uploadDateSet(@RequestParam("file") MultipartFile file) throws IOException {
        List<Investisment> dataSets = new ArrayList<>();
        InputStream inputStream=file.getInputStream();
        CsvParserSettings setiing = new CsvParserSettings();
        setiing.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(setiing);
        List<Record> listRecords = parser.parseAllRecords(inputStream);
        listRecords.forEach(record -> {
            Investisment ds = new Investisment();
            ds.setCategory(record.getString("category"));
            ds.setRoomsCount(record.getString("room_count"));
            ds.setBathroomCount(record.getString("bathroom_count"));
            ds.setSize(record.getString("size"));
            ds.setType(record.getString("type"));
            ds.setPrice(Float.parseFloat(record.getString("price")));
            ds.setCity(record.getString("city"));
            ds.setRegion(record.getString("region"));
            ds.setLogPrice(Float.parseFloat(record.getString("log_price")));
            dataSets.add(ds);
        });
        investismentRepository.saveAll(dataSets);
        return "upload successful !!";
    }

        @PostMapping("/estimation")
        public String estimatePrice(@RequestBody Investisment investisment) {
            return investismentService.estimatePrice(investisment);

        }

    @GetMapping("/simulateurFin")
    public String simulateurfin(@RequestParam("Propertyprice") float Propertyprice,
                                @RequestParam("loanTermInYears")int loanTermInYears,@RequestParam("autoFinanced") double autoFinanced){

       return investismentService.finCalculator(Propertyprice,loanTermInYears,autoFinanced);
    }






    }
