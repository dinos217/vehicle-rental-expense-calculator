package org.example.enums;

public enum VehicleType {

    CAR(5),
    SUV(5),
    VAN(9),
    BUS(35);

    private final int maxCapacity;

    VehicleType(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMacCapacity() {
        return maxCapacity;
    }
}
