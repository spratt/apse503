package apse503;

public class User {
	
	public String userName,
				  passwordHash,
				  salt,
				  firstName,
				  lastName,
				  email,
				  address,
				  city,
				  postalCode,
				  province,
				  country;
	
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
		validLength = validLength && postalCode.length() >= 5;
		validLength = validLength && province.length() >= 6;
		validLength = validLength && country.length() >= 6;
		
		// Check attribute uniqueness - implement later
		// username
		// email
		
		return validLength;
	}
	
	// Just a fake for now
	public boolean save() {
		if (this.isValid()) {
			// Save to database
			return true;
		}
		return false;
	}
	
	// Not yet implemented
	public static String encrypt(String toHash,String salt) {
		// Good resource for when we need to implement this:
		// http://www.owasp.org/index.php/Hashing_Java
		return toHash;
	}
}
