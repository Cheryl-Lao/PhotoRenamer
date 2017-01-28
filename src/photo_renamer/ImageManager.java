package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Manager of all the images in use along with the tags that have ever been used
 **/

public class ImageManager implements Serializable {

	/** List of all the tags across all images **/
	private ArrayList<String> masterTags;

	/** List of all image objects in use **/
	private ArrayList<Image> images;

	/** the location of the serialized images and master tags **/
	private static String filePath = "./Objects.ser";

	/** The only instance of image manager **/
	private static ImageManager imgManager = null;

	private ImageManager() {
		masterTags = new ArrayList<String>();
		images = new ArrayList<Image>();

	}

	private ImageManager(ArrayList<String> masterTags, ArrayList<Image> images) {
		this.masterTags = masterTags;
		this.images = images;
	}

	/**
	 * Returns the only instance of the ImageManager
	 * 
	 * @return the only instance of ImageManager
	 */

	public static ImageManager getInstance() {
		if (imgManager == null) {
			imgManager = new ImageManager();
		}
		return imgManager;
	}

	/**
	 * Reads the object serialized at the .ser file path given and returns it
	 * 
	 */

	public static void readFromFile() {
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<Image> image = new ArrayList<Image>();
		File file = new File(filePath);

		if (file.exists()) {
			// Reads serializable objects from file.
			try {
				InputStream files = new FileInputStream(file);

				InputStream buffer = new BufferedInputStream(files);

				ObjectInput input = new ObjectInputStream(buffer);

				// deserialize the object
				Object[] obj = (Object[]) input.readObject();
				tags = (ArrayList<String>) obj[0];
				image = (ArrayList<Image>) obj[1];

				System.out.println("Read From .ser: " + file);

				input.close();
			}

			catch (IOException ex) {
				// ex.printStackTrace();
				System.out.println("ReadFromFile: Can't read from ser file " + file);
			}

			// should we do this or throw an exception
			catch (ClassNotFoundException ce) {
				System.out.println("class not found for the serialized object");
			}

		}

		else {
			System.out.println(".ser file not found");
			try {
				file.createNewFile();
			}

			catch (IOException e) {
				System.out.println("Could not create a new file");
			}

		}
		imgManager = new ImageManager(tags, image);
	}

	/**
	 * Writes the images and masterTags to file at file path.
	 */
	public void saveToFile() {
		// store the master tags and images in an object array to encapsulate it
		// into one object
		Object[] obj = { masterTags, images };

		try {
			OutputStream file = new FileOutputStream(filePath);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);

			// serialize the list of master tags
			output.writeObject(obj);
			output.close();

		} catch (IOException ie) {
			ie.printStackTrace();
			System.out.println("Couldn't save changes");

		}

	}

	/**
	 * Adds a new image to the list of all images in use
	 * 
	 * @param image
	 *            the image object to add to the list of all images
	 */
	public void addImage(Image image) {
		if (!(images.contains(image))) {
			images.add(image);
		}
		saveToFile();
	}

	/**
	 * Creates a new image object for the image file and adds the object to the
	 * list of all images in use.
	 * 
	 * @param image
	 *            the image object to add to the list of all images
	 */
	public void addImage(File file) {
		Image image = new Image(file);
		addImage(image);

	}

	/**
	 * Adds a tag to the image specified
	 * 
	 * @param image
	 *            the image to add the tag to
	 * @param tag
	 *            the tag to add to the image
	 * @throws FileNotFoundException
	 */
	public void addTagToImg(Image image, String tag) throws FileNotFoundException {
		// add the tag to the image
		image.addTag(tag);
		addTagToMasterList(tag);
		saveToFile();

	}

	/**
	 * Remove a given tag from the given image
	 * 
	 * @param image
	 *            the image to remove the tag from
	 * @param tag
	 *            the tag to be removed from image
	 * @throws FileNotFoundException
	 */
	public void removeTagFromImg(Image image, String tag) throws FileNotFoundException {
		// remove the tag from image if the image has the tag
		if (image.getTags().contains(tag)) {
			image.removeTag(tag);
			saveToFile();
		}

	}

	/**
	 * Remove a given tag from the given image
	 * 
	 * @param image
	 *            the image to remove the tag from
	 * @param tag
	 *            the tag to be removed from image
	 * @param removeFromMasterList
	 *            whether to remove the tag from the master list as well
	 * @throws FileNotFoundException
	 */
	public void removeTagFromImg(Image image, String tag, boolean removeFromMasterList) throws FileNotFoundException {
		// remove the tag from image
		removeTagFromImg(image, tag);
		if (removeFromMasterList && masterTags.contains(tag)) {
			masterTags.remove(tag);
		}
	}

	/**
	 * Add tag to the master list of all tags
	 * 
	 * @param tag
	 *            the tag to add to the master list
	 */
	public void addTagToMasterList(String tag) {
		// Add the tag to the master list if it is not already inside
		if (!masterTags.contains(tag)) {
			masterTags.add(tag);
			saveToFile();
		}
	}

	/**
	 * Revert a particular image to the date given
	 * 
	 * @param image
	 *            the image which is to be changed
	 * @param date
	 *            the date to revert the image to
	 */
	public void revert(Image image, String date) {
		image.revertName(date);
		saveToFile();
	}

	/**
	 * Returns the image object that corresponds to the given file. If no
	 * corresponding image object is found, then it creates a new image object
	 * from the file and adds it to images
	 * 
	 * @param file
	 *            the file name of the expected image
	 * @return image corresponding to the file location
	 * 
	 **/
	public Image getCorrespondingImg(File file) {
		Image image = null;
		// search through images
		for (Image im : images) {
			// see if any of their file names has the file name
			if (im.getImageFile().getAbsolutePath().equals(file.getAbsolutePath())) {
				image = im;
				break;
			}
		}
		// if file is not found, make a new one and add it
		if (image == null) {
			image = new Image(file);
			addImage(image);
		}

		// return that object
		return image;

	}

	/**
	 * Gives the images
	 * 
	 * @return the images
	 */
	public ArrayList<Image> getImages() {
		return images;
	}

	/**
	 * Gives the master list of all tags
	 * 
	 * @return master list of all tags
	 */
	public ArrayList<String> getMasterTags() {
		return masterTags;
	}

	/**
	 * Removes a tag from the master list of all tags
	 * 
	 * @param tag
	 *            the tag to remove from the master list
	 */
	public void removeFromMasterTags(String tag) {
		masterTags.remove(tag);
		saveToFile();
	}
}
