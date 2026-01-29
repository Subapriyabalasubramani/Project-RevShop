package com.revshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revshop.config.DBConnection;
import com.revshop.dao.FavoritesDAO;
import com.revshop.model.Favorites;

public class FavoritesDAOImpl implements FavoritesDAO {

	@Override
    public void addFavorite(int buyerId, int productId) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "INSERT INTO FAVORITES VALUES " +
            "(FAVORITE_SEQ.NEXTVAL, ?, ?)";

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, buyerId);
            ps.setInt(2, productId);
            ps.executeUpdate();

            System.out.println("\nProduct added to the Wishlist.");

        } catch (SQLException e) {
            System.out.println("Error adding favorite");
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Favorites> getFavoritesByBuyer(int buyerId) {

        List<Favorites> favorites = new ArrayList<Favorites>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT F.FAVORITE_ID, P.PRODUCT_ID, P.NAME " +
            "FROM FAVORITES F JOIN PRODUCTS P " +
            "ON F.PRODUCT_ID = P.PRODUCT_ID " +
            "WHERE F.BUYER_ID = ?";

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, buyerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Favorites fav = new Favorites();
                fav.setFavoriteId(rs.getInt("FAVORITE_ID"));
                fav.setProductId(rs.getInt("PRODUCT_ID"));
                fav.setProductName(rs.getString("NAME"));
                favorites.add(fav);
            }

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

        return favorites;
    }

//    @Override
//    public void removeFavorite(int buyerId, int productId) {
//
//        Connection con = null;
//        PreparedStatement ps = null;
//
//        String sql =
//            "DELETE FROM FAVORITES WHERE BUYER_ID = ? AND PRODUCT_ID = ?";
//
//        try {
//            con = DBConnection.getConnection();
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, buyerId);
//            ps.setInt(2, productId);
//            ps.executeUpdate();
//
//            System.out.println("Removed from favorites");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ps != null) ps.close();
//                if (con != null) con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
