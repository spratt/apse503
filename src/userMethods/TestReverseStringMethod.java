/**
 * 
 */
package userMethods;

import java.util.Map;

/**
 * @author spratt
 *
 */
public class TestReverseStringMethod implements UserMethod {

	private static String reverse(String toReverse) {
		String reversed = "";
		for(int i = 0;i<toReverse.length();i++)
			reversed = toReverse.substring(i, i+1) + reversed;
		return reversed;
	}

	/* (non-Javadoc)
	 * @see userMethods.UserMethod#run(java.util.Map)
	 */
	public String run(Map<String, String[]> args) {
		return reverse(args.get("toReverse")[0]);
	}

	public static void main(String[] args) {
		System.out.println("Reverse: " + reverse("reverse"));
	}
}
