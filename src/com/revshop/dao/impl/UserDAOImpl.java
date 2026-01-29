package com.revshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revshop.config.DBConnection;
import com.revshop.dao.UserDAO;
import com.revshop.model.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public void registerUser(User user) {
		String sql = "INSERT INTO USERS (USER_ID, NAME, EMAIL, PASSWORD, ROLE, BUSINESSNAME)"
				+ "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
		Connection con = null;
        PreparedStatement ps = null;
		try{
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			ps.setString(5, user.getBusinessName());
			
			ps.executeUpdate();  //used to execute the insert statement
			System.out.println("Registration successful!");
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

	}
	
	@Override
	public User login(String email, String password){
		String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD =?";
		Connection con = null;
        PreparedStatement ps = null;
		try{
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
			
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				User user = new User();
				user.setUserId(rs.getInt("USER_ID"));
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setRole(rs.getString("ROLE"));
				user.setBusinessName(rs.getString("BUSINESSNAME"));
				
				return user;
			}
		}
		catch(SQLException e){
			System.out.println("Login error");
			e.printStackTrace();
		}
		finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return null;
	}
}
