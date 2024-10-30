package com.apimeli.localizarip;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Statistics statistics = new Statistics();
        
        while (true) {
            System.out.print("Ingrese la dirección IP (o 'salir' para terminar): ");
            String ip = scanner.nextLine();
            if (ip.equalsIgnoreCase("salir")) {
                break;
            }

            GeoInfoService geoInfoService = new GeoInfoService(statistics);
            CountryInfo countryInfo = geoInfoService.fetchData(ip);
            if (countryInfo != null) {
                System.out.println(countryInfo);
            } else {
                System.out.println("No se pudo obtener información para la IP: " + ip);
            }
        }
        
        scanner.close();
        statistics.displayStatistics();
    }
}
