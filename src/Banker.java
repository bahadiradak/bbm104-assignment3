import java.util.ArrayList;
/**
 * Banker
 * @author Bahadir
 */
public class Banker extends Rules{
	
	/**
	 * @return bankerMoney
	 */
	public Integer getBankerMoney() {
		return bankerMoney;
	}
	/**
	 * @param bankerMoney
	 */
	public void setBankerMoney(Integer bankerMoney) {
		this.bankerMoney = bankerMoney;
	}

	ArrayList<String> bankerProperty = new ArrayList<String>();
	
	Integer bankerMoney = 100000;
	String place,price;
	
	Read c = new Read();
	
	@Override
	public void Buy(String a, String b) {
		
		this.place = a;
		this.price = b;
		
		if (!(bankerProperty.contains(a))){
			
			int cost = Integer.parseInt((String) b);
			this.bankerMoney = bankerMoney + cost;
			this.bankerProperty.add(a);
		}
	}
	
	@Override
	public void Move(String a, String b) {
	
		int dice = Integer.parseInt((String) b);
		int pos = Integer.parseInt((String) a);
		
		if((pos - dice <= 0) && pos != 11){
			
			this.bankerMoney = bankerMoney - 200;
		}
	}

	@Override
	public void FreeParking(String a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PayRent(String a,String b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeRent(String a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PayTax() {
		this.bankerMoney = bankerMoney + 100 ;
		
	}

	@Override
	public void AdvanceGo() {
		this.bankerMoney = bankerMoney - 200 ;
		
	}

	@Override
	public void GoBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AdvanceLeicester() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PoorTax() {
		this.bankerMoney = bankerMoney + 15 ;
		
	}

	@Override
	public void LoanMatures() {
		this.bankerMoney = bankerMoney - 150 ;		
	}

	@Override
	public void Compatition() {
		this.bankerMoney = bankerMoney - 100 ;	
	}

	@Override
	public void BankError() {
		this.bankerMoney = bankerMoney - 75 ;	
	}

	@Override
	public void DoctorFee() {
		this.bankerMoney = bankerMoney + 50 ;	
	}

	@Override
	public void BirthdayP1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void BirthdayP2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OperaP1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OperaP2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void TaxRefund() {
		this.bankerMoney = bankerMoney - 20 ;
	}

	@Override
	public void LifeInsurance() {
		this.bankerMoney = bankerMoney - 100 ;
	}

	@Override
	public void PayHospital() {
		this.bankerMoney = bankerMoney + 100 ;
	}

	@Override
	public void PaySchool() {
		this.bankerMoney = bankerMoney + 50 ;
	}

	@Override
	public void Inherit() {
		this.bankerMoney = bankerMoney - 100 ;
	}

	@Override
	public void SaleStock() {
		this.bankerMoney = bankerMoney - 50 ;
	}
}
