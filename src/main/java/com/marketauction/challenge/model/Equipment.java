package com.marketauction.challenge.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * An equipment with <code>schedule</code> and <code>saleDetails</code>.
 */
public record Equipment(Schedule schedule, SaleDetails saleDetails) {

	/**
	 * Calculate the market and auction values based on the provided year.
	 */
	public MarketAuctionResult getMarketAuctionValues(Integer year) {
		if (schedule == null || saleDetails == null) {
			return MarketAuctionResult.createDefaultResult();
		}

		return schedule.getMarketAuctionValues(year, saleDetails.cost);
	}

	/**
	 * Sale details. It contains the cost.
	 */
	public record SaleDetails(BigDecimal cost) {
	}

	/**
	 * Schedule with the following attributes:
	 * <ul>
	 * <li>A set of years and ratios.</li>
	 * <li>Default ratio for market.</li>
	 * <li>Default ratio for auction.</li>
	 * </ul>
	 * 
	 */
	public record Schedule(Map<String, Ratios> years, BigDecimal defaultMarketRatio, BigDecimal defaultAuctionRatio) {

		/**
		 * Calculate and return the value based on the provided year and cost.
		 */
		public MarketAuctionResult getMarketAuctionValues(Integer year, BigDecimal cost) {
			if (cost == null) {
				return MarketAuctionResult.createDefaultResult();
			}

			if (years == null) {
				return calculateDefaultValue(cost);
			}

			var yearObj = years.get(year.toString());
			if (yearObj == null) {
				return calculateDefaultValue(cost);
			}

			return calculateValue(cost, yearObj.marketRatio(), yearObj.auctionRatio());
		}

		/**
		 * Calculate the value based on default ratios.
		 */
		private MarketAuctionResult calculateDefaultValue(BigDecimal cost) {
			if (defaultMarketRatio == null || defaultAuctionRatio == null) {
				return MarketAuctionResult.createDefaultResult();
			}

			return calculateValue(cost, defaultMarketRatio, defaultAuctionRatio);
		}

		/**
		 * Multiply the cost by the ratios.
		 */
		public MarketAuctionResult calculateValue(BigDecimal cost, BigDecimal marketRatio, BigDecimal auctionRatio) {
			return new MarketAuctionResult(cost.multiply(marketRatio), cost.multiply(auctionRatio));
		}

		/**
		 * Ratios for market and auction.
		 */
		public record Ratios(BigDecimal marketRatio, BigDecimal auctionRatio) {
		}
	}
}