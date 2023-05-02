package tn.esprit.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.CategorieAnnouncement;
import tn.esprit.spring.entities.HousePricing;
import tn.esprit.spring.repository.AnnoucementRepository;
import tn.esprit.spring.repository.HousingPricingRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.DoubleStream;

@Service
@AllArgsConstructor
public class HousingPricingService {
    public List<HousePricing> readProperties() throws IOException, CsvException {
        ClassPathResource resource = new ClassPathResource("ExcelFiles/dataSetFull.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()));

        List<String[]> rows = reader.readAll();
        reader.close();

        List<HousePricing> properties = new ArrayList<>();

        // skip the header row
        for (int i = 2; i < rows.size(); i++) {
            String[] row = rows.get(i);

            HousePricing housePricing = new HousePricing();

            if (!row[1].isEmpty()) {
                housePricing.setPriceTND(Double.parseDouble(row[1]));
            }
            if (!row[2].isEmpty()) {
                housePricing.setPriceEUR(Double.parseDouble(row[2]));
            }
            if (!row[3].isEmpty()) {
            housePricing.setLocation(row[3]);}
            if (!row[4].isEmpty()) {
            housePricing.setCity(row[4]);}
            if (!row[5].isEmpty()) {
            housePricing.setGovernorate(row[5]);}
            if (!row[6].isEmpty()) {
            housePricing.setArea(Double.parseDouble(row[6]));}
            if (!row[7].isEmpty()) {
                housePricing.setPieces(Integer.parseInt(row[7]));
            }
            if (!row[8].isEmpty()) {
                housePricing.setRoom(Integer.parseInt(row[8]));
            }
            if (!row[9].isEmpty()&& row[10].equalsIgnoreCase("YES")) {
                housePricing.setBathroom(Integer.parseInt(row[9]));
            }
            if (!row[10].isEmpty() && row[10].equalsIgnoreCase("YES")) {
                housePricing.setAge(row[10]);
            }
            if (!row[11].isEmpty() && row[11].equalsIgnoreCase("YES")) {
            housePricing.setState(Integer.parseInt(row[11]));}
            if (!row[12].isEmpty()) {
                housePricing.setLatitude(Double.parseDouble(row[12]));
            }
            if (!row[13].isEmpty()) {
                housePricing.setLongitude(Double.parseDouble(row[13]));
            }
            if (!row[14].isEmpty()) {
                housePricing.setDistanceToCapital(Double.parseDouble(row[14]));
            }
            if (row[15].equalsIgnoreCase("yes")) {
                housePricing.setGarage(1);
            }
            if (row[16].equalsIgnoreCase("yes")) {
                housePricing.setGarden(1);
            }
            if (row[17].equalsIgnoreCase("yes")) {
                housePricing.setConcierge(Integer.parseInt(row[17]));
            }
            if (row[18].equalsIgnoreCase("yes")) {
                housePricing.setBeachView(1);
            }
            if (row[19].equalsIgnoreCase("yes")) {
                housePricing.setMountainView(1);
            }
            if (row[20].equalsIgnoreCase("yes")) {
                housePricing.setPool(1);
            }
            if (row[21].equalsIgnoreCase("yes")) {
                housePricing.setElevator(1);
            }
            if (row[22].equalsIgnoreCase("yes")) {
                housePricing.setFurnished(1);
            }
            if (row[23].equalsIgnoreCase("yes")) {
                housePricing.setEquippedKitchen(1);
            }
            if (row[24].equalsIgnoreCase("yes")) {
                housePricing.setCentralHeating(1);
            }
            if (row[25].equalsIgnoreCase("yes")) {
                housePricing.setAirConditioning(1);
            }

            properties.add(housePricing);
        }

        return properties;
    }




    @Autowired
    private HousingPricingRepository housingPricingService;

    public List<HousePricing> storageData() throws IOException, CsvException {
        List<HousePricing> housePricingList = new ArrayList<>();

        ClassPathResource resource = new ClassPathResource("ExcelFiles/dataSetFull.csv");
             CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()));

        List<String[]> rows = csvReader.readAll();
        csvReader.close();

        for (int i = 2; i < rows.size(); i++) {
            String[] row = rows.get(i);
                HousePricing housePricing = new HousePricing();
                if (!row[1].isEmpty()) {
                    housePricing.setPriceTND(Double.parseDouble(row[1]));
                }
                if (!row[2].isEmpty()) {
                    housePricing.setPriceEUR(Double.parseDouble(row[2]));
                }
                if (!row[3].isEmpty()) {
                    housePricing.setLocation(row[3]);}
                if (!row[4].isEmpty()) {
                    housePricing.setCity(row[4]);}
                if (!row[5].isEmpty()) {
                    housePricing.setGovernorate(row[5]);}
                if (!row[6].isEmpty()) {
                    housePricing.setArea(Double.parseDouble(row[6]));}
                if (!row[7].isEmpty()) {
                    housePricing.setPieces(Integer.parseInt(row[7]));
                }
                if (!row[8].isEmpty()) {
                    housePricing.setRoom(Integer.parseInt(row[8]));
                }
                if (!row[9].isEmpty()) {
                    housePricing.setBathroom(Integer.parseInt(row[9]));
                }
                if (!row[10].isEmpty()) {
                    housePricing.setAge(row[10]);
                }
                if (!row[11].isEmpty() ) {
                    housePricing.setState(Integer.parseInt(row[11]));}
                if (!row[12].isEmpty()) {
                    housePricing.setLatitude(Double.parseDouble(row[12]));
                }
                if (!row[13].isEmpty()) {
                    housePricing.setLongitude(Double.parseDouble(row[13]));
                }
                if (!row[14].isEmpty()) {
                    housePricing.setDistanceToCapital(Double.parseDouble(row[14]));
                }
                if (!row[15].isEmpty()) {
                    housePricing.setGarage(Integer.parseInt(row[15]));
                }
                if (!row[16].isEmpty()) {
                    housePricing.setGarden(Integer.parseInt(row[16]));
                }
                if (!row[17].isEmpty()) {
                    housePricing.setConcierge(Integer.parseInt(row[17]));
                }
                if (!row[18].isEmpty()) {
                    housePricing.setBeachView(Integer.parseInt(row[18]));
                }
                if (!row[19].isEmpty()) {
                    housePricing.setMountainView(Integer.parseInt(row[19]));
                }
                if (!row[20].isEmpty()) {
                    housePricing.setPool(Integer.parseInt(row[20]));
                }
                if (!row[21].isEmpty()) {
                    housePricing.setElevator(Integer.parseInt(row[21]));
                }
                if (!row[22].isEmpty()) {
                    housePricing.setFurnished(Integer.parseInt(row[22]));
                }
                if (!row[23].isEmpty()) {
                    housePricing.setEquippedKitchen(Integer.parseInt(row[23]));
                }
                if (!row[24].isEmpty()) {
                    housePricing.setCentralHeating(Integer.parseInt(row[24]));
                }
                if (!row[25].isEmpty()) {
                    housePricing.setAirConditioning(Integer.parseInt(row[25]));
                }
                housePricingList.add(housePricing);
            }


        return housingPricingService.saveAll(housePricingList);
    }

    @Autowired
    private AnnoucementRepository annoucementRepository;

    private static final Random RANDOM = new Random();

    private static final String[] TUNISIA_REGIONS = { "Tunis", "Ariana", "Ben Arous", "Manouba", "Nabeul",
            "Zaghouan", "Bizerte", "Beja", "Jendouba", "Kef", "Siliana", "Kairouan", "Kasserine", "Sidi Bouzid",
            "Sousse", "Monastir", "Mahdia", "Sfax", "Gabes", "Medenine", "Tataouine", "Gafsa", "Tozeur", "Kebili" };

    public void addRandomAnnouncements2020(int count) {
        for (int i = 0; i < count; i++) {
            Announcement announcement = new Announcement();
            announcement.setDescription("Description " + i);
            announcement.setName("Announcement " + i);
            announcement.setLocalisation(getRandomTunisiaRegion());
            announcement.setPrice(generateRandomPrice2020());
            announcement.setEtat(RANDOM.nextBoolean());
            announcement.setDate(getRandomDate2020());
            announcement.setPool((RANDOM.nextInt(2) ));
            announcement.setGarage((RANDOM.nextInt(2) ));
            announcement.setAirConditioning((RANDOM.nextInt(2) ));
            announcement.setCategorieAnnouncement(CategorieAnnouncement.valueOf("MAISON"));
            annoucementRepository.save(announcement);
        }
    }
    public void addRandomAnnouncements2021(int count) {
        for (int i = 0; i < count; i++) {
            Announcement announcement = new Announcement();
            announcement.setDescription("Description " + i);
            announcement.setName("Announcement " + i);
            announcement.setLocalisation(getRandomTunisiaRegion());
            announcement.setPrice(generateRandomPrice2021());
            announcement.setEtat(RANDOM.nextBoolean());
            announcement.setDate(getRandomDate2021());
            announcement.setPool((RANDOM.nextInt(2) ));
            announcement.setGarage((RANDOM.nextInt(2) ));
            announcement.setAirConditioning((RANDOM.nextInt(2) ));
            announcement.setCategorieAnnouncement(CategorieAnnouncement.valueOf("MAISON"));

            annoucementRepository.save(announcement);
        }
    }
    public void addRandomAnnouncements2022(int count) {
        for (int i = 0; i < count; i++) {
            Announcement announcement = new Announcement();
            announcement.setDescription("Description " + i);
            announcement.setName("Announcement " + i);
            announcement.setLocalisation(getRandomTunisiaRegion());
            announcement.setPrice(generateRandomPrice2022());
            announcement.setEtat(RANDOM.nextBoolean());
            announcement.setDate(getRandomDate2022());
            announcement.setPool((RANDOM.nextInt(2) ));
            announcement.setGarage((RANDOM.nextInt(2) ));
            announcement.setAirConditioning((RANDOM.nextInt(2) ));
            announcement.setCategorieAnnouncement(CategorieAnnouncement.valueOf("MAISON"));

            annoucementRepository.save(announcement);
        }
    }

    private LocalDate getRandomDate2020() {
        long startEpochDay = LocalDate.of(2020, 1, 1).toEpochDay();
        long endEpochDay = LocalDate.of(2020, 12, 31).toEpochDay();
        long randomDay = startEpochDay + RANDOM.nextInt((int) (endEpochDay - startEpochDay));
        return LocalDate.ofEpochDay(randomDay);
    }
    private LocalDate getRandomDate2021() {
        long startEpochDay = LocalDate.of(2021, 1, 1).toEpochDay();
        long endEpochDay = LocalDate.of(2021, 12, 31).toEpochDay();
        long randomDay = startEpochDay + RANDOM.nextInt((int) (endEpochDay - startEpochDay));
        return LocalDate.ofEpochDay(randomDay);
    }
    private LocalDate getRandomDate2022() {
        long startEpochDay = LocalDate.of(2022, 1, 1).toEpochDay();
        long endEpochDay = LocalDate.of(2022, 12, 31).toEpochDay();
        long randomDay = startEpochDay + RANDOM.nextInt((int) (endEpochDay - startEpochDay));
        return LocalDate.ofEpochDay(randomDay);
    }

    private String getRandomTunisiaRegion() {
        return TUNISIA_REGIONS[RANDOM.nextInt(TUNISIA_REGIONS.length)];
    }

    private float generateRandomPrice2020() {
        Random random = new Random();
        float minPrice = 200000f;
        float maxPrice = 300000f;
        float range = maxPrice - minPrice;
        float randomValue = minPrice + range * random.nextFloat();
        return randomValue;
    }
    private float generateRandomPrice2021() {
        Random random = new Random();
        float minPrice = 300000f;
        float maxPrice = 400000f;
        float range = maxPrice - minPrice;
        float randomValue = minPrice + range * random.nextFloat();
        return randomValue;
    }
    private float generateRandomPrice2022() {
        Random random = new Random();
        float minPrice = 300000f;
        float maxPrice = 600000f;
        float range = maxPrice - minPrice;
        float randomValue = minPrice + range * random.nextFloat();
        return randomValue;
    }



    public List<Announcement> houses = new ArrayList<>();


    public String createModelByDate(HousePricing h , String region , Date date){


// Split the data into input and output variables
        List<double[]> inputs = new ArrayList<>();
        List<Double> outputs = new ArrayList<>();

        for (Announcement row : this.houses) {
            if (row.getLocalisation().equals(region) ) {

                double[] inputRow = new double[3];

                inputRow[0] = row.getPool();
                inputRow[1] = row.getGarage();
                inputRow[2] = row.getAirConditioning();



                double   outputValue = row.getPrice();

                inputs.add(inputRow);
                outputs.add(outputValue);
            }
        }
        if(inputs.size() == 0 || outputs.size() == 0){ return  region + " nexiste pas";}
// Convert the data to arrays
        double[][] inputArray = new double[inputs.size()][inputs.get(0).length];
        double[] outputArray = new double[outputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            inputArray[i] = inputs.get(i);
            outputArray[i] = outputs.get(i);
        }

// Split the data into training and testing sets
     /*   int numTrainingExamples = (int) (inputArray.length * 0.8);
        double[][] trainingInputs = Arrays.copyOfRange(inputArray, 0, numTrainingExamples);
        double[] trainingOutputs = Arrays.copyOfRange(outputArray, 0, numTrainingExamples);
        double[][] testingInputs = Arrays.copyOfRange(inputArray, numTrainingExamples, inputArray.length);
        double[] testingOutputs = Arrays.copyOfRange(outputArray, numTrainingExamples, outputArray.length); */

        // Train a linear regression model
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        //  double[][] X = { { 1, 2 }, { 2, 4 }, { 3, 6 }, { 4, 8 } };
        // double[] Y = { 3, 6, 9, 12 };

        // Fit the model to the training data
        regression.newSampleData(outputArray, inputArray);

        // Print the coefficients of the linear equation
        double[] beta = regression.estimateRegressionParameters();


        // return "Int "+ beta[0]+"Coefficient 1"+beta[1]+"Coefficient 2"+beta[2]+"Coefficient 3"+beta[3];
        double price = beta[0] +(h.getPool() * beta[1]) +(h.getGarage() * beta[2]) +(h.getAirConditioning() * beta[3]) ;
        return region + "price = " + price;

    }










}


