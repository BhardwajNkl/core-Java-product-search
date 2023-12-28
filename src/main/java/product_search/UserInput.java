/**
 * 
 */
package product_search;

/**
 * @author nikhilbhardwaj01 This class is used only for storing the input from
 *         user.
 */

enum GenderType {
	M, F;
}

enum SizeType {
	XXS, XS, S, M, L, XL, XXL, XXXL;
}

enum OutputPreferenceType {
	P, R, PR, RP;
}

public final class UserInput {
	private String color;
	private SizeType size;
	private GenderType gender;
	private OutputPreferenceType outputPreference;

	public UserInput(String color, SizeType size, GenderType gender, OutputPreferenceType outputPreference) {
		this.color = color;
		this.size = size;
		this.gender = gender;
		this.outputPreference = outputPreference;
	}

	public void setColor(String input) {
		color = input;
	}

	public void setSize(SizeType input) {
		size = input;
	}

	public void setGender(GenderType input) {
		gender = input;
	}

	public void setOutputPreference(OutputPreferenceType input) {
		outputPreference = input;
	}

	public String getColor() {
		return color;
	}

	public SizeType getSize() {
		return size;
	}

	public GenderType getGender() {
		return gender;
	}

	public OutputPreferenceType getOutputPreference() {
		return outputPreference;
	}
}
