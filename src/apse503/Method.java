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
	
	//public Date dateTime;
	
	public Method() {
		this.name = "";
		this.description = "";
		this.summary = "";
		this.url = "";
		this.user_id = -1;
		this.status_id = -1;
		this.category_id = -1;
		//this.dateTime = null;
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
		//this.dateTime = originalMethod.dateTime;
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
					 	"NOW()"			+
					 "); ";
			String select =  "select last_insert_id() as method_id"; // grab the id of this new method
			
			try {
				// INSERT the user into the table
				sql.execute(insert);
				sql.execute(select);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) return false;
				this.id = results.getInt("method_id");
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
	
	public Method get(int id) {
		
		if(null == sql){ 
			setUpDataSource();
		}
		try {
			sql.execute("SELECT * FROM method WHERE method_id=" + id);
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("method_id");
			this.name = results.getString("name");
			this.description = results.getString("description");
			this.summary = results.getString("summary");
			this.status_id = results.getInt("status_id");
			this.url = results.getString("url");
			this.user_id = results.getInt("user_id");
			this.category_id = results.getInt("category_id");
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
