package org.example;

import org.example.enums.FuelType;
import org.example.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseCalculatorTest {

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private ExpenseCalculatorImpl expenseCalculator;

    private static final BigDecimal DISTANCE_TO_MUNICH = BigDecimal.valueOf(584);
    private static final BigDecimal PETROL_COST_PER_KM = BigDecimal.valueOf(0.2);
    private static final BigDecimal PETROL_AC_COST_PER_KM = BigDecimal.valueOf(0.3);
    private static final BigDecimal DIESEL_COST_PER_KM = BigDecimal.valueOf(0.15);
    private static final BigDecimal DIESEL_AC_COST_PER_KM = BigDecimal.valueOf(0.25);
    private static final BigDecimal COST_PER_EXTRA_PERSON = BigDecimal.valueOf(0.05);
    private static final BigDecimal BUS_PRICE = BigDecimal.valueOf(0.98);

    @Test
    void testCostForCarPetrol() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.CAR, FuelType.PETROL, "Munich",
                VehicleType.CAR.getMacCapacity(), false);

        BigDecimal costWithAC = expenseCalculator.calculateExpense(VehicleType.CAR, FuelType.PETROL, "Munich",
                VehicleType.CAR.getMacCapacity(), true);

        assertThat(cost).isEqualTo(DISTANCE_TO_MUNICH.multiply(PETROL_COST_PER_KM)
                .setScale(2, RoundingMode.HALF_EVEN));
        assertThat(costWithAC).isEqualTo(DISTANCE_TO_MUNICH.multiply(PETROL_AC_COST_PER_KM)
                .setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void testCostForCarDiesel() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.CAR, FuelType.DIESEL, "Munich",
                VehicleType.CAR.getMacCapacity(), false);

        BigDecimal costWithAC = expenseCalculator.calculateExpense(VehicleType.CAR, FuelType.DIESEL, "Munich",
                VehicleType.CAR.getMacCapacity(), true);

        assertThat(cost).isEqualTo(DISTANCE_TO_MUNICH.multiply(DIESEL_COST_PER_KM)
                .setScale(2, RoundingMode.HALF_EVEN));
        assertThat(costWithAC).isEqualTo(DISTANCE_TO_MUNICH.multiply(DIESEL_AC_COST_PER_KM)
                .setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void testCostForBusPetrol() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.BUS, FuelType.PETROL, "Munich",
                VehicleType.BUS.getMacCapacity(), false);

        BigDecimal costWithAC = expenseCalculator.calculateExpense(VehicleType.BUS, FuelType.PETROL, "Munich",
                VehicleType.BUS.getMacCapacity(), true);

        assertThat(cost).isEqualTo(DISTANCE_TO_MUNICH.multiply(PETROL_COST_PER_KM).multiply(BUS_PRICE)
                .setScale(2, RoundingMode.HALF_EVEN));
        assertThat(costWithAC).isEqualTo(DISTANCE_TO_MUNICH.multiply(PETROL_AC_COST_PER_KM).multiply(BUS_PRICE)
                .setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void testCostForBusDiesel() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.BUS, FuelType.DIESEL, "Munich",
                VehicleType.BUS.getMacCapacity(), false);

        BigDecimal costWithAC = expenseCalculator.calculateExpense(VehicleType.BUS, FuelType.DIESEL, "Munich",
                VehicleType.BUS.getMacCapacity(), true);

        assertThat(cost).isEqualTo(DISTANCE_TO_MUNICH.multiply(DIESEL_COST_PER_KM).multiply(BUS_PRICE)
                .setScale(2, RoundingMode.HALF_EVEN));
        assertThat(costWithAC).isEqualTo(DISTANCE_TO_MUNICH.multiply(DIESEL_AC_COST_PER_KM).multiply(BUS_PRICE)
                .setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void testCostForSuvPetrolWithACAndExtraPeople() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.SUV, FuelType.PETROL, "Munich",
                7, true);

        // maximum capacity of SUV is 5 people

        BigDecimal costForExtraPeople = COST_PER_EXTRA_PERSON.multiply(DISTANCE_TO_MUNICH)
                .multiply(new BigDecimal(2));

        assertThat(cost).isEqualTo(DISTANCE_TO_MUNICH.multiply(PETROL_AC_COST_PER_KM).add(costForExtraPeople)
                .setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void testCostForBusDieselWithACAndExtraPeople() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal costWithAC = expenseCalculator.calculateExpense(VehicleType.BUS, FuelType.DIESEL, "Munich",
                40, true);

        // maximum capacity of bus is 35 people

        BigDecimal costForExtraPeople = COST_PER_EXTRA_PERSON.multiply(DISTANCE_TO_MUNICH)
                .multiply(new BigDecimal(5));

        assertThat(costWithAC).isEqualTo((DISTANCE_TO_MUNICH.multiply(DIESEL_AC_COST_PER_KM).add(costForExtraPeople))
                .multiply(BUS_PRICE).setScale(2, RoundingMode.HALF_EVEN));
    }
}
