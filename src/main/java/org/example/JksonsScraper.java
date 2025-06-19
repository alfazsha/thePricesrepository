package org.example;
//




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JksonsScraper {
    public static class CityRates {
        public double gold;   // ₹ per unit (likely per gram or per 10g)
        public double silver;
    }

    public CityRates fetchRates() throws Exception {
        String url = "http://www.jksons.in/LiveRates.html";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10_000)
                .get();

        // The page includes a table-like block with SYMBOL and RATE values
        Elements lines = doc.select("body").first().text().split("\\b").length > 0
                ? doc.select("body").first().select("p, span, div")
                : doc.select("body").first().select("*");

        System.out.println("here - "+lines);

        double gold = 0, silver = 0;
        // Let's scan for words "GOLD" and "SILVER" and read the next numeric token
        String bodyText = doc.body().text();
        String[] tokens = bodyText.split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            if ("GOLD".equalsIgnoreCase(tokens[i]) && i + 1 < tokens.length) {
                gold = Double.parseDouble(tokens[i + 1]);
            }
            if ("SILVER".equalsIgnoreCase(tokens[i]) && i + 1 < tokens.length) {
                silver = Double.parseDouble(tokens[i + 1]);
            }
        }

        CityRates rates = new CityRates();
        rates.gold = gold;
        rates.silver = silver;
        return rates;
    }

    public static void main(String[] args) throws Exception {
        try {
            CityRates rates = new JksonsScraper().fetchRates();
            System.out.printf("Gold: ₹%.2f\nSilver: ₹%.2f\n", rates.gold, rates.silver);
        } catch (Exception e) {
            System.out.println("aya - "+e.toString());
//            throw new RuntimeException(e);
        }
    }
}





//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//import java.util.concurrent.ExecutionException;
//
//public class JksonsScraper {
//    public static class CityRates {
//        public double gold;   // ₹ per unit (likely grams)
//        public double silver;
//    }
//
//    public CityRates fetchRajkotRates() throws Exception {
//        String url = "http://www.jksons.in/LiveRates.html";
//        Document doc = Jsoup.connect(url)
//                .userAgent("Mozilla/5.0")
//                .timeout(10000)
//                .get();
//
//        // Locate element containing "SYMBOL RATE" section
//        Element liveRatesElem = doc.selectFirst("body");  // we'll parse lines manually
//
//        assert liveRatesElem != null;
//        String text = liveRatesElem.text();
//        double gold = 0, silver = 0;
//        System.out.println("this - "+text);
////        try{
////        // Find GOLD and SILVER lines
////
////        for (String line : text.split("\\s{2,}")) {
////            if (line.startsWith("GOLD")) {
////                String[] parts = line.split(" ");
////                gold = Double.parseDouble(parts[1]);
////            }
////            if (line.startsWith("SILVER")) {
////                String[] parts = line.split(" ");
////                silver = Double.parseDouble(parts[1]);
////            }
////        }}catch (Exception e){
////            System.out.println(e.getMessage());
////        }
//
//        CityRates r = new CityRates();
//        r.gold = gold;
//        r.silver = silver;
//        return r;
//    }
//
//    public static void main(String[] args) throws Exception {
////        System.out.println("hello");
//
//        try {
//            CityRates r = new JksonsScraper().fetchRajkotRates();
//            System.out.printf("Rajkot Gold: %.2f\n", r.gold);
//            System.out.printf("Rajkot Silver: %.2f\n", r.silver);
//        } catch (Exception e) {
//            System.out.println("here - "+e.toString());
////            throw new RuntimeException(e);
//        }
//    }
//}
//
