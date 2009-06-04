package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category extends PersistenceClass {
	
	public int categoryID;
	public String category;
	
	public Category(){
		this.category = "";
	}
	
	public Category(Category firstCategory){
		this.category = firstCategory.category;
	}
	
	public Category get(int id) {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM category WHERE category_id=" + id);
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("category_id");
			this.category = results.getString("category");
			return this;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Category> getAll() {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM category");
			ResultSet results = sql.getResultSet();

			ArrayList<Category> categories = new ArrayList<Category>();
			Category tmp;
			while(results.next())
			{
				tmp = new Category();				
				tmp.categoryID = results.getInt("category_id");
				tmp.category = results.getString("category");
				categories.add(tmp);
			}
			return categories;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Category findByCategory(String category) {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM category WHERE category='" + category + "'");
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("category_id");
			this.category = results.getString("category");
			return this;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isValid() {
		// Check the attribute lengths
		boolean validLength = category.length() >= 2;		
		// TODO Check attribute uniqueness
		if(!this.isSaved()){
			// category
		}		
		return validLength;
	}
	
	public boolean save() {
		if(null == sql) setUpDataSource();
		
		// Don't save if the user is invalid
		if (!this.isValid()) return false;
		
		if (this.isSaved()) {
			// TODO implement update code
			String query;
			query  = "UPDATE category SET ";
			query += "category='" + this.category+"',"
			      + "WHERE category_id=" + this.id +";";
			try {
				sql.execute(query);
				ResultSet results = sql.getResultSet();
				if(!results.next()) return false;
				return true;
			} catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
		} else {
			String query;
			query  = "INSERT INTO category ";
			query += "(category) ";
			query += "VALUES ";
			query += "('"+this.category+"')"; 
			String getid =  "select last_insert_id() as category_id"; // grab the id of this new user
			try {
				// INSERT the user into the table
				sql.execute(query);
				sql.execute(getid);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) return false;
				this.id = results.getInt("category_id");
				return true;
			} catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
		}
		// If the code ever gets to this point, 
		// something went horribly, horribly wrong
		return false;
	}

}
