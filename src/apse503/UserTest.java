package apse503;

import org.junit.Before;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	User validUser = new User();
	
	@Before
	public void setUp() throws Exception {
		// Valid User
		validUser.firstName = "Foo";
		validUser.lastName = "Bar";
		validUser.address = "123 4th Street";
		validUser.city = "Calgary";
		validUser.province = "Alberta";
		validUser.country = "Canada";
		validUser.email = "foo@bar.net";
		validUser.postalCode = "H3Z2K6";
		validUser.userName = "foo";
		validUser.setPassword("foobar");
	}
	
	public void testBlankUserIsInvalid() throws Exception {
		assertFalse("A blank user should be invalid!",new User().isValid());
	}
	
	public void testValidationOnValidUser() throws Exception {
		assertTrue(validUser.isValid());
	}
	
	public void testShortFirstNameIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.firstName = "-2";
		assertFalse("A short first name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortLastNameIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.lastName = "-2";
		assertFalse("A short last name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortAddressIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.address = "----5";
		assertFalse("A short address should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortCityIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.city = "-2";
		assertFalse("A short city name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortProvinceIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.province = "----5";
		assertFalse("A short province name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortCountryIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.country = "----5";
		assertFalse("A short country name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortEmailIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.email = "----5---10";
		assertFalse("A short email address should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortPostalCodeIsInvalid() {
		User invalidUser = new User(validUser);
		// Remember that the minimum is 5, due to US postal codes
		invalidUser.postalCode = "---4";
		assertFalse("A short postal code should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortUserNameIsInvalid() {
		User invalidUser = new User(validUser);
		invalidUser.userName = "-2";
		assertFalse("A short user name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testInvalidAuthenticationFails() {
		assertFalse(validUser.authenticate("wrongpassword"));
		assertFalse(new User().authenticate("wronguser"));
	}
	
	public void testValidAuthenticationPasses() {
		assertTrue(validUser.authenticate("foobar"));
	}
	
	/* save this for later
	public void testSaltIsRandom() {
		String firstSalt = User.generateSalt();
		String secondSalt= User.generateSalt();
		String thirdSalt= User.generateSalt();
		assertFalse("Three salts in a row were the same!", 
				firstSalt.equals(secondSalt) && secondSalt.equals(thirdSalt));
	}
	*/
}
