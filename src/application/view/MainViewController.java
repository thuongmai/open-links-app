package application.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import application.Main;
import application.model.FileHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MainViewController {

	@FXML
	private TextField pathFile;

	@FXML
	private ComboBox<FileHandler.Browser> browser;

	@FXML
	private TextField fromLine;

	@FXML
	private TextField toLine;

	@FXML
	private CheckBox incognito;

	@FXML
	private Button openFile;

	@FXML
	private Button goBtn;

	@SuppressWarnings("unused")
	private Main mainApp;
	// private FileHandler fileHandler;

	@FXML
	private void initialize() {
		populateBrowser();
		incognito.setSelected(true);
	}

	@FXML
	private void handleGoBtn() {
		boolean readAllLines = false;
		try {
			// Check if pathFile is legit
			File tempFile = new File(this.pathFile.getText());
			if (!tempFile.exists()) {
				pathFileError();
				return;
			}
			// Check if fromLine < toLine
			int _fromLine = Integer.parseInt(this.fromLine.getText());
			int _toLine = Integer.parseInt(this.toLine.getText());
			if (_fromLine > _toLine) {
				fromLineError();
				return;
			}
			// If by default is 0 for both textFields, then read all in the text file
			if (_fromLine == 0 && _toLine == 0) {
				readAllLines = true;
			}

			// Handle file
			// fileHandler = new FileHandler(this.pathFile.getText(), 0, _fromLine, _toLine,
			// this.incognito.isSelected());

			// Read text file
			BufferedReader reader = new BufferedReader(new FileReader(tempFile));
			String eachLine = "";
			List<String> storeLinks = new ArrayList<String>();
			int countLine = 1;
			while ((eachLine = reader.readLine()) != null) {
				if (!readAllLines) {
					if (countLine >= _fromLine && countLine <= _toLine)
						storeLinks.add(eachLine);
					else if (countLine > _toLine)
						break;
					countLine++;
				}
				else
					storeLinks.add(eachLine);
			}
			reader.close();

			//Reorganize all links from Arraylist
			for (String line : storeLinks) {
				if (line.isEmpty() || line == null)
					storeLinks.remove(line);
			}
			// Open link to web browser
			// Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome
			// http://goo.gl/EsomR0 -incognito"});

			String[] cmd;// = new String[] { "cmd", "/c", "start" +
							// handleBrowser(this.browser.getSelectionModel().getSelectedIndex())
							// +"http://www.google.ca -incognito" };
			Runtime run = Runtime.getRuntime();
			Process pr;
			String startLinks = "";
			for (String link : storeLinks) {
				startLinks += " " + link + " ";
			}
			int browserIndex = this.browser.getSelectionModel().getSelectedIndex();
			cmd = new String[] { "cmd", "/c", "start" + handleBrowser(browserIndex) + startLinks
					+ (this.incognito.isSelected() ? openIncognito(browserIndex) : "") };
			pr = run.exec(cmd);
			pr.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleOpenBtn() {
		// Clear PathFile textField first
		pathFile.clear();

		// Set current directory and filter extension files
		File workingDirectory = new File(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text (.*txt)", "txt");

		// Create and Open file chooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open .txt file");
		fileChooser.setCurrentDirectory(workingDirectory);
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Get current file path if Open button selected
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			pathFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
		else
			pathFile.clear();
	}

	@FXML
	private void setFromLineToNumericalTextField() {
		setNumericalTextField(fromLine);
	}

	@FXML
	private void setToLineToNumericalTextField() {
		setNumericalTextField(toLine);
	}

	/*
	 * Custom text field to numerical text field (only accept numbers)
	 */
	private void setNumericalTextField(TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	/*
	 * Populate Browser enum to the combo box and select first one
	 */
	private void populateBrowser() {
		ObservableList<FileHandler.Browser> BrowserOptions = FXCollections
				.observableArrayList(FileHandler.Browser.values());
		browser.setItems(BrowserOptions);
		browser.getSelectionModel().selectFirst();
	}

	/*
	 * Set error warning color for textField boxes
	 */
	@FXML
	private void pathFileFocus() {
		this.pathFile.setStyle("-fx-background-color: white;");
	}

	private void pathFileError() {
		this.pathFile.setStyle("-fx-background-color: red;");
	}

	@FXML
	private void fromLineFocus() {
		this.fromLine.setStyle("-fx-background-color: white;");
		this.toLine.setStyle("-fx-background-color: white;");
	}

	private void fromLineError() {
		this.fromLine.setStyle("-fx-background-color: red;");
		this.toLine.setStyle("-fx-background-color: red;");
	}

	/*
	 * Handle browser start chrome www.google.ca gmail.com -incognito start firefox
	 * -new-tab -url www.google.ca -new-tab -url gmail.com -private
	 * 
	 */
	private String handleBrowser(int browser) {
		// if (browser == (int)FileHandler.Browser.Chrome.ordinal())
		String cmdBrowser = "";
		switch (browser) {
		case 0:
			cmdBrowser = " chrome ";
			break;
		/*
		 * case 1: cmdBrowser = " firefox  "; break; case 2: cmdBrowser = " opera ";
		 * break; case 3: cmdBrowser = " microsoft-edge: "; break; //start
		 * microsoft-edge:http://edgetalk.net case 4: cmdBrowser = " iexplore "; break;
		 */
		}
		return cmdBrowser;
	}

	/*
	 * handle private tab
	 */
	private String openIncognito(int browser) {
		String incognitoText = "";
		switch (browser) {
		case 0:
			incognitoText = " -incognito ";
			break;
		/*
		 * case 1: incognitoText = " -private  "; break;
		 */
		}
		return incognitoText;
	}
}
