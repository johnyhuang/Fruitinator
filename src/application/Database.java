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
	
	public String[] searchDatabaseCharacteristic(String[] characteristics) {
		try {
			fr = new FileReader(this.database);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			Vector<String> potentialMatch = new Vector<String>();
			Vector<String> allMatch = new Vector<String>();
			while((reader=br.readLine())!=null) {
				String[] resultCharacteristics = reader.split(":");
				int matches = 0;
				for(int i=1; i<resultCharacteristics.length; i++) {
					if(resultCharacteristics[i].equals(characteristics[i-1])) {
						matches++;
					}				
				}
				if(matches==resultCharacteristics.length-1) {
					allMatch.add(resultCharacteristics[0]);
				} else if (matches>=resultCharacteristics.length/2 && matches<resultCharacteristics.length) {
					potentialMatch.add(resultCharacteristics[0]);
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
