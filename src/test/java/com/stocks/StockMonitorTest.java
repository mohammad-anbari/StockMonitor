package com.stocks;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.stocks.collect.domain.Stock;
import com.stocks.collect.repository.StockRepository;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class StockMonitorTest {

	@Autowired
	private WebTestClient testClientMonitor;
	@MockBean
	private StockRepository stockRepository;

	@Test
	public void collectStockData_shouldReturnStockData_whenPassingGOOG2() {
		Stock sampleStock = createSampleStock();
		when(stockRepository.findBySymbolAndDate(anyString(), anyString()))
				.thenReturn(Flux.just(sampleStock));
		testClientMonitor.get().uri("/monitor/stock/FB/2019-07-15")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$[0].symbol").isEqualTo(sampleStock.getSymbol())
				.jsonPath("$[0].price").isEqualTo(sampleStock.getPrice())
				.jsonPath("$[0].date").isEqualTo(sampleStock.getDate())
				.jsonPath("$[0].time").isEqualTo(sampleStock.getTime());
	}

	private Stock createSampleStock() {
		Stock stock = new Stock();
		stock.setSymbol("AAPL");
		stock.setDate("2019-07-15");
		stock.setTime("12:13:14");
		stock.setPrice(203.12);
		return stock;
	}
}
