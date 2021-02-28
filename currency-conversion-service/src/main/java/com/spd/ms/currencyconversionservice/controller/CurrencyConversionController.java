package com.spd.ms.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spd.ms.currencyconversionservice.entity.CurrencyConversion;
import com.spd.ms.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-conversion/{from}/{from}/{to}/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		//String URL = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,uriVariables);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		return new CurrencyConversion(
				currencyConversion.getId(), from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment()+" with Rest Template");

	}
	
	@GetMapping("/currency-conversion-feign/{from}/{from}/{to}/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFiegn(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {
		
		
		CurrencyConversion currencyConversion = currencyExchangeProxy.getExchangeValue(from, to);
		
		return new CurrencyConversion(
				currencyConversion.getId(), from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment()+" "+" feign ");

	}
}
