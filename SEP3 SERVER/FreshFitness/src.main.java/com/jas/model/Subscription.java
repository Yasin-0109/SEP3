package com.jas.model;

import java.sql.Timestamp;

/**
 * Stores data about Subscription
 * 
 * @author Modaser
 * @version 1.0
 */

public class Subscription {
	private int id;
	private int userId;
	private Timestamp validFrom;
	private Timestamp validTo;
	private SubscriptionType subscriptionType;

	public Subscription(int id, int userId, Timestamp validFrom, Timestamp validTo, SubscriptionType subscriptionType) {
		this.id = id;
		this.userId = userId;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.subscriptionType = subscriptionType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Timestamp validFrom) {
		this.validFrom = validFrom;
	}

	public Timestamp getValidTo() {
		return validTo;
	}

	public void setValidTo(Timestamp validTo) {
		this.validTo = validTo;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((subscriptionType == null) ? 0 : subscriptionType.hashCode());
		result = prime * result + userId;
		result = prime * result + ((validFrom == null) ? 0 : validFrom.hashCode());
		result = prime * result + ((validTo == null) ? 0 : validTo.hashCode());
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
		Subscription other = (Subscription) obj;
		if (id != other.id)
			return false;
		if (subscriptionType == null) {
			if (other.subscriptionType != null)
				return false;
		} else if (!subscriptionType.equals(other.subscriptionType))
			return false;
		if (userId != other.userId)
			return false;
		if (validFrom == null) {
			if (other.validFrom != null)
				return false;
		} else if (!validFrom.equals(other.validFrom))
			return false;
		if (validTo == null) {
			if (other.validTo != null)
				return false;
		} else if (!validTo.equals(other.validTo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + id + ", validFrom=" + validFrom + ", validTo=" + validTo
				+ ", subscriptionType=" + subscriptionType + "]";
	}
}
