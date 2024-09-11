package org.example;

import org.example.enums.FuelType;
import org.example.enums.VehicleType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExpenseCalculatorImpl implements ExpenseCalculator {

    private final DistanceService distanceService;

    public ExpenseCalculatorImpl(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    private static final BigDecimal PETROL_COST_PER_KM = BigDecimal.valueOf(0.20);
    private static final BigDecimal DIESEL_COST_PER_KM = BigDecimal.valueOf(0.15);
    private static final BigDecimal AC_COST_PER_KM = BigDecimal.valueOf(0.10);
    private static final BigDecimal COST_FOR_EXTRA_PERSON = BigDecimal.valueOf(0.05);
    private static final BigDecimal BUS_PRICE = BigDecimal.valueOf(0.98);

    @Override
    public BigDecimal calculateExpense(VehicleType vehicleType, FuelType fuelType, String destination,
                                       Integer numberOfPeopleTravelling, Boolean isAirConditioningRequired) {

        BigDecimal cost = null;

        BigDecimal distance = BigDecimal.valueOf(distanceService.getDistanceFromBerlin(destination));

        switch (fuelType) {
            case FuelType.PETROL -> cost = distance.multiply(PETROL_COST_PER_KM);
            case FuelType.DIESEL -> cost = distance.multiply(DIESEL_COST_PER_KM);
        }

        if (isAirConditioningRequired) {
            cost = cost.add(AC_COST_PER_KM.multiply(distance));
        }

        if (numberOfPeopleTravelling > 5) {
            BigDecimal costForExtraPeople = getCostForExtraPeople(vehicleType, numberOfPeopleTravelling, distance);
            cost = cost.add(costForExtraPeople);
        }

        if (vehicleType == VehicleType.BUS) {
            cost = cost.multiply(BUS_PRICE);
        }

        return cost.setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getCostForExtraPeople(VehicleType vehicleType, Integer numberOfPeopleTravelling,
                                             BigDecimal distance) {

        int extraPeople = 0;

        if (vehicleType == VehicleType.CAR || vehicleType == VehicleType.SUV) {
            extraPeople = numberOfPeopleTravelling - VehicleType.CAR.getMacCapacity();
        } else if (numberOfPeopleTravelling > VehicleType.VAN.getMacCapacity() && vehicleType == VehicleType.VAN) {
            extraPeople = numberOfPeopleTravelling - VehicleType.VAN.getMacCapacity();
        } else if (numberOfPeopleTravelling > VehicleType.BUS.getMacCapacity() && vehicleType == VehicleType.BUS) {
            extraPeople = numberOfPeopleTravelling - VehicleType.BUS.getMacCapacity();
        }

        return COST_FOR_EXTRA_PERSON.multiply(distance).multiply(new BigDecimal(extraPeople));
    }
}
