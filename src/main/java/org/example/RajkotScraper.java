package org.example;

//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Hello, World!");
//    }
//}

// Requires: org.jsoup:jsoup:1.15.4

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RajkotScraper {

    public static class CityRates {
        public double gold24;// ₹ per 10 g 24K gold
        public double gold22;
        public double gold20;
        public double gold18;
        public double silver999; // ₹ per kg silver
        public double silver925;
        public double silver900;
        public double silver800German;

    }

    public CityRates fetchRajkotRates() throws Exception {
        String url = "https://bullions.co.in/location/rajkot/";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible)")
                .timeout(10000)
                .get();

//        System.out.println(doc);

        Elements rows = doc.select("table tr");

        System.out.println(rows);
        String gold24kText = rows.get(1).select("td").get(2).text();
        String gold22kText = rows.get(2).select("td").get(2).text();
        String gold20kText = rows.get(3).select("td").get(2).text();
        String gold18kText = rows.get(4).select("td").get(2).text();
        String silverKgText999 = rows.get(10).select("td").get(4).text();
        String silverKgText925 = rows.get(11).select("td").get(4).text();
        String silverKgText900 = rows.get(12).select("td").get(4).text();
        String silverKgText800 = rows.get(13).select("td").get(4).text();



        System.out.println(gold24kText + " -- "+silverKgText999);

        CityRates rates = new CityRates();
        rates.gold24 = Double.parseDouble(gold24kText.replace(",", "").trim());
        rates.gold22 = Double.parseDouble(gold22kText.replace(",", "").trim());
        rates.gold20 = Double.parseDouble(gold20kText.replace(",", "").trim());
        rates.gold18 = Double.parseDouble(gold18kText.replace(",", "").trim());
        rates.silver999 = Double.parseDouble(silverKgText999.replace(",", "").trim());
        rates.silver925 = Double.parseDouble(silverKgText925.replace(",", "").trim());
        rates.silver900 = Double.parseDouble(silverKgText900.replace(",", "").trim());
        rates.silver800German = Double.parseDouble(silverKgText800.replace(",","").trim());
        return rates;
    }

    public static void main(String[] args) throws Exception {
        CityRates r = new RajkotScraper().fetchRajkotRates();
        System.out.printf("Rajkot Gold 24 (₹/10g): %.2f\n", r.gold24);
        System.out.printf("Rajkot Gold 22 (₹/10g): %.2f\n", r.gold22);
        System.out.printf("Rajkot Gold 20 (₹/10g): %.2f\n", r.gold20);
        System.out.printf("Rajkot Gold 18 (₹/10g): %.2f\n", r.gold18);

        System.out.printf("Rajkot Silver 999 (₹/kg): %.2f\n", r.silver999);
        System.out.printf("Rajkot Silver 925 (₹/kg): %.2f\n", r.silver925);
        System.out.printf("Rajkot Silver 900 (₹/kg): %.2f\n", r.silver900);
        System.out.printf("Rajkot Silver 800 german (₹/kg): %.2f\n", r.silver800German);


    }
}
