package org.example;

public interface DistanceService {

    default int getDistanceFromBerlin(String destination) {

        // below is a default/sample implementation of the method with hard coded values.
        // the actual implementation of this method will pull data from a database, file or HTTP service.
        return switch (destination) {
            case "Munich" -> 584;
            case "Hamburg" -> 289;
            case "Frankfurt" -> 545;
            case "Cologne" -> 576;
            default -> 0;
        };
    }
}
