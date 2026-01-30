package com.revshop.dao;

import com.revshop.model.User;


public interface UserDAO {
	void registerUser(User user);
	
	User login(String email, String password);
	
	boolean changePassword(int userId, String oldHashedPwd, String newHashedPwd);
	
	boolean resetPassword(String email,
            String hashedAnswer,
            String newHashedPwd);
}
