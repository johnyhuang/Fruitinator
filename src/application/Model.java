package application;

import java.util.ArrayList;

public class Model {

	private Catalog ct;
	private Database db;
	private ArrayList<String> perfectMatch;
	private ArrayList<String> potentialMatch;
	
	public Model() {
		ct = new Catalog();
		db = new Database();
	}
	
	/**
	 * Method to get traits within a category
	 * @param category Category of traits to be searched
	 * @return traits as an array
	 */
	public String[] getTraitsArray(String category) {
		String traitsList = ct.getTraits(category);
		String[] traitsArray = traitsList.split(":");
		return traitsArray;
	}
	
	/**
	 * Method to add a trait to catalog
	 * @param category Category of trait
	 * @param name Name of trait
	 */
	public void addTrait(String category, String name) {
		ct.addCatalogEntry(category + ":" + name);
	}
	
	/**
	 * Method to add a fruit to database
	 * @param name Name of fruit
	 * @param traits Traits of the fruit
	 */
	public void addFruit(String name, String[] traits) {
		db.addFruitEntry(name + ":" + traits[0] + ":" + traits[1] + ":" + traits[2] + ":" + traits[3] + ":"
							+ traits[4] + ":" + traits[5] + ":" + traits[6]);
	}
	
	/**
	 * Method to check if a trait already exists in catalog
	 * @param trait Trait to be checked
	 * @return true if trait exists, false if not
	 */
	public boolean checkTrait(String trait) {
		return ct.checkTraitExists(trait);
	}
	
	/**
	 * Method to check if a fruit already exists in database
	 * @param fruit Fruit to be checked
	 * @return true if fruit exists, false if not
	 */
	public boolean checkFruit(String fruit) {
		return db.checkFruitExists(fruit);
	}
	
	/**
	 * Method to find the fruits that matches the traits and updates it in the model
	 * @param traits Array of traits to be matched
	 */
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
	
	/**
	 * Method to get the array of fruits that perfectly match the traits
	 * @return Array of perfect matching fruits or if no match then "No Match"
	 */
	public String[] getPerfectMatch() {
		return perfectMatch.toArray(new String[perfectMatch.size()]);
	}
	
	/**
	 * Method to get the array of fruits that potentially match the traits
	 * @return Array of potentially matching fruits or if no match then "No Match"
	 */
	public String[] getPotentialMatch() {
		return potentialMatch.toArray(new String[potentialMatch.size()]);
	}
}
