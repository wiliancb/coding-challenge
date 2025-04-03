package com.marketauction.challenge.model;

import java.math.BigDecimal;
import java.util.Map;

public record Equipment(Schedule schedule, SaleDetails saleDetails) {

	public MarketAuctionResult getMarketAuctionValues(Integer year) {
		if (schedule == null || saleDetails == null) {
			return MarketAuctionResult.createDefaultResult();
		}

		return schedule.getMarketAuctionValues(year, saleDetails.cost);
	}

	public record SaleDetails(BigDecimal cost) {
	}

	public record Schedule(Map<String, Ratios> years, BigDecimal defaultMarketRatio, BigDecimal defaultAuctionRatio) {

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

		private MarketAuctionResult calculateDefaultValue(BigDecimal cost) {
			if (defaultMarketRatio == null || defaultAuctionRatio == null) {
				return MarketAuctionResult.createDefaultResult();
			}

			return calculateValue(cost, defaultMarketRatio, defaultAuctionRatio);
		}

		public MarketAuctionResult calculateValue(BigDecimal cost, BigDecimal marketRatio, BigDecimal auctionRatio) {
			return new MarketAuctionResult(cost.multiply(marketRatio), cost.multiply(auctionRatio));
		}

		public record Ratios(BigDecimal marketRatio, BigDecimal auctionRatio) {
		}
	}
}