package apse503.tests;

import apse503.User;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
import junit.framework.TestCase;

import static org.easymock.EasyMock.*;


public class UserTest extends TestCase {
	// For holding a valid user used in testing
	User validUser;
	
	@Before
	public void setUp() throws Exception {
		// Construct a simple, valid User
		validUser = new User();
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
	
	public void testShortFirstNameIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.firstName = "-2";
		assertFalse("A short first name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortLastNameIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.lastName = "-2";
		assertFalse("A short last name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortAddressIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.address = "----5";
		assertFalse("A short address should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortCityIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.city = "-2";
		assertFalse("A short city name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortProvinceIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.province = "----5";
		assertFalse("A short province name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortCountryIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.country = "----5";
		assertFalse("A short country name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortEmailIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.email = "----5---10";
		assertFalse("A short email address should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortPostalCodeIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		// Remember that the minimum is 5, due to US postal codes
		invalidUser.postalCode = "---4";
		assertFalse("A short postal code should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortUserNameIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.userName = "-2";
		assertFalse("A short user name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testInvalidAuthenticationFails() throws Exception {
		assertFalse(validUser.authenticate("wrongpassword"));
		assertFalse(new User().authenticate("wronguser"));
	}
	
	public void testValidAuthenticationPasses() throws Exception {
		assertTrue(validUser.authenticate("foobar"));
	}
	
	public void testSaveCallsExpectedSQLandThenIsSavedReturnsTrue() throws Exception {
		ResultSet mockResultSet = MockDB.createResultSet();
		Statement mockStatement = MockDB.createStatement();
		
		User localValidUser = new User(validUser);
		assertFalse("isSaved() called on an unsaved user should return false!",localValidUser.isSaved());
		
		// Mock the insert
		expect(mockStatement.execute("INSERT INTO user "+
				"(first_name,last_name,address,postal_code,city,province_state,country,email,datetime,user_name,password,salt)"+
				" VALUES ('Foo','Bar','123 4th Street','H3Z2K6','Calgary','Alberta','Canada','foo@bar.net',NOW(),'foo','foobar','----5---10---15---20---25---30---35---40---45---50---55---60--64');"+
				"select last_insert_id() as user_id;"
)).andReturn(true);
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		replay(mockStatement);
		
		// Mock the results
		expect(mockResultSet.next()).andReturn(true);
		expect(mockResultSet.getInt("user_id")).andReturn(1);
		replay(mockResultSet);
		
		assertTrue("save() called on a valid user should return true!",localValidUser.save());
		assertTrue("isSaved() called on a saved user should return true!",localValidUser.isSaved());
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
