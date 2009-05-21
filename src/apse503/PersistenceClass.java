package apse503;

import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public abstract class PersistenceClass {
	// A flag value to indicate that this object has not been saved to the DB before
	final static protected int UNSAVED_ID = -1;
	
	// The id of a persistence class begins life as a flag value
	// to show that it hasn't been saved yet.
	protected int id = UNSAVED_ID;
	
	// The resource ID, defined in context.xml and web.xml
	protected static final String DATASOURCE_CONTEXT = "java:comp/env/jdbc/DB";

	// Used for querying the DB
	protected Statement sql = null;
	
	protected void setUpDataSource(){
		/*
		 *  The process for connecting to the db is as follows in pseudocode:
		 *  
		 *    getContext()			<- this context is managed by tomcat and used to store resources
		 *         ||                  like the db datasource
		 *         \/
		 *    getDataSource()		<- this datasource is basically a pool of connections from which
		 *         ||                  we can ask for a connection
		 *         \/
		 *    getConnection()		<- finally we have a connection to the database, which we can use
		 *         ||                  to create sql statements
		 *         \/
		 *    getStatement()		<- which we use can edit and execute to return sets of results
		 *    
		 *    This is mostly taken from:
		 *    http://www.javapractices.com/topic/TopicAction.do?Id=127
		 *    http://tomcat.apache.org/tomcat-5.5-doc/jndi-datasource-examples-howto.html
		 */
		
		try {
			DataSource data = (DataSource)new InitialContext().lookup(DATASOURCE_CONTEXT);
			sql = data.getConnection().createStatement();
		} catch (Exception e) {
			// TODO log this exception
			e.printStackTrace();
		}
	}
	
	// Look, but don't touch my private
	public int getId() {
		return id;
	}

	// not unsaved means saved
	public boolean isSaved() {
		return this.id != UNSAVED_ID;
	}
	
	// To be implemented by subclasses
	public abstract boolean isValid();
	public abstract boolean save();
}
