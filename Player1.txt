import java.util.ArrayList;
/**
 * Player1
 * @author Bahadir
 */
public class Player1 extends Rules {

	Integer p1Money = 15000;
	Integer positionP1 = 1;
	String player,place,price,p1Processing;
	
	/**
	 * @return p1Processing
	 */
	public String getP1Processing() {
		return p1Processing;
	}
	/**
	 * @param p1Processing
	 */
	public void setP1Processing(String p1Processing) {
		this.p1Processing = p1Processing;
	}
	ArrayList<String> p1Property = new ArrayList<String>();
	/**
	 * @return p1Money
	 */
	public Integer getP1Money() {
		return p1Money;
	}
	/**
	 * @param p1Money
	 */
	public void setP1Money(Integer p1Money) {
		this.p1Money = p1Money;
	}
	/**
	 * @return place
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @param place
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Override
	public void Move(String a, String b) {
		
		this.player = a;
		int dice = Integer.parseInt((String) b);
		
		for (int i = 0 ; i< dice; i++){
			this.positionP1 = positionP1+1;
			if(positionP1%41 == 0){
				this.p1Money = p1Money + 200;
				this.positionP1 = positionP1%40 ;
				setP1Processing("Player 1 is in GO square");	
			}
		}
		if(positionP1 == 11){
			this.setP1Processing("Player 1 went to jail");
		}
		else if(p1Processing == "Player 1 went to jail" ){
			this.positionP1 = 11;
			this.setP1Processing("Player 1 in jail (count=1)");
		}
		else if(p1Processing == "Player 1 in jail (count=1)"){
			this.positionP1 = 11;
			this.setP1Processing("Player 1 in jail (count=2)");
		}
		else if(p1Processing == "Player 1 in jail (count=2)"){
			this.positionP1 = 11;
			this.setP1Processing("Player 1 in jail (count=3)");
		}
	}

	@Override
	public void Buy(String a, String b) {
		
		this.place = a;
		this.price = b;

		int cost = Integer.parseInt((String) b);
		Banker banker = new Banker();
		
		if (!(banker.bankerProperty.contains(a))){

			if(this.p1Money - cost > 0){
				setP1Processing("Player 1 bought " + a);
				this.p1Money = p1Money - cost;
				this.p1Property.add(a);
			}
			else if(this.p1Money - cost < 0){
				setP1Processing("Player 1 goes bankrupt");
			}
		}
	}

	@Override
	public void PayRent(String a,String b) {
		
		this.price = a;
		this.place = b;
		
		int cost = Integer.parseInt((String) a);

		if (cost <= 2000){
			if(p1Money - cost*40/100 >= 0){
				this.p1Money = p1Money - cost*40/100;
				setP1Processing("Player 1 paid rent for " + b);
			}
			else if (p1Money - cost*40/100 < 0){
				setP1Processing("Player 1 goes bankrupt");
			}
		}
		else if (cost <= 3000){
			if(p1Money - cost*30/100 >= 0){
				this.p1Money = p1Money - cost*30/100;
				setP1Processing("Player 1 paid rent for " + b);
			}
			else if (p1Money - cost*30/100< 0){
				setP1Processing("Player 1 goes bankrupt");
			}
		}
		else if(cost <= 4000){
			if(p1Money - cost*35/100 >= 0){
				this.p1Money = p1Money - cost*35/100;
				setP1Processing("Player 1 paid rent for " + b);
			}
			else if(p1Money - cost*35/100< 0){
				setP1Processing("Player 1 goes bankrupt");
			}
		}
	}
	
	@Override
	public void takeRent(String a) {
		
		this.price = a;
		
		int cost = Integer.parseInt((String) a);
		
		if (cost <= 2000){
			this.p1Money = p1Money + cost*40/100;

		}
		else if (cost <= 3000){
			this.p1Money = p1Money + cost*30/100;

		}
		else if(cost <= 4000){
			this.p1Money = p1Money + cost*35/100;

		}
	}
	@Override
	public void FreeParking(String a) {
		setP1Processing("Player 1 is in Free Parking");
		
	}

	@Override
	public void PayTax() {
		if(p1Money - 100 >= 0){
			this.p1Money = p1Money - 100 ;
			setP1Processing("Player 1 paid Tax");
		}
		else if(p1Money - 100 < 0){
			setP1Processing("Player 1 goes bankrupt");
		}
	}

	@Override
	public void AdvanceGo() {
		this.p1Money = p1Money + 200 ;
		this.positionP1 = 1 ;
		setP1Processing("Player 1 draw Advance to go (Collect $200)");
	}

	@Override
	public void GoBack() {
		this.positionP1 = positionP1 - 3 ;
	}

	@Override
	public void AdvanceLeicester() {
		this.positionP1 = 27;
		setP1Processing("Player 1 draw Advance to go Leicester Square");
		
	}

	public Integer getPositionP1() {
		return positionP1;
	}

	public void setPositionP1(Integer positionP1) {
		this.positionP1 = positionP1;
	}

	@Override
	public void PoorTax() {
		this.p1Money = p1Money - 15;
		this.setP1Processing("Player 1 draw Pay poor tax of $15 and paid tax");
	}

	@Override
	public void LoanMatures() {
		this.p1Money = p1Money + 150;
		this.setP1Processing("Player 1 draw Your building loan matures - collect $150 and collected money");
	}

	@Override
	public void Compatition() {
		this.p1Money = p1Money + 100;
		this.setP1Processing("Player 1 draw You have won a crossword competition - collect $100 and collected money");
	}

	@Override
	public void BankError() {
		this.p1Money = p1Money + 75;
		this.setP1Processing("Player 1 draw Bank error in your favor - collect $75 and collected money");	
	}

	@Override
	public void DoctorFee() {
		this.p1Money = p1Money - 50;
		this.setP1Processing("Player 1 draw Doctor's fees - Pay $50 and paid money");	
	}

	@Override
	public void BirthdayP1() {
		this.p1Money = p1Money + 10;
		this.setP1Processing("Player 1 draw It is your birthday Collect $10 from each player and collected money");
	}

	@Override
	public void BirthdayP2() {
		this.p1Money = p1Money - 10;
	}

	@Override
	public void OperaP1() {
		this.p1Money = p1Money + 50;
		this.setP1Processing("Player 1 draw Grand Opera Night - collect $50 from every player for opening night seats and collected money");
	}

	@Override
	public void OperaP2() {
		this.p1Money = p1Money - 50;
	}

	@Override
	public void TaxRefund() {
		this.p1Money = p1Money +20;
		this.setP1Processing("Player 1 Income Tax refund - collect $20 and collected money");	
	}

	@Override
	public void LifeInsurance() {
		this.p1Money = p1Money +100;
		this.setP1Processing("Player 1 draw Life Insurance Matures - collect $100 and collected money");
	}

	@Override
	public void PayHospital() {
		this.p1Money = p1Money - 100;
		this.setP1Processing("Player 1 draw Pay Hospital Fees of $100 and paid money");
	}

	@Override
	public void PaySchool() {
		this.p1Money = p1Money -50;
		this.setP1Processing("Player 1 draw Pay School Fees of $50 and paid money");
	}

	@Override
	public void Inherit() {
		this.p1Money = p1Money +100;
		this.setP1Processing("Player 1 draw You inherit $100 and collected money");
	}

	@Override
	public void SaleStock() {
		this.p1Money = p1Money +50;
		this.setP1Processing("Player 1 draw From sale of stock you get $50 collected money");
	}
}
