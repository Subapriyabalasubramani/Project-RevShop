package com.revshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.revshop.model.Order;
import com.revshop.model.CartItem;
import com.revshop.dao.OrderDAO;
import com.revshop.config.DBConnection;

public class OrderDAOImpl implements OrderDAO {
	private static final Logger LOGGER = Logger.getLogger(OrderDAOImpl.class
			.getName());

	@Override
	public int createOrder(Order order) {

		String sql = "INSERT INTO ORDERS "
				+ "(ORDER_ID, BUYER_ID, SHIPPING_ADDRESS, BILLING_ADDRESS, TOTAL_AMOUNT, ORDER_DATE, PAYMENT_MODE, PAYMENT_STATUS) "
				+ "VALUES (ORDER_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";

		int orderId = -1;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql, new String[] { "ORDER_ID" });
			ps.setInt(1, order.getBuyerId());
			ps.setString(2, order.getShippingAddress());
			ps.setString(3, order.getBillingAddress());
			ps.setDouble(4, order.getTotalAmount());
			ps.setString(5, "COD");
			ps.setString(6, "SUCCESS");

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

		} catch (SQLException e) {
			LOGGER.severe("Error creating order. buyerId: "
					+ order.getBuyerId() + " | " + e.getMessage());
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
				LOGGER.severe("Error closing DB resources in createOrder: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return orderId;
	}

	@Override
	public void addOrderItem(int orderId, CartItem item) {

		String sql = "INSERT INTO ORDER_ITEMS VALUES "
				+ "(ORDER_ITEM_SEQ.NEXTVAL, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, orderId);
			ps.setInt(2, item.getProductId());
			ps.setInt(3, item.getQuantity());
			ps.setDouble(4, item.getPrice());

			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.severe("Error adding order item. orderId: " + orderId
					+ ", productId: " + item.getProductId() + " | "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in addOrderItem: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reduceProductStock(int productId, int quantity) {

		String sql = "UPDATE PRODUCTS SET QUANTITY = QUANTITY - ? "
				+ "WHERE PRODUCT_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, quantity);
			ps.setInt(2, productId);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.severe("Error reducing stock. productId: " + productId
					+ ", quantity: " + quantity + " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in reduceProductStock: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clearCart(int cartId) {

		String sql = "DELETE FROM CART_ITEMS WHERE CART_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cartId);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.severe("Error clearing cart. cartId: " + cartId + " | "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in clearCart: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Order> getOrdersByBuyer(int buyerId) {

		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT ORDER_ID, ORDER_DATE, TOTAL_AMOUNT "
				+ "FROM ORDERS WHERE BUYER_ID = ? "
				+ "ORDER BY ORDER_DATE DESC";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, buyerId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("ORDER_ID"));
				order.setOrderDate(rs.getDate("ORDER_DATE"));
				order.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
				orders.add(order);
			}

		} catch (SQLException e) {
			LOGGER.severe("Error fetching orders for buyerId: " + buyerId
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
				LOGGER.severe("Error closing DB resources in getOrdersByBuyer: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return orders;
	}

	@Override
	public List<CartItem> getOrderItems(int orderId) {

		List<CartItem> items = new ArrayList<CartItem>();
		String sql = "SELECT P.NAME, OI.PRODUCT_ID, OI.QUANTITY, OI.PRICE "
				+ "FROM ORDER_ITEMS OI JOIN PRODUCTS P "
				+ "ON OI.PRODUCT_ID = P.PRODUCT_ID " + "WHERE OI.ORDER_ID = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, orderId);
			rs = ps.executeQuery();

			while (rs.next()) {
				CartItem item = new CartItem();
				item.setProductId(rs.getInt("PRODUCT_ID"));
				item.setProductName(rs.getString("NAME"));
				item.setQuantity(rs.getInt("QUANTITY"));
				item.setPrice(rs.getDouble("PRICE"));
				items.add(item);
			}

		} catch (SQLException e) {
			LOGGER.severe("Error fetching order items. orderId: " + orderId
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
				LOGGER.severe("Error closing DB resources in getOrderItems: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return items;
	}

	@Override
	public boolean hasOrdersForSeller(int sellerId) {

		String sql = "SELECT COUNT(*) FROM ORDER_ITEMS OI "
				+ "JOIN PRODUCTS P ON OI.PRODUCT_ID = P.PRODUCT_ID "
				+ "WHERE P.SELLER_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			LOGGER.severe("Error checking orders for sellerId: " + sellerId
					+ " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in hasOrdersForSeller: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public List<Order> getOrdersForSeller(int sellerId) {

		List<Order> orders = new ArrayList<Order>();

		String sql = "SELECT DISTINCT O.ORDER_ID, O.ORDER_DATE, O.TOTAL_AMOUNT "
				+ "FROM ORDERS O "
				+ "JOIN ORDER_ITEMS OI ON O.ORDER_ID = OI.ORDER_ID "
				+ "JOIN PRODUCTS P ON OI.PRODUCT_ID = P.PRODUCT_ID "
				+ "WHERE P.SELLER_ID = ? " + "ORDER BY O.ORDER_DATE DESC";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sellerId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("ORDER_ID"));
				order.setOrderDate(rs.getDate("ORDER_DATE"));
				order.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
				orders.add(order);
			}

		} catch (SQLException e) {
			LOGGER.severe("Error fetching seller orders. sellerId: " + sellerId
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
				LOGGER.severe("Error closing DB resources in getOrdersForSeller: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return orders;
	}

	@Override
	public List<CartItem> getOrderItemsForSeller(int orderId, int sellerId) {

		List<CartItem> items = new ArrayList<CartItem>();

		String sql = "SELECT P.NAME, OI.PRODUCT_ID, OI.QUANTITY, OI.PRICE "
				+ "FROM ORDER_ITEMS OI "
				+ "JOIN PRODUCTS P ON OI.PRODUCT_ID = P.PRODUCT_ID "
				+ "WHERE OI.ORDER_ID = ? AND P.SELLER_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, orderId);
			ps.setInt(2, sellerId);

			rs = ps.executeQuery();

			while (rs.next()) {
				CartItem item = new CartItem();
				item.setProductId(rs.getInt("PRODUCT_ID"));
				item.setProductName(rs.getString("NAME"));
				item.setQuantity(rs.getInt("QUANTITY"));
				item.setPrice(rs.getDouble("PRICE"));
				items.add(item);
			}

		} catch (SQLException e) {
			LOGGER.severe("Error fetching seller order items. orderId: "
					+ orderId + ", sellerId: " + sellerId + " | "
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
				LOGGER.severe("Error closing DB resources in getOrderItemsForSeller: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return items;
	}

	@Override
	public List<CartItem> getPurchasedProducts(int buyerId) {

		List<CartItem> items = new ArrayList<CartItem>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT DISTINCT OI.PRODUCT_ID, P.NAME "
				+ "FROM ORDER_ITEMS OI "
				+ "JOIN ORDERS O ON OI.ORDER_ID = O.ORDER_ID "
				+ "JOIN PRODUCTS P ON OI.PRODUCT_ID = P.PRODUCT_ID "
				+ "WHERE O.BUYER_ID = ?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, buyerId);
			rs = ps.executeQuery();

			while (rs.next()) {
				CartItem item = new CartItem();
				item.setProductId(rs.getInt("PRODUCT_ID"));
				item.setProductName(rs.getString("NAME"));
				items.add(item);
			}

		} catch (SQLException e) {
			LOGGER.severe("Error fetching purchased products. buyerId: "
					+ buyerId + " | " + e.getMessage());
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
				LOGGER.severe("Error closing DB resources in getPurchasedProducts: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return items;
	}

	@Override
	public void updatePaymentDetails(int orderId, String paymentMode,
			String paymentStatus) {

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE ORDERS SET PAYMENT_MODE = ?, PAYMENT_STATUS = ? WHERE ORDER_ID = ?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, paymentMode);
			ps.setString(2, paymentStatus);
			ps.setInt(3, orderId);

			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.severe("Error updating payment details. orderId: " + orderId
					+ " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				LOGGER.severe("Error closing DB resources in updatePaymentDetails: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
