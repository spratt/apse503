package apse503;

import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public abstract class PersistenceClass {
	// The resource ID, defined in context.xml and web.xml
	protected static final String DATASOURCE_CONTEXT = "java:comp/env/jdbc/DB";

	// For querying the DB
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
		 */
		
		try {
			DataSource data = (DataSource)new InitialContext().lookup(DATASOURCE_CONTEXT);
			sql = data.getConnection().createStatement();
		} catch (Exception e) {
			// TODO log this exception
			e.printStackTrace();
		}
	}
	
	public abstract boolean isValid();
	public abstract boolean save();
}
