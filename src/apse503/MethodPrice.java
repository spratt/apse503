package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MethodPrice extends PersistenceClass{
	
	public int method_price_id, method_id, method_price_status_id, quantity;
	
	public double price;
	
	public Date date_time;
	
	public MethodPrice(){
		this.method_price_id = method_price_id;
		this.method_id = method_id;
		this.method_price_status_id = method_price_status_id;
		this.quantity = quantity;
		this.price = price;
		this.date_time = date_time;
	}
	
	public boolean isValid() {
		// Check the attribute lengths
		boolean valid = true;
		
		//validate price as regex
		//validate quanitity as ?? number
		
		
		
		return valid;
	}
	
	public boolean save() {
		if(null == sql) setUpDataSource();
		
		// Don't save if the price is invalid
		if (!this.isValid()) return false;
		
		if (this.isSaved()) {
			// UPDATE ROW IN TABLE
			// Return true if sql executed properly
		} 
		else {
			String insert;
			insert  = "INSERT INTO method_price" +
					 "(" +
					 	"date_time," +
					 	"price," +
					 	"quantity," +
					 	"method_id," +
					 	"method_price_status_id," +
					 ") " +
					 "VALUES" +
					 "(" +
					 	"'" + this.date_time + "'," +
					 	"'" + this.price + "'," +
					 	"'" + this.quantity + "'," +
					 	"'" + this.method_id + "'," +
					 	this.method_price_status_id +
					 ");" +
					 "select last_insert_id() as id;";
					 			
			try {
				// INSERT the price into the table
				sql.execute(insert);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) 
					return false;
				
				this.id = results.getInt("id");
				return true;
			} 
			catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
		}
		// If the code ever gets to this point, 
		// something went horribly, horribly wrong
		return false;
	}

}
