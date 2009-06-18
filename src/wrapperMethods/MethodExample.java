package wrapperMethods;

import apse503.*;

public class MethodExample extends userMethods.MethodExample {
	private String name = (this.getClass().getSimpleName()).substring(0, this.getClass().getSimpleName().length());

	public int countCharactersWrapper(String userName, String password,String toCount) {
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
		thisMethodUse.methodID = thisMethod.getId();
		thisMethodUse.userID   = thisUser.getId();
		if(!thisMethodUse.hasUsesLeft()) {
			// TODO log this lack of uses
			return -4;
		}
		thisMethodUse.save();
		return this.countCharacters(toCount);
	}
}
