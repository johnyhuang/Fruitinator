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
	public String getCharacteristics(String characteristics) {
		StringBuilder characteristicsList = new StringBuilder();
		String result = null;
		try {
			fr = new FileReader(catalog);
			BufferedReader br = new BufferedReader(fr);
			
			String reader;
			while((reader=br.readLine())!=null) {
				String[] segmentedReader = reader.split(":");
				if(segmentedReader[0].equals(characteristics)) {
					characteristicsList.append(segmentedReader[1] + ":");
				}
			}
			result = characteristicsList.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	public boolean searchTraitExists(String search) {
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
