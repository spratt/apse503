package apse503;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
	// For connecting to the DB
	// Remove these when we refactor the persistence code
	private Connection db;
	private Statement sql;
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DB_CONN_STRING = "jdbc:mysql://localhost:3306/webMethods";
	private static final String DB_PASSWORD = "root";
	private static final String DB_USERNAME = "root";
	
	private void setUpDB(){
	    try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			this.db = DriverManager.getConnection(DB_CONN_STRING, DB_USERNAME, DB_PASSWORD);
			this.sql = db.createStatement();
		} catch (Exception e) {
			// TODO log this exception
			e.printStackTrace();
		}
	}
	
	// A flag value to indicate that this object has not been saved
	// to the DB before
	final static private int UNSAVED_ID = -1;
	
	private int id = UNSAVED_ID;

	public String userName,
				  firstName,
				  lastName,
				  email,
				  address,
				  city,
				  postalCode,
				  province,
				  country;
	
	private String passwordHash,
				   salt;
	
	public User() {
		this.userName = "";
		this.passwordHash = "";
		this.salt = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.address = "";
		this.city = "";
		this.postalCode = "";
		this.province = "";
		this.country = "";
		this.setUpDB();
	}
	
	// Create a new user from an old user
	// Mostly for testing purposes, but could serve other uses ;-)
	public User(User originalUser) {
		this.userName = originalUser.userName;
		this.passwordHash = originalUser.passwordHash;
		this.salt = originalUser.salt;
		this.firstName = originalUser.firstName;
		this.lastName = originalUser.lastName;
		this.email = originalUser.email;
		this.address = originalUser.address;
		this.city = originalUser.city;
		this.postalCode = originalUser.postalCode;
		this.province =originalUser.province;
		this.country = originalUser.country;
		this.setUpDB();
	}
	
	// Look, but don't touch my private
	public int getId() {
		return id;
	}

	public boolean isValid() {
		// Check the attribute lengths
		boolean validLength = userName.length() >= 3;
		validLength = validLength && passwordHash.length() >= 6;
		validLength = validLength && salt.length() >= 6;
		validLength = validLength && firstName.length() >= 3;
		validLength = validLength && lastName.length() >= 3;
		validLength = validLength && email.length() >= 11;
		validLength = validLength && address.length() >= 6;
		validLength = validLength && city.length() >= 3;
		// This is 5 to take US zip codes into consideration
		validLength = validLength && postalCode.length() >= 5;
		validLength = validLength && province.length() >= 6;
		validLength = validLength && country.length() >= 6;
		
		// TODO Check attribute uniqueness
		// username
		// email
		
		return validLength;
	}
	
	public boolean authenticate(String password) {	
		return this.isValid() && this.encrypt(password) == this.passwordHash;
	}
	
	public void setPassword(String password) {
		this.passwordHash = this.encrypt(password);
	}
	
	// TODO implement persistence code
	public boolean save() {
		// Don't save if the user is invalid
		if (!this.isValid()) return false;
		
		if (this.isSaved()) {
			// UPDATE ROW IN TABLE
			// Return true if sql executed properly
		} else {
			String query;
			query  = "INSERT INTO user ";
			query += "(first_name,last_name,address,postal_code,city,province_state,country,email,datetime,user_name,password,salt) ";
			query += "VALUES ";
			query += "('"+this.firstName+"','"
			      +  this.lastName      +"','"
			      +  this.address       +"','"
			      +  this.postalCode    +"','"
			      +  this.city          +"','"
			      +  this.province      +"','"
			      +  this.country       +"','"
			      +  this.email         +"',"
			      +  "NOW(),'"
			      +  this.userName      +"','"
			      +  this.passwordHash  +"','"
			      +  this.salt          +"')";
			try {
				// INSERT the user into the table
				sql.execute(query);
				
				// Figure out the user's new ID number
				query = "SELECT user_id FROM user WHERE user_name='" + this.userName + "'";
				sql.execute(query);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) return false;
				this.id = results.getInt("user_id");
			} catch (SQLException e) {
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

	// TODO implement randomized salt generation
	private void generateSalt() {
		// Generate salt here
		this.salt = "----5---10---15---20---25---30---35---40---45---50---55---60--64";
	}
	
	// TODO implement proper encryption
	private String encrypt(String toHash) {
		// Good resource for when we need to implement this:
		// http://www.owasp.org/index.php/Hashing_Java
		this.generateSalt();
		// Do some hashing here ;-)
		return toHash;
	}
}
