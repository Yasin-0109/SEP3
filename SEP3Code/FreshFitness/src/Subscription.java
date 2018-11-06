
public class Subscription {
	private int ID;
	private int userID;
	private MyDate validFrom;
	private MyDate validTo;
	private int subscriptionTypeID;
	private SubscriptionType subscriptionType;

	public Subscription(int ID, int userID, MyDate validFrom, MyDate validTo, int subscriptiontypeID,
			SubscriptionType subscriptiontype) {
		this.ID = ID;
		this.userID = userID;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.subscriptionTypeID = subscriptionTypeID;
		this.subscriptionType = subscriptionType;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public MyDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(MyDate validFrom) {
		this.validFrom = validFrom;
	}

	public MyDate getValidTo() {
		return validTo;
	}

	public void setValidTo(MyDate validTo) {
		this.validTo = validTo;
	}

	public int getSubscriptionTypeID() {
		return subscriptionTypeID;
	}

	public void setSubscriptionTypeID(int subscriptionTypeID) {
		this.subscriptionTypeID = subscriptionTypeID;
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
		return ID == other.ID && userID == other.userID && validFrom.equals(other.validFrom)
				&& validTo.equals(other.validTo) && subscriptionTypeID == other.subscriptionTypeID
				&& subscriptionType.equals(other.subscriptionType);
	}

	public boolean isUserUnregistered() {
		if (validTo == null) {
			return false;
		} else
			return true;

	}

	@Override
	public String toString() {
		return "Subscription [ID=" + ID + ", userID=" + userID + ", validFrom=" + validFrom + ", validTo=" + validTo
				+ ", subscriptionTypeID=" + subscriptionTypeID + ", subscriptionType=" + subscriptionType + "]";
	}
}
