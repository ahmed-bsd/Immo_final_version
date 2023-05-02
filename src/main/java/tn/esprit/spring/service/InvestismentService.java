package tn.esprit.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Investisment;
import tn.esprit.spring.repository.InvestismentRepository;
import tn.esprit.spring.serviceInterface.IinvestismentService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InvestismentService implements IinvestismentService {

    InvestismentRepository investismentRepository;


    public String estimatePrice(Investisment inv) {
        String category=inv.getCategory();
        String city=inv.getCity();
        String region=inv.getRegion();
        String type=inv.getType();
        String roomsCount= inv.getRoomsCount();
        List<Investisment> investisments = investismentRepository.findInvestismentByCategoryAndCityAndRegion(category,city,region,type);
        System.out.println(investisments);
        List<String> data=new ArrayList<>();
        if (category == "Appartements" || category=="Maisons et Villas") {

            float bienVenteMax = investismentRepository.findMaxInvestA(roomsCount,category, city, region, type);
            String max = String.format("%.0f", bienVenteMax);
            float bienMin = investismentRepository.findMinInvestA(roomsCount,category,city,region,type);
            String min = String.format("%.0f", bienMin);
            float moyenne = investismentRepository.averageA(roomsCount,category,city,region,type);
            String moy = String.format("%.0f", moyenne);
            System.out.println("max :"  +max+"\n min :"  +min+"\nmoyenne :"  +moy);
            return " max prices found :"  +max+"\n lowest price is :"  +min+"\n to buy a property you must have about :"  +moy;

        }else{
            float bienVenteMax = investismentRepository.findMaxInvest(category, city, region, type);
            String max = String.format("%.0f", bienVenteMax);
            float bienMin = investismentRepository.findMinInvest(category,city,region,type);
            String min = String.format("%.0f", bienMin);
            float moyenne = investismentRepository.average(category,city,region,type);
            String moy = String.format("%.0f", moyenne);

            System.out.println("max :"  +max+"\n min :"  +min+"\nmoyenne :"  +moy);
            return "For terrains : \nmax prices found :"  +max+"\n lowest price is :"  +min+"\n to buy a property you must have about :"  +moy;

        }

    }


    public String finCalculator(float Propertyprice,  int loanTermInYears, double autoFinanced) {
        //double Propertyprice = investisment.getPrice();
        List<Investisment> less3Prices=investismentRepository.findLessPrices(Propertyprice);
        List<String> top3 = new ArrayList<>();
        double tauxIntert = 7.96;
        double tauxInteretMensuel =( tauxIntert / 12)/100;
        //exple sur 5 annee ==> 5*12 pour avoir le nbr de mois
        int numberOfPayments = loanTermInYears * 12;
        double resteApayer = Propertyprice - autoFinanced;
        //La formule permettant de connaître ses mensualités:
        // [ (resteApayer* tauxInteretMensuel *(1+tauxInteretMensuel)^nbr de mois ] / (1+tauxInteretMensuel)^nbr de mois -1
        double numerator = tauxInteretMensuel * Math.pow(1 + tauxInteretMensuel, numberOfPayments);
        double denominator = Math.pow(1 + tauxInteretMensuel, numberOfPayments) - 1;

        for (Investisment i : less3Prices){
            double propPrice=i.getPrice();
            double resteApayer1 = propPrice - autoFinanced;
            double numerator1 = tauxInteretMensuel * Math.pow(1 + tauxInteretMensuel, numberOfPayments);
            double denominator2 = Math.pow(1 + tauxInteretMensuel, numberOfPayments) - 1;
            double cal = resteApayer1 * (numerator1 / denominator2);
           // top3.add(cal);
            top3.add("\n #\n" +"Region :"+i.getRegion()+"\nPrice :"+ i.getPrice()+"\nCategory :"+i.getCategory()+"\nRooms :"+i.getRoomsCount()+"\nsuperficie :"+i.getSize() + "\n --> Le montant mensuel de votre prêt est de: " + resteApayer1 + " dinars"); // ajouter le calcul de prêt pour chaque maison à la liste

        }
        return "The monthly amount of your loan is : \n" +resteApayer * (numerator / denominator)
                +"\n the top three properties where you can invest : \n"+top3;

    }

    public List <Investisment> choiceInvest(Investisment inv){
        //List<Investisment> invests = investismentRepository.findAll();
        String category=inv.getCategory();
        String city=inv.getCity();
        String region=inv.getRegion();
        String type=inv.getType();

        List<Investisment> investisments = investismentRepository.findInvestismentByCategoryAndCityAndRegion(category,city,region,type);



        return investisments;
    }




}
