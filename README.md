# Fruitinator :banana:

Fruitinator is a project developed for UPH's Operation Systems 2018/2019 class. It is a program for predicting fruits names from the given traits.

## Getting Started

### Prerequisites

What things you need to install the software and how to install them

+ Java 8 JDK or above

### Installing

A step by step series of examples that tell you how to get a development env running

1. Download the file from the github repository

2. Compile and run main.java on your Java IDE

### Restrictions & Scope

Restrictions  and Scope of fruitinator

+ Fruitinator is designed to work with only fruits, it may not run correctly with items outside of fruits.

+ Fruitinator is made using JavaFX 9, JDK 8 or above is required.

## Fruitinator Tour
Quick tour of Fruitinator!
### Features:

#### Search fruit

Fruitinator is able to take 7 trait catagories and find the fruit that matches and potentially matches the given traits from a wide range of other fruits.
![search_fruit](https://github.com/johnyhuang/Fruitinator/blob/master/Demos/fruitinator_search.gif)

#### Add trait

Fruitinator is able to constantly expand its traits catalog by allowing Users to input traits that are not yet available into the system.
![add_trait](https://github.com/johnyhuang/Fruitinator/blob/master/Demos/fruitinator_add_trait.gif)

#### Add fruit

Fruitinator is able to constantly expand its fruits database to be able to match more fruits by allowing Users to add even more fruits data to the system.
![add_fruit](https://github.com/johnyhuang/Fruitinator/blob/master/Demos/fruitinator_add_fruit.gif)

## Explanations
This section will explain only the important techniques used to make fruitinator.

### Model.java
Model.java is responsible for the logic behind the program

#### findFruitMatches

findFruitMatches takes the parameter an array of String traits used to find matching fruits and updates it in the model.

```java
public void findFruitMatches(String[] traits) {
		String[] searchResults = db.searchFruit(traits);
		perfectMatch = new ArrayList<String>();
		potentialMatch = new ArrayList<String>();
		if(searchResults == null) {
			perfectMatch.add("No Matches");
			potentialMatch.add("No Matches");
		} else {
			boolean switchMatch = false;
			for(int i=0; i<searchResults.length; i++) {
				if(searchResults[i].equals(":")) {
					switchMatch = true;
					continue;
				}
				if(switchMatch == false) {
					perfectMatch.add(searchResults[i]);
				}else{
					potentialMatch.add(searchResults[i]);
				}
			}
			if(perfectMatch.isEmpty()) {
				perfectMatch.add("No Matches");
			}else if(potentialMatch.isEmpty()) {
				potentialMatch.add("No Matches");
			}
		}
	}
```

#### getPerfectMatch

getPerfectMatch returns an array of String containing perfect matches to the specified traits stored in the model.

```java
public String[] getPerfectMatch() {
		return perfectMatch.toArray(new String[perfectMatch.size()]);
	}
```

#### getPotentialMatch

getPotentialMatch returns an array of String containing potential matches to the specified traits stored in the model.

```java
public String[] getPotentialMatch() {
		return potentialMatch.toArray(new String[potentialMatch.size()]);
	}
```

#### addTrait

addTrait takes the parameter String category and String name used to build the trait entry to be added in the catalog.

```java
public void addTrait(String category, String name) {
		ct.addCatalogEntry(category + ":" + name);
	}
```

#### addFruit

addFruit takes the parameter String name and array of String traits used to build the fruit entry to be added in the database.

```java
public void addFruit(String name, String[] traits) {
		db.addFruitEntry(name + ":" + traits[0] + ":" + traits[1] + ":" + traits[2] + ":" + traits[3] + ":"
							+ traits[4] + ":" + traits[5] + ":" + traits[6]);
	}
```

### Controller.java
Controller.java is responsible for the interaction between the user through the GUI and the system.

#### searchAction

searchAction serves as the action listener for the "Search fruit" button. When the button is clicked it will get the selected traits, find matching fruits and display it in the Match and Potential Match TextAreas.
```java
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
```

#### addFruitAction

addFruitAction serves as the action listener for the "Add a fruit" button. When the button is clicked it will get the selected traits and fruit name, and add it to the system.
```java
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
```

#### addTraitAction

addTraitAction serves as the action listener for the "Add trait" button. When the button is clicked it will get the selected category and trait name, and add it to the system.
```java
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
	
```

### View.fxml
View.fxml is responsible for the UI that the user will interact with.

#### View.fxml
```fxml
<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="750.0" prefWidth="1000.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="application.Controller">
   <children>
      <TextField editable="false" layoutX="334.0" layoutY="20.0" style="-fx-alignment: center; -fx-background-color: transparent;" text="Fruitinator">
         <font>
            <Font name="System Bold" size="25.0" />
         </font>
      </TextField>
      <HBox layoutY="81.0" prefHeight="62.0" prefWidth="1000.0">
         <children>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Outer Color" />
                  <ChoiceBox fx:id="outerColorCB" prefWidth="150.0" />
               </children>
            </VBox>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Inner Color" />
                  <ChoiceBox fx:id="innerColorCB" prefWidth="150.0" />
               </children>
            </VBox>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Size" />
                  <ChoiceBox fx:id="sizeCB" prefWidth="150.0" />
               </children>
            </VBox>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Shape" />
                  <ChoiceBox fx:id="shapeCB" prefWidth="150.0" />
               </children>
            </VBox>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Seeds" />
                  <ChoiceBox fx:id="seedCB" prefWidth="150.0" />
               </children>
            </VBox>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Exterior" />
                  <ChoiceBox fx:id="exteriorCB" prefWidth="150.0" />
               </children>
            </VBox>
            <VBox prefHeight="63.0" prefWidth="141.0">
               <children>
                  <TextField editable="false" style="-fx-alignment: center;" text="Quirk" />
                  <ChoiceBox fx:id="quirkCB" prefWidth="150.0" />
               </children>
            </VBox>
         </children>
         <padding>
            <Insets left="5.0" right="5.0" />
         </padding>
      </HBox>
      <VBox layoutX="280.0" layoutY="293.0" prefHeight="200.0" prefWidth="440.0">
         <children>
            <TextField editable="false" style="-fx-alignment: center;" text="Matches" />
            <TextArea fx:id="perfectMatchResult" editable="false" prefHeight="111.0" prefWidth="440.0" />
            <TextField editable="false" style="-fx-alignment: center;" text="Pontential Matches" />
            <TextArea fx:id="potentialMatchResult" editable="false" prefHeight="112.0" prefWidth="440.0" />
         </children>
      </VBox>
      <Button layoutX="280.0" layoutY="208.0" mnemonicParsing="false" onAction="#searchAction" prefHeight="50.0" prefWidth="150.0" text="Search" textAlignment="CENTER">
         <font>
            <Font size="20.0" />
         </font>
      </Button>
      <Separator layoutY="493.0" prefHeight="31.0" prefWidth="1000.0" />
      <HBox layoutY="509.0" prefHeight="243.0" prefWidth="1000.0">
         <children>
            <AnchorPane prefHeight="243.0" prefWidth="1017.0">
               <children>
                  <Button layoutX="425.0" layoutY="155.0" mnemonicParsing="false" onAction="#addTraitAction" prefHeight="74.0" prefWidth="150.0" text="Add trait" textAlignment="CENTER">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </Button>
                  <TextField editable="false" layoutX="334.0" layoutY="2.0" style="-fx-alignment: center; -fx-background-color: transparent;" text="Add a Trait">
                     <font>
                        <Font name="System Bold" size="25.0" />
                     </font>
                  </TextField>
                  <VBox layoutX="421.0" layoutY="56.0" prefHeight="93.0" prefWidth="158.0">
                     <children>
                        <TextField editable="false" style="-fx-alignment: center; -fx-background-color: transparent;" text="Select Trait Catagory" />
                        <ChoiceBox fx:id="traitCB" prefHeight="31.0" prefWidth="160.0" />
                        <TextField fx:id="traitInput" promptText="Input Trait" style="-fx-alignment: center;" />
                     </children>
                  </VBox>
               </children>
            </AnchorPane>
         </children>
      </HBox>
      <VBox layoutX="570.0" layoutY="176.0" prefHeight="82.0" prefWidth="150.0">
         <children>
            <TextField fx:id="fruitInput" promptText="Fruit name" style="-fx-alignment: center;" />
            <Button mnemonicParsing="false" onAction="#addFruitAction" prefHeight="50.0" prefWidth="150.0" text="Add a fruit" textAlignment="CENTER">
               <font>
                  <Font size="20.0" />
               </font>
            </Button>
         </children>
      </VBox>
      <VBox layoutX="14.0" layoutY="149.0" prefHeight="354.0" prefWidth="217.0">
         <children>
            <TextArea editable="false" prefHeight="151.0" prefWidth="217.0" text="About&#10;Welcome to Fruitinator. There &#10;are 3 features that are:&#10;1. Seach&#10;2. Add a fruit&#10;3. Add a trait&#10;" />
            <Separator prefWidth="200.0" />
            <TextArea prefHeight="197.0" prefWidth="217.0" text="Help&#10;1. Search: &#10;- Select the fruit traits&#10;- Select &quot;Search&quot; button&#10;2. Add a fruit:&#10;- Select the fruit traits&#10;- Input fruit name&#10;- Select &quot;Add a fruit&quot; button&#10;3. Add a trait:&#10;- Select trait catagory&#10;- Input trait&#10;- Select &quot;Add trait&quot; button&#10;" />
         </children>
      </VBox>
      <VBox layoutX="769.0" layoutY="149.0" prefHeight="354.0" prefWidth="217.0">
         <children>
            <TextField editable="false" style="-fx-alignment: center;" text="Output Log" />
            <TextArea fx:id="outputLog" editable="false" prefHeight="337.0" prefWidth="217.0" />
         </children>
      </VBox>
   </children>
</AnchorPane>
```

### Catalog.java
Catalog.java is the class responsible for operations related to the traits of fruits data.

#### getTraits

getTraits takes the parameter that is a String catagory which is used to get a list of traits that belong to that catagory from Catalog.txt and returns it.
```java
public String getTraits(String catagory) {
		StringBuilder characteristicsList = new StringBuilder();
		String result = null;
		try {
			fr = new FileReader(catalog);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			while((reader=br.readLine())!=null) {
				String[] segmentedReader = reader.split(":");
				if(segmentedReader[0].equals(catagory)) {
					characteristicsList.append(segmentedReader[1] + ":");
				}
			}
			result = characteristicsList.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
```
#### checkTraitExists

checkTraitExists takes the parameter String search which is compared to the traits in Catalog.txt and returns a true value if a match is found.
 ```java
 public boolean checkTraitExists(String search) {
		try {
			fr = new FileReader(catalog);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			while((reader=br.readLine())!=null) {
				String[] segmentedResult = reader.split(":");
				if(segmentedResult[1].equals(search)) {
					return true;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
 ```
 #### addCatalogEntry
 
 addCatalogEntry takes the parameter String entry and appends it to Catalog.txt.
 ```java
 public void addCatalogEntry(String entry) {
		try {
			fw = new FileWriter(catalog, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(entry);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 ```

### Database.java
Database.java is the class responsible for operations related to the fruits and its traits data.

#### searchFruit

searchFruit takes the parameter String[] traits and compares it to the traits of each fruits from Fruits List.txt. If every trait matches then it is added to Vector<String> allMatch, and if more than half and less than all traits matches it is added to Vector<String> potentialMatch. The combination of both allMatch and potentialMatch is then returned by the method.
```java
public String[] searchFruit(String[] traits) {
		try {
			fr = new FileReader(this.database);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			Vector<String> potentialMatch = new Vector<String>();
			Vector<String> allMatch = new Vector<String>();
			while((reader=br.readLine())!=null) {
				String[] segmentedReader = reader.split(":");
				int matches = 0;
				for(int i=1; i<segmentedReader.length; i++) {
					if(segmentedReader[i].equals(traits[i-1])) {
						matches++;
					}				
				}
				if(matches==segmentedReader.length-1) {
					allMatch.add(segmentedReader[0]);
				} else if (matches>=segmentedReader.length/2 && matches<segmentedReader.length) {
					potentialMatch.add(segmentedReader[0]);
				}
			}
			allMatch.add(":");
			allMatch.addAll(potentialMatch);
			String[] result = allMatch.toArray(new String[allMatch.size()]);
			if(allMatch.size()>1) {
				return result;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
```

#### checkFruitExists

checkFruitExists takes the parameter String search and compares it to the names of each fruit in Fruit List.txt and returns a true value if a match is found.
```java
public boolean checkFruitExists(String search) {
		try {
			fr = new FileReader(this.database);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			while((reader=br.readLine())!=null) {
				String[] segmentedResult = reader.split(":");
				if(segmentedResult[0].equals(search)) {
					return true;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
```

#### addFruitEntry

addFruitEntry takes the parameter String entry and appends it to Fruit List.txt.
```java
public void addFruitEntry(String entry) {
		try {
			fw = new FileWriter(this.database, true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(entry);
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
```

### Main.java
Main.java is responsible as the entry point of the program and initializes the components.

#### Main.java
```java
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("View.fxml"));
			Scene scene = new Scene(root,1000,750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Fruitinator");
			primaryStage.getIcons().add(new Image("file:icon.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
```

## Built With

* [Eclipse](https://www.eclipse.org/) - The IDE used
* [Java 9](https://www.java.com/en/) - Language used
* [JavaFX Scene Builder 2.0](https://gluonhq.com/products/scene-builder/) - Used for making GUI

## Authors

* **Johny Huang** - TIF UPH 2017 
* **Nicholas** - TIF UPH 2017
* **Nicholas Chen** - TIF UPH 2017
* **Dave Joshua** - TIF UPH 2017
* **Leon Chrisdion** - TIF UPH 2017
* **Sutedja The Ho Ping** - TIF UPH 2017

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Icon made by Freepik from www.flaticon.com

