package Home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegUser{

	public Boolean regval(String phoneNum,String password) throws SQLException {
		Connection conn=null;
		Statement st=null;
		int count=0;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
			String user="root";
			String userpassword="123";
			conn=DriverManager.getConnection(url, user, userpassword);
			st=conn.createStatement();
			String sql="insert into reg(phoneNumber,password) values('"+phoneNum+"','"+password+"')";
			count=st.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if(st!=null)
			{
				st.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		}
				
		return count==1;
	}
}
