package photo_renamer;

import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.*;

/**
 * @author Madumitha Ravichandran
 * @author Cheryl Lao
 *
 */

/** An image **/
public class Image implements Serializable, Iterable<String> {

	/** The file location of the image **/
	private File imageFile;

	/** List of all the tags. **/
	private ArrayList<String> tags;

	/** Map of the time stamp to the image file at that point **/
	private HashMap<Date, File> log;

	/** the location to log to **/
	public String logPath = "./imageLog.txt";

	/** latest name of the image **/
	private String imgName;

	/** The original name of the image **/
	private String originalName;

	/** the logger for this image **/
	private static final Logger logger = Logger.getLogger(Image.class.getName());

	/** the handler that records the logs to the log path **/
	private static Handler fileHandler;

	/**
	 * Creates a new Image object
	 *
	 * @param imageFile
	 *            A file that represents the selected image file
	 * @param tags
	 *            An ArrayList of the tags that an Image has
	 * @param log
	 *            A HashMap of Timestamps to files
	 * @param imgName
	 *            The current name of the image, including any tags it may have
	 * @param originalName
	 *            The original name of the image, without tags
	 */
	public Image(File imageFile, ArrayList<String> tags, HashMap<Date, File> log, String imgName, String originalName) {
		this.imageFile = imageFile;
		this.tags = tags;
		this.log = log;
		log.put(new Date(), imageFile);
		this.imgName = imgName;
		this.originalName = originalName;
		setUpLog();
	}

	public Image(File imageFile) {

		this.imageFile = imageFile;
		this.tags = new ArrayList<String>();
		this.log = new HashMap<Date, File>();
		log.put(new Date(), imageFile);
		this.imgName = imageFile.getName();
		this.originalName = imageFile.getName();
		setUpLog();
	}

	/**
	 * Sets up a logger to log the changes to this particular image
	 */
	private void setUpLog() {
		try {
			fileHandler = new FileHandler(logPath, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		logger.addHandler(fileHandler);
		fileHandler.setFormatter(new SimpleFormatter());

	}

	/**
	 * Gives this image's name
	 * 
	 * @return The image's name
	 */
	public String getImgName() {
		return imgName;
	}

	/**
	 * Gives the list of the image's tags at the moment it was called
	 * 
	 * @return The image's present set of tags
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * Adds a new tag to the image Precondition: tag shouldn't have an '@'
	 * symbol
	 * 
	 * @param tag
	 *            the tag to be added to the image
	 * @throws FileNotFoundException
	 */
	public void addTag(String tag) throws FileNotFoundException {
		if (imageFile.exists()) {
			if (!(tags.contains(tag))) {
				String oldName = imageFile.getName();
				String path = imageFile.getAbsolutePath();
				String newName = imgName.substring(0, imgName.lastIndexOf(".")) + "@" + tag
						+ imgName.substring(imgName.lastIndexOf("."));
				String newPath = path.replace(oldName, newName);
				Boolean ok = imageFile.renameTo(new File(newPath));

				if (ok) {
					// update the file name if it was renamed
					updateFileName(newName);
					tags.add(tag);
					System.out.println("Successfully added tag" + oldName + " " + tag);

					logger.log(Level.FINE, "added " + tag + " to " + imgName + "\n");

				} else {
					System.out.println("Something wrong in adding tag");
					logger.log(Level.SEVERE, newName + "already exists so cannot overwrite it " + "\n");

				}

			}

			else {
				System.out.println("Tag already exists");
			}
		}

		else {
			throw new FileNotFoundException(imageFile.getPath() + ": Image file does not exist");
		}
	}

	/**
	 * Updates the name of the file after adding or removing a tag and logs it
	 * 
	 * @param newname
	 *            the new name to be given to the image
	 */
	public void updateFileName(String newName) {
		String path = imageFile.getAbsolutePath();
		String newPath = path.replace(imageFile.getName(), newName);
		imageFile = new File(newPath);
		imgName = imageFile.getName();
		log.put(new Date(), this.imageFile);
	}

	/**
	 * Removes the given tag from the image's tags and the image's name. If the
	 * tag doesn't exist, it doesn't do anything.
	 * 
	 * @param tag
	 *            the tag to be removed from the image
	 * @throws FileNotFoundException
	 */
	public void removeTag(String tag) throws FileNotFoundException {

		if (imageFile.exists()) {
			// Search for tag in imgName
			if (imgName.contains("@" + tag)) {
				String oldName = imageFile.getName();
				String path = imageFile.getAbsolutePath();
				String newName = imgName.replace("@" + tag, "");
				String newPath = path.replace(oldName, newName);
				Boolean ok = imageFile.renameTo(new File(newPath));

				if (ok && tags.contains(tag)) {
					// Update the file name if file can be renamed
					updateFileName(newName);
					tags.remove(tag);
					logger.log(Level.FINE, "removed " + tag + " from " + imgName + "\n");
					System.out.println("Successfully removed" + tag + " " + oldName + "-> " + newName + " " + tag);
				} else {
					System.out.println("Something wrong in removing tag");
					logger.log(Level.SEVERE, newName + " already exists so cannot overwrite it " + "\n");
				}
			}

			else {
				System.out.println("Tag does not exist, so no changes were made");
			}
		} else {
			throw new FileNotFoundException("Image does not exist");
		}
	}

	/**
	 * Reverts the image's specifications to a particular time as chosen by the
	 * user
	 * 
	 * @param dates
	 *            the date to revert to
	 * 
	 */
	public void revertName(String dates) {

		Date date = new Date();

		for (Date d : log.keySet()) {
			if (d.toString().equals(dates)) {
				date = d;
			}
		}

		File newPath = log.get(date); // get the file at that time
		Boolean ok = imageFile.renameTo(newPath);
		if (ok) {
			System.out.println("Successfully reverted");

			String message = "reverted " + imgName + " to " + date + " date ";
			// Update the file name to name
			imgName = log.get(date).getName();
			message += "the new name is " + imgName;

			logger.log(Level.FINE, message + "\n");

		} else {
			System.out.println("Something wrong in reverting to old file");
			logger.log(Level.SEVERE, " a file of the same name as " + imgName + " at " + date
					+ " date already exists, so no changes made" + "\n");
		}

		imageFile = log.get(date);

		log.put(date, imageFile);
	}

	/**
	 * Returns a string version of an Image's log
	 * 
	 * @return The log in a formatted string
	 */
	public String displayImageLog() {
		String logs = imgName + "'s log: \n";

		for (Date key : log.keySet()) {
			logs += "\n" + key.toString() + "  " + log.get(key).toString();
		}
		return logs;
	}

	/**
	 * Returns the image name
	 * 
	 * @return The image's name
	 */
	public String toString() {
		return this.imgName;

	}

	/**
	 * Returns the original name of the image
	 * 
	 * @return The original name of the image
	 */
	public String getOriginalName() {
		return this.originalName;

	}

	/**
	 * Returns this image's file path
	 * 
	 * @return the file path of the image
	 */

	public File getImageFile() {
		return this.imageFile;
	}

	/**
	 * Returns an iterator for the chronological list (oldest first, latest
	 * last) of this image's previous names.
	 * 
	 * @return an iterator for chronological list of this image's previous
	 *         names.
	 */
	@Override
	public Iterator<String> iterator() {
		return new ImageNameIterator();
	}

	/**
	 * Iterator for previous image names
	 *
	 */
	private class ImageNameIterator implements Iterator<String> {
		/** Arraylist of the dates **/
		ArrayList<Date> dates = new ArrayList<Date>(log.keySet());
		/** The iterator of the ArrayList **/
		Iterator<Date> it;

		/**
		 * The chronological iterator of log
		 */
		public ImageNameIterator() {
			// sort the dates
			Collections.sort(dates);
			it = dates.iterator();
		}

		@Override
		/**
		 * Returns whether the iterator has a next item
		 * 
		 * @return whether the iterator has a next item
		 */
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		/**
		 * Returns the next item in order
		 * 
		 * @return the next item in order
		 */
		public String next() {
			Date date = it.next();
			System.out.println(date);
			// return the date along with its corresponding log entry
			return date.toString() + " ~ " + log.get(date).getName();
		}

	}

	@Override
	/**
	 * Returns if the given object is equal to this image
	 * 
	 * @param obj
	 *            the object to compare the image to
	 * @return whether this image is equal to the object
	 */
	public boolean equals(Object obj) {
		if ((obj.getClass()).equals(this.getClass())) {
			Image newImg = ((Image) obj);
			if ((newImg.getImageFile().equals(this.imageFile)) && (newImg.getTags().equals(this.tags))) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

}