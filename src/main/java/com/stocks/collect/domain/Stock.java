package com.stocks.collect.domain;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("stock")
public class Stock implements Serializable {

    @PrimaryKeyColumn(name = "stock_symbol", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String symbol;

    @PrimaryKeyColumn(name = "stock_date", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String date;

    @PrimaryKeyColumn(name = "stock_time", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    private String time;

    @PrimaryKeyColumn(name = "stock_price", type = PrimaryKeyType.CLUSTERED, ordinal = 3)
    private double price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
