package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Method extends PersistenceClass {

	public String name,
				  description,
				  summary,
				  url;
	
	public int user_id,
			   status_id,
			   category_id;
	
	public Date dateTime;
	
	public Method() {
		this.name = "";
		this.description = "";
		this.summary = "";
		this.url = "";
		this.user_id = -1;
		this.status_id = -1;
		this.category_id = -1;
		this.dateTime = null;
	}
	
	// Create a new method from an old method
	// Mostly for testing purposes, but could serve other uses ;-)
	public Method(Method originalMethod) {
		
		this.name = originalMethod.name;
		this.description = originalMethod.description;
		this.summary = originalMethod.summary;
		this.url = originalMethod.url;
		this.user_id = originalMethod.user_id;
		this.status_id = originalMethod.status_id;
		this.category_id = originalMethod.category_id;
		this.dateTime = originalMethod.dateTime;
	}
	
	public boolean isValid()
	{
		boolean valid = true;
		
		valid = valid && name.length() > 0;
		valid = valid && description.length() > 0;
		valid = valid && summary.length() > 0;
		valid = valid && user_id > 0;
		valid = valid && status_id > 0;
		valid = valid && category_id > 0;
		return valid;
	}
	
	
	// TODO implement persistence code
	public boolean save() {
		if(null == sql) setUpDataSource();
		
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
					 	"name," 				+
					 	"description," 			+
					 	"summary," 				+
					 	"url," 					+
					 	"user_id," 				+
					 	"status_id," 			+
					 	"category_id," 			+
					 	"date_time"				+
					 ") "						+
					 "VALUES"					+
					 "("						+
					 	"'" + this.name			+ "'," +
					 	"'" + this.description 	+ "'," +
					 	"'" + this.summary		+ "'," +
					 	"'" + this.url			+ "'," +
					 	this.user_id			+ "," +
					 	this.status_id			+ "," +
					 	this.category_id		+ "," +
					 	this.dateTime			+
					 ");"						+
					 "select last_insert_id() as id;";
					 			
			try {
				// INSERT the user into the table
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
	
	public boolean isSaved() {
		// not unsaved means saved
		return this.id != UNSAVED_ID;
	}
}
