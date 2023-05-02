package tn.esprit.spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Insurance;
import tn.esprit.spring.entities.ResponseDTO;
import tn.esprit.spring.service.ScraperServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "api/user/client/Scrapping")
public class ScraperController {

    @Autowired
    ScraperServiceImpl scraperService;

    @PostMapping(path = "/")
    @ResponseBody
    public Set<ResponseDTO> getPopulatioByRegion() {
        ResponseDTO responseDTO = new ResponseDTO();
        return  scraperService.getPopulatioByRegion(responseDTO);
    }

    @GetMapping(path = "/averageHouseByZone/{region}")

    public Map<String,Double> averageHouseByZone(@PathVariable String region){
        return scraperService.calculeDistance(region);
    }
}