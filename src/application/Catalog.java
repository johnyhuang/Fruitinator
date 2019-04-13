package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Catalog {
	private File catalog;
	private FileWriter fw;
	private FileReader fr;
	
	public Catalog() {
		catalog = new File("Catalog.txt");
	}
	
	/**
	 * Method to get all the traits that belongs to the selected category from the system
	 * @param category This is the category of traits to be searched
	 * @return a string of all the traits that belongs to the category
	 */
	public String getTraits(String category) {
		StringBuilder characteristicsList = new StringBuilder();
		String result = null;
		try {
			fr = new FileReader(catalog);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			while((reader=br.readLine())!=null) {
				String[] segmentedReader = reader.split(":");
				if(segmentedReader[0].equals(category)) {
					characteristicsList.append(segmentedReader[1] + ":");
				}
			}
			result = characteristicsList.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	/**
	 * Method to check if a trait exists already in the system
	 * @param search This is the trait to be checked
	 * @return true if trait already exists in the system, false if it doesn't exists in the sytem
	 */
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
	
	/**
	 * Method to add a trait to the system
	 * @param entry This is the trait to be added along with its category already in the correct format
	 */
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
}
