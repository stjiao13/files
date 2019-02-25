package myIndeed;

public class numOfMatchedString {
	public int findMatch(String src, String pattern) {
		int count = 0;
		int index = 0;
		for (int i = 0; i < src.length(); i++) {
			if (src.charAt(i) == pattern.charAt(index)) {
				index++;
				if (index == pattern.length()) {
					count++;
					index = 0;
				}
			}
		}

		return count;
	}


	public static void main (String[] args) {

		System.out.println(new numOfMatchedString().findMatch("ade bdef","de"));

	}
}
