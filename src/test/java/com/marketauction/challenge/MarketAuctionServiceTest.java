package com.marketauction.challenge;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MarketAuctionServiceTest {

	@Autowired
	private MarketAuctionService service;

	@ParameterizedTest
	@CsvSource({ "67352,2007", "87964,2011" })
	void testGetEquipmentValues(Integer modelId, Integer year) throws Exception {
		var result = service.getMarketAuctionValues(modelId, year);

		System.out.println("Market value: " + result.market());
		System.out.println("Auction value: " + result.auction());
	}
}