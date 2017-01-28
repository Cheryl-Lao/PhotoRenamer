package photo_renamer;

public class BubbleBrand extends Refrigerator {
	private String dimensions;
	private static int wattage = 100;
	public static int yearOfManufacture = 2017;
	private String colour;
	private static String level = "BubbleBrand level";

	public BubbleBrand(String dimensions, int wattage, String colour) {
		super(dimensions, wattage);
		this.colour = colour;
	}

	public BubbleBrand(String dimensions, String colour) {
		super(dimensions);
		this.colour = colour;
	}

	public static String whatIsThis() {
		return "This is a BubbleBrand Refrigerator";
	}

	public void setNewYearOfManufacture(int year) {
		super.yearOfManufacture = year;
		System.out.println("The year is: " + super.yearOfManufacture);
	}

	public int getWattage() {
		System.out.println("Is BubbleBrand wattage accessible from here?");
		return wattage;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getDimensions() {
		System.out.println("Bubble brand get dimension");
		return dimensions;
	}

	public static void main(String[] args) {
		// Main method goes here.
		//Refrigerator r = new Refrigerator("Test", 100);
		Refrigerator ref = new BubbleBrand("18 Litres", "purple");
		//Refrigerator ref = new BubbleBrand("18 Litres", "purple");
		//BubbleBrand bub = new BubbleBrand("17 Litres", 120, "silver");
		//System.out.println(r.getDimensions());
		//System.out.println(ref.yearOfManufacture);
		//System.out.println(bub.yearOfManufacture);
		System.out.println(ref.whatIsThis());
		System.out.println(((BubbleBrand)ref).level);
	}
}