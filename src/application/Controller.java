package application;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements Initializable {
	ObservableList<String> outerColorsChoices = FXCollections.observableArrayList();
	ObservableList<String> innerColorsChoices = FXCollections.observableArrayList();
	ObservableList<String> sizeChoices = FXCollections.observableArrayList();
	ObservableList<String> shapeChoices = FXCollections.observableArrayList();
	ObservableList<String> seedChoices = FXCollections.observableArrayList();
	ObservableList<String> exteriorChoices = FXCollections.observableArrayList();
	ObservableList<String> quirkChoices = FXCollections.observableArrayList();
	ObservableList<String> traitChoices = FXCollections.observableArrayList();
	
	@FXML 
	private ChoiceBox<String> outerColorCB, innerColorCB, sizeCB, shapeCB, seedCB, exteriorCB, quirkCB, traitCB;
	@FXML
	private TextField traitInput, fruitInput;
	@FXML
	private TextArea perfectMatchResult, potentialMatchResult, outputLog;
	Model md = new Model();
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		getChoiceBoxesOptions();
		
	}
	
	/**
	 * Action Listener for "Search fruit" button that takes the selected traits and finds fruits that matches in the system
	 * @param event This is the event that triggers the method
	 */
	@FXML
	public void searchAction(ActionEvent event) {
		if(checkSearchChoiceBoxFilled() == false)
			outputLog.appendText("Please input traits \n");
		else {
			outputLog.appendText("Search successful \n");
			perfectMatchResult.clear();
			potentialMatchResult.clear();
			String[] traits = getFruitinatorChoiceBoxInput();
			md.findFruitMatches(traits);
			String[] perfectMatches = md.getPerfectMatch();
			String[] potentialMatches = md.getPotentialMatch();
			for(int i=0; i<perfectMatches.length; i++) {
				perfectMatchResult.appendText(perfectMatches[i] + "\n");
			}
			for(int i=0; i<potentialMatches.length; i++) {
				potentialMatchResult.appendText(potentialMatches[i] + "\n");
			
			}
			
		}
	}
	
	/**
	 * Action Listener for "Add a fruit" button that takes the selected traits and fruit name and adds it to the system
	 * @param event This is the event that triggers the method
	 */
	@FXML 
	public void addFruitAction(ActionEvent event) {
		if(checkSearchChoiceBoxFilled() == false && checkFruitNameFilled() == false) 
			outputLog.appendText("Please input traits and fruit name \n");
		else if (checkSearchChoiceBoxFilled() == false)
			outputLog.appendText("Please input traits \n");
		else if (checkFruitNameFilled() == false)
			outputLog.appendText("Please input fruit name \n");
		else if (md.checkFruit(fruitInput.getText()))
			outputLog.appendText("Fruit already exists");
		else {
			String[] traits = getFruitinatorChoiceBoxInput();
			md.addFruit(fruitInput.getText(), traits);
			fruitInput.clear();
			outputLog.appendText("Fruit successfully added \n");
			getChoiceBoxesOptions();
		}	
	}
	
	/**
	 * Action Listener for "Add trait" button that takes the selected category and trait name and adds it to the system
	 * @param event This is the event that triggers the method
	 */
	@FXML
	public void addTraitAction(ActionEvent event) {
		if(checkTraitChoiceBoxFilled() == false && checkTraitNameFilled() == false) 
			outputLog.appendText("Please input trait category and name \n");
		else if (checkTraitNameFilled() == false) 
			outputLog.appendText("Please input trait name \n");
		else if (checkTraitChoiceBoxFilled() == false) 
			outputLog.appendText("Please input trait category \n");
		else if (md.checkTrait(traitInput.getText()))
			outputLog.appendText("Trait already exists");
		else {
			md.addTrait(traitCB.getValue(), traitInput.getText());
			traitInput.clear();
			getChoiceBoxesOptions();
			outputLog.appendText("Trait successfully added \n");
		}
		
	}
	
	/**
	 * Method to get the selected traits from the ChoiceBoxes
	 * @return selected traits from the ChoiceBoxes
	 */
	public String[] getFruitinatorChoiceBoxInput() {
		String[] inputs = {outerColorCB.getValue(),innerColorCB.getValue(), sizeCB.getValue(), shapeCB.getValue(), seedCB.getValue(),
							exteriorCB.getValue(),quirkCB.getValue()};
		return inputs;
		
	}
	
	/**
	 * Method to check if search ChoiceBox has been filled completely
	 * @return true if it is all filled, false if there are still blanks
	 */
	public boolean checkSearchChoiceBoxFilled() {
		if(outerColorCB.getValue() == null || innerColorCB.getValue() == null || sizeCB.getValue() == null || shapeCB.getValue() == null 
			|| seedCB.getValue() == null || exteriorCB.getValue() == null || quirkCB.getValue() == null) 
			return false;
		else
			return true;
	}
	
	/**
	 * Method to check if fruit name has been filled
	 * @return true if it is filled, false if it is still blank
	 */
	public boolean checkFruitNameFilled() {
		if(fruitInput.getText().equals(""))
			return false;
		else
			return true;
	}
	
	/**
	 * Method to check if trait ChoiceBox has been filled
	 * @return true if it is filled, false if it is still blank
	 */
	public boolean checkTraitChoiceBoxFilled() {
		if(traitCB.getValue() == null) 
			return false;
		else 
			return true;
	}
	
	/**
	 * Method to check if trait name has been filled
	 * @return true if it is filled, false if it is still blank
	 */
	public boolean checkTraitNameFilled() {
		if(traitInput.getText().equals(""))
			return false;
		else
			return true;
	}
	
	/**
	 * Method to call the methods to get all the ChoiceBox options
	 */
	public void getChoiceBoxesOptions() {
		initializeOuterColorChoiceBox();	
		initializeInnerColorChoiceBox();
		initializeSizeChoiceBox();
		initializeShapeChoiceBox();
		initializeSeedChoiceBox();
		initializeExteriorChoiceBox();
		initializeQuirkChoiceBox();
		initializeTraitChoiceBox();
	}
	
	/**
	 * Method to get the OuterColor ChoiceBox options from the Model
	 */
	public void initializeOuterColorChoiceBox() {
		String[] outerColorsList = md.getTraitsArray("Color");
		outerColorsChoices.removeAll(outerColorsChoices);
		for(int i=0; i<outerColorsList.length; i++) {
			outerColorsChoices.add(outerColorsList[i]);
		}
		outerColorCB.getItems().setAll(outerColorsChoices);
	}
	
	/**
	 * Method to get the InnerColor ChoiceBox options from the Model
	 */
	public void initializeInnerColorChoiceBox() {
		String[] innerColorsList = md.getTraitsArray("Color");
		innerColorsChoices.removeAll(innerColorsChoices);
		for(int i=0; i<innerColorsList.length; i++) {
			innerColorsChoices.add(innerColorsList[i]);
		}
		innerColorCB.getItems().setAll(innerColorsChoices);
	}
	
	/**
	 * Method to get the Size ChoiceBox options from the Model
	 */
	public void initializeSizeChoiceBox() {
		String[] sizeList = md.getTraitsArray("Size");
		sizeChoices.removeAll(sizeChoices);
		for(int i=0; i<sizeList.length; i++) {
			sizeChoices.add(sizeList[i]);
		}
		sizeCB.getItems().setAll(sizeChoices);
	}
	
	/**
	 * Method to get the Shape ChoiceBox options from the Model
	 */
	public void initializeShapeChoiceBox() {
		String[] shapeList = md.getTraitsArray("Shape");
		shapeChoices.removeAll(shapeChoices);
		for(int i=0; i<shapeList.length; i++) {
			shapeChoices.add(shapeList[i]);
		}
		shapeCB.getItems().setAll(shapeChoices);
	}
	
	/**
	 * Method to get the Seed ChoiceBox options from the Model
	 */
	public void initializeSeedChoiceBox() {
		String[] seedList = md.getTraitsArray("Seeds");
		seedChoices.removeAll(seedChoices);
		for(int i=0; i<seedList.length; i++) {
			seedChoices.add(seedList[i]);
		}
		seedCB.getItems().setAll(seedChoices);
	}
	
	/**
	 * Method to get the Exterior ChoiceBox options from the Model
	 */
	public void initializeExteriorChoiceBox() {
		String[] exteriorList = md.getTraitsArray("Exterior");
		exteriorChoices.removeAll(exteriorChoices);
		for(int i=0; i<exteriorList.length; i++) {
			exteriorChoices.add(exteriorList[i]);
		}
		exteriorCB.getItems().setAll(exteriorChoices);
	}
	
	/**
	 * Method to get the Quirk ChoiceBox options from the Model
	 */
	public void initializeQuirkChoiceBox() {
		String[] quirkList = md.getTraitsArray("Quirk");
		quirkChoices.removeAll(quirkChoices);
		for(int i=0; i<quirkList.length; i++) {
			quirkChoices.add(quirkList[i]);
		}
		quirkCB.getItems().setAll(quirkChoices);
	}
	
	/**
	 * Method to get the Trait ChoiceBox options
	 */
	public void initializeTraitChoiceBox() {
		traitChoices.removeAll(traitChoices);
		traitChoices.addAll("Color", "Size", "Shape", "Seeds", "Exterior", "Quirk");
		traitCB.getItems().setAll(traitChoices);
	}
}
