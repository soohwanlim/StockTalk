package config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// [A 담당] 외부 주가 API 호출 유틸리티
public class StockAPIClient {

    // 주요 종목 실패 시 Fallback 기본 가격 정보
    private static final Map<String, Integer> FALLBACK_PRICES = new HashMap<>();

    static {
        FALLBACK_PRICES.put("005930", 250000); // 삼성전자
        FALLBACK_PRICES.put("000660", 1500000); // SK하이닉스
        FALLBACK_PRICES.put("402340", 1000000); // SK스퀘어
        FALLBACK_PRICES.put("005935", 180000); // 삼성전자우
        FALLBACK_PRICES.put("005380", 600000); // 현대차
        FALLBACK_PRICES.put("373220", 380000); // LG에너지솔루션
        FALLBACK_PRICES.put("034020", 100000); // 두산에너빌리티
        FALLBACK_PRICES.put("329180", 600000); // HD현대중공업
        FALLBACK_PRICES.put("028260", 350000); // 삼성물산
        FALLBACK_PRICES.put("009150", 1000000); // 삼성전기
        FALLBACK_PRICES.put("000270", 160000); // 기아
        FALLBACK_PRICES.put("035720", 50000); // 카카오
    }

    /**
     * [A] 주어진 stockCode로 외부 API를 호출해 종가(int)를 반환합니다.
     * - HTTP GET 요청, 타임아웃 2초 (Connect Timeout & Read Timeout)
     * - 실패 시 Fallback 값 반환 (삼성전자: 250000, SK하이닉스: 1500000 ...)
     */
    public static int fetchClosePrice(String stockCode) {
        if (stockCode == null || stockCode.trim().isEmpty()) {
            return 0;
        }

        String ticker = stockCode.trim();
        // 한국 주식 코드(숫자로만 이루어진 경우)이고 접미사(.KS 또는 .KQ)가 없는 경우 기본적으로 KOSPI(.KS) 추가
        if (ticker.matches("\\d+") && !ticker.endsWith(".KS") && !ticker.endsWith(".KQ")) {
            ticker = ticker + ".KS";
        }

        // Yahoo Finance v8 chart API 호출 URL
        String urlString = "https://query1.finance.yahoo.com/v8/finance/chart/" + ticker + "?interval=1d&range=1d";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000); // 2초 연결 타임아웃
            conn.setReadTimeout(2000); // 2초 읽기 타임아웃
            // Yahoo Finance 서버가 봇을 필터링하므로 표준 브라우저 User-Agent 제공
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    String json = response.toString();

                    // 정규식을 사용하여 "regularMarketPrice":숫자 데이터 추출 (외부 JSON 라이브러리 의존성 없이 경량 처리)
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

        // 실패 또는 존재하지 않는 종목인 경우 Fallback 값 반환
        return FALLBACK_PRICES.getOrDefault(stockCode, 0);
    }
}
