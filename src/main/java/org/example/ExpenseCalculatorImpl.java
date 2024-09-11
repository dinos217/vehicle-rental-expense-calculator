package org.example;

import org.example.enums.FuelType;
import org.example.enums.VehicleType;

import java.math.BigDecimal;

public class ExpenseCalculatorImpl implements ExpenseCalculator {

    private final DistanceService distanceService;

    public ExpenseCalculatorImpl(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    private final BigDecimal petrolCostPerKm = BigDecimal.valueOf(0.2);
    private final BigDecimal dieselCostPerKm = BigDecimal.valueOf(0.15);

    @Override
    public BigDecimal calculateExpense(VehicleType vehicleType, FuelType fuelType, String destination,
                                       Integer numberOfPeopleTravelling, Boolean isAirConditioningRequired) {

        BigDecimal cost = null;

        BigDecimal distance = BigDecimal.valueOf(distanceService.getDistanceFromBerlin(destination));

        switch (fuelType) {
            case FuelType.PETROL -> cost = distance.multiply(petrolCostPerKm);
            case FuelType.DIESEL -> cost = distance.multiply(dieselCostPerKm);
        }

        return cost;
    }
}
