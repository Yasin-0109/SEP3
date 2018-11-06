/**
 * Stores data about SubscriptionType
 * 
 * @author Modaser
 * @version 1.0
 */

public class SubscriptionType {

	private int ID;
	private String type;
	private int price;
	
/**
 * Creates a new SubscriptionType
 * 
 * @param ID
 * 			SubscriptionTypeID
 * @param type
 * 			The type of the Subscription
 * @param price
 * 			The price of the Subscription
 */
	
	public SubscriptionType(int ID, String type, int price) {
		this.ID = ID;
		this.type = type;
		this.price = price;
	}

	public void setID(int id) {
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SubscriptionType)) {
			return false;
		}
		SubscriptionType other = (SubscriptionType) obj;
		return ID == other.ID && type.equals(other.type) && price == other.price;
	}

	@Override
	public String toString() {
		return "SubscriptionType [ID=" + ID + ", type=" + type + ", price=" + price + "]";
	}
}
