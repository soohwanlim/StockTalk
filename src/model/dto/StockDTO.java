package model.dto;

// [A 담당] 종목 데이터 전달 객체
public class StockDTO {

    private String stockCode;
    private String stockName;
    private int    currentPrice;

    // [A] TODO: 기본 생성자, 전체 필드 생성자 작성
    // [A] TODO: getter / setter 작성

    // toString()은 JList 표시용 - 완성 예시
    @Override
    public String toString() {
        return stockName + " (" + String.format("%,d", currentPrice) + "원)";
    }
}
