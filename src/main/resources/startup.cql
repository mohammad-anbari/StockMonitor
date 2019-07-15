
CREATE KEYSPACE IF NOT EXISTS stocks
WITH REPLICATION = {
'class':'SimpleStrategy',
'replication_factor':1
};

CREATE TABLE IF NOT EXISTS stocks.stock(
                 stock_symbol text,
                 stock_date text,
                 stock_time text,
                 stock_price double,
                 primary key ((stock_symbol,stock_date),stock_time,stock_price));