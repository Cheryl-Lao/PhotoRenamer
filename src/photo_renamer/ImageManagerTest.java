package photo_renamer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageManagerTest {
	File imageFile, imageFile2, imageFile3, imageFile4;
	Image sample1, sample2, sample3, sample4;
	ImageManager imageManager;

	@Before
	public void setUp() throws Exception {
		imageFile = new File("./sample.jpg");
		sample1 = new Image(imageFile);

		imageFile2 = new File("./sample2.jpg");
		sample2 = new Image(imageFile2);

		imageFile3 = new File("./sample3.jpg");
		sample3 = new Image(imageFile3);

		imageFile4 = new File("./sample4.jpg");
		sample4 = new Image(imageFile4);

		imageManager = ImageManager.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		imageManager = null;
	}

	@Test
	public void testGetInstance() {
		assertEquals(imageManager, ImageManager.getInstance());
	}

	@Test
	public void testAddImageOneImage() {
		// What the new arraylist of images should look like:
		ArrayList<Image> expected = new ArrayList<Image>();
		expected.add(sample1);

		imageManager.addImage(sample1);
		assertEquals(imageManager.getImages(), expected);
	}

	@Test
	public void testAddImageMoreImages() {
		// Now try adding another image to the list of images

		// What the new arraylist of images should look like:
		ArrayList<Image> expected = new ArrayList<Image>();
		expected.add(sample1);
		expected.add(sample2);

		imageManager.addImage(sample1);
		imageManager.addImage(sample2);
		assertEquals(imageManager.getImages(), expected);
	}

	@Test
	public void testAddImageForDuplicates() {
		// What the new arraylist of images should look like:
		ArrayList<Image> expected = new ArrayList<Image>();
		expected.add(sample1);
		expected.add(sample2);

		imageManager.addImage(sample1);
		imageManager.addImage(sample2);

		// Try adding an image that is already in the list of images
		// addImage() should not add the image
		imageManager.addImage(sample2);
		ArrayList<Image> got = imageManager.getImages();
		System.out.println("mm  " + got);
		assertEquals(got, expected);
	}

	@Test
	public void testAddImageUsingImageFile() {
		ArrayList<Image> expected = new ArrayList<Image>();
		// since ImageManager is singleton, the changes from the previous
		// methods hold, so imageManager would already have
		// sample1 and sample2
		expected.add(sample1);
		expected.add(sample2);
		expected.add(sample3);
		imageManager.addImage(imageFile3);
		assertEquals(expected, imageManager.getImages());
	}

	@Test
	public void testRemoveTagFromImg() throws FileNotFoundException {
		if (imageFile3.exists()) {
			System.out.println("The boston terrier exists!");
		}

		imageManager.addTagToImg(sample3, "CutePuppy");

		// Remove the tag we just added
		imageManager.removeTagFromImg(sample3, "CutePuppy");

		// The arraylist of tags for the picture should be empty
		ArrayList<String> expected = new ArrayList<String>();

		assertEquals(expected, sample3.getTags());
	}

	@Test
	public void RemoveTagFromImgWithBoolean() throws FileNotFoundException {
		// --- Test the overload constructor to see if it removes from the
		// master list of tags
		imageManager.addTagToImg(sample4, "CutePuppy2");
		// Remove the tag we just added and remove it from the master list of
		// tags
		imageManager.removeTagFromImg(sample4, "CutePuppy2", true);

		ArrayList<String> expected = new ArrayList<String>();
		// add the tags that were added in previous methods.
		expected.add("NEWTAG");
		expected.add("CutePuppy");

		// The tag should not be in the master list of tags
		assertEquals(expected, imageManager.getMasterTags());
	}

	@Test
	public void testAddTagToMasterList() {
		// --- Testing adding a new tag to the master list of tags
		String newTag = "NEWTAG";
		ArrayList<String> expected = new ArrayList<String>();
		expected.add(newTag);

		imageManager.addTagToMasterList(newTag);

		assertEquals(expected, imageManager.getMasterTags());
	}

	@Test
	public void testAddTagToMasterListDuplicate() {
		String newTag = "NEWTAG";
		ArrayList<String> expected = new ArrayList<String>();
		expected.add(newTag);
		expected.add("CutePuppy"); // since it was added in a previous method
		// --- Testing adding a tag that's already in the master tags list
		imageManager.addTagToMasterList(newTag);
		imageManager.addTagToMasterList(newTag);
		// masterTags shouldn't change
		assertEquals(expected, imageManager.getMasterTags());
	}

	@Test
	public void testAddTag() throws FileNotFoundException {
		// --- Test to check that the tag is being added to the image's file
		// name and tags list
		Image sample2 = new Image(imageFile2);
		sample2.addTag("AnotherTag");
		assertEquals(sample2.getImgName(), "sample2@AnotherTag.jpg");

		ArrayList<String> expectedTags = new ArrayList<String>();
		expectedTags.add("AnotherTag");

		assertEquals(sample2.getTags(), expectedTags);

		// --- Attempt to add a duplicate tag. It should not add it

		// Remove the tag to reset for later tests
		sample2.removeTag("AnotherTag");
	}

	@Test
	public void testRemoveTag() throws FileNotFoundException {
		// --- Test to check that the tag is being removed from the image's file
		// name and tags list
		Image sample2 = new Image(imageFile3);

		sample2.addTag("AnotherTag");
		sample2.removeTag("AnotherTag");

		assertEquals(sample2.getImgName(), "sample3.jpg");

		ArrayList<String> expectedTags = new ArrayList<String>();

		assertEquals(sample2.getTags(), expectedTags);

		// --- Remove the tag from the master tags list too

		ArrayList<String> newMasterTags = imageManager.getMasterTags();

		sample2.addTag("AnotherTag");
		imageManager.removeTagFromImg(sample2, "AnotherTag", true);

		assertEquals(newMasterTags, imageManager.getMasterTags());

	}

}