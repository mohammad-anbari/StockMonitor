package com.stocks.collect.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.stocks.collect.domain.Stock;
import com.stocks.collect.repository.StockRepository;
import com.sun.tools.javac.util.List;
import reactor.core.publisher.Flux;

@Service
@EnableScheduling
public class CollectStockService {
    private static String baseUrl = "https://financialmodelingprep.com/api/v3";
    private static WebClient client = WebClient.create(baseUrl);
    private static final List<String> stockList = List.of("AAPL","FB","GOOG");
    private final StockRepository stockRepository;

    public CollectStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Scheduled(fixedRateString = "30000")
    public void run() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        stockList.forEach(s -> collectStockData(s,localDateTime));
    }

    private void collectStockData(String stockName, LocalDateTime localTime) {
        final Flux<Stock> stockFlux = client
                .get()
                .uri("/stock/real-time-price/" + stockName)
                .retrieve()
                .bodyToFlux(Stock.class);
        stockFlux.subscribe(stock -> {
            DateTimeFormatter timeFormatter = DateTimeFormatter
                    .ofPattern("HH:mm:ss");
            DateTimeFormatter dateFormatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd");
            stock.setTime(localTime.format(timeFormatter));
            stock.setDate(localTime.format(dateFormatter));
            stockRepository.save(stock).subscribe();
        });
    }
}
