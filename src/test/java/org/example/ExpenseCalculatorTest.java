package org.example;

import org.example.enums.FuelType;
import org.example.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseCalculatorTest {

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private ExpenseCalculatorImpl expenseCalculator;

    private static final BigDecimal distanceToMunich = BigDecimal.valueOf(584);
    private static final BigDecimal petrolCostPerKm = BigDecimal.valueOf(0.2);
    private static final BigDecimal petrolACCostPerKm = BigDecimal.valueOf(0.3);
    private static final BigDecimal dieselCostPerKm = BigDecimal.valueOf(0.15);
    private static final BigDecimal dieselACCostPerKm = BigDecimal.valueOf(0.25);

    @Test
    void testCostForCarPetrol() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.CAR, FuelType.PETROL, "Munich",
                5, false);

        assertThat(cost).isEqualTo(distanceToMunich.multiply(petrolCostPerKm));
    }

    @Test
    void testCostForCarDiesel() {
        when(distanceService.getDistanceFromBerlin("Munich")).thenReturn(584);

        BigDecimal cost = expenseCalculator.calculateExpense(VehicleType.CAR, FuelType.DIESEL, "Munich",
                5, false);

        assertThat(cost).isEqualTo(distanceToMunich.multiply(dieselCostPerKm));
    }
}
