package com.stocks.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.collect.domain.Stock;
import com.stocks.collect.repository.StockRepository;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/monitor")
public class StockMonitor {
    private final StockRepository stockRepository;

    @Autowired
    public StockMonitor(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/stock/{symbol}/{interval}")
    public Flux<Stock> getStock(@PathVariable String symbol,
                                      @PathVariable String interval) {
        return  stockRepository.findBySymbolAndDate(symbol, interval);
    }

}
