package apse503.tests;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class MockInitialContextFactory implements InitialContextFactory {
	/*
	 *  This was taken practicaly verbatim from:
	 *  http://amazing-development.com/archives/2006/07/24/mocking-context-lookups/
	 */
    private static Context mockCtx = null;
    public static void setMockContext(Context ctx) {
        mockCtx = ctx;
    }
    public Context getInitialContext(java.util.Hashtable< ?, ?> environment)
      throws NamingException {
        if (mockCtx == null) {
            throw new IllegalStateException("mock context was not set.");
        }
        return mockCtx;
    }
}
