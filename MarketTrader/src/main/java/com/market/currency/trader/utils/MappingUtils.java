package com.market.currency.trader.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

public class MappingUtils {
	
	static public ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(String obj, Class<T> type) {
		try {
			return mapper.readValue(obj, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T convert(Object obj, Class<T> type) {
		try {
			
			if(obj instanceof com.market.currency.trader.to.TradeMessage && type == com.market.currency.trader.model.TradeMessage.class) {
				com.market.currency.trader.model.TradeMessage tm = new com.market.currency.trader.model.TradeMessage();
				com.market.currency.trader.to.TradeMessage origin = (com.market.currency.trader.to.TradeMessage)obj;
				
				if(StringUtils.hasText(origin.getAmountBuy())) {
					tm.setAmountBuy(new BigDecimal(origin.getAmountBuy()));
				}
				
				if(StringUtils.hasText(origin.getAmountSell())) {
					tm.setAmountSell(new BigDecimal(origin.getAmountSell()));
				}
				
				if(StringUtils.hasText(origin.getCurrencyFrom())) {
					tm.setCurrencyFrom(origin.getCurrencyFrom());
				}
				
				if(StringUtils.hasText(origin.getCurrencyTo())) {
					tm.setCurrencyTo(origin.getCurrencyTo());
				}
				
				if(StringUtils.hasText(origin.getOriginatingCountry())) {
					tm.setOriginatingCountry(origin.getOriginatingCountry());
				}
				
				if(StringUtils.hasText(origin.getRate())) {
					tm.setRate(new BigDecimal(origin.getRate()));
				}
				
				if(StringUtils.hasText(origin.getTimePlaced())) {
					String pattern = "dd-MMM-yy HH:mm:ss";
					SimpleDateFormat sdf =
					        new SimpleDateFormat(pattern, new Locale("en", origin.getOriginatingCountry()));
					
					tm.setTimePlaced(sdf.parse(origin.getTimePlaced()));
				}
				
				if(StringUtils.hasText(origin.getUserId())) {
					tm.setUserId(new Long(origin.getUserId()));
				}
				
				return (T) tm;
			}
			
		}  catch (Exception e) {
			
			if(e instanceof ParseException) {
				System.out.println("Date Parse Exception:" + e);
			}
		}
		
		return null;
	}
	
	
}
