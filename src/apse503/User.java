package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends PersistenceClass {

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
	}
	
	public User get(int id) {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM user WHERE user_id=" + id);
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("user_id");
			this.userName = results.getString("user_name");
			this.passwordHash = results.getString("password");
			this.salt = results.getString("salt");
			this.firstName = results.getString("first_name");
			this.lastName = results.getString("last_name");
			this.email = results.getString("email");
			this.address = results.getString("address");
			this.city = results.getString("city");
			this.postalCode = results.getString("postal_code");
			this.province = results.getString("province_state");
			this.country = results.getString("country");
			return this;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public User findByUserName(String userName) {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM user WHERE user_name='" + userName + "'");
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("user_id");
			this.userName = results.getString("user_name");
			this.passwordHash = results.getString("password");
			this.salt = results.getString("salt");
			this.firstName = results.getString("first_name");
			this.lastName = results.getString("last_name");
			this.email = results.getString("email");
			this.address = results.getString("address");
			this.city = results.getString("city");
			this.postalCode = results.getString("postal_code");
			this.province = results.getString("province_state");
			this.country = results.getString("country");
			return this;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public User findByEmail(String email) {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("SELECT * FROM user WHERE email='" + email + "'");
			ResultSet results = sql.getResultSet();
			if(!results.next()) return null;
			this.id = results.getInt("user_id");
			this.userName = results.getString("user_name");
			this.passwordHash = results.getString("password");
			this.salt = results.getString("salt");
			this.firstName = results.getString("first_name");
			this.lastName = results.getString("last_name");
			this.email = results.getString("email");
			this.address = results.getString("address");
			this.city = results.getString("city");
			this.postalCode = results.getString("postal_code");
			this.province = results.getString("province_state");
			this.country = results.getString("country");
			return this;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see apse503.PersistenceClass#isValid()
	 */
	public boolean isValid() {
		// Check the attribute lengths
		boolean validLength = userName.length() >= 2;
		validLength = validLength && passwordHash.length() >= 6;
		validLength = validLength && salt.length() >= 6;
		validLength = validLength && firstName.length() >= 1;
		validLength = validLength && lastName.length() >= 1;
		validLength = validLength && email.length() >= 6;
		validLength = validLength && address.length() >= 5;
		validLength = validLength && city.length() >= 3;
		// This is 5 to take US zip codes into consideration
		validLength = validLength && postalCode.length() >= 5;
		validLength = validLength && province.length() >= 2;
		validLength = validLength && country.length() >= 3;
		
		// TODO Check attribute uniqueness
		if(!this.isSaved()){
			// username
			// email
		}
		
		return validLength;
	}
	
	/*
	 * (non-Javadoc)
	 * @see apse503.PersistenceClass#save()
	 */
	public boolean save() {
		if(null == sql) setUpDataSource();
		
		// Don't save if the user is invalid
		if (!this.isValid()) return false;
		
		if (this.isSaved()) {
			// TODO implement update code
			String query;
			query  = "UPDATE user SET ";
			query += "first_name='" + this.firstName+"',"
			      + "last_name='" + this.lastName      +"',"
			      + "address='" + this.address       +"',"
			      + "postal_code='" + this.postalCode    +"',"
			      + "city='" + this.city          +"',"
			      + "province_state='" + this.province      +"',"
			      + "country='" + this.country       +"',"
			      + "email='" + this.email         +"',"
			      + "user_name='" + this.userName      +"',"
			      + "password='" + this.passwordHash  +"' "
			      + "WHERE user_id=" + this.id +";";
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
			String getid =  "select last_insert_id() as user_id"; // grab the id of this new user
			try {
				// INSERT the user into the table
				sql.execute(query);
				sql.execute(getid);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) return false;
				this.id = results.getInt("user_id");
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
	
	public boolean authenticate(String password) {	
		return this.isValid() && this.encrypt(password).equals(this.passwordHash);
	}
	
	public void setPassword(String password) {
		this.passwordHash = this.encrypt(password);
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
	
	public ArrayList<Method> getMyContributed() {
		if(null == sql) setUpDataSource();
		try {
			sql.execute(
					"SELECT * FROM method where user_id=" + this.id);
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
				tmp.url = results.getString("url");
				methods.add(tmp);
			}
			return methods;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Method> getMyPurchased() {
		if(null == sql) setUpDataSource();
		try {
			sql.execute("select * " +
			"from method_purchase mp " +
			"join method m " +
			"on mp.method_id = m.method_id " +
			"join user u " +
			"on u.user_id = mp.user_id " +
			"where u.user_id=" + this.id);
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
				tmp.url = results.getString("url");
				methods.add(tmp);
			}
			return methods;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		return null;
	}
}
