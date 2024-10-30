package com.apimeli.localizarip;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeoInfoService {
    private static final String IP_API_URL = "https://ip2country.info/ip?"; // API de geolocalización
    private static final String COUNTRY_API_URL = "https://restcountries.com/v3.1/name/";
    private static final String CURRENCY_API_URL = "http://data.fixer.io/api/latest?access_key=YOUR_API_KEY"; // Reemplaza con tu clave de API
    private Statistics statistics;

    public GeoInfoService(Statistics statistics) {
        this.statistics = statistics;
    }

    public CountryInfo fetchData(String ip) {
        try {
            String countryCode = getCountryCode(ip);
            if (countryCode != null) {
                CountryInfo countryInfo = getCountryInfo(countryCode);
                statistics.addDistance(countryInfo.getDistanceFromBuenosAires());
                return countryInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCountryCode(String ip) throws Exception {
        URL url = new URL(IP_API_URL + ip);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = in.readLine();
        in.close();
        return response.split(",")[0]; // Suponiendo que el primer elemento es el código del país
    }

    private CountryInfo getCountryInfo(String countryCode) throws Exception {
        URL url = new URL(COUNTRY_API_URL + countryCode);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Gson gson = new Gson();
        JsonObject countryJson = gson.fromJson(response.toString(), JsonObject[].class)[0].getAsJsonObject();

        String name = countryJson.get("name").getAsJsonObject().get("common").getAsString();
        String isoCode = countryJson.get("cca2").getAsString();
        String[] officialLanguages = gson.fromJson(countryJson.get("languages"), String[].class);
        double distance = calculateDistanceFromBuenosAires(isoCode); // Implementa esta función según la lógica deseada

        String currency = countryJson.get("currencies").getAsJsonObject().entrySet().iterator().next().getValue().getAsJsonObject().get("name").getAsString();
        double exchangeRate = getCurrencyExchangeRate(currency);

        CountryInfo countryInfo = new CountryInfo(name, isoCode, officialLanguages, distance, currency, exchangeRate);
        return countryInfo;
    }

    private double getCurrencyExchangeRate(String currency) throws Exception {
        // Lógica para obtener la tasa de cambio
        URL url = new URL(CURRENCY_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = in.readLine();
        in.close();

        Gson gson = new Gson();
        JsonObject currencyJson = gson.fromJson(response, JsonObject.class);
        return currencyJson.getAsJsonObject("rates").get(currency).getAsDouble(); // Devuelve la tasa de cambio
    }

    private double calculateDistanceFromBuenosAires(String isoCode) {
        // Lógica para calcular la distancia desde Buenos Aires
        // Aquí podrías usar coordenadas o una base de datos de distancias
        return Math.random() * 10000; // Simulación
    }
}