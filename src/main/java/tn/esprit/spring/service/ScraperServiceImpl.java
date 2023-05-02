package tn.esprit.spring.service;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.HousePricing;
import tn.esprit.spring.entities.ResponseDTO;
import tn.esprit.spring.repository.HousingPricingRepository;
import tn.esprit.spring.repository.ResponseDTORepository;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ScraperServiceImpl  {
    @Autowired
    private HousingPricingRepository housingPricingRepository;
    @Autowired
    ResponseDTORepository responseDTORepository;





    //Reading data from property file to a list
    @Value("${website.urls}")
    private List<String> urls;


    public Set<ResponseDTO> getPopulatioByRegion(ResponseDTO responseDTO) {
        //Using a set here to only store unique elements
        Set<ResponseDTO> responseDTOS = new HashSet<>();
        //Traversing through the urls
        for (String url: urls) {

            if (url.contains("populationdata")) {
                //modifying the URL to include the region name
                String urlWithRegion = url ; //replace "region-name" with the actual region name
                //method to extract data from Ikman.lk
                extractDataFromIkman(responseDTOS, urlWithRegion);
            }

        }

        return responseDTOS;
    }


    private void extractDataFromIkman(Set<ResponseDTO> responseDTOS, String url) {
        try {
            //loading the HTML to a Document Object
            Document document = Jsoup.connect(url).get();
        //Selecting the element which contains the ad list
            Elements liste = document.select("tbody tr");
            for (Element row : liste) {
                ResponseDTO responseDTO = new ResponseDTO();

                // Extract region name from first <td> in row
                String regionName = row.select("td").first().text();
                responseDTO.setRegion(regionName);

                // Extract population and surface area from remaining <td> elements in row
                Elements dataCells = row.select("td.numbers");
                for (int i = 0; i < dataCells.size(); i++) {
                    Element cell = dataCells.get(i);
                    String cellText = cell.text();
                    if (cellText.contains("habitant")) {
                        // This is the population cell
                        String population = cellText.replaceAll("", "");
                        responseDTO.setPopulation(population);
                    } else {
                        // This is the surface area cell
                        String surfaceArea = cellText.replaceAll("", "");
                        responseDTO.setSuperficie(surfaceArea);
                    }
                }
                responseDTOS.add(responseDTO);
                responseDTORepository.save(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }





    public Map<String,Double> calculeDistance(String region ){
        Map<String, String> centers = new HashMap<String, String>();
        centers.put("tunis", "36.7997, 10.1797");
        centers.put("Ariana", "36.8625, 10.1956");
        centers.put("Ben Arous", "36.7477, 10.2278");
        centers.put("La Manouba", "36.8081, 9.8942");
        centers.put("Nabeul", "36.4556, 10.7314");
        centers.put("Zaghouan", "36.4014, 10.1436");
        centers.put("Bizerte", "37.2744, 9.8739");
        centers.put("Béja", "36.7311, 9.1831");
        centers.put("Jendouba", "36.5014, 8.7775");
        centers.put("Le Kef", "36.1694, 8.7089");
        centers.put("Siliana", "36.0842, 9.3658");
        centers.put("Kairouan", "35.6762, 10.1013");
        centers.put("Kasserine", "35.1667, 8.8333");
        centers.put("Sidi Bouzid", "35.0428, 9.485");
        centers.put("Sousse", "35.8288, 10.6401");
        centers.put("Monastir", "35.7864, 10.8119");
        centers.put("Mahdia", "35.5044, 11.0622");
        centers.put("Sfax", "34.7393, 10.7598");
        centers.put("Gabès", "33.8811, 10.0982");
        centers.put("Medenine", "33.3569, 10.4947");
        centers.put("Tataouine", "32.929, 10.4511");
        centers.put("Tozeur", "33.9183, 8.1336");
        centers.put("Kébili", "33.7031, 8.9692");
        centers.put("Gafsa", "34.425, 8.7847");
        centers.put("Djerba", "33.8750, 10.8583");

        String surface = responseDTORepository.findByRegion(region).get(0).getSuperficie();
        Integer surfaceToInteger = Integer.parseInt(surface.split(" ")[0]);
        float zoneNumber = (surfaceToInteger/2)/10;
        float latitude = Float.parseFloat(centers.get(region).split(",")[0]);
        float longitude = Float.parseFloat(centers.get(region).split(",")[1]);
        Map<String,Double> averagePrice = new HashMap<>();
        Integer l = 0;
        double tp = 0;
        for (int i = 0 ; i < Math.round(zoneNumber) ; i++ ) {
            for (HousePricing housePricing : housingPricingRepository.findByGovernorate(region)) {
                //if (housePricing.getLatitude() != null && housePricing.getLongitude() !=null){
                    if(calculateDistance(latitude , longitude , housePricing.getLatitude() ,housePricing.getLongitude()) <= i+1*10 && calculateDistance(latitude , longitude , housePricing.getLatitude() ,housePricing.getLongitude()) >= i*10){
                        if(housePricing.getPriceTND() != null){
                            tp+= housePricing.getPriceTND();
                            l++;
                        }
                }
                //}else {l=1;tp=0;}
            }
            if(l==0)
                l=1;
            averagePrice.put("Zone : " + i ,tp/l);
            l=0;
            tp=0;
        }
    return averagePrice;

    }



    private static final double EARTH_RADIUS = 6371; // in km

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        return distance;
    }

}