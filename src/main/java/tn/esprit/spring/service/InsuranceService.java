package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Insurance;
import tn.esprit.spring.repository.InsurranceRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.IInsuranceService;

import java.util.List;

@Service
@Slf4j
public class InsuranceService implements IInsuranceService {

    @Autowired
    InsurranceRepository insurranceRepository;

    public void addInsurance (Insurance insurance){
        insurranceRepository.save(insurance);
    }
    public  void deleteInsurance (Long id ){
        insurranceRepository.deleteById(id);
    }
    public List<Insurance> ListInsurance(){
        List<Insurance> insurrances = insurranceRepository.findAll();
        return  insurrances;
    }
    public Insurance updateInsurance(Long id, Insurance insurance) {

        Insurance insurr = insurranceRepository.findByIdInsurrance(id);
        insurr.setInsurranceName(insurance.getInsurranceName());
        insurr.setAnnualPrice(insurance.getAnnualPrice());
        insurr.setDateInsurance(insurance.getDateInsurance());
        insurr.setDateExpiration(insurance.getDateExpiration());
        //insurr.setContrats(insurance.getContrats());
        insurr.setDescription(insurance.getDescription());
        insurranceRepository.save(insurr);
        return insurr;
    }
}
