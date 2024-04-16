package atmstructure;
import java.io.BufferedReader;
import AtmConnection.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import javax.sound.midi.Soundbank;
import javax.xml.transform.Source;

public class ATMmain 
{
	public static String cardNo(int length)
	{
		
		char cardno[]=new char[length];
		Random random=new Random();
		String no="1234567890";
		for(int i=0;i<length;i++) 
		{
			cardno[i]=no.charAt(random.nextInt(no.length()));
		}
		return new String(cardno);
	}
	
	public static String otp(int length) 
	{
		Random random=new Random();
		char pass[]=new char[length];
		String ch="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvnbm@#*";
		for(int i=0;i<length;i++) 
		{
			pass[i]=ch.charAt(random.nextInt(ch.length()));
		}
		
		return new String(pass);
	}
	
	public static boolean checkPinLength(String pin)
	{
		if(pin.length()>=4)
		{
			return true;
		}
		return false;
	}
	
	
	public static boolean newUserWork(BufferedReader br) throws IOException, SQLException
	{
		String userPin="";
		double accountBal;
		long phoneNo;
		boolean ansUserAdd=false;
		String name,cardno;
		
		System.out.println("Create your account\n-------------------------------------");
		System.out.println("enter your name");
		 name=br.readLine();
		System.out.println("enter your Phone no:");
        phoneNo=Long.parseLong(br.readLine());
		String captcha=otp(5);
		System.out.println(captcha);
		System.out.println("enter captcha given above");
		String userCaptcha=br.readLine();
		
		if(captcha.equals(userCaptcha))
		{
			System.out.println("verified your account");
			
			 cardno=cardNo(12);
			
			System.out.println("Your card no : "+cardno);
			String otpsend=cardNo(6);
			System.out.println("\nIts time to make pin\n This is a Otp for account verification \n"+otpsend+"\n enter otp here ");
			String pinOtp=br.readLine();
			if(otpsend.equals(pinOtp)) 
			{
				System.out.println("\notp verified \n Now enter your pin upto 4 length");
				 userPin=br.readLine();
				 try
				 {
					if(checkPinLength(userPin))
					 {
						 System.out.println("pin generated successfully!");
					 }
					else
					{
						System.out.println("Pin is too short ! again enter");
						userPin=br.readLine();
					}
				} 
				 catch (Exception e) 
				 {
					
					e.printStackTrace();
				}
			}
			System.out.println("\nenter the money to the account");
			accountBal=Double.parseDouble(br.readLine());
			
			ATM a1=new ATM(name,cardno,userPin,phoneNo,accountBal);
;
			 ansUserAdd=ATMDao.addNewUser(a1);
			if(ansUserAdd) 
			{
				System.out.println("\nYour data is successfully stored\n---------------------------------------\n");

				
				
				
			}
			else 
			{
				System.out.println("\nsomething went wrong");
				return ansUserAdd;
			}
		}
		else
		{
			System.out.println("\n account not verified! \n try again!");
			newUserWork(br);
			return ansUserAdd;
			
		}
		return ansUserAdd;
		
	}
	
	
	public static  void existingUser(BufferedReader br) throws IOException,SQLException
	{
		
		System.out.println("enter your card no");
		String userInputCardno=br.readLine();
		System.out.println("enter pin");
		String userInputpass=br.readLine();
		boolean validate=ATMDao.validateUserThroughCardandPass(userInputCardno,userInputpass);
		if(validate)
		{
	 	System.out.println("\nCongratulations! you have successfully login to your account");
			
			existingUserAccOperations(br,userInputCardno,userInputpass);
		}
		else 
		{
			System.out.println("\n you have entered wrong cardno or pin");
		}
		
	}
	
	//code for ATM Operations 
	public  static  void existingUserAccOperations(BufferedReader br,String cardno,String pin)throws IOException,SQLException{
		System.out.println("\nNow, You can do following operations in your account");
		while(true) 
		{
			System.out.print("\033[H\033[2J");  
			System.out.flush();
			System.out.println("\n               ATM Operations                \n----------------------------------------");
			System.out.println("choose 1 :  Withdraw");
			System.out.println("choose 2 :  Deposit");
			System.out.println("choose 3 :  Check Balance");
			System.out.println("choose 4 :  Pin Change");
			System.out.println("choose 5 :  Account Details ");
			System.out.println("choose 6 :  EXIT");
			
			System.out.println("enter the option");
			int option=Integer.parseInt(br.readLine());
			switch(option) {
			case 1:
				double withdrawl;
				//withdraw
				System.out.println("--------------WITHDRAWL---------------");
				System.out.println("enter the amount to be withdrawl");
				withdrawl=Double.parseDouble(br.readLine());
				if(ATMDao.getAccountBalance(cardno)>=withdrawl) 
				{
					boolean isWithdrawl=ATMDao.withDrawlMoney(cardno,withdrawl);
					if(isWithdrawl)
					{
						System.out.println("\nYour "+withdrawl+" is successfully withdrawl from your account");
						System.out.println("\nYour balance after withdrawl is "+ATMDao.getAccountBalance(cardno));
					}
					else
					{
						System.out.println("\nsomething went wrong! try again");
					}
				}
				else
				{
					System.out.println("\nInSufficient Balance");
				}
				
				
				break;
			case 2:
				
				System.out.println("--------------DEPOSITING---------------");

				System.out.println("\nenter the amount to deposit");
				double deposit=Double.parseDouble(br.readLine());
				boolean isDeposit=ATMDao.depositMoney(cardno,deposit);
				if(isDeposit) 
				{
					System.out.println("\nyour "+deposit+" is successfully deposited");
					System.out.println("\nyour account balance after depositing is "+ATMDao.getAccountBalance(cardno));
				}
				else 
				{
					System.out.println("\nsomething went wrong");
				}
				break;
			case 3:
				
				System.out.println("--------------ACCOUNT BALANCE ---------------");

				System.out.println("\nyour account balance:  "+ATMDao.getAccountBalance(cardno));
				break;
			case 4:
				
				System.out.println("--------------CHANGE PIN---------------");
				
				System.out.println("press 1 : Enter current pin");
				System.out.println("press 2 : Forgot password?");
				int choice=Integer.parseInt(br.readLine());
				
				if(choice==1) 
				{
					
					System.out.println("\nenter your current pin");
					String userInCurrentPin=br.readLine();
					String currentPin=ATMDao.getCurrentPin(cardno);
					if(currentPin.equals(userInCurrentPin)) 
					{
						
						System.out.println("\nEnter new pin");
					    String newPin=br.readLine();
					    try
						 {
							if(checkPinLength(newPin))
							 {
								  System.out.println("\nRe-enter new pin");
								    String reenterPin=br.readLine();
								    if(newPin.equals(reenterPin)) 
								    {
								    	boolean isPinChange=ATMDao.changePin(cardno,newPin);
								    	if(isPinChange) {
								    		System.out.println("Pin is successfully changed!");
								    	

								    	}
								    	else 
								    	{
								    		System.out.println("Something went wrong!");
								    	}
								    }
								    else
								    {
								    	System.out.println("Your new pin and re-enter is not matching");
								    }
							 }
							else
							{
								System.out.println("Pin is too short ! again enter");
								newPin=br.readLine();
							}
						} 
						 catch (Exception e) 
						 {
							
							e.printStackTrace();
						}
					  
				   }
					else
					{
						System.out.println("You have entered wrong password");
						System.out.println();
					}
					
				}
				else if(choice==2)
				{
					String pinOtp=cardNo(6);
					System.out.println(pinOtp);
					System.out.println("Enter the OTP ");
					String userOtpPin=br.readLine();
					if(userOtpPin.equals(pinOtp))
					{
						System.out.println("------------otp verified--------------");
						System.out.println("Enter new pin");
					    String newPin=br.readLine();
					    System.out.println("Re-enter new pin");
					    String reenterPin=br.readLine();
					    if(newPin.equals(reenterPin)) 
					    {
					    	boolean isPinChange=ATMDao.changePin(cardno,newPin);
					    	if(isPinChange) 
					    	{
					    		System.out.println("Pin is successfully changed!");
					    	}else
					    	{
					    		System.out.println("Something went wrong!");
					    	}
					    }
					    else
					    {
					    	System.out.println("Your new pin and reenter is not matching");
					    }
					}
					else 
					{
						System.out.println("You have entered wrong otp");
					}
				}
				else 
				{
					System.out.println("Invalid option");
				}
				
				
				break;
			case 5:
			    ATMDao.showAccountDetails(cardno);
				break;
			case 6:
				
				System.out.println("You have exited successfully!\n-------------------------------------------------"
						+ "\n ThankYou for using our ATM \n Have a nice day!");
				System.exit(0);
				break;
			default:
				System.out.println("You have entered wrong option\n try again");
				break;
			}
			
		}
		
		
		
	}
	
	//code for asking new or existing user
	public static void operationWithAccount(BufferedReader br) throws IOException,SQLException
	{
	
		
		System.out.println("         Welcome in ATM Machine   ");
		System.out.println("------------------------------------------");
		
			
			System.out.println("press 1: New User Login\n"
					+ "press 2: Old User Login\n");
			int login=Integer.parseInt(br.readLine());//br take input as string so convert into int
			
			if(login==1)
			{
				//code of new user
				boolean cond=newUserWork(br);
				
				if(cond) 
				{
					System.out.println("Do you wanted to do ATM operations ? \n enter 1 : Yes   ||    enter 2 : No ");
					int n=Integer.parseInt(br.readLine());
					if(n==1)
					{
						existingUser(br);
					}
					else
					{
						System.out.println("Thank You for creating account! \n Have a nice day");
					}
					
				}
			
			}
			else if(login==2)
			{
				
				existingUser(br);
				operationWithAccount(br);
			}
			else 
			{
				System.out.println("you have entered wrong option \n try again");
				operationWithAccount(br);
			}
		}
	

	
	public static void main(String[] args)throws IOException ,SQLException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		

	    operationWithAccount(br);
	}

}
