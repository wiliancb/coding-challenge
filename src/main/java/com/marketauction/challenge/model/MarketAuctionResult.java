package com.marketauction.challenge.model;

import java.math.BigDecimal;

public record MarketAuctionResult(BigDecimal market, BigDecimal auction) {

	public static MarketAuctionResult createDefaultResult() {
		return new MarketAuctionResult(null, null);
	}
}