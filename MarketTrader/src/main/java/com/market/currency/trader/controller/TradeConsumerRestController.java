package com.market.currency.trader.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.currency.trader.MockDataConfiguration;
import com.market.currency.trader.manager.UserManager;
import com.market.currency.trader.to.TradeMessage;
import com.market.currency.trader.utils.MappingUtils;

@RestController
@RequestMapping("/api/trader")
public class TradeConsumerRestController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private MockDataConfiguration dataConfig;
	
	@PostMapping("/send")
	public ResponseEntity<Void> receiveTrade(@RequestBody TradeMessage tradeMessage) {
	
		if(tradeMessage == null) {
			logger.error("Trade Message is null");
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); 
		}
		
		if(!StringUtils.hasText(tradeMessage.getUserId())) {
			logger.error("User Id is null");
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); 
		}
		
		String result = userManager.findUser(tradeMessage.getUserId());
		
		if(!StringUtils.hasText(result) || (StringUtils.hasText(result) && !"OK".equalsIgnoreCase(result))) {
			logger.error("User Id not exists:" + tradeMessage.getUserId());
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		com.market.currency.trader.model.TradeMessage tm0 = MappingUtils.convert(tradeMessage, com.market.currency.trader.model.TradeMessage.class);
		
		if(tm0 == null) {
			logger.error("Conversation problem:" + tradeMessage.toString());
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}
		
		dataConfig.getMessageList().add(tm0);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
