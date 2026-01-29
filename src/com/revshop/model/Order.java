package com.revshop.model;

import java.util.Date;

public class Order {
	private int orderId;
    private int buyerId;
    private String shippingAddress;
    private String billingAddress;
    private double totalAmount;
    private Date orderDate;
    
    public int getOrderId(){
    	return orderId;
    }
    
    public void setOrderId(int orderId){
    	this.orderId = orderId;
    }
    
    public int getBuyerId(){
    	return buyerId;
    }
    
    public void setBuyerId(int buyerId){
    	this.buyerId = buyerId;
    }
    
    public String getShippingAddress(){
    	return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress){
    	this.shippingAddress = shippingAddress;
    }
    
    public String getBillingAddress(){
    	return billingAddress;
    }
    public void setBillingAddress(String billingAddress){
    	this.billingAddress = billingAddress;
    }
    
    public double getTotalAmount (){
    	return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount){
    	this.totalAmount = totalAmount;
    }
    
    public Date getOrderDate(){
    	return orderDate;
    }
    
    public void setOrderDate(Date orderDate){
    	this.orderDate = orderDate;
    }
}
