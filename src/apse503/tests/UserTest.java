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
		invalidUser.firstName = "";
		assertFalse("A short first name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortLastNameIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.lastName = "";
		assertFalse("A short last name should be invalid!",
				invalidUser.isValid());
	}
	
	public void testShortAddressIsInvalid() throws Exception {
		User invalidUser = new User(validUser);
		invalidUser.address = "---4";
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
		invalidUser.email = "----5";
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
		invalidUser.userName = "1";
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
	
	/*
	 * For more information on EasyMock, visit: 
	 * http://easymock.org/EasyMock2_4_Documentation.html
	 */
	public void testSaveCallsExpectedSQLandThenIsSavedReturnsTrue() throws Exception {
		ResultSet mockResultSet = MockDB.createResultSet();
		Statement mockStatement = MockDB.createStatement();
		
		User localValidUser = new User(validUser);
		assertFalse("isSaved() called on an unsaved user should return false!",localValidUser.isSaved());
		
		// Mock the insert
		expect(mockStatement.execute("INSERT INTO user "+
				"(first_name,last_name,address,postal_code,city,province_state,country,email,datetime,user_name,password,salt)"+
				" VALUES ('Foo','Bar','123 4th Street','H3Z2K6','Calgary','Alberta','Canada','foo@bar.net',NOW(),'foo','foobar','----5---10---15---20---25---30---35---40---45---50---55---60--64');"+
				"select last_insert_id() as user_id;")).andReturn(true);
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		
		// Mock the update
		expect(mockStatement.execute("UPDATE user SET "+
				"first_name='Foo'," +
				"last_name='SNAFU'," +
				"address='123 4th Street'," +
				"postal_code='H3Z2K6'," +
				"city='Calgary'," +
				"province_state='Alberta'," +
				"country='Canada'," +
				"email='foo@bar.net'," +
				"user_name='foo'," +
				"password='foobar' "+
				"WHERE user_id=1;")).andReturn(true);
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		replay(mockStatement);
		
		// Mock the results
		expect(mockResultSet.next()).andReturn(true);
		expect(mockResultSet.getInt("user_id")).andReturn(1);
		expect(mockResultSet.next()).andReturn(true);
		replay(mockResultSet);
		
		assertTrue("save() called on a valid user should return true!",localValidUser.save());
		assertTrue("isSaved() called on a saved user should return true!",localValidUser.isSaved());
		
		// Now make the changes!
		localValidUser.lastName = "SNAFU"; // User got married
		assertTrue("save() called on a valid user should return true!",localValidUser.save());
	}
	
	public void testGettingAUserByID() throws Exception {
		User getThisUser = new User(validUser);
		
		// Mocks
		ResultSet mockResultSet = MockDB.createResultSet();
		Statement mockStatement = MockDB.createStatement();
		
		// Mock the select
		expect(mockStatement.execute("SELECT * FROM user WHERE user_id=1")).andReturn(true);
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		replay(mockStatement);
		
		// Mock the results
		expect(mockResultSet.next()).andReturn(true);
		expect(mockResultSet.getInt("user_id")).andReturn(1);
		expect(mockResultSet.getString("user_name")).andReturn(getThisUser.userName);
		expect(mockResultSet.getString("password")).andReturn("123456");
		expect(mockResultSet.getString("salt")).andReturn("123456");
		expect(mockResultSet.getString("first_name")).andReturn(getThisUser.firstName);
		expect(mockResultSet.getString("last_name")).andReturn(getThisUser.lastName);
		expect(mockResultSet.getString("email")).andReturn(getThisUser.email);
		expect(mockResultSet.getString("address")).andReturn(getThisUser.address);
		expect(mockResultSet.getString("city")).andReturn(getThisUser.city);
		expect(mockResultSet.getString("postal_code")).andReturn(getThisUser.postalCode);
		expect(mockResultSet.getString("province_state")).andReturn(getThisUser.province);
		expect(mockResultSet.getString("country")).andReturn(getThisUser.country);
		replay(mockResultSet);
		
		// Do it!
		User foundThisUser = new User().get(1);
		
		// Assert we got the same user
		assertEquals("userName didn't match",foundThisUser.userName,getThisUser.userName);
		assertEquals("firstName didn't match",foundThisUser.firstName,getThisUser.firstName);
		assertEquals("lastName didn't match",foundThisUser.lastName,getThisUser.lastName);
		assertEquals("email didn't match",foundThisUser.email,getThisUser.email);
		assertEquals("address didn't match",foundThisUser.address,getThisUser.address);
		assertEquals("city didn't match",foundThisUser.city,getThisUser.city);
		assertEquals("postalCode didn't match",foundThisUser.postalCode,getThisUser.postalCode);
		assertEquals("province didn't match",foundThisUser.province,getThisUser.province);
		assertEquals("country didn't match",foundThisUser.country,getThisUser.country);
	}
	
	public void testFindingAUserByUserName() throws Exception {
		User getThisUser = new User(validUser);
		
		// Mocks
		ResultSet mockResultSet = MockDB.createResultSet();
		Statement mockStatement = MockDB.createStatement();
		
		// Mock the select
		expect(mockStatement.execute("SELECT * FROM user WHERE user_name='" + getThisUser.userName + "'")).andReturn(true);
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		replay(mockStatement);
		
		// Mock the results
		expect(mockResultSet.next()).andReturn(true);
		expect(mockResultSet.getInt("user_id")).andReturn(1);
		expect(mockResultSet.getString("user_name")).andReturn(getThisUser.userName);
		expect(mockResultSet.getString("password")).andReturn("123456");
		expect(mockResultSet.getString("salt")).andReturn("123456");
		expect(mockResultSet.getString("first_name")).andReturn(getThisUser.firstName);
		expect(mockResultSet.getString("last_name")).andReturn(getThisUser.lastName);
		expect(mockResultSet.getString("email")).andReturn(getThisUser.email);
		expect(mockResultSet.getString("address")).andReturn(getThisUser.address);
		expect(mockResultSet.getString("city")).andReturn(getThisUser.city);
		expect(mockResultSet.getString("postal_code")).andReturn(getThisUser.postalCode);
		expect(mockResultSet.getString("province_state")).andReturn(getThisUser.province);
		expect(mockResultSet.getString("country")).andReturn(getThisUser.country);
		replay(mockResultSet);
		
		// Do it!
		User foundThisUser = new User().findByUserName(getThisUser.userName);
		
		// Assert we got the same user
		assertEquals("userName didn't match",foundThisUser.userName,getThisUser.userName);
		assertEquals("firstName didn't match",foundThisUser.firstName,getThisUser.firstName);
		assertEquals("lastName didn't match",foundThisUser.lastName,getThisUser.lastName);
		assertEquals("email didn't match",foundThisUser.email,getThisUser.email);
		assertEquals("address didn't match",foundThisUser.address,getThisUser.address);
		assertEquals("city didn't match",foundThisUser.city,getThisUser.city);
		assertEquals("postalCode didn't match",foundThisUser.postalCode,getThisUser.postalCode);
		assertEquals("province didn't match",foundThisUser.province,getThisUser.province);
		assertEquals("country didn't match",foundThisUser.country,getThisUser.country);
	}
	
	public void testFindingAUserByEmail() throws Exception {
		User getThisUser = new User(validUser);
		
		// Mocks
		ResultSet mockResultSet = MockDB.createResultSet();
		Statement mockStatement = MockDB.createStatement();
		
		// Mock the select
		expect(mockStatement.execute("SELECT * FROM user WHERE email='" + getThisUser.email + "'")).andReturn(true);
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		replay(mockStatement);
		
		// Mock the results
		expect(mockResultSet.next()).andReturn(true);
		expect(mockResultSet.getInt("user_id")).andReturn(1);
		expect(mockResultSet.getString("user_name")).andReturn(getThisUser.userName);
		expect(mockResultSet.getString("password")).andReturn("123456");
		expect(mockResultSet.getString("salt")).andReturn("123456");
		expect(mockResultSet.getString("first_name")).andReturn(getThisUser.firstName);
		expect(mockResultSet.getString("last_name")).andReturn(getThisUser.lastName);
		expect(mockResultSet.getString("email")).andReturn(getThisUser.email);
		expect(mockResultSet.getString("address")).andReturn(getThisUser.address);
		expect(mockResultSet.getString("city")).andReturn(getThisUser.city);
		expect(mockResultSet.getString("postal_code")).andReturn(getThisUser.postalCode);
		expect(mockResultSet.getString("province_state")).andReturn(getThisUser.province);
		expect(mockResultSet.getString("country")).andReturn(getThisUser.country);
		replay(mockResultSet);
		
		// Do it!
		User foundThisUser = new User().findByEmail(getThisUser.email);
		
		// Assert we got the same user
		assertEquals("userName didn't match",foundThisUser.userName,getThisUser.userName);
		assertEquals("firstName didn't match",foundThisUser.firstName,getThisUser.firstName);
		assertEquals("lastName didn't match",foundThisUser.lastName,getThisUser.lastName);
		assertEquals("email didn't match",foundThisUser.email,getThisUser.email);
		assertEquals("address didn't match",foundThisUser.address,getThisUser.address);
		assertEquals("city didn't match",foundThisUser.city,getThisUser.city);
		assertEquals("postalCode didn't match",foundThisUser.postalCode,getThisUser.postalCode);
		assertEquals("province didn't match",foundThisUser.province,getThisUser.province);
		assertEquals("country didn't match",foundThisUser.country,getThisUser.country);
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
