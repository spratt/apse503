package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MethodPurchase extends PersistenceClass {
	
	public int user_id,
				method_id,
				method_price_id,
				method_purchase_id,
			   paid_developer;
	
	public Date dateTime;
	
	public MethodPurchase() {
		
		this.user_id = -1;
		this.method_id = -1;
		this.method_price_id = -1;
		this.method_purchase_id = -1;
		this.paid_developer = -1;
		this.dateTime = null;
	}
	
	// Create a new method from an old method
	// Mostly for testing purposes, but could serve other uses ;-)
	public MethodPurchase(MethodPurchase originalMethodPurchase) {
		
		this.user_id = originalMethodPurchase.user_id;
		this.method_id = originalMethodPurchase.method_id;
		this.method_price_id = originalMethodPurchase.method_price_id;
		this.method_purchase_id = originalMethodPurchase.method_purchase_id;
		this.paid_developer = originalMethodPurchase.paid_developer;
		this.dateTime = originalMethodPurchase.dateTime;
	}
	
	public boolean isValid()
	{
		boolean valid = true;
		
		valid = valid && user_id > 0;
		valid = valid && method_id > 0;
		valid = valid && method_price_id > 0;
		valid = valid && method_purchase_id > 0;
		valid = valid && paid_developer > 0;
		return valid;
	}
	
	
	// TODO implement persistence code
	public boolean save() {
		System.out.println("in save");
		if(null == sql) {
			System.out.println("sql is null");
			setUpDataSource();
		}
		
		// Don't save if the method is invalid
		if (!this.isValid()) return false;
		
		if (this.isSaved()) {
			// UPDATE ROW IN TABLE
			// Return true if sql executed properly
		} 
		else {
			String insert;
			insert  = "INSERT INTO method" 		+
					 "("						+
					 	"user_id," 				+
					 	"method_id"				+
					 	"method_price_id"		+
					 	"method_purchase_id"	+
					 	"paid_developer"		+
					 	"date_time"				+
					 ") "						+
					 "VALUES"					+
					 "("						+
					 	this.user_id			+ "," +
					 	this.method_id			+ "," +
					 	this.method_price_id	+ "," +
					 	this.method_purchase_id + "," +
					 	this.paid_developer		+ "," +
					 	this.dateTime			+
					 "); ";
			String select =  "select last_insert_id() as user_id"; // grab the id of this new method
			
			try {
				// INSERT the user into the table
				sql.execute(insert);
				sql.execute(select);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) return false;
				this.id = results.getInt("method_purchase_id");
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
	
	public MethodPurchase get(int id) {
		
		if(null == sql){ 
			setUpDataSource();
		}
		try {
			sql.execute("SELECT * FROM method WHERE method_purchase_id=" + id);
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("method_purchase_id");
			this.user_id = results.getInt("user_id");
			this.method_id = results.getInt("metod_id");
			this.method_price_id = results.getInt("method_price_id");
			this.paid_developer = results.getInt("paid_developer");
			return this;
		} 
		catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isSaved() {
		// not unsaved means saved
		return this.id != UNSAVED_ID;
	}
}