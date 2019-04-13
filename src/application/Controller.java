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
	Catalog ct = new Catalog();
	Database db = new Database();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		getChoiceBoxesOptions();
		
	}
	@FXML
	public void searchAction(ActionEvent event) {
		if(checkSearchChoiceBoxFilled() == false)
			outputLog.appendText("Please input traits \n");
		else {
			outputLog.appendText("Search successful \n");
			perfectMatchResult.clear();
			potentialMatchResult.clear();
			String[] traits = getFruitinatorChoiceBoxInput();
			String[] searchResults = db.searchFruit(traits);
			Boolean switchMatch = false;
			if(searchResults == null) {
				perfectMatchResult.appendText("No Matches");
				potentialMatchResult.appendText("No Matches");
			}else {
				for(int i=0; i<searchResults.length; i++) {
					if(searchResults[i].equals(":")) {
						switchMatch = true;
						continue;
					}
					if(switchMatch == false) {
						perfectMatchResult.appendText(searchResults[i]+"\n");
					}else{
						potentialMatchResult.appendText(searchResults[i]+"\n");
					}
				}
				if(perfectMatchResult.getText().equals("")) {
					perfectMatchResult.appendText("No Matches");
				}else if(potentialMatchResult.getText().equals("")) {
					potentialMatchResult.appendText("No Matches");
				}
			}
			getChoiceBoxesOptions();
		}
	}
	@FXML 
	public void addFruitAction(ActionEvent event) {
		if(checkSearchChoiceBoxFilled() == false && checkFruitNameFilled() == false) 
			outputLog.appendText("Please input traits and fruit name \n");
		else if (checkSearchChoiceBoxFilled() == false)
			outputLog.appendText("Please input traits \n");
		else if (checkFruitNameFilled() == false)
			outputLog.appendText("Please input fruit name \n");
		else if (db.checkFruitExists(fruitInput.getText()))
			outputLog.appendText("Fruit already exists");
		else {
			String[] traits = getFruitinatorChoiceBoxInput();
			db.addFruitEntry(fruitInput.getText() + ":" + traits[0] + ":" + traits[1] + ":" + traits[2] + ":" + traits[3] + ":"
							+ traits[4] + ":" + traits[5] + ":" + traits[6]);
			fruitInput.clear();
			outputLog.appendText("Fruit successfully added \n");
			getChoiceBoxesOptions();
		}	
	}
	@FXML
	public void addTraitAction(ActionEvent event) {
		if(checkTraitChoiceBoxFilled() == false && checkTraitNameFilled() == false) 
			outputLog.appendText("Please input trait catagory and name \n");
		else if (checkTraitNameFilled() == false) 
			outputLog.appendText("Please input trait name \n");
		else if (checkTraitChoiceBoxFilled() == false) 
			outputLog.appendText("Please input trait catagory \n");
		else if (ct.searchTraitExists(traitInput.getText()))
			outputLog.appendText("Trait already exists");
		else {
			ct.addCatalogEntry(traitCB.getValue() + ":" + traitInput.getText());
			traitInput.clear();
			getChoiceBoxesOptions();
			outputLog.appendText("Trait successfully added \n");
		}
		
	}
	public String[] getFruitinatorChoiceBoxInput() {
		String[] inputs = {outerColorCB.getValue(),innerColorCB.getValue(), sizeCB.getValue(), shapeCB.getValue(), seedCB.getValue(),
							exteriorCB.getValue(),quirkCB.getValue()};
		return inputs;
		
	}
	public boolean checkSearchChoiceBoxFilled() {
		if(outerColorCB.getValue()==null || innerColorCB.getValue() == null || sizeCB.getValue() == null || shapeCB.getValue() == null 
			|| seedCB.getValue() == null || exteriorCB.getValue() == null || quirkCB.getValue() == null) 
			return false;
		else
			return true;
	}
	public boolean checkFruitNameFilled() {
		if(fruitInput.getText().equals(""))
			return false;
		else
			return true;
	}
	public boolean checkTraitChoiceBoxFilled() {
		if(traitCB.getValue() == null) 
			return false;
		else 
			return true;
	}
	public boolean checkTraitNameFilled() {
		if(traitInput.getText().equals(""))
			return false;
		else
			return true;
	}
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
	public void initializeOuterColorChoiceBox() {
		String outerColors = ct.getTraits("Color");
		String[] outerColorsList = outerColors.split(":");
		outerColorsChoices.removeAll(outerColorsChoices);
		for(int i=0; i<outerColorsList.length; i++) {
			outerColorsChoices.add(outerColorsList[i]);
		}
		outerColorCB.getItems().setAll(outerColorsChoices);
	}
	public void initializeInnerColorChoiceBox() {
		String innerColors = ct.getTraits("Color");
		String[] innerColorsList = innerColors.split(":");
		innerColorsChoices.removeAll(innerColorsChoices);
		for(int i=0; i<innerColorsList.length; i++) {
			innerColorsChoices.add(innerColorsList[i]);
		}
		innerColorCB.getItems().setAll(innerColorsChoices);
	}
	public void initializeSizeChoiceBox() {
		String sizes = ct.getTraits("Size");
		String[] sizeList = sizes.split(":");
		sizeChoices.removeAll(sizeChoices);
		for(int i=0; i<sizeList.length; i++) {
			sizeChoices.add(sizeList[i]);
		}
		sizeCB.getItems().setAll(sizeChoices);
	}
	public void initializeShapeChoiceBox() {
		String shapes = ct.getTraits("Shape");
		String[] shapeList = shapes.split(":");
		shapeChoices.removeAll(shapeChoices);
		for(int i=0; i<shapeList.length; i++) {
			shapeChoices.add(shapeList[i]);
		}
		shapeCB.getItems().setAll(shapeChoices);
	}
	public void initializeSeedChoiceBox() {
		String seeds= ct.getTraits("Seeds");
		String[] seedList = seeds.split(":");
		seedChoices.removeAll(seedChoices);
		for(int i=0; i<seedList.length; i++) {
			seedChoices.add(seedList[i]);
		}
		seedCB.getItems().setAll(seedChoices);
	}
	public void initializeExteriorChoiceBox() {
		String exteriors = ct.getTraits("Exterior");
		String[] exteriorList = exteriors.split(":");
		exteriorChoices.removeAll(exteriorChoices);
		for(int i=0; i<exteriorList.length; i++) {
			exteriorChoices.add(exteriorList[i]);
		}
		exteriorCB.getItems().setAll(exteriorChoices);
	}
	public void initializeQuirkChoiceBox() {
		String quirks = ct.getTraits("Quirk");
		String[] quirkList = quirks.split(":");
		quirkChoices.removeAll(quirkChoices);
		for(int i=0; i<quirkList.length; i++) {
			quirkChoices.add(quirkList[i]);
		}
		quirkCB.getItems().setAll(quirkChoices);
	}
	public void initializeTraitChoiceBox() {
		traitChoices.removeAll(traitChoices);
		traitChoices.addAll("Color", "Size", "Shape", "Seeds", "Exterior", "Quirk");
		traitCB.getItems().setAll(traitChoices);
	}
}
