package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.PromoCodeAnnouncement;
import tn.esprit.spring.repository.PromoCodeRepository;



@Service
public class PromoCodeService {
    @Autowired
    PromoCodeRepository promoCodeRepository;

    public PromoCodeAnnouncement addPromoCode(PromoCodeAnnouncement promoCodeAnnouncement) {
        return promoCodeRepository.save(promoCodeAnnouncement);
    }



}
