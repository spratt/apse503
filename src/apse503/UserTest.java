package apse503;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	public void testBlankUserIsInvalid() throws Exception {
		User myUser = new User();
		assertFalse("A blank user should be invalid!",myUser.isValid());
	}
	
	public void testValidationOnValidUser() throws Exception {
		User myUser = new User();
		myUser.address = "123 4th Street";
		myUser.city = "Calgary";
		myUser.country = "Canada";
		myUser.email = "foo@bar.net";
		myUser.firstName = "Foo";
		myUser.lastName = "Bar";
		myUser.passwordHash = "----5---10---15---20---25---30---35---40---45---50---55---60--64";
		myUser.postalCode = "H3Z2K6";
		myUser.province = "Alberta";
		myUser.salt = "----5---10---15---20---25---30---35---40---45---50---55---60--64";
		myUser.userName = "foo";
		assertTrue(myUser.isValid());
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
