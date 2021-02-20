package usecase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainDao { 
	private String driverName="com.mysql.cj.jdbc.Driver";
	private String db_url="jdbc:mysql://localhost:3306/traininfo?autoReconnect=true&useSSL=false";
	private String userName="root";
	private String password="root";
	Train train;
		
	public Train findTrain(int trainNo){
	   

		try{
			Class.forName(driverName);
			Connection con=DriverManager.getConnection(db_url,userName,password);
			
			PreparedStatement p = con.prepareStatement("select * from Train_Details where train_no=?");
			p.setInt(1, trainNo);
		    ResultSet rs=p.executeQuery();
			while(rs.next())
			{
				int train_no=rs.getInt("train_no");
				String train_name=rs.getString("train_name");
				String source=rs.getString("source_station");
				String destination=rs.getString("destination_station");
				Double ticket_price=rs.getDouble("ticket_price");
				train=new Train(train_no,train_name,source,destination,ticket_price);
				System.out.println(train);
				
	       }
			if(rs==null)
			{
				System.out.println("Train no not found");
			}
			

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return train;

	}

}