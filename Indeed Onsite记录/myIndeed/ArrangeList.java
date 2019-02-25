package myIndeed;

import java.util.ArrayList;
import java.util.List;

public class ArrangeList {
	// optimize : round robin

	public static List<List<Character>> arrange (List<Character> ls) {
		int[] count = new int[26];
		int maxCount = 0;
		for (int i = 0; i < ls.size(); i++) {
			count[ls.get(i) - 'a']++;
			maxCount = Math.max(maxCount,count[ls.get(i) - 'a']);
		}
		List<List<Character>> ans = new ArrayList<>();

		for (int i = 0; i < maxCount; i++) {
			ans.add(new ArrayList<>());
		}

		int len = ans.size();
		int index = 0;
		for (int i = 0; i < 26; i++) {
			char cur = (char) ('a' + i);
			for (int j = 0; j < count[i]; j++) {
				ans.get(index).add(cur);
				index = (index + 1) %len;
			}
		}

		for (int i = 0; i < ans.size(); i++) {
			for (int j = 0; j < ans.get(i).size(); j++) {
				System.out.println(ans.get(i).get(j));
			}
			System.out.println();
		}

		return ans;
	}

	public static void main(String[] args) {
		List<Character> ls = new ArrayList<>();
		char[]arr = "aaaabbbcc".toCharArray();
		for (int i = 0; i < arr.length; i++) {
			ls.add(arr[i]);
		}

		arrange(ls);

	}

}
