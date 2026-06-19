package com.mycom.myapp.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockAPIClient {

    private static final Map<String, Integer> FALLBACK_PRICES = new HashMap<>();

    static {
        FALLBACK_PRICES.put("005930", 250000);
        FALLBACK_PRICES.put("000660", 1500000);
        FALLBACK_PRICES.put("402340", 1000000);
        FALLBACK_PRICES.put("005935", 180000);
        FALLBACK_PRICES.put("005380", 600000);
        FALLBACK_PRICES.put("373220", 380000);
        FALLBACK_PRICES.put("034020", 100000);
        FALLBACK_PRICES.put("329180", 600000);
        FALLBACK_PRICES.put("028260", 350000);
        FALLBACK_PRICES.put("009150", 1000000);
        FALLBACK_PRICES.put("000270", 160000);
        FALLBACK_PRICES.put("035720", 50000);
    }

    public static int fetchClosePrice(String stockCode) {
        if (stockCode == null || stockCode.trim().isEmpty()) {
            return 0;
        }

        String ticker = stockCode.trim();
        if (ticker.matches("\\d+") && !ticker.endsWith(".KS") && !ticker.endsWith(".KQ")) {
            ticker = ticker + ".KS";
        }

        String urlString = "https://query1.finance.yahoo.com/v8/finance/chart/" + ticker + "?interval=1d&range=1d";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    String json = response.toString();
                    Pattern pattern = Pattern.compile("\"regularMarketPrice\"\\s*:\\s*([0-9.]+)");
                    Matcher matcher = pattern.matcher(json);
                    if (matcher.find()) {
                        double price = Double.parseDouble(matcher.group(1));
                        return (int) Math.round(price);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching price for stockCode [" + stockCode + "]: " + e.getMessage());
        }

        return FALLBACK_PRICES.getOrDefault(stockCode, 0);
    }
}
