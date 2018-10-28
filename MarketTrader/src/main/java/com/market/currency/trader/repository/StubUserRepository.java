package com.market.currency.trader.repository;

import org.springframework.stereotype.Component;

import com.market.currency.trader.model.User;

@Component
public class StubUserRepository implements UserRepository{

	@Override
	public User findById(String userId) {
		return new User();
		
	}
	
}
