package com.market.currency.trader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.market.currency.trader.MockDataConfiguration;
import com.market.currency.trader.model.Greeting;
import com.market.currency.trader.model.HelloMessage;

@EnableScheduling
@Controller
public class MarketTraderController {

	@Autowired
    private SimpMessagingTemplate template;
	
	@Autowired
	private MockDataConfiguration dataConfig;

	
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
	
	@SendTo("/topic/trades")
    @Scheduled(fixedRate = 1000)
    public void trades() throws Exception {
        Thread.sleep(1000); // simulated delay
        this.template.convertAndSend("/topic/trades", dataConfig.getMessageList());
    }

}
