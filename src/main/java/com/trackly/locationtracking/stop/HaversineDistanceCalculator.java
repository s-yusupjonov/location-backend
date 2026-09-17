package com.trackly.locationtracking.stop;

public final class HaversineDistanceCalculator {

    private static final double EARTH_RADIUS_METERS = 6_371_000;

    private HaversineDistanceCalculator() {
    }

    public static double distanceMeters(double latitude1, double longitude1, double latitude2, double longitude2) {
        double latitude1Rad = Math.toRadians(latitude1);
        double latitude2Rad = Math.toRadians(latitude2);
        double deltaLatitude = Math.toRadians(latitude2 - latitude1);
        double deltaLongitude = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
                + Math.cos(latitude1Rad) * Math.cos(latitude2Rad)
                * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_METERS * c;
    }
}
