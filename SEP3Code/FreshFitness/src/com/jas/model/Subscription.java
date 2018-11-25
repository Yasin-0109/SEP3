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

	public boolean equals(Object obj) {
		if (!(obj instanceof Subscription)) {
			return false;
		}
		Subscription other = (Subscription) obj;
		return id == other.getId() && userId == other.getUserId() && validFrom.equals(other.getValidFrom())
				&& validTo.equals(other.getValidTo()) && subscriptionType.equals(other.getSubscriptionType());
	}

	public boolean isUserUnregistered() {
		if (validTo == null) {
			return false;
		} else
			return true;

	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + id + ", validFrom=" + validFrom + ", validTo=" + validTo
				+ ", subscriptionType=" + subscriptionType + "]";
	}
}
