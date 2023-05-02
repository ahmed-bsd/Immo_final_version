package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Insurance;
import tn.esprit.spring.repository.AnnoucementRepository;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.InsurranceRepository;
import tn.esprit.spring.serviceInterface.IContratService;

@Service
@Slf4j
public class ContratService implements IContratService {
    @Autowired
    private AnnoucementRepository annoucementRepository;
    @Autowired
    ContratRepository contratRepository;
    @Autowired
    InsurranceRepository insurranceRepository;


    public void addContratInsurance ( Long idInsurance , Long idAnnoncement , Contrat contrat){

    Insurance insurr = insurranceRepository.findByIdInsurrance(idInsurance);
    Announcement ann = annoucementRepository.findByIdAnnouncement(idAnnoncement);

    contrat.setInsurance(insurr);
    contrat.setAnnouncement(ann);
    contratRepository.save(contrat);
}

}
