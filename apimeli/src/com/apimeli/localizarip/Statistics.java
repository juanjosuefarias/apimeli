package com.apimeli.localizarip;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private List<Double> distances = new ArrayList<>();
    private double maxDistance = 0;
    private double minDistance = Double.MAX_VALUE;

    public void addDistance(double distance) {
        distances.add(distance);
        if (distance > maxDistance) {
            maxDistance = distance;
        }
        if (distance < minDistance) {
            minDistance = distance;
        }
    }

    public double calculateAverageDistance() {
        return distances.stream().mapToDouble(d -> d).average().orElse(0.0);
    }

    public void displayStatistics() {
        System.out.println("\nEstadísticas de uso:");
        System.out.println("Distancia más lejana: " + maxDistance + " km");
        System.out.println("Distancia más cercana: " + minDistance + " km");
        System.out.println("Distancia promedio: " + calculateAverageDistance() + " km");
    }
}