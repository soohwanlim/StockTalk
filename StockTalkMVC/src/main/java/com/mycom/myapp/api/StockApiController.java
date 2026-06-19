package com.mycom.myapp.api;

import com.mycom.myapp.model.dao.StockDAO;
import com.mycom.myapp.model.dto.StockDTO;
import com.mycom.myapp.config.StockAPIClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockApiController {

    private final StockDAO stockDAO;

    public StockApiController(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @GetMapping
    public List<StockDTO> getStocks() {
        List<StockDTO> stocks = stockDAO.selectAll();
        for (StockDTO stock : stocks) {
            int price = StockAPIClient.fetchClosePrice(stock.getStockCode());
            stock.setCurrentPrice(price);
            stockDAO.updatePrice(stock.getStockCode(), price);
        }
        return stocks;
    }
}
