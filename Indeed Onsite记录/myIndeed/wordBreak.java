package myIndeed;

import java.util.*;

public class wordBreak {
	public List<String> wordBreak(String s, List<String> wordDict) {
		List<Integer>[] dp = new List[s.length() + 1];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = new ArrayList<>();
		}

		Set<String> wordSet = new HashSet<>();
		for (int i = 0; i < wordDict.size(); i++) {
			wordSet.add(wordDict.get(i));
		}

		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j <= i; j++) {
				String cur = s.substring(j,i + 1);
				if (wordSet.contains(cur)) {
					if (dp[j].size() != 0 || j == 0) {
						dp[i + 1].add(j);
					}
				}
			}
		}

		List<String> ans = new ArrayList<>();
		dfs(ans,s,new ArrayList<>(),s.length() - 1,dp);
		return ans;
	}

	private void dfs(List<String> ans, String s, List<String> words,int index,List<Integer>[] dp) {
		if (index < 0) {
			Collections.reverse(words);
			ans.add(String.join(" ", words));
			Collections.reverse(words);
		} else {
			for (int i = 0; i < dp[index + 1].size(); i++) {
				words.add(s.substring(dp[index + 1].get(i), index + 1));
				dfs(ans, s, words, dp[index + 1].get(i) - 1, dp);
				words.remove(words.size() - 1);
			}
		}
	}
}
