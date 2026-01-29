package com.revshop.dao;

import java.util.List;

import com.revshop.model.Product;

public interface ProductDAO {

	void addProduct(Product product);
	
	List <Product> getProductsBySeller(int sellerId);
	
	boolean updateProduct(Product product);
	
	boolean deleteProduct(int productId, int sellerId);
	
	List<Product> getAllProducts();
	
	Product getProductById(int productId);
	
	List<Product> getProductsByCategory(String category);
	
	List<Product> searchProducts(String keyword);
}
