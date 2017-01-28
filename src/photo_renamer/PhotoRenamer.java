package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Create and show a Photo Renamer GUI and links to a directory explorer, which
 * displays the contents of a directory.
 * 
 * GUI component code generated using the GUI builder in NetBeans IDE and the
 * directory explorer code is almost the same as the code from a1 part 2
 */
public class PhotoRenamer extends JFrame {
	// Variables declaration for GUI components
	private JButton btnAddTag;
	private JButton btnAvailableTags;
	private JButton btnChooseFile;
	private JButton btnOpenFileLocation;
	private JButton btnRemoveTag;
	private JButton btnRevertName;
	private JButton btnViewTagLog;
	private JLabel jLabel1;
	private JMenuItem jMenuItem1;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private static JTextPane txtMainTextArea;

	private static File currentFile = new File("PlaceHolder.png");
	private static Image currentImg;

	private static ImageManager imageManager;

	private static JFrame FileDirectoryPicker = PhotoRenamer.buildWindow();

	/**
	 * Create and show a photo renamer choice screen, which displays options to
	 * choose and manipulate a file
	 *
	 * @param argsv
	 *            the command-line arguments.
	 */
	public static void main(String[] args) {
		ImageManager.readFromFile();
		imageManager = ImageManager.getInstance();

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PhotoRenamer().setVisible(true);
			}
		});

	}

	public PhotoRenamer() {
		initComponents();

		// Keeping these fields disabled until the user chooses an image file
		this.btnAddTag.setEnabled(false);
		this.btnRemoveTag.setEnabled(false);
		this.btnRevertName.setEnabled(false);
		this.btnViewTagLog.setEnabled(false);
		this.btnOpenFileLocation.setEnabled(false);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Initializes the GUI components
	 *
	 */

	private void initComponents() {

		jMenuItem1 = new JMenuItem();
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		btnChooseFile = new JButton();
		btnAddTag = new JButton();
		btnRemoveTag = new JButton();
		btnAvailableTags = new JButton();
		btnViewTagLog = new JButton();
		jScrollPane1 = new JScrollPane();
		txtMainTextArea = new JTextPane();
		btnOpenFileLocation = new JButton();
		btnRevertName = new JButton();

		jMenuItem1.setText("jMenuItem1");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBackground(new Color(204, 255, 204));

		jLabel1.setFont(new java.awt.Font("Adobe Caslon Pro", 1, 24)); // NOI18N
		jLabel1.setText("Photo Renamer");

		btnChooseFile.setText("Choose a photo");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnChooseFileActionPerformed(evt);
			}
		});

		btnAddTag.setText("Add Tag");
		btnAddTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAddTagActionPerformed(evt);
			}
		});

		btnRemoveTag.setText("Remove Tag");
		btnRemoveTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRemoveTagActionPerformed(evt);
			}
		});

		btnAvailableTags.setText("View All Tags");
		btnAvailableTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAvailableTagsActionPerformed(evt);
			}
		});

		btnViewTagLog.setText("View Tag Log");
		btnViewTagLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnViewTagLogActionPerformed(evt);
			}
		});

		// txtMainTextArea.setColumns(20);
		// txtMainTextArea.setRows(5);

		jScrollPane1.setViewportView(txtMainTextArea);

		btnOpenFileLocation.setText("Open File Location");
		btnOpenFileLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnOpenFileLocationActionPerformed(evt);
			}
		});

		btnRevertName.setText("Revert Name");
		btnRevertName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRevertNameActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(95, 95, 95).addComponent(btnViewTagLog)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
										btnOpenFileLocation))
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(63, 63, 63).addGroup(jPanel1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
										jPanel1Layout.createSequentialGroup().addComponent(btnChooseFile)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnAvailableTags))))
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(170, 170, 170).addComponent(jLabel1))
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(136, 136, 136)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup().addComponent(btnAddTag)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btnRemoveTag))))
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(175, 175, 175)
								.addComponent(btnRevertName)))
						.addContainerGap(75, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnChooseFile).addComponent(btnAvailableTags))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnAddTag).addComponent(btnRemoveTag))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnRevertName)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnViewTagLog).addComponent(btnOpenFileLocation))
						.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
	}

	public static void setCurrentFile(File file) {

		currentFile = file;

	}

	/**
	 * Create and return the window for the PhotoRenamer * @return the window
	 * for the photo renamer
	 */

	public static JFrame buildWindow() {

		JFrame directoryFrame = new JFrame("File Renamer");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpeg", "jpg", "png", "gif", "bmp"));

		JLabel directoryLabel = new JLabel("Select an image file");

		// Set up the area for the directory contents.
		JTextArea textArea = new JTextArea(15, 50);
		textArea.setEditable(true);

		// Put it in a scroll pane in case the output is long.
		JScrollPane scrollPane = new JScrollPane(textArea);

		// The directory choosing button.
		JButton openButton = new JButton("Choose Directory");
		openButton.setVerticalTextPosition(AbstractButton.CENTER);
		openButton.setHorizontalTextPosition(AbstractButton.LEADING); // aka
																		// LEFT,
																		// for
																		// left-to-right
																		// locales
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");

		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();

					if (file.exists()) {

						directoryLabel.setText("Selected File" + file.getAbsolutePath());

						// Make an image object out of that file

						setCurrentFile(file);

						updatePicture(currentFile);

						currentImg = imageManager.getCorrespondingImg(currentFile);

						setCurrentFile(fileChooser.getSelectedFile());

						FileDirectoryPicker.setVisible(false);

						// Lines for testing
						ArrayList<Image> tester = imageManager.getImages();
						System.out.println(tester.size());

						for (int i = 0; i < tester.size(); i++) {
							System.out.println("Added " + i + " " + tester.get(i));
						}

					}
				} else {
					directoryLabel.setText("No Path Selected");
				}
			}

		});

		// Put it all together.
		Container c = directoryFrame.getContentPane();
		c.add(directoryLabel, BorderLayout.PAGE_START);
		c.add(scrollPane, BorderLayout.CENTER);
		c.add(openButton, BorderLayout.PAGE_END);

		directoryFrame.pack();
		return directoryFrame;
	}

	public static void updatePicture(File file) {

		if (file.exists()) {
			// Display the image
			ImageIcon pictureImage = new ImageIcon(file.getAbsolutePath());
			txtMainTextArea.insertIcon(pictureImage);
		}

	}

	private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {

		// Show the current window
		FileDirectoryPicker.setVisible(true);

		// Once the user chooses a file, they'll be able to access all of these
		// features. To prevent null pointers, disable some buttons before they
		// choose a file
		this.btnAddTag.setEnabled(true);
		this.btnRemoveTag.setEnabled(true);
		this.btnRevertName.setEnabled(true);
		this.btnViewTagLog.setEnabled(true);
		this.btnOpenFileLocation.setEnabled(true);
	}

	private void btnAddTagActionPerformed(ActionEvent evt) {
		updatePicture(currentFile);
		JFrame addWindow = new JFrame("Add Tag(s) to the selected image");

		ArrayList<JCheckBox> boxes = createCheckBoxes(imageManager.getMasterTags());

		JPanel checkBoxPanel = new JPanel();
		JTextField tagInput = new JTextField();

		for (JCheckBox checkBox : boxes) {
			checkBoxPanel.add(checkBox);

		}
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

		JButton ok = new JButton("Add Tag(s)");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					doAdd(boxes, tagInput.getText());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addWindow.dispose();
			}
		});

		JPanel basePanel = new JPanel(new GridLayout(0, 2, 0, 1));
		basePanel.add(checkBoxPanel);
		basePanel.add(tagInput);
		addWindow.add(basePanel, BorderLayout.CENTER);

		JLabel label = new JLabel("Select/type the tags(s) you wish to add ");
		addWindow.add(ok, BorderLayout.SOUTH);
		addWindow.add(label, BorderLayout.NORTH);
		addWindow.pack();
		addWindow.setVisible(true);

	}

	private void btnAvailableTagsActionPerformed(ActionEvent evt) {

		String allTags = "";

		for (int i = 0; i < imageManager.getMasterTags().size(); i++) {
			int j = i + 1;
			allTags += "\n" + j + ") " + imageManager.getMasterTags().get(i);
		}

		this.txtMainTextArea.setText(allTags);
	}

	private void btnRemoveTagActionPerformed(ActionEvent evt) {

		JFrame addWindow = new JFrame("Remove tags from the image. ");

		ArrayList<JCheckBox> boxes = createCheckBoxes(currentImg.getTags());

		JPanel checkBoxPanel = new JPanel();
		JTextField tagInput = new JTextField();

		for (JCheckBox checkBox : boxes) {
			checkBoxPanel.add(checkBox);

		}
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

		JButton ok = new JButton("Remove Tag(s)");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					doRemove(boxes, tagInput.getText(), false);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addWindow.dispose();
			}
		});

		JButton ok2 = new JButton("Remove Tag(s) from master list");
		ok2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					doRemove(boxes, tagInput.getText(), true);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addWindow.dispose();
			}
		});

		JPanel basePanel = new JPanel(new GridLayout(0, 2, 0, 1));
		basePanel.add(checkBoxPanel);
		basePanel.add(tagInput);
		addWindow.add(basePanel, BorderLayout.CENTER);

		JPanel basePanel2 = new JPanel();
		basePanel2.add(ok);
		basePanel2.add(ok2);
		addWindow.add(basePanel2, BorderLayout.SOUTH);

		JLabel label = new JLabel("Select/type the tags(s) you wish to remove ");
		addWindow.add(label, BorderLayout.NORTH);
		addWindow.pack();
		addWindow.setVisible(true);

	}

	private void btnRevertNameActionPerformed(ActionEvent evt) {

		// displayRevertWindow();
		JFrame revertWindow = new JFrame("Revert to Image");

		ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();
		Iterator<String> it = currentImg.iterator();
		JPanel checkBoxPanel = new JPanel();
		while (it.hasNext()) {
			JCheckBox box = new JCheckBox(it.next());
			boxes.add(box);
			checkBoxPanel.add(box);
		}
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

		JButton ok = new JButton("Revert");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doRevert(boxes);
				revertWindow.dispose();
			}
		});

		JLabel label = new JLabel("Select the version to revert to ");
		revertWindow.add(ok, BorderLayout.SOUTH);
		revertWindow.add(checkBoxPanel, BorderLayout.CENTER);
		revertWindow.add(label, BorderLayout.NORTH);
		revertWindow.pack();
		revertWindow.setVisible(true);

	}

	public void doRevert(ArrayList<JCheckBox> boxes) {
		String date = new Date().toString();
		for (JCheckBox box : boxes) {
			if (box.isSelected()) {
				date = box.getText().split(" ~ ")[0];
				break;
			}
		}
		imageManager.revert(currentImg, date);
	}

	public void doAdd(ArrayList<JCheckBox> boxes, String tag) throws FileNotFoundException {
		for (JCheckBox box : boxes) {
			if (box.isSelected()) {
				imageManager.addTagToImg(currentImg, box.getText());
			}
		}
		if (!tag.equals(null) && !tag.equals("")) {
			imageManager.addTagToImg(currentImg, tag);
		}

	}

	public void doRemove(ArrayList<JCheckBox> boxes, String tag, Boolean removeFromMaster)
			throws FileNotFoundException {
		for (JCheckBox box : boxes) {
			if (box.isSelected()) {
				imageManager.removeTagFromImg(currentImg, box.getText(), removeFromMaster);
			}
		}
		// Removing the typed tag
		if (!tag.equals(null) && !tag.equals("")) {
			imageManager.removeTagFromImg(currentImg, tag, removeFromMaster);
		}

	}

	private void btnViewTagLogActionPerformed(ActionEvent evt) {

		this.txtMainTextArea.setText(currentImg.displayImageLog());

	}

	private void btnOpenFileLocationActionPerformed(ActionEvent evt) {

		// Opening the file location
		try {
			Desktop.getDesktop().open(currentFile.getParentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<JCheckBox> createCheckBoxes(ArrayList<String> tags) {
		ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();
		for (String tag : tags) {
			System.out.println(tag);
			boxes.add(new JCheckBox(tag));
		}

		return boxes;
	}

}
