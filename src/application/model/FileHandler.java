package application.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileHandler {

	public enum Browser {
		Chrome//, Firefox, Opera, Edge, IE
	};

	private StringProperty pathFile;
	private IntegerProperty browser;
	private IntegerProperty fromLine;
	private IntegerProperty toLine;
	private BooleanProperty isIncognito;

	public FileHandler() {
		this(null, 0, 0, 0, true);
	}

	public FileHandler(String pathFile, int browser, int fromLine, int toLine, boolean isIncognito) {
		this.pathFile = new SimpleStringProperty(pathFile);
		this.browser = new SimpleIntegerProperty(browser);
		this.fromLine = new SimpleIntegerProperty(fromLine);
		this.toLine = new SimpleIntegerProperty(toLine);
		this.isIncognito = new SimpleBooleanProperty(isIncognito);
	}

	/*
	 * ============= GETTERS AND SETTERS =============
	 */
	public String getPathFile() {
		return pathFile.get();
	}

	public void setPathFile(String pathFile) {
		this.pathFile.set(pathFile);
	}

	public StringProperty pathFileProperty() {
		return pathFile;
	}

	public int getBrowser() {
		return browser.get();
	}

	public void setBrowser(int browser) {
		this.browser.set(browser);
	}

	public IntegerProperty browserProperty() {
		return browser;
	}

	public int getFromLine() {
		return fromLine.get();
	}

	public void setFromLine(int fromLine) {
		this.fromLine.set(fromLine);
	}

	public IntegerProperty fromLineProperty() {
		return fromLine;
	}

	public int getToLine() {
		return toLine.get();
	}

	public void setToLine(int toLine) {
		this.toLine.set(toLine);
	}

	public IntegerProperty toLineProperty() {
		return toLine;
	}

	public boolean getIncognito() {
		return isIncognito.get();
	}

	public void setIncognito(boolean isIncognito) {
		this.isIncognito.set(isIncognito);
	}

	public BooleanProperty isIncognitoProperty() {
		return isIncognito;
	}

}
