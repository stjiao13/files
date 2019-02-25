package myIndeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class oa {

	class CategoryNode {
		public int value;
		public ArrayList<CategoryNode> subCategoryNode;
	}


	Map<CategoryNode,int[]> map = new HashMap<>();
	CategoryNode maxnode = null;
	double maxpop = 0.0;
	public CategoryNode find( CategoryNode root) {
		helper(root);
		return maxnode;
	}

	public int[] helper(CategoryNode root) {
		if (root == null) {
			return new int[2];
		}
		else if (map.containsKey(root)) {
			return map.get(root);
		}
		else {
			int[] ans = new int[2];
			for (CategoryNode child :
					root.subCategoryNode) {
				ans[0] += helper(child)[0];
				ans[1] += helper(child)[1];
			}
			if (ans[1] != 0) {
				double pop = ans[0]*1.0/ans[1]*1.0;
				if (pop > maxpop) {
					maxpop = pop;
					maxnode = root;
				}
			}
			map.put(root,ans);
			return ans;
		}
	}


	public int count (String inputString, int num) {
		int start = 0;
		int res = 1;
		Map<Character,Integer> map = new HashMap<>();
		map.put(inputString.charAt(0),1);
		for (int i = 1; i < inputString.length(); i++) {
			char ch = inputString.charAt(i);
			if (map.containsKey(ch)) {
				continue;
			}
			else {
				map.put(ch,1);
				if (map.size() <= num) {
					res += res;
				}
				else {
					start++;
				}
			}
		}

		return res;
	}

	public int helper(String s, int k) {
		if (s == null || s.length() == 0 || k <= 0) {
			return 0;
		}
		int[] count = new int[256];
		int times = 0;
		int ans = 0;

		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			count[s.charAt(j) ]++;
			if (count[s.charAt(j)] == 1) {
				times++;
			}
			if (times == k) {
				ans++;
			}
			else {
				while (times > k) {
					count[s.charAt(i) ]--;
					if (count[s.charAt(i)] == 0) {
						times--;
					}
					i++;
				}
			}
		}

		while (times == k) {
			ans++;
		}

		return ans;
	}



	public static void main(String[] args) {
		oa test = new oa();
		System.out.println("test.count() = " + test.count("aaa",1));
	}


}
