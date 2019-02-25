package myIndeed;

import java.util.HashMap;
import java.util.Map;

public class charMatch {
	public String[] transform (String[] input) {
		Map<Character,Integer> map1 = new HashMap<>();

		Map<Character,Character> map2 = new HashMap<>();

		String[] ans = new String[input.length];

		// preprocess

		for (int i = 0; i < input.length; i++) {
			String entry = input[i];
			String[] splits = entry.split(":");
			Character c = splits[0].charAt(0);
			String value  = splits[1];
			Character cvalue = splits[1].charAt(0);

			if (Character.isLetter(cvalue)) {
				map2.put(c,cvalue);
			}
			else {
				map1.put(c,Integer.parseInt(value));
			}
		}

		for (int i = 0; i < input.length; i++) {
			String entry = input[i];
			String[] splits = entry.split(":");
			Character c = splits[0].charAt(0);
			String value  = splits[1];
			Character cvalue = splits[1].charAt(0);

			if (Character.isLetter(cvalue)) {
				boolean found = false;
				while (!found) {
					if (map1.containsKey(cvalue)) {
						found = true;
					}
					else {
						cvalue = map2.get(cvalue);
					}
				}
				ans[i] = splits[0] +":"+ map1.get(cvalue);
			}
			else {
				ans[i] = input[i];
			}
		}

		return ans;
	}
}
