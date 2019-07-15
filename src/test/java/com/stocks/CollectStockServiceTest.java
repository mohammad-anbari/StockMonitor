package com.stocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.stocks.collect.domain.Stock;
import com.stocks.collect.repository.StockRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectStockServiceTest {

	private static String baseUrlFinancial = "https://financialmodelingprep.com/api/v3";
	private WebTestClient testClientFinancial;
	@Autowired
	private StockRepository stockRepository;

	@Before
	public void setup() {
		testClientFinancial = WebTestClient
				.bindToServer()
				.baseUrl(baseUrlFinancial)
				.build();
	}

	@Test
	public void collectStockData_shouldReturnStockData_whenPassingFB() {
		testClientFinancial.get().uri("/stock/real-time-price/FB")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.symbol").isEqualTo("FB")
				.jsonPath("$.price").isNotEmpty();
	}

	@Test
	public void collectStockData_shouldReturnStockData_whenPassingAAPL() {
		testClientFinancial.get().uri("/stock/real-time-price/AAPL")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.symbol").isEqualTo("AAPL")
				.jsonPath("$.price").isNotEmpty();
	}

	@Test
	public void collectStockData_shouldReturnStockData_whenPassingGOOG() {
		testClientFinancial.get().uri("/stock/real-time-price/GOOG")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.symbol").isEqualTo("GOOG")
				.jsonPath("$.price").isNotEmpty();
	}

	@Test
	public void shouldSaveStockDataInEmbededCassandra() {
		final Stock sampleStock = createSampleStock();
		final Stock stock = stockRepository.save(sampleStock).block();

		assertNotNull(stock);
		assertEquals(sampleStock.getSymbol(), stock.getSymbol());
		assertEquals(sampleStock.getDate(), stock.getDate());
		assertEquals(sampleStock.getTime(), stock.getTime());
		assertEquals(sampleStock.getPrice(), stock.getPrice(), 2);
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
