package tn.esprit.spring.controllers;

import com.opencsv.exceptions.CsvException;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.dynamic.scaffold.TypeWriter;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.AnnoucementRepository;
import tn.esprit.spring.repository.HousingPricingRepository;
import tn.esprit.spring.service.HousingPricingService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Api(tags = "HousingPricingController")
@RequestMapping("api/user/client/HousingPricingController")
public class HousingPricingController {

    @Autowired
    private HousingPricingService housingPricingService;
    @Autowired
    private HousingPricingRepository housingPricingRepository;

    @ApiOperation(value = "Get all properties", response = HousePricing.class, responseContainer = "List")
    @GetMapping("/properties")
    public ResponseEntity<List<HousePricing>> getProperties() throws IOException, CsvException {
        List<HousePricing> properties = housingPricingService.readProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all propertiessssss")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HousePricing>> getAllProperties() {
        try {
            List<HousePricing> properties = housingPricingService.readProperties();
            return ResponseEntity.ok(properties);
        } catch (IOException | CsvException e) {
            // handle exception here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/import-csv")
    public ResponseEntity<?> importCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<HousePricing> housePricingList = housingPricingService.readProperties();
            return ResponseEntity.ok("Imported " + housePricingList.size() + " records.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error importing CSV: " + e.getMessage());
        }
    }

    @GetMapping("/storage")
    public ResponseEntity<List<HousePricing>> storeData() {
        try {
            List<HousePricing> housePricingList = housingPricingService.storageData();
            return new ResponseEntity<>(housePricingList, HttpStatus.OK);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/import-csvvvv")
    public ResponseEntity<String> importDataFromCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<HousePricing> housePricingList = housingPricingService.storageData();
            return ResponseEntity.ok("Successfully imported " + housePricingList.size() + " rows.");
        } catch (IOException | CsvException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import data: " + e.getMessage());
        }
    }


    @PostMapping("/upload")
    public String uploadDateSet(@RequestParam("file") MultipartFile file) throws IOException {
        List<HousePricing> dataSets = new ArrayList<>();
        InputStream inputStream=file.getInputStream();
        CsvParserSettings setiing = new CsvParserSettings();

        setiing.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(setiing);
        List<Record> listRecords = parser.parseAllRecords(inputStream);
        listRecords.forEach(record -> {
            HousePricing ds = new HousePricing();
           /* ds.setPriceTND(record.getString("priceTND"));
            ds.setPriceEUR(record.getString("priceEUR"));
            ds.setLocation(record.getString("location"));
            ds.setCity(record.getString("city"));*/
            dataSets.add(ds);
        });
        housingPricingRepository.saveAll(dataSets);
        return "upload successful !!";
    }

    @PostMapping("/announcements/random/2020")
    @ApiOperation(value = "Add random announcements to the database")
    public ResponseEntity<String> addRandomAnnouncements2020(
            @RequestParam(value = "count", defaultValue = "1") int count) {
        housingPricingService.addRandomAnnouncements2020(count);
        return ResponseEntity.ok(String.format("Added %d random announcements to the database.", count));
    }
    @PostMapping("/announcements/random/2021")
    @ApiOperation(value = "Add random announcements to the database")
    public ResponseEntity<String> addRandomAnnouncements2021(
            @RequestParam(value = "count", defaultValue = "1") int count) {
        housingPricingService.addRandomAnnouncements2021(count);
        return ResponseEntity.ok(String.format("Added %d random announcements to the database.", count));
    }
    @PostMapping("/announcements/random/2022")
    @ApiOperation(value = "Add random announcements to the database")
    public ResponseEntity<String> addRandomAnnouncements2022(
            @RequestParam(value = "count", defaultValue = "1") int count) {
        housingPricingService.addRandomAnnouncements2022(count);
        return ResponseEntity.ok(String.format("Added %d random announcements to the database.", count));
    }

    @GetMapping("/predictPrice")
    public double predictPricee(HousePricing house) {
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        // Define the independent variables (predictors)
        double[][] X = new double[][]{
                {house.getArea(), house.getRoom(), house.getBathroom(), house.getGarage(),
                        house.getGarden(), house.getPool(), house.getElevator(), house.getEquippedKitchen(),
                        house.getCentralHeating(), house.getAirConditioning()}
        };

        // Define the dependent variable (response)
        double[] Y = new double[]{house.getPriceTND()};

        // Fit the regression model
        regression.newSampleData(Y, X);
        double[] beta = regression.estimateRegressionParameters();

        // Predict the price of a new house based on its features
        double predictedPrice = beta[0] + beta[1]*house.getArea() + beta[2]*house.getRoom() +
                beta[3]*house.getBathroom() + beta[4]*house.getGarage() +
                beta[5]*house.getGarden() + beta[6]*house.getPool() +
                beta[7]*house.getElevator() + beta[8]*house.getEquippedKitchen() +
                beta[9]*house.getCentralHeating() + beta[10]*house.getAirConditioning();

        return predictedPrice;
    }
/*
    @GetMapping("/HPP")

    public String predict() {
        List<HousePricing> housePricingList;
        if (housingPricingRepository != null) {
            housePricingList = housingPricingRepository.findAll();
        } else {
            housePricingList = new ArrayList<>();
        }
        HousePricePrediction HPP = new HousePricePrediction(housePricingList);

        housingPricingService.trainModell();
        HousePricing house = new HousePricing();
        house.setGarage(1);
        house.setMountainView(1);
        house.setBeachView(1);
        house.setPool(1);
        house.setDistanceToCapital(22.22);
        HPP.predictPrice(house);
        return "HPP";
    }


    }*/

    @PostMapping("/preeeeeedictHousingPrice")
    public String prreeeee(@RequestBody HousePricing housePricing){
        Prediction p = new Prediction(housingPricingRepository.findAll() , 1);
        List<String> ss = new ArrayList<>();

        ss.add("Nabeul");
        ss.add("tunis");
        ss.add("Djerba");
       String ff ="";
        for (String region : ss ){
            ff = ff + "***************" + p.createModel(housePricing,region);
        }
        return ff;
    }

    @Autowired
    AnnoucementRepository annoucementRepository;
    @PostMapping("/preeeeeedictttttttttAnnoncement")
    public String prreeeeedict(@RequestBody Announcement announcement){


        Prediction p = new Prediction(annoucementRepository.findAll(), "announcement");
        List<String> ss = new ArrayList<>();

        ss.add("Nabeul");
        ss.add("Tunis");
        ss.add("Djerba");
        int year = 2022;
        String ff ="";
        for (String region : ss ){
            ff = ff + "***************" + p.createModelByDate(announcement,region, year) + "YEAR" +year ;
        }
        return ff;
    }


    @PostMapping("/preeeeeedictttttttttONFuture/{year}")
    public List<ResultPriductionOnFuture> prreeeeedictOnFuture(@RequestBody Announcement announcement , @PathVariable int year){


        Prediction p = new Prediction(annoucementRepository.findAll(), "announcement");


       List<String> ss = new ArrayList<>(Arrays.asList(
                "Tunis", "Ariana", "Ben Arous", "Manouba", "Nabeul",
              //  "Zaghouan", "Bizerte", "Beja", "Jendouba", "Kef", "Siliana", "Kairouan", "Kasserine", "Sidi Bouzid",
                "Sousse", "Monastir", "Mahdia", "Sfax", "Gabes", "Medenine", "Tataouine", "Gafsa", "Tozeur", "Kebili"
        ));


        List<ResultPriductionOnFuture> ff = new ArrayList<>();
        for (String region : ss ){
            ff.add(p.predictOnFuture(announcement,year, region)) ;
        }
        return ff;
    }


}
