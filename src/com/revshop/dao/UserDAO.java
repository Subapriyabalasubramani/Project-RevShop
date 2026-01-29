package com.revshop.dao;

import com.revshop.model.User;


public interface UserDAO {
	void registerUser(User user);
	
	User login(String email, String password);
}
