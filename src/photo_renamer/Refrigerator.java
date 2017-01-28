package photo_renamer;

public class Refrigerator implements Appliance, Furniture {
	private String dimensions;
	private int wattage = 200;
	public static int yearOfManufacture = 2016;
	private static String level = "Refrigerator level";

	public Refrigerator(String dimensions, int wattage) {
		this.dimensions = dimensions;
		this.wattage = wattage;
	}

	public Refrigerator(String dimensions) {
		this.dimensions = dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
		System.out.println("Refrigerator setter: dimensions");
	}

	public String getDimensions() {
		System.out.println("Refrigerator getter: dimensions");
		return dimensions;
	}

	public void setWattage(int wattage) {
		this.wattage = wattage;
		System.out.println("Refrigerator setter: wattage");
	}

	public int getWattage() {
		System.out.println("Refrigerator getter: wattage");
		return wattage;
	}

	public static String whatIsThis() {
		System.out.println("This is a refrigerator");
		return "This is a refrigerator.";
	}

	public static void setYearOfManufacture(int year) {
		yearOfManufacture = year;
		System.out.println("Refrigerator setter: yearOfManufacture");
	}
}
