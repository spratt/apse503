package apse503.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MockDB {
	/*
	 *   As was explained in the user domain class, there is a chain of execution
	 *   for setting up a db connection.
	 *   
	 *   Here we have to mock out the various parts of the chain in reverse to run
	 *   tests decoupled from the db.
	 */
	public static Context createContext() {
		// When the User class asks for an initial context factory, give it the mock
		System.setProperty("java.naming.factory.initial", "apse503.tests.MockInitialContextFactory");
		
		Context mockContext = createMock(Context.class);
		
		// When the context factory is called, have it return the mock context 
		MockInitialContextFactory.setMockContext(mockContext);
		
		return mockContext;
	}
	
	public static DataSource createDataSource() throws NamingException {
		DataSource mockDataSource = createMock(DataSource.class);
		
		Context mockContext = createContext();
		expect(mockContext.lookup("java:comp/env/jdbc/DB")).andReturn(mockDataSource);
		replay(mockContext);
		
		return mockDataSource;
	}
	
	public static Connection createConnection() throws NamingException, SQLException {
		Connection mockConnection = createMock(Connection.class);
		
		DataSource mockDataSource = createDataSource();
		expect(mockDataSource.getConnection()).andReturn(mockConnection);
		replay(mockDataSource);
		
		return mockConnection;
	}
	
	public static Statement createStatement() throws SQLException, NamingException {
		Statement mockStatement = createMock(Statement.class);
		
		Connection mockConnection = createConnection();
		expect(mockConnection.createStatement()).andReturn(mockStatement);
		replay(mockConnection);
		
		return mockStatement;
	}
	
	/*
	 *  Not part of the DB call chain
	 */
	public static ResultSet createResultSet() {
		ResultSet mockResultSet = createMock(ResultSet.class);
		return mockResultSet;
	}
}
