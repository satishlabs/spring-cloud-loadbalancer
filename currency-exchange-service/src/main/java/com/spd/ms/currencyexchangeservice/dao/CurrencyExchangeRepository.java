package com.spd.ms.currencyexchangeservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spd.ms.currencyexchangeservice.entity.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{
	public CurrencyExchange findByFromAndTo(String from,String to);
}
