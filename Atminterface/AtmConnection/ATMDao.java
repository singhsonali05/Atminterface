package AtmConnection;
import java.sql.*;
import java.sql.Connection;


public class ATMDao
{


	static boolean flag=false;
	static Connection conn;

	public static boolean addNewUser(ATM a1) 
	{
		try
		{
			conn=ConnectionProvider.create();
			String query="insert into atm_info (accountHolder_name,mobile_no,card_no,user_pin,account_balance)"
					+ " values (?,?,?,?,?)";
			PreparedStatement pt=conn.prepareStatement(query);
			
			pt.setString(1, a1.getAccHolderName());
			pt.setLong(2, a1.getMobileNo());
			pt.setString(3, a1.getCardnumber());
			pt.setString(4, a1.getPassword());
			pt.setDouble(5, a1.getAccountBal());
			pt.executeUpdate();

			flag=true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	
	public static boolean displayAllUser()
	{
	try 
	{
		conn=ConnectionProvider.create();
		String query="select * from atm_info";
		Statement st=conn.createStatement();
		
		ResultSet set=st.executeQuery(query);
		while(set.next())
		{
			int userid=set.getInt(1);
			String name=set.getString(2);
			long mobileno=set.getLong(3);
			String cardno=set.getString(4);
			String password=set.getString(5);
			long accBal=set.getLong(6);
			
			System.out.println("--------------------------");
		    
			System.out.println("id: "+userid+"\n"
					+ "acc holder name: "+name+"\n"
					+ "mobile no: "+mobileno+"\n"
					+ "card no: "+cardno+"\n"
			    	+ "password: "+password+"\n"
			    	+ "accBal: "+accBal);
		}
		flag =true;	
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
		return flag;
	}
	
	
	

	public static boolean validateUserThroughCardandPass(String cardno,String pin) throws SQLException
	{
		
		try 
		{
			conn=ConnectionProvider.create();
			String query="select user_pin ,card_no from atm_info";
			Statement stmt=conn.createStatement();
			ResultSet set=stmt.executeQuery(query);
			
			while(set.next())
			{
				String dbcardNo=set.getString("card_no");
				String dbpinNo=set.getString("user_pin");
				
				if(cardno.equals(dbcardNo) && pin.equals(dbpinNo)) {
					flag=true;
				}
			}
		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
	public static double getAccountBalance(String cardno)
	{
		double accountBalance=0;
		try 
		{
			
			conn=ConnectionProvider.create();
			String query="select account_balance from atm_info where card_no=?";
			PreparedStatement pt=conn.prepareStatement(query);
			pt.setString(1, cardno);
			ResultSet set=pt.executeQuery();
			while(set.next()) 
			{
				accountBalance=set.getDouble("account_balance");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return accountBalance;
	
	}
	
	
	
	public static boolean withDrawlMoney(String cardno, double withdrawl)
	{
		try 
		{
			conn=ConnectionProvider.create();
			double accBal=getAccountBalance(cardno);
			double accBalAfterWithdrawl=accBal-withdrawl;
			String query="update atm_info set account_balance=? where card_no=?";
			PreparedStatement pt=conn.prepareStatement(query);
			pt.setDouble(1, accBalAfterWithdrawl);
			pt.setString(2, cardno);
			
			pt.executeUpdate();
			flag=true;
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return flag;
	}



	public static boolean depositMoney(String cardno, double deposit) throws SQLException 
	{
		try 
		{
			conn=ConnectionProvider.create();
			double accBal=getAccountBalance(cardno);
			double amountAfterDeposit=accBal+deposit;
			String query="update atm_info set account_balance=? where card_no=?";
			PreparedStatement pt=conn.prepareStatement(query);
			pt.setDouble(1, amountAfterDeposit);
			pt.setString(2, cardno);
			
			pt.executeUpdate();
			flag=true;
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return flag;
	}


	public static String getCurrentPin(String cardno ) throws SQLException 
	{
		String oldPin="";
		try 
		{
			
			conn=ConnectionProvider.create();
			String query="select user_pin from atm_info where card_no=? ";
			PreparedStatement pt=conn.prepareStatement(query);
			pt.setString(1, cardno);
			ResultSet set=pt.executeQuery();
			while(set.next())
			{
				oldPin=set.getString("user_pin");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		return oldPin;
	}

	
	public static boolean changePin(String cardno,String newPin) throws SQLException 
	{
		try
		{
			conn=ConnectionProvider.create();
			String query="update atm_info set user_pin =? where card_no=?";
			PreparedStatement pt=conn.prepareStatement(query);
			pt.setString(1, newPin);
			pt.setString(2, cardno);
			pt.executeUpdate();
			flag=true;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return flag;
	}



	public static void showAccountDetails(String cardno) throws SQLException
	{			        		

      try 
          {
			conn=ConnectionProvider.create();
			String query="select * from atm_info where card_no=? ";
			PreparedStatement pt=conn.prepareStatement(query);
			pt.setString(1, cardno);
			ResultSet set=pt.executeQuery();
			while(set.next()) {
				String name=set.getString("accountHolder_name");
				long mobileno=set.getLong("mobile_no");
				String cardNo=set.getString("card_no");
				long accBal=set.getLong("account_balance");
				
				System.out.println("--------------------------");
			    
				System.out.println("acc holder name: "+name+"\n"
						+ "mobile no: "+mobileno+"\n"
						+ "card no: "+cardNo+"\n"
				    	+ "accBal: "+accBal);
			}
		} 
      catch (Exception e) 
        {
			e.printStackTrace();
        }
		
		
	}

}
