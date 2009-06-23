package wrapperMethods;

import apse503.*;

// Must be named the same as the user's class
public class MethodExample extends userMethods.MethodExample {

	// Make sure that this is the only published method
	public int countCharactersWrapper(String userName, String password,String toCount) {
		//  PASTE AFTER THIS LINE
		
		// Get the name of this method
		String name = (this.getClass().getSimpleName()).substring(0, this.getClass().getSimpleName().length());
		
		// Authenticate the user
		User thisUser = new User().findByUserName(userName);
		if(null == thisUser || !thisUser.authenticate(password)) {
			// TODO log this failed authentication
			return -1;
		}
		
		// Find the method
		Method thisMethod;
		if(null == (thisMethod = new Method().findByFilepath(name))) {
			System.out.println("Method failing to find method '" + name + "'");
			return -2;
		}
		
		// TODO Make sure the method is approved
		
		// Create a new method use to track the uses
		MethodUse thisMethodUse = new MethodUse();
		try{
			thisMethodUse.methodID = thisMethod.getId();
			thisMethodUse.userID   = thisUser.getId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Make sure the user has purchased enough uses of this method
		if(!thisMethodUse.hasUsesLeft()) {
			// TODO log this lack of uses
			return -4;
		}
		
		// Save the method use, thereby "using" one of the purchased uses
		try {
			thisMethodUse.save();
		} catch (Exception e) {
			// TODO log this failed method use
			return -8;
		}
		// Call User Method
		// STOP PASTING
		return this.countCharacters(toCount);
	}
}
