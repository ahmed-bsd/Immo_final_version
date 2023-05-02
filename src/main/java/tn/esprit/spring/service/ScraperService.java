package tn.esprit.spring.service;

import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.ResponseDTO;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScraperService {
    //permet de lire les donnée a partir du fichier application.property en une liste
    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;


    public Set<ResponseDTO> getHomeByModel(String vehicleModel) {
        //Using a set here to only store unique elements
        Set<ResponseDTO> responseDTOS = new HashSet<>();

        //on va parcourir les urls
        for (String url: urls) {

            if (url.contains("mubawab")) {
                //method to extract data from Mubawb.tn
                extractDataFromMubawb(responseDTOS, url + vehicleModel);
            }

        }

        return responseDTOS;
    }

    private void extractDataFromMubawb(Set<ResponseDTO> responseDTOS, String url) {
        try {
            //loading the HTML to a Document Object
            Document document = Jsoup.connect(url).get();
          //Selecting the element which contains the ad list
            Element element = document.getElementsByClass("ulListing").first();
            //getting all the <a> tag elements inside the list-       -3NxGO class
            Elements elements = element.getElementsByTag("a");

            for (Element ads: elements) {

                ResponseDTO responseDTO = new ResponseDTO();

                if (StringUtils.isNotEmpty(ads.attr("href"))) {
                    //mapping data to our model class
                   responseDTO.setTitle(ads.attr("title"));
                    responseDTO.setUrl("https://www.mubawab.tn"+ ads.attr("href"));
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
