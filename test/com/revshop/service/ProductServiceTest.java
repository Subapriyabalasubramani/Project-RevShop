package com.revshop.service;

import static org.mockito.Mockito.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revshop.dao.ProductDAO;
import com.revshop.dao.ReviewDAO;
import com.revshop.model.Product;
import com.revshop.model.Review;
import com.revshop.model.User;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private ProductService productService;

    private User seller;

    @Before
    public void setup() {
        seller = new User();
        seller.setUserId(10);

        productService.setProductDAO(productDAO);
        productService.setReviewDAO(reviewDAO);
    }

    @Test
    public void testAddSellerProduct_success() {

        Scanner sc = new Scanner(
            "Phone\n" +
            "Smart phone\n" +
            "Electronics\n" +
            "10000\n" +
            "9000\n" +
            "5\n"
        );

        productService.addSellerProduct(seller, sc);

        verify(productDAO).addProduct(any(Product.class));
    }

    @Test
    public void testAddSellerProduct_fail_discountGreaterThanMrp() {

        Scanner sc = new Scanner(
            "Phone\n" +
            "Smart phone\n" +
            "Electronics\n" +
            "9000\n" +
            "10000\n" +  // discount > mrp
            "5\n"
        );

        productService.addSellerProduct(seller, sc);

        verify(productDAO, never()).addProduct(any(Product.class));
    }
    
    @Test
    public void testViewBySellerProducts_withProducts() {

        Product p = new Product();
        p.setProductId(1);
        p.setName("Phone");
        p.setQuantity(5);

        when(productDAO.getProductsBySeller(10))
            .thenReturn(Arrays.asList(p));

        productService.viewBySellerProducts(10);

        verify(productDAO).getProductsBySeller(10);
    }

    @Test
    public void testViewBySellerProducts_noProducts() {

        when(productDAO.getProductsBySeller(10))
            .thenReturn(Collections.<Product>emptyList());

        productService.viewBySellerProducts(10);

        verify(productDAO).getProductsBySeller(10);
    }


    @Test
    public void testUpdateSellerProduct_success() {

        when(productDAO.getProductsBySeller(10))
            .thenReturn(Collections.<Product>emptyList());

        when(productDAO.updateProduct(any(Product.class)))
            .thenReturn(true);

        Scanner sc = new Scanner(
            "1\n" +
            "10000\n" +
            "9000\n" +
            "5\n"
        );

        productService.updateSellerProduct(seller, sc);

        verify(productDAO).updateProduct(any(Product.class));
    }

    @Test
    public void testDeleteSellerProduct_confirmYes() {

        when(productDAO.getProductsBySeller(10))
            .thenReturn(Collections.<Product>emptyList());

        when(productDAO.deleteProduct(1, 10))
            .thenReturn(true);

        Scanner sc = new Scanner(
            "1\n" +
            "yes\n"
        );

        productService.deleteSellerProduct(seller, sc);

        verify(productDAO).deleteProduct(1, 10);
    }

    @Test
    public void testViewProductDetails_withReviews() {

        Product product = new Product();
        product.setProductId(1);
        product.setName("Phone");

        Review review = new Review();
        review.setRating(5);
        review.setComment("Great");

        when(productDAO.getProductById(1))
            .thenReturn(product);

        when(reviewDAO.getReviewsByProductId(1))
            .thenReturn(Arrays.asList(review));

        Scanner sc = new Scanner("1\n");

        productService.viewProductDetails(sc);

        verify(productDAO).getProductById(1);
        verify(reviewDAO).getReviewsByProductId(1);
    }

    @Test
    public void testBrowseByCategory() {

        when(productDAO.getProductsByCategory("Electronics"))
            .thenReturn(Collections.<Product>emptyList());

        Scanner sc = new Scanner(
            "1\n" +
            "Electronics\n"
        );

        productService.browseOrSearchProducts(sc);

        verify(productDAO).getProductsByCategory("Electronics");
    }

    @Test
    public void testSearchByKeyword() {

        when(productDAO.searchProducts("Phone"))
            .thenReturn(Collections.<Product>emptyList());

        Scanner sc = new Scanner(
            "2\n" +
            "Phone\n"
        );

        productService.browseOrSearchProducts(sc);

        verify(productDAO).searchProducts("Phone");
    }
}
