package apse503;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	// For holding a valid user used in testing
	User validUser = new User();
	
	// For connecting to the DB
	// Remove these when we refactor the persistence code
	Connection db;
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DB_CONN_STRING = "jdbc:mysql://localhost:3306/webMethods";
	private static final String DB_PASSWORD = "root";
	private static final String DB_USERNAME = "root";
	
	@Before
	public void setUp() throws Exception {
		// Construct a simple, valid User
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
		
		// Set up the connection to the DB
	    Class.forName(DRIVER_CLASS_NAME).newInstance();
		db = DriverManager.getConnection(DB_CONN_STRING, DB_USERNAME, DB_PASSWORD);
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
	
	public void testSavePersistsToDatabase() {
		User saveMe = new User(validUser);
		
		saveMe.save();
		assertTrue(saveMe.isSaved());
		
		try {
			// Set up a statement to execute SQL on the db
			Statement sql = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// Check to make sure the user was saved
			sql.execute("SELECT * FROM user WHERE user_id=" + saveMe.getId());
			ResultSet results = sql.getResultSet();
			assertTrue("Results should return at least one result!",results.next());
			
			// Clear out that row for further testing
			// make the results updatable
			results.deleteRow();
		} catch (SQLException e) {
			// TODO log this SQLException
			e.printStackTrace();
		}
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
