import java.util.ArrayList;
/**
 * Player2
 * @author Bahadir
 */
public class Player2 extends Rules{
	
	ArrayList<String> p2Property = new ArrayList<String>();
	
	Integer p2Money = 15000;
	Integer positionP2 = 1;
	String player,place,price,p2Processing;
	
	/**
	 * @return p2Processing
	 */
	public String getP2Processing() {
		return p2Processing;
	}
	/**
	 * @param p2Processing
	 */
	public void setP2Processing(String p2Processing) {
		this.p2Processing = p2Processing;
	}
	/**
	 * @return positionP2
	 */
	public Integer getPositionP2() {
		return positionP2;
	}
	/**
	 * @param positionP2
	 */
	public void setPositionP2(Integer positionP2) {
		this.positionP2 = positionP2;
	}

	@Override
	public void Move(String a, String b) {
		
		this.player = a;
		int dice = Integer.parseInt((String) b);
		
		for (int i = 0 ; i< dice; i++){
			this.positionP2 = positionP2+1;
			if(positionP2%41 == 0){
				this.p2Money = p2Money + 200;
				this.positionP2 = positionP2%40 ;
				setP2Processing("Player 2 is in GO square");	
			}
		}
		if(positionP2 == 11){
			this.setP2Processing("Player 2 went to jail");
		}
		else if(p2Processing == "Player 2 went to jail"){
			this.positionP2 = 11;
			this.setP2Processing("Player 2 in jail (count=1)");
		}
		else if(p2Processing == "Player 2 in jail (count=1)"){
			this.positionP2 = 11;
			this.setP2Processing("Player 2 in jail (count=2)");
		}
		else if(p2Processing == "Player 2 in jail (count=2)"){
			this.positionP2 = 11;
			this.setP2Processing("Player 2 in jail (count=3)");
		}
	}

	@Override
	public void Buy(String a, String b) {
		
		this.place = a;
		this.price = b;
		
		int cost = Integer.parseInt((String) b);
		Banker banker = new Banker();
		
		if (!(banker.bankerProperty.contains(a))){

			if(this.p2Money - cost > 0){
				setP2Processing("Player 2 bought " + a);
				this.p2Money = p2Money - cost;
				this.p2Property.add(a);
			}
			else if(this.p2Money - cost <0){
				setP2Processing("Player 2 goes bankrupt");
			}
		}
	}
	
	@Override
	public void PayRent(String a,String b) {
		
		this.price = a;
		this.place = b;
		
		int cost = Integer.parseInt((String) a);
		
		if (cost <= 2000){
			if(p2Money - cost*40/100 >= 0){
				this.p2Money = p2Money - cost*40/100;
				setP2Processing("Player 2 paid rent for " + b);
			}
			else if (p2Money - cost*40/100 < 0){
				setP2Processing("Player 2 goes bankrupt");
			}
		}
		else if (cost <= 3000){
			if(p2Money - cost*30/100 >= 0){
				this.p2Money = p2Money - cost*30/100;
				setP2Processing("Player 2 paid rent for " + b);
			}
			else if (p2Money - cost*30/100< 0){
				setP2Processing("Player 2 goes bankrupt");
			}
		}
		else if(cost <= 4000){
			if(p2Money - cost*35/100 >= 0){
				this.p2Money = p2Money - cost*35/100;
				setP2Processing("Player 2 paid rent for " + b);
			}
			else if(p2Money - cost*35/100< 0){
				setP2Processing("Player 2 goes bankrupt");
			}
		}
	}
	
	@Override
	public void takeRent(String a) {
		
		this.price = a;
		
		int cost = Integer.parseInt((String) a);
		
		if (cost <= 2000){
			this.p2Money = p2Money + (cost*40)/100;
		}
		else if (cost <= 3000){
			this.p2Money = p2Money + (cost*30)/100;
		}
		else if(cost <= 4000){
			this.p2Money = p2Money + (cost*35)/100;
		}	
	}
	
	@Override
	public void FreeParking(String a) {
		setP2Processing("Player 2 is in Free Parking");	
	}

	@Override
	public void PayTax() {
		if(p2Money - 100 >= 0){
			this.p2Money = p2Money - 100 ;
			setP2Processing("Player 2 paid Tax");
		}
		else if(p2Money - 100 < 0){
			setP2Processing("Player 2 goes bankrupt");
		}
	}

	@Override
	public void AdvanceGo() {
		this.p2Money = p2Money + 200 ;
		this.positionP2 = 1 ;
		setP2Processing("Player 2 draw Advance to go (Collect $200)");
		
	}

	@Override
	public void GoBack() {
		this.positionP2 = positionP2 - 3 ;
		
	}

	@Override
	public void AdvanceLeicester() {
		this.positionP2 = 27 ;
		setP2Processing("Player 2 draw Advance to go Leicester Square");
		
	}

	@Override
	public void PoorTax() {
		this.p2Money = p2Money - 15;
		this.setP2Processing("Player 2 draw Pay poor tax of $15 and paid tax");
		
	}

	@Override
	public void LoanMatures() {
		this.p2Money = p2Money + 150;
		this.setP2Processing("Player 2 draw Your building loan matures - collect $150 and collected money");

	}

	@Override
	public void Compatition() {
		this.p2Money = p2Money + 100;
		this.setP2Processing("Player 2 draw You have won a crossword competition - collect $100 and collected money");
		
	}

	@Override
	public void BankError() {
		this.p2Money = p2Money + 75;
		this.setP2Processing("Player 2 draw Bank error in your favor - collect $75 and collected money");	
	}

	@Override
	public void DoctorFee() {
		this.p2Money = p2Money - 50;
		this.setP2Processing("Player 2 draw Doctor's fees - Pay $50 and paid money");	
	}

	@Override
	public void BirthdayP1() {
		this.p2Money = p2Money - 10;
	}

	@Override
	public void BirthdayP2() {
		this.p2Money = p2Money + 10;
		this.setP2Processing("Player 2 draw It is your birthday Collect $10 from each player and collected money");	
	}

	@Override
	public void OperaP1() {
		this.p2Money = p2Money - 50;
	}

	@Override
	public void OperaP2() {
		this.p2Money = p2Money + 50;
		this.setP2Processing("Player 2 draw Grand Opera Night - collect $50 from every player for opening night seats and collected money");
		
	}

	@Override
	public void TaxRefund() {
		this.p2Money = p2Money +20;
		this.setP2Processing("Player 2 draw Income Tax refund - collect $20 and collected money");	
	}

	@Override
	public void LifeInsurance() {
		this.p2Money = p2Money +100;
		this.setP2Processing("Player 2 draw Life Insurance Matures - collect $100 and collected money");
	}

	@Override
	public void PayHospital() {
		this.p2Money = p2Money - 100;
		this.setP2Processing("Player 2 draw Pay Hospital Fees of $100 and paid money");
	}
	
	@Override
	public void PaySchool() {
		this.p2Money = p2Money -50;
		this.setP2Processing("Player 2 draw Pay School Fees of $50 and paid money");
	}

	@Override
	public void Inherit() {
		this.p2Money = p2Money +100;
		this.setP2Processing("Player 2 draw You inherit $100 and collected money");
	}

	@Override
	public void SaleStock() {
		this.p2Money = p2Money +50;
		this.setP2Processing("Player 2 draw From sale of stock you get $50 collected money");
	}
}
