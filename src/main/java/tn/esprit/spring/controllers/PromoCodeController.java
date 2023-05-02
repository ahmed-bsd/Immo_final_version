package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.PromoCodeAnnouncement;
import tn.esprit.spring.service.PromoCodeService;

@RestController
@RequestMapping("api/dashboard/promocode")
public class PromoCodeController {
   @Autowired
    PromoCodeService promoCodeService;

    @PostMapping("/add/promocode")
    @ResponseBody
    public PromoCodeAnnouncement addPromocode(@RequestBody PromoCodeAnnouncement promocode) {
        PromoCodeAnnouncement p= promoCodeService.addPromoCode(promocode);
        return p;
    }
}
