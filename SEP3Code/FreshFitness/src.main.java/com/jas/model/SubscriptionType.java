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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubscriptionType other = (SubscriptionType) obj;
		if (id != other.id)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubscriptionType [id=" + id + ", type=" + type + ", price=" + price + "]";
	}
}
