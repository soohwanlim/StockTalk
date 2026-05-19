package model.dto;

// [A 담당] 종목 데이터 전달 객체
public class StockDTO {

    private String stockCode;
    private String stockName;
    private int currentPrice;

    // [A] TODO: 기본 생성자, 전체 필드 생성자 작성
    // [A] TODO: getter / setter 작성
    // [A] 기본 생성자
    public StockDTO() {
    }

    // [A] 전체 필드 생성자
    public StockDTO(String stockCode, String stockName, int currentPrice) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }

    // [A] getter / setter
    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    // toString()은 JList 표시용 - 완성 예시
    @Override
    public String toString() {
        return stockName + " (" + String.format("%,d", currentPrice) + "원)";
    }
}
