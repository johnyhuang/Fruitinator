package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Vector;


public class Database {
	private File database;
	private FileReader fr;
	private FileWriter fw;
	
	public Database() {
		database = new File("Fruits List.txt");
	}
	
	/**
	 * Method to check if fruit exists already in the system
	 * @param search This is the name of the fruit to be checked
	 * @return true if fruit already exists in the system, false if it doesn't exists in the system
	 */
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
	
	/**
	 * Method to find fruits that matches the selected traits
	 * @param traits This is an array that contains all the traits inputed by the user
	 * @return a string containing name of fruits that matches all the selected traits and matches more than half of the selected traits. 
	 */
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
	
	/**
	 * Method to add a fruit with its traits to the system
	 * @param entry This contains the fruit name and traits already in the correct format
	 */
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
	
}
