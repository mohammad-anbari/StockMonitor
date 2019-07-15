package com.stocks.collect.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.stocks.collect.domain.Stock;
import reactor.core.publisher.Flux;

@Repository
public interface StockRepository extends ReactiveCassandraRepository<Stock, UUID> {

    Flux<Stock> findBySymbolAndDate(String symbol, String interval);
}
