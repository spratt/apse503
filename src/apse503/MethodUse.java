/**
 * 
 */
package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author spratt
 *
 */
public class MethodUse extends PersistenceClass {

	int methodID = -1;
	int userID = -1;
	
	/**
	 * 
	 */
	public MethodUse(int methodID,int userID) {
		this.methodID = methodID;
		this.userID   = userID;
	}

	/* (non-Javadoc)
	 * @see apse503.PersistenceClass#isValid()
	 */
	@Override
	public boolean isValid() {
		return (methodID > 0 && userID > 0);
	}

	/* (non-Javadoc)
	 * @see apse503.PersistenceClass#save()
	 */
	@Override
	public boolean save() {
		if(null == sql) setUpDataSource();
		
		// Don't save if the user is invalid
		if (!this.isValid()) return false;
		
		String query;
		query  = "INSERT INTO method_use ";
		query += "(method_id,user_id,date_time) ";
		query += "VALUES ";
		query += "('" + this.methodID + "','"
		      +         this.userID   + "',"
		      +        "NOW())"; 
		String getid =  "select last_insert_id() as user_id"; // grab the id of this new user
		try {
			// INSERT the user into the table
			sql.execute(query);
			sql.execute(getid);
			ResultSet results = sql.getResultSet();
			
			// Set the attribute to the new ID number
			if(!results.next()) return false;
			return true;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return false;
	}

}
