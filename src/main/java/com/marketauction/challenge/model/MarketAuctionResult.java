package com.marketauction.challenge.model;

import java.math.BigDecimal;

/**
 * Result of the <code>getMarketAuctionValues</code> operation.
 */
public record MarketAuctionResult(BigDecimal market, BigDecimal auction) {

	/**
	 * Creates a default response to be used when no matching result is found.
	 * 
	 * @return An object containing null values for market and auction.
	 */
	public static MarketAuctionResult createDefaultResult() {
		return new MarketAuctionResult(null, null);
	}
}