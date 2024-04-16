package AtmConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider
{
static Connection conn;
public static Connection create()
{
	try
	{	
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","password");
	} 
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	
	return conn;
}

}
