package com.revshop.dao.impl;

import java.util.List;

import com.revshop.model.Product;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductDAOImplTest {

	private static final int TEST_SELLER_ID = 1;

	private Product createTestProduct(int sellerId) {
		Product p = new Product();
		p.setName("JUnit Product");
		p.setDescription("JUnit Test Description");
		p.setCategory("Electronics");
		p.setMrp(1000);
		p.setDiscountPrice(800);
		p.setQuantity(10);
		p.setSellerId(sellerId);
		return p;
	}

	@Test
	public void testAddProduct() {
		ProductDAOImpl dao = new ProductDAOImpl();
		Product product = createTestProduct(TEST_SELLER_ID);

		// Passes if no exception occurs
		dao.addProduct(product);
	}

	@Test
	public void testGetProductsBySeller() {
		ProductDAOImpl dao = new ProductDAOImpl();

		List<Product> products = dao.getProductsBySeller(TEST_SELLER_ID);

		assertNotNull(products);
	}

	@Test
	public void testGetAllProducts() {
		ProductDAOImpl dao = new ProductDAOImpl();

		List<Product> products = dao.getAllProducts();

		assertNotNull(products);
	}

	@Test
	public void testGetProductById_invalid() {
		ProductDAOImpl dao = new ProductDAOImpl();

		Product product = dao.getProductById(-1); // failing scenario

		assertNull(product);
	}

	@Test
	public void testGetProductsByCategory() {
		ProductDAOImpl dao = new ProductDAOImpl();

		List<Product> products = dao.getProductsByCategory("Electronics");

		assertNotNull(products);
	}
	
	@Test
	public void testGetProductsByCategoryFail() {
		ProductDAOImpl dao = new ProductDAOImpl();

		List<Product> products = dao.getProductsByCategory("Mobile");

		assertNotNull(products); 
		assertTrue(products.isEmpty());
	}

	@Test
	public void testSearchProducts() {
		ProductDAOImpl dao = new ProductDAOImpl();

		List<Product> products = dao.searchProducts("test");

		assertNotNull(products);
	}
	
	@Test
	public void testSearchProductsFail() {
		ProductDAOImpl dao = new ProductDAOImpl();

		List<Product> products = dao.searchProducts("example");

		assertNotNull(products); 
		assertTrue(products.isEmpty());
	}

	@Test
	public void testDeleteProduct_invalid() {
	    ProductDAOImpl dao = new ProductDAOImpl();

	    boolean deleted = dao.deleteProduct(-1, TEST_SELLER_ID);

	    assertFalse(deleted);
	}

}
