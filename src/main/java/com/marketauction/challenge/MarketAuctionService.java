package com.marketauction.challenge;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketauction.challenge.model.Equipment;
import com.marketauction.challenge.model.MarketAuctionResult;

import jakarta.annotation.PostConstruct;

@Service
public class MarketAuctionService {

	private Map<String, Equipment> equipments;

	private ObjectMapper objectMapper;

	public MarketAuctionService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@PostConstruct
	public void init() throws Exception {
		equipments = objectMapper.readValue(getClass().getResourceAsStream("/equipments.json"),
				objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Equipment.class));
	}

	public MarketAuctionResult getMarketAuctionValues(Integer modelId, Integer year) {
		if (modelId == null || year == null) {
			return MarketAuctionResult.createDefaultResult();
		}

		var equipment = equipments.get(modelId.toString());
		if (equipment == null) {
			return MarketAuctionResult.createDefaultResult();
		}

		return equipment.getMarketAuctionValues(year);
	}
}