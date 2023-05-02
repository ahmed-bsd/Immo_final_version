package tn.esprit.spring.entities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Prediction {


    public List<HousePricing> houses = new ArrayList<>();
    public List<Announcement> announcements = new ArrayList<>();

    public Prediction(List<HousePricing> houses, int houseType) {
        if (houseType == 1) {
            this.houses = houses;
        }
    }

    public Prediction(List<Announcement> announcements, String announcementType) {
        if ("announcement".equals(announcementType)) {
            this.announcements = announcements;
        }
    }

    public String createModel(HousePricing h , String region){


// Split the data into input and output variables
        List<double[]> inputs = new ArrayList<>();
        List<Double> outputs = new ArrayList<>();

        for (HousePricing row : this.houses) {
            if (row.getGovernorate().equals(region) ) {

                double[] inputRow = new double[3];

                inputRow[0] = row.getPool();
                inputRow[1] = row.getGarage();
                inputRow[2] = row.getAirConditioning();

                double outputValue = 0;
                if (row.getPriceTND() != null) {
                    outputValue = row.getPriceTND();
                }
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











    public double createModelByDate(Announcement h , String region , int year ){


// Split the data into input and output variables
        List<double[]> inputs = new ArrayList<>();
        List<Double> outputs = new ArrayList<>();

        for (Announcement row : this.announcements) {
            if (row.getLocalisation().equals(region) && row.getDate().getYear() == year ) {

                double[] inputRow = new double[3];

                inputRow[0] = row.getPool();
                inputRow[1] = row.getGarage();
                inputRow[2] = row.getAirConditioning();


                double   outputValue = row.getPrice();

                inputs.add(inputRow);
                outputs.add(outputValue);
            }
        }
        if(inputs.size() < 4 || outputs.size() < 4 ){ return  0;}
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
      //  return region + "price = " + price;
        return price;
    }

    public ResultPriductionOnFuture predictOnFuture (Announcement ann , int year , String region){

        List<Double> listePrice = new ArrayList<>();
        int[] listeYears = new int[10];
        for (int i=2020; i <= 2022 ; i++){
           double testYear =  createModelByDate(ann,region,i);
            if (testYear != 0){
            listeYears[i-2020] = i;
            listePrice.add(testYear) ;}
        }
        double priceFuture = 0 ;
        for (int j = 2023 ; j<= year ; j++){
            priceFuture = regression(listePrice , listeYears, j);
            listePrice.add(priceFuture) ;
            listeYears[j-2020] = j;


        }
        DecimalFormat df = new DecimalFormat("#.####");
        String formattedNumber = df.format(priceFuture);
        ResultPriductionOnFuture RPOF = new ResultPriductionOnFuture(year , region , priceFuture);
        return RPOF;

    }

    public  double regression(List<Double> listePrice , int[] listeYears , int year ){
        SimpleRegression regression = new SimpleRegression();
        int i=0;
        for (Double p : listePrice ){
            regression.addData(listeYears[i],p);
            i++;
        }
        double predictedPrice = regression.predict(year);
        return predictedPrice ;

    }
}