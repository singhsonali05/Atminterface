package AtmConnection;

public class ATM
{
	private int userNo; 
	private String accHolderName;
	private String cardnumber;
	private String password;
	private long mobileNo;
	private double accountBal;
	public ATM(String accHolderName, String cardnumber, String password, long mobileNo,double accountBal) 
	{
		super();
		this.accHolderName = accHolderName;
		this.cardnumber = cardnumber;
		this.password = password;
		this.mobileNo = mobileNo;
		this.accountBal=accountBal;
	}
	public int getUserNo()
	{
		return userNo;
	}
	public void setUserNo(int userNo)
	{
		this.userNo = userNo;
	}
	public ATM()
	{
		super();
	}
	public String getAccHolderName() 
	{
		return accHolderName;
	}
	public ATM(int userNo, String accHolderName, String cardnumber, String password, long mobileNo, double accountBal) 
	{
		super();
		this.userNo = userNo;
		this.accHolderName = accHolderName;
		this.cardnumber = cardnumber;
		this.password = password;
		this.mobileNo = mobileNo;
		this.accountBal = accountBal;
	}
	public void setAccHolderName(String accHolderName)
	{
		this.accHolderName = accHolderName;
	}
	public String getCardnumber() 
	{
		return cardnumber;
	}
	public void setCardnumber(String cardnumber)
	{
		this.cardnumber = cardnumber;
	}
	public String getPassword() 
	{
		return password;
	}
	
	@Override
	public String toString() 
	{
		return "ATM [accHolderName=" + accHolderName + ", cardnumber=" + cardnumber + ", password=" + password
				+ ", mobileNo=" + mobileNo + ", accountBal=" + accountBal + "]";
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public long getMobileNo()
	{
		return mobileNo;
	}
	public void setMobileNo(long mobileNo)
	{
		this.mobileNo = mobileNo;
	}
	public double getAccountBal() 
	{
		return accountBal;
	}
	public void setAccountBal(double accountBal) 
	{
		this.accountBal = accountBal;
	}
	

	}


