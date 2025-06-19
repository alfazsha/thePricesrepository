package org.example;


import static spark.Spark.*;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MainClass {

    public static class CityRates {
        public double gold24;
        public double gold22;
        public double gold20;
        public double gold18;
        public double silver999;
        public double silver925;
        public double silver900;
        public double silver800German;
    }

    public static CityRates fetchRajkotRates() throws Exception {
        String url = "https://bullions.co.in/location/rajkot/";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        Elements rows = doc.select("table tr");

        CityRates rates = new CityRates();
        rates.gold24 = Double.parseDouble(rows.get(1).select("td").get(2).text().replace(",", "").trim());
        rates.gold22 = Double.parseDouble(rows.get(2).select("td").get(2).text().replace(",", "").trim());
        rates.gold20 = Double.parseDouble(rows.get(3).select("td").get(2).text().replace(",", "").trim());
        rates.gold18 = Double.parseDouble(rows.get(4).select("td").get(2).text().replace(",", "").trim());
        rates.silver999 = Double.parseDouble(rows.get(10).select("td").get(4).text().replace(",", "").trim());
        rates.silver925 = Double.parseDouble(rows.get(11).select("td").get(4).text().replace(",", "").trim());
        rates.silver900 = Double.parseDouble(rows.get(12).select("td").get(4).text().replace(",", "").trim());
        rates.silver800German = Double.parseDouble(rows.get(13).select("td").get(4).text().replace(",", "").trim());
        return rates;
    }

    public static void main(String[] args) {
        System.out.println("1");
        try {
                            port(Integer.parseInt(System.getenv("PORT")));
//            port(8092); // server on http://localhost:4567
            Gson gson = new Gson();

            get("/rates/rajkot", (req, res) -> {
                try {
                    CityRates rates = fetchRajkotRates();
                    res.type("application/json");
                    return rates;
                } catch (Exception e) {
                    res.status(500);
                    return "Error fetching rates: " + e.getMessage();
                }
            }, gson::toJson);
        }catch (Exception e){
            System.out.println("locha - "+e.toString());
        }

        System.out.println("here");
    }
}
