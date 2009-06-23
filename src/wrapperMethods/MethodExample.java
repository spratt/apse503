package wrapperMethods;

import apse503.*;

public class MethodExample extends userMethods.MethodExample {
	String name = (this.getClass().getSimpleName()).substring(0, this.getClass().getSimpleName().length());

	public int countCharactersWrapper(String userName, String password,String toCount) {
		//  PASTE AFTER THIS LINE
		System.out.println("NAME: " + name);
		User thisUser = new User().findByUserName(userName);
		if(!thisUser.authenticate(password)) {
			// TODO log this failed authentication
			return -1;
		}
		Method thisMethod;
		if(null == (thisMethod = new Method().findByFilepath(name))) {
			System.out.println("Method failing to find method '" + name + "'");
			return -2;
		}
		MethodUse thisMethodUse = new MethodUse();
		try{
			thisMethodUse.methodID = thisMethod.getId();
			thisMethodUse.userID   = thisUser.getId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(!thisMethodUse.hasUsesLeft()) {
			// TODO log this lack of uses
			return -4;
		}
		// STOP PASTING
		// CALL USER METHOD
		int toReturn = -8;
		try {
			thisMethodUse.save();
			toReturn = this.countCharacters(toCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
}
