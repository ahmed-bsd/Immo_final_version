package tn.esprit.spring.entities;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.List;

public class HousePricePrediction {
    // train the model
    OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

    // input data for training the model
    private List<HousePricing> housePricingList;

    // constructor
    public HousePricePrediction(List<HousePricing> housePricingList) {
        this.housePricingList = housePricingList;
    }

    // method to train the linear regression model
    public void trainModel() {
        // add a null check
        if (housePricingList == null) {
            // handle null case here
            return;
        }

        // define input variables and response variable
        double[][] input = new double[housePricingList.size()][5];
        double[] response = new double[housePricingList.size()];

        for (int i = 0; i < housePricingList.size(); i++) {
            HousePricing house = housePricingList.get(i);
            input[i][0] = house.getDistanceToCapital();
            input[i][1] = house.getGarage();
            input[i][2] = house.getBeachView();
            input[i][3] = house.getMountainView();
            input[i][4] = house.getPool();

            response[i] = house.getPriceTND();
        }

        // add the input and response arrays to the regression object
        regression.newSampleData(response, input);

        // estimate the regression parameters
        double[] beta = regression.estimateRegressionParameters();

        // print regression coefficients
        System.out.println("Regression coefficients: ");
        for (int i = 0; i < beta.length; i++) {
            System.out.println("beta" + i + ": " + beta[i]);
        }

    }

    // method to predict the price of a house given its features
    public double predictPrice(HousePricing house) {
        double[] beta = regression.estimateRegressionParameters();
        double prediction = beta[0] * house.getDistanceToCapital()
                + beta[1] * house.getGarage()
                + beta[2] * house.getBeachView()
                + beta[3] * house.getMountainView()
                + beta[4] * house.getPool();
        return prediction;
    }


}
