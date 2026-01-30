package com.revshop.dao.impl;

import com.revshop.model.CartItem;
import com.revshop.dao.CartDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.revshop.config.DBConnection;

public class CartDAOImpl implements CartDAO {
	private static final Logger logger = Logger.getLogger(CartDAOImpl.class);

	@Override
	public int getCartIdByBuyer(int buyerId) {
		String sql = "SELECT CART_ID FROM CART WHERE BUYER_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, buyerId);
			rs = ps.executeQuery();
			if (rs.next()) {
				logger.info("Cart found for buyerId: " + buyerId);
				return rs.getInt("CART_ID");
			}

		} catch (SQLException e) {
			logger.error("Error getting cartId for buyerId: " + buyerId, e);
			System.out.println("Error getting cart");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error getting cartId for buyerId: " + buyerId
						+ " | " + e.getMessage());
				e.printStackTrace();
			}
		}
		return -1;
	}

	@Override
	public void createCart(int buyerId) {

		String sql = "INSERT INTO CART VALUES (CART_SEQ.NEXTVAL, ?)";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, buyerId);
			ps.executeUpdate();
			logger.info("Cart created for buyerId: " + buyerId);

		} catch (SQLException e) {
			logger.error("Error creating cart for buyerId: " + buyerId, e);
			System.out.println("Error creating cart");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error getting cartId for buyerId: " + buyerId
						+ " | " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addToCart(int cartId, int productId, int quantity, double price) {

		String sql = "INSERT INTO CART_ITEMS VALUES "
				+ "(CART_ITEM_SEQ.NEXTVAL, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cartId);
			ps.setInt(2, productId);
			ps.setInt(3, quantity);
			ps.setDouble(4, price);
			ps.executeUpdate();
			logger.info("Item added to cart. cartId=" + cartId + ", productId="
					+ productId);

		} catch (SQLException e) {
			logger.error("Error adding to cart. cartId: " + cartId
					+ ", productId: " + productId + " | " + e.getMessage());
			System.out.println("Error adding to cart");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in addToCart: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<CartItem> viewCart(int cartId) {

		List<CartItem> items = new ArrayList<CartItem>();

		String sql = "SELECT P.NAME, CI.PRODUCT_ID, CI.QUANTITY, CI.PRICE "
				+ "FROM CART_ITEMS CI JOIN PRODUCTS P "
				+ "ON CI.PRODUCT_ID = P.PRODUCT_ID " + "WHERE CI.CART_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, cartId);
			rs = ps.executeQuery();

			while (rs.next()) {
				CartItem item = new CartItem();
				item.setProductId(rs.getInt("PRODUCT_ID"));
				item.setProductName(rs.getString("NAME"));
				item.setQuantity(rs.getInt("QUANTITY"));
				item.setPrice(rs.getDouble("PRICE"));
				items.add(item);
			}
			logger.info("Viewed cart items for cartId: " + cartId);

		} catch (SQLException e) {
			logger.error("Error viewing cart. cartId: " + cartId + " | "
					+ e.getMessage());
			System.out.println("Error viewing cart");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in viewCart: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return items;
	}

	@Override
	public void updateCartItem(int cartId, int productId, int quantity) {

		String sql = "UPDATE CART_ITEMS SET QUANTITY = ? "
				+ "WHERE CART_ID = ? AND PRODUCT_ID = ? ";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantity);
			ps.setInt(2, cartId);
			ps.setInt(3, productId);
			ps.executeUpdate();

			logger.info("Cart item updated. cartId=" + cartId + ", productId="
					+ productId);

		} catch (SQLException e) {
			logger.error("Error updating cart item. cartId: " + cartId
					+ ", productId: " + productId + " | " + e.getMessage());
			System.out.println("Error updating cart item");
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in updateCartItem: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void removeFromCart(int cartId, int productId) {

		String sql = "DELETE FROM CART_ITEMS WHERE CART_ID = ? AND PRODUCT_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, cartId);
			ps.setInt(2, productId);
			ps.executeUpdate();

			logger.info("Item removed from cart. cartId=" + cartId
					+ ", productId=" + productId);

		} catch (SQLException e) {
			logger.error("Error removing item from cart. cartId: " + cartId
					+ ", productId: " + productId + " | " + e.getMessage());
			System.out.println("Error removing item");
		} finally {
			try {
				if (ps != null)
					ps.close();
				
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in removeFromCart: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
