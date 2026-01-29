package com.revshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revshop.model.Order;
import com.revshop.model.CartItem;
import com.revshop.dao.OrderDAO;
import com.revshop.config.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int createOrder(Order order) {

        String sql =
            "INSERT INTO ORDERS VALUES " +
            "(ORDER_SEQ.NEXTVAL, ?, SYSDATE, ?, ?, ?)";

        int orderId = -1;
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{

        	con = DBConnection.getConnection();
            ps =
                con.prepareStatement(sql, new String[] {"ORDER_ID"});
            ps.setInt(1, order.getBuyerId());
            ps.setString(2, order.getShippingAddress());
            ps.setString(3, order.getBillingAddress());
            ps.setDouble(4, order.getTotalAmount());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderId;
    }

    @Override
    public void addOrderItem(int orderId, CartItem item) {

        String sql =
            "INSERT INTO ORDER_ITEMS VALUES " +
            "(ORDER_ITEM_SEQ.NEXTVAL, ?, ?, ?, ?)";
        
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
    }

    @Override
    public void reduceProductStock(int productId, int quantity) {

        String sql =
            "UPDATE PRODUCTS SET QUANTITY = QUANTITY - ? " +
            "WHERE PRODUCT_ID = ?";
        
        Connection con = null;
        PreparedStatement ps = null;

        try{
        	
        	con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
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
    }
    
    @Override
    public List<Order> getOrdersByBuyer(int buyerId) {

        List<Order> orders = new ArrayList<Order>();
        String sql =
            "SELECT ORDER_ID, ORDER_DATE, TOTAL_AMOUNT " +
            "FROM ORDERS WHERE BUYER_ID = ? " +
            "ORDER BY ORDER_DATE DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{

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
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }
    
    @Override
    public List<CartItem> getOrderItems(int orderId) {

        List<CartItem> items = new ArrayList<CartItem>();
        String sql =
            "SELECT P.NAME, OI.PRODUCT_ID, OI.QUANTITY, OI.PRICE " +
            "FROM ORDER_ITEMS OI JOIN PRODUCTS P " +
            "ON OI.PRODUCT_ID = P.PRODUCT_ID " +
            "WHERE OI.ORDER_ID = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
        	
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
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

}
