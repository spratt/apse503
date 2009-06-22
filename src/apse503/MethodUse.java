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

	public int methodID = -1;
	public int userID = -1;
	
	/**
	 * 
	 */
	public MethodUse() {
		// I do nothing!  Nothing!!
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
		setUpDataSource();
		
		// Don't save if the user is invalid
		if (!this.isValid()) return false;
		
		// Don't save if the user has no uses left
		if (!this.hasUsesLeft()) return false;
		
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
			if(!results.next()) {
				closeDataSource();
				return false;
			}
			return true;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return false;
	}
	
	public int totalPurchased() {
		setUpDataSource();
		
		String purchasedQuery = "select " +
				"sum(quantity) as purchased " +
				"from method_purchase " +
				"inner join method_price " +
				"on method_purchase.method_price_id = method_price.method_price_id " +
				"where user_id=" + this.userID + " AND method_purchase.method_id="+ this.methodID +";";
		try {
			// INSERT the user into the table
			sql.execute(purchasedQuery);
			ResultSet results = sql.getResultSet();
			
			// Set the attribute to the new ID number
			if(!results.next()){
				closeDataSource();
				return -1;
			}
			int toReturn = results.getInt("purchased");
			closeDataSource();
			return toReturn;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return -1;
	}
	
	public int totalUsed() {
		setUpDataSource();
		
		String usedQuery = "select " +
				"count(*) as used " +
				"from method_use " +
				"where user_id=" + this.userID + " " +
				"and method_id=" + this.methodID;
		try {
			// INSERT the user into the table
			sql.execute(usedQuery);
			ResultSet results = sql.getResultSet();
			
			// Set the attribute to the new ID number
			if(!results.next()) {
				closeDataSource();
				return -1;
			}
			int toReturn = results.getInt("used");
			closeDataSource();
			return toReturn;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return -1;
	}
	
	public boolean hasUsesLeft() {
		int purchased = this.totalPurchased();
		System.err.println("Purchased: " + purchased);
		int used = this.totalUsed();
		System.err.println("Used: " + used);
		int left = purchased - used;
		System.err.println("Uses left: " + left);
		return left>0;
	}
}
