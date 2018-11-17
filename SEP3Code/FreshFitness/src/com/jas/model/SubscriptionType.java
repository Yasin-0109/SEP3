package com.jas.model;

import java.math.BigDecimal;

/**
 * Stores data about SubscriptionType
 * 
 * @author Modaser
 * @version 1.0
 */

public class SubscriptionType {

	private int id;
	private String type;
	private BigDecimal price; // Dealing with money in Java - https://blogs.oracle.com/corejavatechtips/the-need-for-bigdecimal
	
/**
 * Creates a new SubscriptionType
 * 
 * @param id
 * 			SubscriptionTypeID
 * @param type
 * 			The type of the Subscription
 * @param price
 * 			The price of the Subscription
 */
	
	public SubscriptionType(int id, String type, BigDecimal price) {
		this.id = id;
		this.type = type;
		this.price = price;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SubscriptionType)) {
			return false;
		}
		SubscriptionType other = (SubscriptionType) obj;
		return id == other.getId() && type.equals(other.getType()) && price == other.getPrice();
	}

	@Override
	public String toString() {
		return "SubscriptionType [id=" + id + ", type=" + type + ", price=" + price + "]";
	}
}
