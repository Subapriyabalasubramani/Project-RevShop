package com.revshop.dao.impl;

import com.revshop.model.Review;
import com.revshop.dao.ReviewDAO;
import com.revshop.config.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ReviewDAOImpl implements ReviewDAO {
	 private static final Logger logger =
	            Logger.getLogger(ReviewDAOImpl.class);

	@Override
	public List<Review> getReviewsByProductId(int productId) {
		List<Review> reviews = new ArrayList<Review>();

		String sql = "SELECT * FROM PRODUCT_REVIEWS WHERE PRODUCT_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, productId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Review review = new Review();

				review.setReviewId(rs.getInt("REVIEW_ID"));
				review.setRating(rs.getInt("RATING"));
				review.setComment(rs.getString("REVIEW_COMMENT"));
				reviews.add(review);
			}
			logger.info("Fetched reviews for productId="
                    + productId + ", count=" + reviews.size());
		} catch (SQLException e) {
			logger.error("Error fetching reviews for productId: " + productId
					+ " | " + e.getMessage());
			System.out.println("Error fetching reviews");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getReviewsByProductId: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return reviews;
	}

	@Override
	public List<Review> getReviewsBySeller(int sellerId) {

		List<Review> reviews = new ArrayList<Review>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT R.REVIEW_ID, R.PRODUCT_ID, P.NAME, R.RATING, R.REVIEW_COMMENT "
				+ "FROM PRODUCT_REVIEWS R "
				+ "JOIN PRODUCTS P ON R.PRODUCT_ID = P.PRODUCT_ID "
				+ "WHERE P.SELLER_ID = ? ";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sellerId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Review review = new Review();
				review.setReviewId(rs.getInt("REVIEW_ID"));
				review.setProductId(rs.getInt("PRODUCT_ID"));
				review.setProductName(rs.getString("NAME"));
				review.setRating(rs.getInt("RATING"));
				review.setComment(rs.getString("REVIEW_COMMENT"));
				reviews.add(review);
			}
			logger.info("Fetched reviews for sellerId="
                    + sellerId + ", count=" + reviews.size());

		} catch (SQLException e) {
			logger.error("Error fetching reviews for sellerId: " + sellerId
					+ " | " + e.getMessage());
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
				logger.error("Error closing DB resources in getReviewsBySeller: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return reviews;
	}

	@Override
	public boolean hasBuyerPurchasedProduct(int buyerId, int productId) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) FROM ORDER_ITEMS OI "
				+ "JOIN ORDERS O ON OI.ORDER_ID = O.ORDER_ID "
				+ "WHERE O.BUYER_ID = ? AND OI.PRODUCT_ID = ?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, buyerId);
			ps.setInt(2, productId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			logger.error("Error checking purchase history. buyerId: "
					+ buyerId + ", productId: " + productId + " | "
					+ e.getMessage());
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
				logger.error("Error closing DB resources in hasBuyerPurchasedProduct: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void addReview(int buyerId, int productId, int rating, String comment) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO PRODUCT_REVIEWS "
				+ "(REVIEW_ID, PRODUCT_ID, BUYER_ID, RATING, REVIEW_COMMENT, CREATED_AT) "
				+ "VALUES (REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ps.setInt(2, buyerId);
			ps.setInt(3, rating);
			ps.setString(4, comment);
			ps.executeUpdate();

			logger.info("Review added successfully. buyerId: " + buyerId
					+ ", productId: " + productId);
			System.out.println("Review submitted successfully");

		} catch (SQLException e) {
			logger.error("Error adding review. buyerId: " + buyerId
					+ ", productId: " + productId + " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in addReview: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
