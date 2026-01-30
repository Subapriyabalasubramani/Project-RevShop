package com.revshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.revshop.config.DBConnection;
import com.revshop.dao.NotificationDAO;

public class NotificationDAOImpl implements NotificationDAO {
	private static final Logger logger =
            Logger.getLogger(NotificationDAOImpl.class);

	@Override
	public void createNotification(int sellerId, String message) {

		String sql = "INSERT INTO NOTIFICATIONS VALUES "
				+ "(NOTIFICATION_SEQ.NEXTVAL, ?, ?, 'N')";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, sellerId);
			ps.setString(2, message);
			ps.executeUpdate();
			
			logger.info("Notification created for sellerId=" + sellerId);

		} catch (SQLException e) {
			logger.error("Error creating notification for sellerId: "
					+ sellerId + " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in createNotification: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean hasUnreadNotifications(int sellerId) {

		String sql = "SELECT COUNT(*) FROM NOTIFICATIONS "
				+ "WHERE SELLER_ID = ? AND IS_READ = 'N'";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sellerId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			logger.info("Unread notifications check for sellerId");

		} catch (SQLException e) {
			logger.error("Error checking unread notifications for sellerId: "
					+ sellerId + " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in hasUnreadNotifications: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public List<String> getUnreadNotifications(int sellerId) {

		List<String> messages = new ArrayList<String>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT MESSAGE FROM NOTIFICATIONS "
				+ "WHERE SELLER_ID = ? AND IS_READ = 'N'";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, sellerId);
			rs = ps.executeQuery();

			while (rs.next()) {
				messages.add(rs.getString("MESSAGE"));
			}
			
			logger.info("Fetched unread notifications for sellerId="
                    + sellerId + ", count=" + messages.size());

		} catch (SQLException e) {
			logger.error("Error fetching unread notifications for sellerId: "
					+ sellerId + " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getUnreadNotifications: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return messages;
	}

	@Override
	public void markNotificationsAsRead(int sellerId) {

		String sql = "UPDATE NOTIFICATIONS SET IS_READ = 'Y' "
				+ "WHERE SELLER_ID = ? AND IS_READ = 'N'";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, sellerId);
			ps.executeUpdate();
			
			logger.info("Marked notifications as read for sellerId");

		} catch (SQLException e) {
			logger.error("Error marking notifications as read for sellerId: "
					+ sellerId + " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in markNotificationsAsRead: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
