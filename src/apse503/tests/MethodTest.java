package apse503.tests;

import apse503.Method;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
import junit.framework.TestCase;
import static org.easymock.EasyMock.*;


public class MethodTest extends TestCase {
	// For holding a valid user used in testing
	Method validMethod;
	
	@Before
	public void setUp() throws Exception {
		// Construct a simple, valid User
		validMethod = new Method();
		validMethod.name = "Foo";
		validMethod.description = "A Test Description";
		validMethod.summary = "A Test Summary";
		validMethod.url = "www.testing.com";
		validMethod.category_id = 1;
		validMethod.user_id = 1;
		validMethod.status_id = 1;
		validMethod.dateTime = null;
	}
	
	public void testBlankMethodIsInvalid() throws Exception {
		assertFalse("A blank method should be invalid!",new Method().isValid());
	}
	
	public void testValidationOnvalidMethod() throws Exception {
		assertTrue(validMethod.isValid());
	}
	
	public void testSaveCallsExpectedSQLandThenIsSavedReturnsTrue() throws Exception {
		ResultSet mockResultSet = MockDB.createResultSet();
		Statement mockStatement = MockDB.createStatement();
		
		Method localvalidMethod = new Method(validMethod);
		assertFalse("isSaved() called on an unsaved user should return false!",
				localvalidMethod.isSaved());
		
		// Mock the insert
		expect(mockStatement.execute(
				"INSERT INTO method("		+
					"name," 				+
					"description,"			+
					"summary,"				+
					"url,"					+
					"user_id,"				+
					"status_id,"			+
					"category_id,"			+
					"date_time"				+
				 ") " 						+
				"VALUES("					+
					"'Foo',"				+
					"'A Test Description',"	+
					"'A Test Summary',"		+
					"'www.testing.com',"	+
					"1,"					+
					"1,"					+
					"1,"					+
					"null"					+
				");"						+
				"select last_insert_id() as id;"
		)).andReturn(true);
		
		expect(mockStatement.getResultSet()).andReturn(mockResultSet);
		replay(mockStatement);
		
		// Mock the results
		expect(mockResultSet.next()).andReturn(true);
		expect(mockResultSet.getInt("id")).andReturn(1);
		replay(mockResultSet);
		
		assertTrue("save() called on a valid method should return true!",localvalidMethod.save());
		assertTrue("isSaved() called on a saved method should return true!",localvalidMethod.isSaved());
	}
}
