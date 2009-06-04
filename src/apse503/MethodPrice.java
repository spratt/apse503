package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MethodPrice extends PersistenceClass{
	
	public int method_price_id, method_id, method_price_status_id, quantity;
	
	public double price;
	
	public Date date_time;
	
	public MethodPrice(){
	}
	
	public boolean isValid() {
		// Check the attribute lengths
		boolean valid = true;
		
		//validate price as regex
		//validate quanitity as ?? number
		valid = valid && quantity > 0  && quantity < 1000000;
		valid = valid && price    > 0  && price    < 1000000;
		
		return valid;
	}
	
	public boolean save() {
		if(null == sql) setUpDataSource();
		
		// Don't save if the price is invalid
		if (!this.isValid()) return false;
		
		if (this.isSaved()) {
			String update;
			update  = "UPDATE method_price SET " 	+
					 	"date_time='" 				+ this.date_time + "'," +
					 	"price='" 					+ this.price + "'," +
					 	"quantity='" 				+ this.quantity + "'," +
					 	"method_id='" 				+ this.method_id + "'," +
					 	"method_price_status_id='" 	+ this.method_price_status_id + "' " +
					 "WHERE method_price_id='"+ method_price_id +"';";
					 			
			try {
				// UPDATE the price in the table
				sql.execute(update);
				
				return true;
			} 
			catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
			// Return true if sql executed properly
		} 
		else {
			String insert;
			insert  = "INSERT INTO method_price" 	+
					 "(" 							+
					 	"date_time," 				+
					 	"price," 					+
					 	"quantity," 				+
					 	"method_id," 				+
					 	"method_price_status_id" 	+
					 ") " 							+
					 "VALUES" 						+
					 "(" 							+
					 	"NOW()," +
					 	"'" + this.price 			+ "'," 	+
					 	"'" + this.quantity 		+ "'," 	+
					 	"'" + this.method_id 		+ "'," +
					 	this.method_price_status_id +
					 ");";
			String select =  "select last_insert_id() as method_price_id"; // grab the id of this new method
			
			try {
				// INSERT the price into the table
				sql.execute(insert);
				sql.execute(select);
				ResultSet results = sql.getResultSet();
				
				if(!results.next()) 
					return false;
				
				// Set the attribute to the new ID number
				// Is this going to work?
				this.id = results.getInt("method_price_id");
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
