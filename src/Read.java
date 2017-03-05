import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * Read
 * @author Bahadir
 */
public class Read {
	
	ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> landList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> railroadList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> companyList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> chanceList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> communityChestList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> commandList = new ArrayList<ArrayList<String>>();
	
	/**
	 * @throws ParseException Construct a new ParseException with an external cause .
	 */
	
	public void readProperty() throws ParseException {
		
		try {
			JSONParser parser = new JSONParser();
			Object property = parser.parse(new FileReader("property.json"));	
			JSONObject jsonObject0 = (JSONObject) property;
			
			JSONArray land =(JSONArray) jsonObject0.get("1");
			JSONArray railroads =(JSONArray) jsonObject0.get("2");
			JSONArray company =(JSONArray) jsonObject0.get("3");

			for(int i=0;i<land.size() ;i++)
			{
				ArrayList<String> temporary = new ArrayList<String>();
				JSONObject x=(JSONObject) land.get(i);
				String L_id=(String) x.get("id");
				temporary.add(L_id);
				String L_name=(String)x.get("name");
				temporary.add(L_name);	
				String L_cost=(String) x.get("cost");
				temporary.add(L_cost);
				landList.add(temporary);
			}
			board.addAll(landList);
			for(int j=0;j<railroads.size();j++)
			{
				ArrayList<String> temporary = new ArrayList<String>();
				JSONObject y=(JSONObject) railroads.get(j);
				String R_id=(String) y.get("id");
				temporary.add(R_id);
				String R_name=(String)y.get("name");
				temporary.add(R_name);
				String R_cost=(String) y.get("cost");
				temporary.add(R_cost);
				railroadList.add(temporary);
			}
			board.addAll(railroadList);
			for(int k=0;k<company.size();k++)
			{
				ArrayList<String> temporary = new ArrayList<String>();
				JSONObject z=(JSONObject) company.get(k);
				String C_id=(String) z.get("id");
				temporary.add(C_id);
				String C_name=(String)z.get("name");
				temporary.add(C_name);
				String C_cost=(String) z.get("cost");
				temporary.add(C_cost);
				companyList.add(temporary);
			}
			board.addAll(companyList);
			
			addBoard("1", "GO");
			addBoard("3", "Community Chest");
			addBoard("5", "Income Tax");
			addBoard("8", "Chance");
			addBoard("11", "Jail");
			addBoard("18", "Community Chest");
			addBoard("21", "Free Parking");
			addBoard("23", "Chance");
			addBoard("31", "Go to Jail");
			addBoard("34", "Community Chest");
			addBoard("37", "Chance");
			addBoard("39", "Super Tax");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param path read the list.json file .
	 * @throws ParseException Construct a new ParseException with an external cause .
	 */
	public void readList(String path)  throws ParseException{
		
		try{
			JSONParser parser = new JSONParser();
			Object list = parser.parse(new FileReader("list.json"));	
			JSONObject jsonObject1 = (JSONObject) list;
			
			JSONArray chance =(JSONArray) jsonObject1.get("chanceList");
			JSONArray communityChest =(JSONArray) jsonObject1.get("communityChestList");
			
			for(int l=0;l<chance.size() ;l++)
			{
				ArrayList<String> temporary = new ArrayList<String>();
				JSONObject x=(JSONObject) chance.get(l);
				String chance_item=(String) x.get("item");
				temporary.add(chance_item.replace("–", "-"));
				chanceList.add(temporary);
			}
			for(int m=0;m<communityChest.size() ;m++)
			{
				ArrayList<String> temporary = new ArrayList<String>();
				JSONObject x=(JSONObject) communityChest.get(m);
				String communityChest_item=(String) x.get("item");
				temporary.add(communityChest_item.replace("–", "-"));
				communityChestList.add(temporary);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param path the path way of command.txt .
	 * @return command line by adding array list .
	 */
	public String[] readCommand(String path){
		try{
			int i =0;
			int lenght = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String [lenght];
				for (String line : Files.readAllLines(Paths.get(path))){
					results[i++] = line;
					ArrayList<String> temporary = new ArrayList<String>(Arrays.asList(line.split("\\;")));				
					commandList.add(temporary);	
				}
			return results;
		}		
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**   
	 * @param i other place which is not include property i is its id .
	 * @param s other place which is not include property s is its name .
	 */
	public void addBoard(String i , String s){
		ArrayList<String> stuff = new ArrayList<String>();
		stuff.add(i);
		stuff.add(s);
		board.add(stuff);
	}
}