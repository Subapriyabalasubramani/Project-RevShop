package com.revshop.dao.impl;

import com.revshop.model.Review;
import com.revshop.dao.ReviewDAO;
import com.revshop.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ReviewDAOImpl implements ReviewDAO {

	@Override
	public List<Review> getReviewsByProductId(int productId){
		List<Review> reviews = new ArrayList<Review>();
		
		String sql = "SELECT * FROM PRODUCT_REVIEWS WHERE PRODUCT_ID = ?";
		
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		
		try{
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, productId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Review review = new Review();
				
				review.setReviewId(rs.getInt("REVIEW_ID"));
				review.setRating(rs.getInt("RATING"));
	            review.setComment(rs.getString("REVIEW_COMMENT"));
	            reviews.add(review);
			}
		}
		catch (SQLException e){
			System.out.println("Error fetching reviews");
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
		
		return reviews;
	}
}
