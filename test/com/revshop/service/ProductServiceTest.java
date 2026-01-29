package com.revshop.service;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;
import com.revshop.model.User;

public class ProductServiceTest {

	private static final int VALID_SELLER_ID = 4;

	@Test
	public void testProductServiceObjectCreation() {
		ProductService service = new ProductService();
		assertNotNull(service);
	}

	@Test
	public void testAddSellerProduct_discountGreaterThanMrp() {
		ProductService service = new ProductService();

		User seller = new User();
		seller.setUserId(VALID_SELLER_ID);

		Scanner sc = new Scanner("Test Product\n" + "Test Description\n"
				+ "Electronics\n" + "500\n" + // MRP
				"600\n" + // Discount (invalid)
				"10\n");

		service.addSellerProduct(seller, sc);
	}

	@Test
	public void testViewBySellerProducts_pass() {
		ProductService service = new ProductService();

		service.viewBySellerProducts(VALID_SELLER_ID);
	}

	@Test
	public void testShowAllProductsForBuyer_pass() {
		ProductService service = new ProductService();

		service.showAllProductsForBuyer();
	}

}
