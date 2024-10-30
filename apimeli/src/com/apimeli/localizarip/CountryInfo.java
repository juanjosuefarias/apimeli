package com.apimeli.localizarip;

import java.util.Arrays;

public class CountryInfo {
    private String name;
    private String isoCode;
    private String[] officialLanguages;
    private double distanceFromBuenosAires;
    private String currency;
    private double currencyExchangeRate;

    public CountryInfo(String name, String isoCode, String[] officialLanguages, double distanceFromBuenosAires, String currency, double currencyExchangeRate) {
        this.name = name;
        this.isoCode = isoCode;
        this.officialLanguages = officialLanguages;
        this.distanceFromBuenosAires = distanceFromBuenosAires;
        this.currency = currency;
        this.currencyExchangeRate = currencyExchangeRate;
    }

    public double getDistanceFromBuenosAires() {
        return distanceFromBuenosAires;
    }

    @Override
    public String toString() {
        return String.format("País: %s (%s)\nISO Code: %s\nIdiomas: %s\nMoneda: %s (1 %s = %.4f U$S)\nDistancia estimada: %.2f km",
                name, name.toLowerCase(), isoCode, Arrays.toString(officialLanguages), currency, currency, currencyExchangeRate, distanceFromBuenosAires);
    }
}
