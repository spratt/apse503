package apse503;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Method extends PersistenceClass {
	
	public String name,
				  description,
				  summary,
				  url,
				  filePath;
	
	//public URL url;  //if we switch to actual URL instead of a url String attribute
	
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
		//System.out.println("in save");
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
					 	"filepath," 			+
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
					 	"'" + this.filePath		+ "'," +
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
			this.filePath = results.getString("filepath");
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
	
	public ArrayList<Method> getAll() {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM method");
			ResultSet results = sql.getResultSet();

			ArrayList<Method> methods = new ArrayList<Method>();
			Method tmp;
			while(results.next())
			{
				tmp = new Method();				
				tmp.id = results.getInt("method_id");
				tmp.name = results.getString("name");
				tmp.user_id = results.getInt("user_id");
				tmp.status_id = results.getInt("status_id");
				tmp.category_id = results.getInt("category_id");
				tmp.description = results.getString("description");
				tmp.summary = results.getString("summary");
				//tmp.date_time = results.getDate("datetime");
				tmp.url = results.getString("url");
				tmp.filePath = results.getString("filepath");
				//tmp.url = results.getURL("url");  //might want to switch to actual URL type
				//
				methods.add(tmp);
			}
			return methods;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Method> getTopTen() {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("select * " +
			"from(" +
			"select AVG(rating) AS 'Rating', Count(rating) AS 'Count', m.name, m.summary, m.method_id " +
			"from method m " +
			"left join rating r " +
			"on m.method_id = r.method_id " +
			"group by m.method_id " +
			"order by rating desc,count desc" +
			") a " +
			"limit 10");
			ResultSet results = sql.getResultSet();

			ArrayList<Method> methods = new ArrayList<Method>();
			Method tmp;
			while(results.next())
			{
				tmp = new Method();				
				tmp.id = results.getInt("method_id");
				tmp.name = results.getString("name");
				//tmp.user_id = results.getInt("user_id");
				//tmp.status_id = results.getInt("status_id");
				//tmp.category_id = results.getInt("category_id");
				//tmp.description = results.getString("description");
				tmp.summary = results.getString("summary");
				//tmp.filePath = results.getString("filepath");
				//tmp.date_time = results.getDate("datetime");
				//tmp.url = results.getString("url");
				//tmp.url = results.getURL("url");  //might want to switch to actual URL type
				//
				methods.add(tmp);
			}
			return methods;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public String getEarnedByMethod(){
		if(null == sql) setUpDataSource();
		try {
			sql.execute("select SUM(Total) AS 'Earned' " +
			"from ( " +
			"select m.method_id, m.name, price, count(mpr.method_id) AS 'Transactions', price * count(mpr.method_id) AS 'Total' " +
			"from method m " +
			"left join method_purchase mpr " +
			"on m.method_id = mpr.method_id " +
			"join method_price mp " +
			"on mpr.method_price_id = mp.method_price_id " +
			"where m.method_id =" + this.id + " " +
			"group by mp.method_price_id" +
			") a " +
			"group by a.method_id " +
			"order by SUM(Total) Desc");

			ResultSet results = sql.getResultSet();
			
			if (!results.next())
				return "$0.00";
			else
				return new java.text.DecimalFormat("$0.00").format((results.getDouble("Earned")* (0.55)));
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return "$0.00";
	}
	
	public int getTotalPurchases(){
		if(null == sql) setUpDataSource();
		try {
			sql.execute("select SUM(Transactions) AS 'Total Purchases' " +
			"from ( " +
			"select m.method_id, m.name, price, count(mpr.method_id) AS 'Transactions', price * count(mpr.method_id) AS 'Total' " +
			"from method m " +
			"left join method_purchase mpr " +
			"on m.method_id = mpr.method_id " +
			"join method_price mp " +
			"on mpr.method_price_id = mp.method_price_id " +
			"where m.method_id =" + this.id + " " +
			"group by mp.method_price_id" +
			") a " +
			"group by a.method_id " +
			"order by SUM(Transactions) Desc");

			ResultSet results = sql.getResultSet();
			
			if(!results.next())
				return 0;
			
			else			
				return results.getInt("Total Purchases");
			
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return 0;
	}
	
}
