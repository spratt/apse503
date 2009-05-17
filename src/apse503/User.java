package apse503;

public class User {
	
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
		if (!this.isValid()) return false;
		// Save to database here
		return true;
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
