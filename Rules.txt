/**
 * Rules
 * @author Bahadir
 */
public abstract class Rules {
	
	/**
	 * @param a Player name
	 * @param b	dice
	 */
	public abstract void Move(String a,String b);
	/**
	 * @param a place
	 * @param b price
	 */
	public abstract void Buy(String a,String b);
	/**
	 * @param a price
	 * @param b place
	 */
	public abstract void PayRent(String a,String b);
	/**
	 * @param a price
	 */
	public abstract void takeRent(String a);
	/**
	 * @param a place
	 */
	public abstract void FreeParking(String a);
	
	public abstract void PayTax();
	
	public abstract void AdvanceGo();
	public abstract void AdvanceLeicester();
	public abstract void GoBack();
	public abstract void PoorTax();
	public abstract void LoanMatures();
	public abstract void Compatition();
	
	public abstract void BankError();
	public abstract void DoctorFee();
	public abstract void BirthdayP1();
	public abstract void BirthdayP2();
	public abstract void OperaP1();
	public abstract void OperaP2();
	public abstract void TaxRefund();
	public abstract void LifeInsurance();
	public abstract void PayHospital();
	public abstract void PaySchool();
	public abstract void Inherit();
	public abstract void SaleStock();
}
