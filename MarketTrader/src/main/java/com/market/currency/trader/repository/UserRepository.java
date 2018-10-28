package com.market.currency.trader.repository;

import com.market.currency.trader.model.User;

//import org.springframework.data.jpa.repository.JpaRepository;

//public interface UserRepository extends JpaRepository<User, Long>{
//@Repository

public interface UserRepository {
	User findById(String userId);
	
}
