package userMethods;

import java.util.*;

public class testPrintParametersMethod implements UserMethod {

	public String run(Map<String, String[]> args) {
		String toReturn = "{";
		Iterator<String> i = args.keySet().iterator();
		while(i.hasNext()) {
			String key = i.next();
			String value = args.get(key)[0];
			toReturn += "\"" + key + "\":\"" + value + "\"";
			if(i.hasNext()) toReturn += ",";
		}
		toReturn += "}";
		return toReturn;
	}

}
