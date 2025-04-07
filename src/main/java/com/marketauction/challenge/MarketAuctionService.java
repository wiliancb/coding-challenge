package com.marketauction.challenge;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketauction.challenge.model.Equipment;
import com.marketauction.challenge.model.MarketAuctionResult;

import jakarta.annotation.PostConstruct;

/**
 * Service responsible for calculating the market and auction values.
 */
@Service
public class MarketAuctionService {

	private Map<String, Equipment> equipments;

	private ObjectMapper objectMapper;

	public MarketAuctionService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Initializes a list of equipments that will act as a memory data store.
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		equipments = objectMapper.readValue(getClass().getResourceAsStream("/equipments.json"),
				objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Equipment.class));
	}

	/**
	 * Search the appropriate equipment and calculate the market and auction values
	 * based on the respective ratio.
	 * 
	 * @param modelId Model ID of the equipment.
	 * @param year    Schedule's year of the equipment.
	 * @return An object containing the market and auction values.
	 */
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