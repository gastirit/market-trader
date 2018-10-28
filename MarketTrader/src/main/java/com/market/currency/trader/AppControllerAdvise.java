package com.market.currency.trader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.market.currency.trader.controller.MarketTraderController;

@ControllerAdvice(assignableTypes={MarketTraderController.class})
public class AppControllerAdvise {

	@Autowired
	private MockDataConfiguration dataConfig;
}
