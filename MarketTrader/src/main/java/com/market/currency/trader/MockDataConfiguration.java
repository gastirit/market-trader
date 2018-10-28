package com.market.currency.trader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.market.currency.trader.model.TradeMessage;

@Component
public class MockDataConfiguration {

	private List<TradeMessage> messageList = new ArrayList<TradeMessage>();

	public List<TradeMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<TradeMessage> messageList) {
		this.messageList = messageList;
	}
	
}
