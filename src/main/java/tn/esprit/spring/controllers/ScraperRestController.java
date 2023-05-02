package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.ResponseDTO;
import tn.esprit.spring.service.ScraperService;

import java.util.Set;

@RestController
@RequestMapping(path = "api/user/client/scrapper")
public class ScraperRestController {
    @Autowired
    ScraperService scraperService;

    @GetMapping (path = "/{Model}")
    public Set<ResponseDTO> getHomeByModel(@PathVariable String Model) {
        return  scraperService.getHomeByModel(Model);
    }
}
