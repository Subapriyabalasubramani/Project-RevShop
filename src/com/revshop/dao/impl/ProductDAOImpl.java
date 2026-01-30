package com.revshop.dao.impl;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revshop.config.DBConnection;
import com.revshop.model.Product;
import com.revshop.dao.ProductDAO;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
	 private static final Logger logger =
	            Logger.getLogger(ProductDAOImpl.class);

	@Override
	public void addProduct(Product product) {
		String sql = "INSERT INTO PRODUCTS"
				+ "(PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, MRP, DISCOUNT_PRICE, QUANTITY, SELLER_ID)"
				+ "VALUES(PRODUCT_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBConnection.getConnection(); // live connection
												// object to db
			ps = con.prepareStatement(sql);

			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setString(3, product.getCategory());
			ps.setDouble(4, product.getMrp());
			ps.setDouble(5, product.getDiscountPrice());
			ps.setInt(6, product.getQuantity());
			ps.setInt(7, product.getSellerId());

			ps.executeUpdate();

			logger.info("Product added successfully. sellerId: "
					+ product.getSellerId());
			System.out.println("Product added successfully!");

		} catch (SQLException e) {
			logger.error("Error adding product. sellerId: "
					+ product.getSellerId() + " | " + e.getMessage());
			System.out.println("Error adding product");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in addProduct: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Product> getProductsBySeller(int sellerId) {

		List<Product> products = new ArrayList<Product>();

		String sql = "SELECT * FROM PRODUCTS WHERE SELLER_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, sellerId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("PRODUCT_ID"));
				product.setName(rs.getString("NAME"));
				product.setDescription(rs.getString("DESCRIPTION"));
				product.setCategory(rs.getString("CATEGORY"));
				product.setMrp(rs.getDouble("MRP"));
				product.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
				product.setQuantity(rs.getInt("QUANTITY"));
				product.setStockThreshold(rs.getInt("STOCK_THRESHOLD"));

				products.add(product);
			}
		} catch (SQLException e) {
			logger.error("Error fetching products for sellerId: " + sellerId
					+ " | " + e.getMessage());
			System.out.println("Error fetching products");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getProductsBySeller: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return products;
	}

	@Override
	public boolean updateProduct(Product product) {

		String sql = "UPDATE PRODUCTS "
				+ "SET MRP = ?, DISCOUNT_PRICE = ?, QUANTITY = ? "
				+ "WHERE PRODUCT_ID = ? AND SELLER_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setDouble(1, product.getMrp());
			ps.setDouble(2, product.getDiscountPrice());
			ps.setInt(3, product.getQuantity());
			ps.setInt(4, product.getProductId());
			ps.setInt(5, product.getSellerId());

			int rows = ps.executeUpdate();

			return rows > 0;
		} catch (SQLException e) {
			logger.error("Error updating product. productId: "
					+ product.getProductId() + ", sellerId: "
					+ product.getSellerId() + " | " + e.getMessage());
			System.out.println("Error updating product");
			return false;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in updateProduct: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean deleteProduct(int productId, int sellerId) {

		String sql = "DELETE FROM PRODUCTS WHERE PRODUCT_ID = ? AND SELLER_ID = ?";

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, productId);
			ps.setInt(2, sellerId);

			int rows = ps.executeUpdate();

			return rows > 0;
		} catch (SQLException e) {
			logger.error("Error deleting product. productId: " + productId
					+ ", sellerId: " + sellerId + " | " + e.getMessage());
			System.out.println("Error deleting product");
			return false;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in deleteProduct: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();

		String sql = "SELECT PRODUCT_ID, NAME, DISCOUNT_PRICE, QUANTITY FROM PRODUCTS";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("PRODUCT_ID"));
				product.setName(rs.getString("NAME"));
				product.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
				product.setQuantity(rs.getInt("QUANTITY"));

				products.add(product);
			}
		} catch (SQLException e) {
			logger.error("Error fetching all products | " + e.getMessage());
			System.out.println("Error fetching products");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getAllProducts: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return products;
	}

	@Override
	public Product getProductById(int productId) {

		String sql = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?";

		Product product = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, productId);
			rs = ps.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setProductId(rs.getInt("PRODUCT_ID"));
				product.setName(rs.getString("NAME"));
				product.setDescription(rs.getString("DESCRIPTION"));
				product.setCategory(rs.getString("CATEGORY"));
				product.setMrp(rs.getDouble("MRP"));
				product.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
				product.setQuantity(rs.getInt("QUANTITY"));
			}
		} catch (SQLException e) {
			logger.error("Error fetching product details. productId: "
					+ productId + " | " + e.getMessage());
			System.out.println("Error fetching product details");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getProductById: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return product;
	}

	@Override
	public List<Product> getProductsByCategory(String category) {

		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT * FROM PRODUCTS WHERE LOWER(CATEGORY) = LOWER(?)";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, category);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setProductId(rs.getInt("PRODUCT_ID"));
				p.setName(rs.getString("NAME"));
				p.setCategory(rs.getString("CATEGORY"));
				p.setMrp(rs.getDouble("MRP"));
				p.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
				p.setDescription(rs.getString("DESCRIPTION"));
				products.add(p);
			}

		} catch (SQLException e) {
			logger.error("Error fetching products by category: " + category
					+ " | " + e.getMessage());
			System.out.println("Error fetching products by category");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getProductsByCategory: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return products;
	}

	@Override
	public List<Product> searchProducts(String keyword) {

		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT * FROM PRODUCTS "
				+ "WHERE LOWER(NAME) LIKE ? OR LOWER(DESCRIPTION) LIKE ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			String search = "%" + keyword.toLowerCase() + "%";
			ps.setString(1, search);
			ps.setString(2, search);

			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setProductId(rs.getInt("PRODUCT_ID"));
				p.setName(rs.getString("NAME"));
				p.setCategory(rs.getString("CATEGORY"));
				p.setMrp(rs.getDouble("MRP"));
				p.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
				p.setDescription(rs.getString("DESCRIPTION"));
				products.add(p);
			}

		} catch (SQLException e) {
			logger.error("Error searching products. keyword: " + keyword
					+ " | " + e.getMessage());
			System.out.println("Error searching products");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in searchProducts: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return products;
	}

	@Override
	public int getSellerIdByProductId(int productId) {

		String sql = "SELECT SELLER_ID FROM PRODUCTS WHERE PRODUCT_ID = ?";
		int sellerId = -1;

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				sellerId = rs.getInt("SELLER_ID");
			}

		} catch (SQLException e) {
			logger.error("Error fetching sellerId for productId: " + productId
					+ " | " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				logger.error("Error closing DB resources in getSellerIdByProductId: "
						+ e.getMessage());
				e.printStackTrace();
			}
		}

		return sellerId;
	}

}
