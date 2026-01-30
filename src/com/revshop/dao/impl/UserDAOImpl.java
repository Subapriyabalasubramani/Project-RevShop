package com.revshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revshop.config.DBConnection;
import com.revshop.dao.UserDAO;
import com.revshop.model.User;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {
	private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class
			.getName());

	@Override
	public void registerUser(User user) {
		String sql = "INSERT INTO USERS (USER_ID, NAME, EMAIL, PASSWORD, ROLE, BUSINESSNAME, SECURITY_ANSWER)"
				+ "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			ps.setString(5, user.getBusinessName());
			ps.setString(6, user.getSecurityAnswer());

			ps.executeUpdate(); // used to execute the insert statement
			LOGGER.info("User registered successfully. Email: "
					+ user.getEmail());
			System.out.println("Registration successful!");
		} catch (SQLException e) {
			LOGGER.severe("Error while registering user. Email: "
					+ user.getEmail() + " | " + e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in registerUser: "
						+ e.getMessage());
			}
		}

	}

	@Override
	public User login(String email, String password) {
		String sql = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD =?";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("USER_ID"));
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setRole(rs.getString("ROLE"));
				user.setBusinessName(rs.getString("BUSINESSNAME"));

				LOGGER.info("User login successful. Email: " + email);

				return user;
			}
		} catch (SQLException e) {
			LOGGER.severe("Login failed due to DB error. Email: " + email
					+ " | " + e.getMessage());
			System.out.println("Login error");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in login: "
						+ e.getMessage());
			}
		}
		return null;
	}
	
	@Override
	public boolean changePassword(int userId,
	                              String oldHashedPwd,
	                              String newHashedPwd) {

	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = DBConnection.getConnection();

	        // verify old password
	        String checkSql =
	            "SELECT PASSWORD FROM USERS WHERE USER_ID = ?";
	        ps = con.prepareStatement(checkSql);
	        ps.setInt(1, userId);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            String dbPwd = rs.getString("PASSWORD");

	            if (!dbPwd.equals(oldHashedPwd)) {
	                return false;
	            }
	        } else {
	            return false;
	        }

	        rs.close();
	        ps.close();

	        // update new password
	        String updateSql =
	            "UPDATE USERS SET PASSWORD = ? WHERE USER_ID = ?";
	        ps = con.prepareStatement(updateSql);
	        ps.setString(1, newHashedPwd);
	        ps.setInt(2, userId);
	        ps.executeUpdate();

	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
	
	@Override
	public boolean resetPassword(String email,
	                             String hashedAnswer,
	                             String newHashedPwd) {

	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = DBConnection.getConnection();

	        String sql =
	            "SELECT USER_ID FROM USERS " +
	            "WHERE EMAIL = ? AND SECURITY_ANSWER = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, email);
	        ps.setString(2, hashedAnswer);
	        rs = ps.executeQuery();

	        if (!rs.next()) {
	            return false;
	        }

	        int userId = rs.getInt("USER_ID");

	        rs.close();
	        ps.close();

	        String updateSql =
	            "UPDATE USERS SET PASSWORD = ? WHERE USER_ID = ?";
	        ps = con.prepareStatement(updateSql);
	        ps.setString(1, newHashedPwd);
	        ps.setInt(2, userId);
	        ps.executeUpdate();

	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}


}
