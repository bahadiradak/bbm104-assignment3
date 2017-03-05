import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 * Game
 * @author Bahadir
 */
public class Game {

	public PrintWriter writer;
	ArrayList<String> steps = new ArrayList<String>();
	/**
	 * @param args the path way of command.txt .
	 * @throws FileNotFoundException if file not found .
	 * @throws UnsupportedEncodingException if the character encoding is not supported .
	 * @throws ParseException Construct a new ParseException with an external cause .
	 * @throws org.json.simple.parser.ParseException if json-simple.jar not found .
	 */
	public void game(String[] args) throws FileNotFoundException, UnsupportedEncodingException, ParseException, org.json.simple.parser.ParseException{
		
		Read c = new Read();
		Player1 p1 = new Player1();
		Player2 p2 = new Player2();
		Banker banker = new Banker();

		c.readCommand(args[0]);
		c.readProperty();
		c.readList("list.json");
		
		ArrayList<String> last = new ArrayList<String>();
		last.add("show()");
		c.commandList.add(last);
		
		for (int i =0;i<c.commandList.size();i++){
			
			if(c.commandList.get(i).size()==2 && c.commandList.get(i).get(0).equals("Player 1")){
				
				p1.Move(c.commandList.get(i).get(0), c.commandList.get(i).get(1));
				String strPos1 = Integer.toString(p1.positionP1);
				banker.Move(strPos1, c.commandList.get(i).get(1));
				

				for(int j = 0 ; j<c.board.size();j++){
					
					String aString = Integer.toString(p1.positionP1);
					
					if(aString.equals(c.board.get(j).get(0)) && c.board.get(j).size() == 3 ){
						
						if(!(p2.p2Property.contains(c.board.get(j).get(1)))){
							
							if(!(p1.p1Property.contains(c.board.get(j).get(1)))){
								
								int cost = Integer.parseInt(c.board.get(j).get(2));
								if(p1.p1Money - cost >= 0){
									p1.Buy(c.board.get(j).get(1), c.board.get(j).get(2));
									banker.Buy(c.board.get(j).get(1), c.board.get(j).get(2));
								}
								else if (p1.p1Money - cost < 0){
									p1.setP1Processing("Player 1 goes bankrupt");
								}
							}
							else{
								p1.setP1Processing("Player 1 has " + c.board.get(j).get(1));
							}
						}
						else{
							if((p2.p2Property.contains("Electric Company") || p1.p1Property.contains("Water Works")) && (p1.positionP1 == 13 || p1.positionP1 == 29)){
								int dice = Integer.parseInt(c.commandList.get(i).get(1));
								p1.p1Money = p1.p1Money - (4*dice);
								p2.p2Money = p2.p2Money + (4*dice);
								p1.setP1Processing("Player 1 paid rent for " + c.board.get(j).get(1));
							}
							else if((p2.p2Property.contains("Kings Cross Station") || p2.p2Property.contains("Marylebone Station") || p2.p2Property.contains("Fenchurch St Station") || p2.p2Property.contains("Liverpool Street Station")) && (p1.positionP1 == 6 || p1.positionP1 == 16 || p1.positionP1 == 26 || p1.positionP1 == 36)){
								int words = 0;
								
								if(p2.p2Property.contains("Kings Cross Station")){
									words = words + 1;
								}
								if(p2.p2Property.contains("Marylebone Station")){
									words = words + 1;
								}
								if(p2.p2Property.contains("Fenchurch St Station")){
									words = words + 1;
								}
								if(p2.p2Property.contains("Liverpool Street Station")){
									words = words + 1;
								}
								
								p2.p2Money = p2.p2Money + ((words)*25);
								p1.p1Money = p1.p1Money - ((words)*25);
								p1.setP1Processing("Player 1 paid rent for " + c.board.get(j).get(1));
							}
							else if(p2.p2Property.contains(c.board.get(j).get(1))){
								p2.takeRent(c.board.get(j).get(2));
								p1.PayRent(c.board.get(j).get(2), c.board.get(j).get(1));
							}
						}						
					}	
					
					if(aString.equals(c.board.get(j).get(0)) && c.board.get(j).size() == 2 ){
						if(c.board.get(j).get(0) == "31"){
							p1.setPositionP1(11);
							p1.setP1Processing("Player 1 went to jail");
						}
						else if(c.board.get(j).get(0) == "21"){
							p1.FreeParking("21");
						}
						else if(c.board.get(j).get(0) == "5" || c.board.get(j).get(0) == "39"){
							if(p1.p1Money - 100 >= 0){
								banker.PayTax();
							}
							p1.PayTax();
						}
						else if(c.board.get(j).get(0) == "8" || c.board.get(j).get(0) == "23" || c.board.get(j).get(0) == "37"){
							if(c.chanceList.get(0).get(0).equals("Advance to Go (Collect $200)")){
								p1.AdvanceGo();
								banker.AdvanceGo();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Advance to Leicester Square")){
								if(p1.positionP1 == 37){
									p1.p1Money = p1.p1Money + 200;
									banker.bankerMoney = banker.bankerMoney - 200;
								}
								p1.AdvanceLeicester();
								if(!(banker.bankerProperty.contains("Leicester Square"))){	
									if(p1.p1Money - 2600 >=0){
										p1.Buy("Leicester Square", "2600");
										banker.Buy("Leicester Square", "2600");
										p1.setP1Processing("Player 1 draw Advance to Leicester Square Player 1 bought Leicester Square");
									}
								}
								else if(p1.p1Property.contains("Leicester Square")){
									p1.setP1Processing("Player 1 draw Advance to Leicester Square Player 1 has Leicester Square");
								}
								else if(p2.p2Property.contains("Leicester Square")){
									p1.PayRent("2600", "Leicester Square");
									p2.takeRent("2600");
									p1.setP1Processing("Player 1 draw Advance to Leicester Square Player 1 paid rent for Leicester Square");
								}
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Go back 3 spaces")){
								p1.GoBack();
								
								if(p1.positionP1 == 5){
									if(p1.p1Money - 100 >= 0){
										banker.PayTax();
									}
									p1.PayTax();
									p1.setPositionP1(5);
								}
								else if(p1.positionP1 == 20){
									if(!(banker.bankerProperty.contains("Vine Street"))){
										p1.Buy("Vine Street", "2000");
										p1.setPositionP1(20);
										if(p1.p1Money - 2000 >=0){
											banker.Buy("Vine Street", "2000");
										}
									}
									else if(p2.p2Property.contains("Vine Street")){
										p1.PayRent("2000", "Vine Street");
										p2.takeRent("2000");
										p1.setP1Processing("Player 1 draw Go back 3 spaces Player 1 paid rent for Vine Street");
									}
									else{
										p1.setP1Processing("Player 1 draw Go back 3 spaces Player 1 has Vine Street");
										p1.setPositionP1(20);
									}
								}
								else if(p1.positionP1 == 24){
									if(!(banker.bankerProperty.contains("Fleet Street"))){
										p1.Buy("Fleet Street", "2200");
										p1.setPositionP1(24);
										if(p1.p1Money - 2200 >= 0 ){
											banker.Buy("Fleet Street", "2200");
										}	
									}
									else if(p2.p2Property.contains("Fleet Street")){
										p1.PayRent("2200", "Fleet Street");
										p2.takeRent("2200");
										p1.setP1Processing("Player 1 draw Go back 3 spaces Player 1 paid rent for Fleet Street");
									}
									else{
										p1.setP1Processing("Player 1 draw Chance Go back 3 spaces Player 1 has Fleet Street");
										p1.setPositionP1(24);
									}
								}
								else if(p1.positionP1 == 34){
									if(c.communityChestList.get(0).get(0).equals("Advance to Go (Collect $200)")){
										p1.AdvanceGo();
										banker.AdvanceGo();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Advance to go (Collect $200)");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Bank error in your favor - collect $75")){
										p1.BankError();
										banker.BankError();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Bank error in your favor - collect $75");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Doctor's fees - Pay $50")){
										p1.DoctorFee();
										banker.DoctorFee();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Doctor's fees - Pay $50 ");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("It is your birthday Collect $10 from each player")){
										p1.BirthdayP1();
										p2.BirthdayP1();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw It is your birthday Collect $10 from each player");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Grand Opera Night - collect $50 from every player for opening night seats")){
										p1.OperaP1();
										p2.OperaP1();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Grand Opera Night - collect $50 from every player for opening night seats");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Income Tax refund - collect $20")){
										p1.TaxRefund();
										banker.TaxRefund();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Income Tax refund - collect $20");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Life Insurance Matures - collect $100")){
										p1.LifeInsurance();
										banker.LifeInsurance();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Life Insurance Matures - collect $100");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Pay Hospital Fees of $100")){
										p1.PayHospital();
										banker.PayHospital();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Pay Hospital Fees of $100");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Pay School Fees of $50")){
										p1.PaySchool();
										banker.PaySchool();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw Pay School Fees of $50");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("You inherit $100")){
										p1.Inherit();
										banker.Inherit();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw You inherit $100");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("From sale of stock you get $50")){
										p1.SaleStock();
										banker.SaleStock();
										p1.setP1Processing("Player 1 draw Go back 3 spaces draw From sale of stock you get $50");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
								}
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Pay poor tax of $15")){
								p1.PoorTax();
								banker.PoorTax();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Your building loan matures - collect $150")){
								p1.LoanMatures();
								banker.LoanMatures();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("You have won a crossword competition - collect $100 ")){
								p1.Compatition();
								banker.Compatition();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
						}
						else if(c.board.get(j).get(0) == "3" || c.board.get(j).get(0) == "18" || c.board.get(j).get(0) == "34"){
							if(c.communityChestList.get(0).get(0).equals("Advance to Go (Collect $200)")){
								p1.AdvanceGo();
								banker.AdvanceGo();
								p1.setP1Processing("Player 1 draw Advance to go (Collect $200) ");
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Bank error in your favor - collect $75")){
								p1.BankError();
								banker.BankError();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Doctor's fees - Pay $50")){
								p1.DoctorFee();
								banker.DoctorFee();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("It is your birthday Collect $10 from each player")){
								p1.BirthdayP1();
								p2.BirthdayP1();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Grand Opera Night - collect $50 from every player for opening night seats")){
								p1.OperaP1();
								p2.OperaP1();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Income Tax refund - collect $20")){
								p1.TaxRefund();
								banker.TaxRefund();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Life Insurance Matures - collect $100")){
								p1.LifeInsurance();
								banker.LifeInsurance();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Pay Hospital Fees of $100")){
								p1.PayHospital();
								banker.PayHospital();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Pay School Fees of $50")){
								p1.PaySchool();
								banker.PaySchool();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("You inherit $100")){
								p1.Inherit();
								banker.Inherit();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("From sale of stock you get $50")){
								p1.SaleStock();
								banker.SaleStock();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
						}
					}				
				}
				
				steps.add((c.commandList.get(i).get(0) + "\t" + c.commandList.get(i).get(1)+ "\t" + p1.positionP1 + "\t" + p1.p1Money + "\t" + p2.p2Money + "\t" + p1.getP1Processing()));

			}
			
			else if(c.commandList.get(i).size()==2 && c.commandList.get(i).get(0).equals("Player 2")){
				
				p2.Move(c.commandList.get(i).get(0), c.commandList.get(i).get(1));
				String strPos3 = Integer.toString(p2.positionP2);
				banker.Move(strPos3, c.commandList.get(i).get(1));
				
				
				for(int j = 0 ; j<c.board.size();j++){
					
					String aString = Integer.toString(p2.positionP2);
					
					if(aString.equals(c.board.get(j).get(0)) && c.board.get(j).size() == 3 ){
						
						if(!(p1.p1Property.contains(c.board.get(j).get(1)))){
							
							if(!(p2.p2Property.contains(c.board.get(j).get(1)))){

								int cost = Integer.parseInt(c.board.get(j).get(2));
								if(p2.p2Money - cost >= 0){
									p2.Buy(c.board.get(j).get(1), c.board.get(j).get(2));
									banker.Buy(c.board.get(j).get(1), c.board.get(j).get(2));
								}
								else if (p2.p2Money - cost < 0){
									p2.setP2Processing("Player 2 goes bankrupt");
								}
							}
							else{
								p2.setP2Processing("Player 2 has " + c.board.get(j).get(1));
							}
						}
						else{
							if((p1.p1Property.contains("Electric Company") || p1.p1Property.contains("Water Works")) && (p2.positionP2 == 13 || p2.positionP2 == 29)){
								int dice = Integer.parseInt(c.commandList.get(i).get(1));
								p2.p2Money = p2.p2Money - (4*dice);
								p1.p1Money = p1.p1Money + (4*dice);
								p2.setP2Processing("Player 2 paid rent for " + c.board.get(j).get(1));
							}
							else if((p1.p1Property.contains("Kings Cross Station") || p1.p1Property.contains("Marylebone Station") || p1.p1Property.contains("Fenchurch St Station") || p1.p1Property.contains("Liverpool Street Station") ) && (p2.positionP2 == 6 || p2.positionP2 == 16 || p2.positionP2 == 26 || p2.positionP2 == 36)){
								
								int words = 0;
	
								if(p1.p1Property.contains("Kings Cross Station")){
									words = words + 1;
								}
								if(p1.p1Property.contains("Marylebone Station")){
									words = words + 1;
								}
								if(p1.p1Property.contains("Fenchurch St Station")){
									words = words + 1;
								}
								if(p1.p1Property.contains("Liverpool Street Station")){
									words = words + 1;
								}
								
								p2.p2Money = p2.p2Money - ((words)*25);
								p1.p1Money = p1.p1Money + ((words)*25);
								p2.setP2Processing("Player 2 paid rent for " + c.board.get(j).get(1));
							}
							else if(p1.p1Property.contains(c.board.get(j).get(1))){
								p1.takeRent(c.board.get(j).get(2));
								p2.PayRent(c.board.get(j).get(2), c.board.get(j).get(1));
							}
						}
					}
					if(aString.equals(c.board.get(j).get(0)) && c.board.get(j).size() == 2 ){
						if(c.board.get(j).get(0) == "31"){
							p2.setPositionP2(11);
							p2.setP2Processing("Player 2 went to jail");
						}
						else if(c.board.get(j).get(0) == "21"){
							p2.FreeParking("21");
						}
						else if(c.board.get(j).get(0) == "5" || c.board.get(j).get(0) == "39"){
							if(p2.p2Money - 100 >= 0){
								banker.PayTax();
							}
							p2.PayTax();
						}
						else if(c.board.get(j).get(0) == "8" || c.board.get(j).get(0) == "23" || c.board.get(j).get(0) == "37"){
							if(c.chanceList.get(0).get(0).equals("Advance to Go (Collect $200)")){
								p2.AdvanceGo();
								banker.AdvanceGo();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Advance to Leicester Square")){
								if(p2.positionP2 == 37){
									p2.p2Money = p2.p2Money + 200;
									banker.bankerMoney = banker.bankerMoney - 200;
								}
								p2.AdvanceLeicester();
								if(!(banker.bankerProperty.contains("Leicester Square"))){
									
									if(p2.p2Money - 2600 >= 0){
										p2.Buy("Leicester Square", "2600");
										banker.Buy("Leicester Square", "2600");
										p2.setP2Processing("Player 2 draw Advance to Leicester Square Player 2 bought Leicester Square");
									}
								}
								else if((p2.p2Property.contains("Leicester Square"))){
									p2.setP2Processing("Player 2 draw Advance to Leicester Square Player 2 has Leicester Square");

								}
								else if(p1.p1Property.contains("Leicester Square")){
									p2.PayRent("2600", "Leicester Square");
									p1.takeRent("2600");
									p2.setP2Processing("Player 2 draw Advance to Leicester Square Player 2 paid rent for Leicester Square");
								}
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Go back 3 spaces")){
								p2.GoBack();
								
								if(p2.positionP2 == 5){
									if(p2.p2Money - 100 >= 0){
										banker.PayTax();
									}
									p2.PayTax();
									p2.setPositionP2(5);
								}
								else if(p2.positionP2 == 20){
									if(!(p2.p2Property.contains("Vine Street"))){
										p2.Buy("Vine Street", "2000");
										p2.setPositionP2(20);
										if(p2.p2Money - 2000 >= 0){
											banker.Buy("Vine Street", "2000");
										}
									}
									else if(p1.p1Property.contains("Vine Street")){
										p2.PayRent("2000", "Vine Street");
										p1.takeRent("2000");
										p2.setP2Processing("Player 2 draw Go back 3 spaces Player 2 paid rent for Vine Street");
									}
									else{
										p2.setP2Processing("Player 2 draw Go back 3 spaces Player 2 has Vine Street");
										p2.setPositionP2(20);
									}
								}
								else if(p2.positionP2 == 24){
									if(!(p2.p2Property.contains("Fleet Street"))){
										p2.Buy("Fleet Street", "2200");
										p2.setPositionP2(24);
										if(p2.p2Money - 2200 >= 0){
											banker.Buy("Fleet Street", "2200");
										}
									}
									else if(p1.p1Property.contains("Fleet Street")){
										p2.PayRent("2200", "Fleet Street");
										p1.takeRent("2200");
										p2.setP2Processing("Player 2 draw Go back 3 spaces Player 2 paid rent for Fleet Street");
									}
									else{
										p2.setP2Processing("Player 2 draw Go back 3 spaces Player 2 has Fleet Street");
										p2.setPositionP2(24);
									}
								}
								else if(p2.positionP2 == 34){
									if(c.communityChestList.get(0).get(0).equals("Advance to Go (Collect $200)")){
										p2.AdvanceGo();
										banker.AdvanceGo();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Advance to go (collect 200)");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Bank error in your favor - collect $75")){
										p2.BankError();
										banker.BankError();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Bank error in your favor - collect $75");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Doctor's fees - Pay $50")){
										p2.DoctorFee();
										banker.DoctorFee();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Doctor's fees - Pay $50");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("It is your birthday Collect $10 from each player")){
										p2.BirthdayP2();
										p1.BirthdayP2();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw It is your birthday Collect $10 from each player");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Grand Opera Night - collect $50 from every player for opening night seats")){
										p2.OperaP2();
										p1.OperaP2();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Grand Opera Night - collect $50 from every player for opening night seats");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Income Tax refund - collect $20")){
										p2.TaxRefund();
										banker.TaxRefund();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Income Tax refund - collect $20");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Life Insurance Matures - collect $100")){
										p2.LifeInsurance();
										banker.LifeInsurance();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Life Insurance Matures - collect $100");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Pay Hospital Fees of $100")){
										p2.PayHospital();
										banker.PayHospital();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Pay Hospital Fees of $100");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("Pay School Fees of $50")){
										p2.PaySchool();
										banker.PaySchool();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw Pay School Fees of $50");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("You inherit $100")){
										p2.Inherit();
										banker.Inherit();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw You inherit $100");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
									else if(c.communityChestList.get(0).get(0).equals("From sale of stock you get $50")){
										p2.SaleStock();
										banker.SaleStock();
										p2.setP2Processing("Player 2 draw Go back 3 spaces draw From sale of stock you get $50");
										c.communityChestList.add(c.communityChestList.get(0));
										c.communityChestList.remove(0);
									}
								}
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Pay poor tax of $15")){
								p2.PoorTax();
								banker.PoorTax();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("Your building loan matures - collect $150")){
								p2.LoanMatures();
								banker.LoanMatures();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
							else if(c.chanceList.get(0).get(0).equals("You have won a crossword competition - collect $100 ")){
								p2.Compatition();
								banker.Compatition();
								c.chanceList.add(c.chanceList.get(0));
								c.chanceList.remove(0);
							}
						}
						else if(c.board.get(j).get(0) == "3" || c.board.get(j).get(0) == "18" || c.board.get(j).get(0) == "34"){
							if(c.communityChestList.get(0).get(0).equals("Advance to Go (Collect $200)")){
								p2.AdvanceGo();
								banker.AdvanceGo();
								p2.setP2Processing("Player 2 draw Community Chest - advance to go (collect 200) ");
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Bank error in your favor - collect $75")){
								p2.BankError();
								banker.BankError();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Doctor's fees - Pay $50")){
								p2.DoctorFee();
								banker.DoctorFee();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("It is your birthday Collect $10 from each player")){
								p2.BirthdayP2();
								p1.BirthdayP2();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Grand Opera Night - collect $50 from every player for opening night seats")){
								p2.OperaP2();
								p1.OperaP2();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Income Tax refund - collect $20")){
								p2.TaxRefund();
								banker.TaxRefund();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Life Insurance Matures - collect $100")){
								p2.LifeInsurance();
								banker.LifeInsurance();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Pay Hospital Fees of $100")){
								p2.PayHospital();
								banker.PayHospital();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("Pay School Fees of $50")){
								p2.PaySchool();
								banker.PaySchool();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("You inherit $100")){
								p2.Inherit();
								banker.Inherit();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
							else if(c.communityChestList.get(0).get(0).equals("From sale of stock you get $50")){
								p2.SaleStock();
								banker.SaleStock();
								c.communityChestList.add(c.communityChestList.get(0));
								c.communityChestList.remove(0);
							}
						}
					}
				}		
				steps.add((c.commandList.get(i).get(0) + "\t" + c.commandList.get(i).get(1)+ "\t" + p2.positionP2 + "\t" + p1.p1Money + "\t" + p2.p2Money + "\t" + p2.getP2Processing()));
			}
			
			if(( c.commandList.get(i).size()==1 && c.commandList.get(i).get(0).equals("show()") )|| p2.p2Processing == "Player 2 goes bankrupt" || p1.p1Processing == "Player 1 goes bankrupt" ){
				
				String strp1Property = String.join(",", p1.p1Property);
				String strp2Property = String.join(",", p2.p2Property);
				
				steps.add(("-----------------------------------------------------------------------------------------------------------"));
				steps.add(("Player 1" + "\t" + p1.p1Money + "\t" + "have: " + strp1Property ));
				steps.add(("Player 2" + "\t" + p2.p2Money + "\t" + "have: " + strp2Property ));
				steps.add(("Banker" + "\t" + banker.bankerMoney ));
				
				if(p1.p1Money > p2.p2Money){
					steps.add(("Winner Player 1" ));
				}
				else if(p2.p2Money > p1.p1Money){
					steps.add(("Winner Player 2" ));
				}
				else if(p2.p2Money.equals(p1.p1Money)){
					steps.add(("Player1 vs. Player 2 draws" ));
				}	
				steps.add(("-----------------------------------------------------------------------------------------------------------"));
				
				if(p2.p2Processing == "Player 2 goes bankrupt" || p1.p1Processing == "Player 1 goes bankrupt"){
					c.commandList.clear();
				}
			}	
		}
	}
	/**
	 * @throws FileNotFoundException if file not found .
	 * @throws UnsupportedEncodingException if the character encoding is not supported .
	 */
	public void writer() throws FileNotFoundException, UnsupportedEncodingException{
		
		writer = new PrintWriter("output.txt", "UTF-8");
		
		for(int i = 0 ; i<steps.size() ; i++){
			writer.println(steps.get(i));
		}
		writer.close();
	}
}
