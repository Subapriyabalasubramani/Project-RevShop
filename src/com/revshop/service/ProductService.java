package com.revshop.service;

import java.util.ArrayList;
import java.util.Scanner;
import com.revshop.model.*;
import com.revshop.dao.impl.ProductDAOImpl;
import com.revshop.dao.ProductDAO;
import java.util.List;
import com.revshop.dao.ReviewDAO;
import com.revshop.dao.impl.ReviewDAOImpl;
import java.util.logging.Logger;


public class ProductService {
	private static final Logger LOGGER =
	        Logger.getLogger(ProductService.class.getName());

	private ProductDAO productDAO = new ProductDAOImpl();
	private ReviewDAO reviewDAO = new ReviewDAOImpl();

	public void addSellerProduct(User seller, Scanner sc) {
		LOGGER.info("Add product initiated by sellerId: " + seller.getUserId());

		System.out.print("Enter product name: ");
		String name = sc.nextLine();

		System.out.print("Enter product description: ");
		String description = sc.nextLine();

		System.out.print("Enter product category: ");
		String category = sc.nextLine();

		System.out.print("Enter MRP: ");
		double mrp = sc.nextDouble();

		System.out.print("Enter Discount price: ");
		double discount = sc.nextDouble();

		System.out.print("Enter product quantity: ");
		int quantity = sc.nextInt();
		sc.nextLine();

		if (discount > mrp) {
			LOGGER.warning("Product add failed: discount > MRP. sellerId: " + seller.getUserId());
			System.out.println("Discount price cannot be greater than MRP");
			return;
		}

		Product product = new Product();

		product.setName(name);
		product.setDescription(description);
		product.setCategory(category);
		product.setMrp(mrp);
		product.setDiscountPrice(discount);
		product.setQuantity(quantity);
		product.setSellerId(seller.getUserId());

		productDAO.addProduct(product);
		LOGGER.info("Product added successfully by sellerId: " + seller.getUserId());

	}
	
	public void viewBySellerProducts(int sellerId){
		LOGGER.info("View seller products requested. sellerId: " + sellerId);
		List<Product> products = productDAO.getProductsBySeller(sellerId);
		
		if(products.isEmpty()){
			LOGGER.info("No products found for sellerId: " + sellerId);
			System.out.println("No products found");
			return;
		}
		
		System.out.println("\n--- My Products ---");
		
		for(Product p: products){
			System.out.println("Id           : " + p.getProductId());
			System.out.println("Name         : " + p.getName());
	        System.out.println("Description  : " + p.getDescription());
	        System.out.println("Category     : " + p.getCategory());
	        System.out.println("MRP          : " + p.getMrp());
	        System.out.println("Discount     : " + p.getDiscountPrice());
	        System.out.println("Quantity     : " + p.getQuantity());
	        
	        if(p.getQuantity() <= p.getStockThreshold()){
	        	System.out.println("ALERT: STOCK IS LOW!");
	        }
	        System.out.println("---------------------------");
		}
	}
	
	public void updateSellerProduct(User seller, Scanner sc){
		LOGGER.info("Update product initiated. sellerId: " + seller.getUserId());

		
		viewBySellerProducts(seller.getUserId());
		
		System.out.print("Enter Product ID to update: ");
        int productId = sc.nextInt();

        System.out.print("Enter MRP: ");
        double mrp = sc.nextDouble();

        System.out.print("Enter Discount Price: ");
        double discount = sc.nextDouble();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        if (discount > mrp) {
        	 LOGGER.warning("Product update failed: discount > MRP. sellerId: " + seller.getUserId());
            System.out.println("Discount price cannot be greater than MRP");
            return;
        }
        
        Product product = new Product();
        
        product.setProductId(productId);
        product.setMrp(mrp);
        product.setDiscountPrice(discount);
        product.setQuantity(quantity);
        product.setSellerId(seller.getUserId());
        
        boolean updatedProduct = productDAO.updateProduct(product);
        
        if(updatedProduct){
        	LOGGER.info("Product updated successfully. productId: " + productId +
                    ", sellerId: " + seller.getUserId());
        	System.out.println("Product updated successfully");
        }
        else{
        	LOGGER.warning("Product update failed. productId: " + productId +
                    ", sellerId: " + seller.getUserId());
        	System.out.println("Update Failed. Please check the product id");
        }
	}
	
	public void deleteSellerProduct(User seller, Scanner sc){
		LOGGER.info("Delete product initiated. sellerId: " + seller.getUserId());

		viewBySellerProducts(seller.getUserId());
		
		System.out.print("Enter Product ID to delete: ");
		int productId = sc.nextInt();
		sc.nextLine();
		
		System.out.print("Are you sure, do you want to delete this product? (yes/no): ");
		String confirm = sc.nextLine();
		
		if(!confirm.equalsIgnoreCase("yes")){
			LOGGER.info("Product deletion cancelled by seller. sellerId: " + seller.getUserId());
			System.out.println("Delete cancelled");
			return;
		}
		
		boolean deleteProduct = productDAO.deleteProduct(productId, seller.getUserId());
		
		if(deleteProduct){
			LOGGER.info("Product deleted successfully. productId: " + productId +
	                ", sellerId: " + seller.getUserId());
			System.out.println("Product Deleted Successfully");
		}
		else{
			LOGGER.warning("Product deletion failed. productId: " + productId +
	                   ", sellerId: " + seller.getUserId());
			System.out.println("Product Deletion failed. Check the Product ID");
		}
	}
	
	public void showAllProductsForBuyer(){
		LOGGER.info("Buyer requested all products list");

		List<Product> products = productDAO.getAllProducts();
		
		if(products.isEmpty()){
			LOGGER.info("No products available for buyers");
			System.out.println("No products available");
			return;
		}
		
		System.out.println("\n--- Available Products ---");

	    for (Product p : products) {
	        System.out.println("Product ID : " + p.getProductId()
	                         + " | Name : " + p.getName()
	                         + " | Discount Price: " + p.getDiscountPrice());
	    }
	}
	
	public void viewProductDetails(Scanner sc){
		LOGGER.info("View product details requested");
		
		System.out.print("\nEnter Product ID to view details: ");
		int productId = sc.nextInt();
		sc.nextLine();
		
		Product product = productDAO.getProductById(productId);
		
		if(product == null){
			LOGGER.warning("Product not found. productId: " + productId);
			System.out.println("Product not found");
			return;
		}
		System.out.println("\n--- Product Details ---");
	    System.out.println("Name        : " + product.getName());
	    System.out.println("MRP         : " + product.getMrp());
	    System.out.println("Price       : " + product.getDiscountPrice());
	    System.out.println("Description : " + product.getDescription());
		
	    System.out.println("\n--- User Reviews ---");
	    
	    List<Review> reviews = reviewDAO.getReviewsByProductId(productId);
	    
	    if(reviews.isEmpty()){
	    	LOGGER.info("No reviews found for productId: " + productId);
	    	System.out.println("No reviews yet.");
	    }
	    else{
	    	for (Review r : reviews) {
	            System.out.println("Rating  : " + r.getRating() + "/5");
	            System.out.println("Comment : " + r.getComment());
	            System.out.println("----------------------");
	        }
	    }
	}
	
	public void browseOrSearchProducts(Scanner sc) {
		LOGGER.info("Browse/Search products initiated");


	    System.out.println("\nChoose option:");
	    System.out.println("1. Browse by Category");
	    System.out.println("2. Search by Keyword");
	    System.out.print("Enter choice: ");

	    int choice = sc.nextInt();
	    sc.nextLine();

	    List<Product> products = new ArrayList<Product>();

	    if (choice == 1) {
	        System.out.print("Enter category: ");
	        String category = sc.nextLine();
	        products = productDAO.getProductsByCategory(category);

	    } else if (choice == 2) {
	        System.out.print("Enter keyword: ");
	        String keyword = sc.nextLine();
	        products = productDAO.searchProducts(keyword);

	    } else {
	    	LOGGER.warning("Invalid browse/search option selected");
	        System.out.println("Invalid choice.");
	        return;
	    }

	    if (products.isEmpty()) {
	    	LOGGER.info("No products found for browse/search");
	        System.out.println("No products found.");
	        return;
	    }

	    System.out.println("\n--- Products ---");
	    for (Product p : products) {
	        System.out.println("Name : " + p.getName());
	        System.out.println("MRP  : " + p.getMrp());
	        System.out.println("Price: " + p.getDiscountPrice());
	        System.out.println("----------------------");
	    }
	}
}
