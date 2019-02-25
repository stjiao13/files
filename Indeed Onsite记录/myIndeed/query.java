package myIndeed;

import java.util.*;

public class query {

	Map<String, Set<String>> userWords = new HashMap<>();
	Map<String,Map<String,Integer>> wordWordCount = new HashMap<>();

	public void query (String username, String word) {
		List<String> ans = new ArrayList<>();
		// get the answer
		int maxCount = 0;
//		if (wordWordCount.containsKey(word)) {
//			return ;
//		}
		Map<String,Integer> map = wordWordCount.getOrDefault(word,new HashMap<>());
		for (String word2 :
				map.keySet()) {
			int count = map.get(word2);
			if (count > maxCount) {
				maxCount = count;
				ans = new ArrayList<>();
				ans.add(word2);
			}
			else if (count == maxCount) {
				ans.add(word2);
			}
		}

		System.out.println(maxCount);
		for (String word2:ans
			 ) {
			System.out.println(word2);
		}

		// update
		wordWordCount.putIfAbsent(word,new HashMap<>());
		Set<String> wordset = userWords.getOrDefault(username,new HashSet<>());
		Map<String,Integer> wordmap = wordWordCount.get(word);

		for (String word2 :
				wordset) {
			wordmap.put(word2,wordmap.getOrDefault(word2,0) + 1);
			Map<String,Integer> wordmap2 = wordWordCount.get(word2);
			wordmap2.put(word,wordmap2.getOrDefault(word,0) + 1);

		}

		userWords.putIfAbsent(username,new HashSet<>());
		userWords.get(username).add(word);

	}

	public static void main(String[] args) {
		query test = new query();
		test.query("A","python");
		test.query("B","java");
		test.query("A","java");
		test.query("B","php");
		test.query("C","python");


		test.query("C","java");
		test.query("D","java");



	}

}
