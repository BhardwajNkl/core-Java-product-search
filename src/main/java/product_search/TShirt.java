/**
 * 
 */
package product_search;

/**
 * @author nikhilbhardwaj01
 *
 */
public class TShirt {
	private String id;
	private String name;
	private String color;
	private String gender;
	private String size;
	private float price;
	private float rating;
	private String availability;

	public TShirt(String id, String name, String color, String gender, String size, float price, float rating,
			String availability) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.gender = gender;
		this.size = size;
		this.price = price;
		this.rating = rating;
		this.availability = availability;
	}

	public String getShirtId() {
		return this.id;
	}

	public String getShirtName() {
		return this.name;
	}

	public String getShirtColor() {
		return this.color;
	}

	public String getShirtSize() {
		return this.size;
	}

	public String getShirtGender() {
		return this.gender;
	}

	public float getShirtPrice() {
		return this.price;
	}

	public float getShirtRating() {
		return this.rating;
	}

	public String getShirtAvailability() {
		return this.availability;
	}

}
