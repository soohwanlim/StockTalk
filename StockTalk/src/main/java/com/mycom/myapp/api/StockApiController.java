package com.mycom.myapp.api;

import com.mycom.myapp.model.dao.StockDAO;
import com.mycom.myapp.model.dto.StockDTO;
import com.mycom.myapp.config.StockAPIClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockApiController {

    private final StockDAO stockDAO;

    public StockApiController(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @GetMapping
    public Map<String, Object> getStocks(@RequestParam(required = false) String search,
                                         @RequestParam(defaultValue = "1") int page) {
        search = (search == null) ? "" : search.trim();
        page = Math.max(page, 1);
        int limit = 10;
        int offset = (page - 1) * limit;
        List<StockDTO> stocks = stockDAO.selectStocks(search, offset, limit);
        int totalCount = stockDAO.countStocks(search);
        for (StockDTO stock : stocks) {
            int price = StockAPIClient.fetchClosePrice(stock.getStockCode());
            stock.setCurrentPrice(price);
            stockDAO.updatePrice(stock.getStockCode(), price);
        }
        return Map.of("stocks", stocks, "totalCount", totalCount);
    }
}
