package com.market.currency.trader.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.currency.trader.repository.StubUserRepository;


@Service
public class UserManagerImpl implements UserManager{
	
	@Autowired
	private StubUserRepository repository;
	
	@Override
	public String findUser(String userId) {
		// If we work with any kind of DB
		// We can use spring data and repository
		// It is very important to check if user exists or not
		// in real time scenarios 
		repository.findById(userId);
		
		return "OK";
	}

}
