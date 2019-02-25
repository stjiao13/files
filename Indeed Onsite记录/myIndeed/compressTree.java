package myIndeed;

import java.util.LinkedList;
import java.util.Queue;
//两种做法：数组和string
//如果非常unbalanced数组会爆int，只能用string去序列化
//两个部分 ：tree to Array and Array to tree

//扩展：compress integer using lucene vint algotrithm:
// ref: https://blog.csdn.net/iAm333/article/details/38038879
// https://lucene.apache.org/core/3_5_0/fileformats.html#VInt
// 2 way : array or serialize by string
// Another way:
//如果⼤大的数字⽐比较多, 则⽤用1个bit存是否为null节点，接下来32个bit存节点value
// 如果⼩小的数字⽐比较多，则⽤用1个bit存是否为null节点，接下来5个字节表示value占⽤用⼏几个bit,
// 然后再接下来若⼲干个bit存value


//gzip use lz777 and huffman encoding to do compression
//https://www.cnblogs.com/kuang17/p/7193124.html

//如果比较full的tree，用heap的实现方式。比较sparse的tree就用tree本身。
// 介于中间的可以用两个数组，一个表示value，一个表示这个节点在第一种表示方式下的index。

//

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode(int x) {
		val = x;
	}
}
public class compressTree {


	public Integer[] compressTree (TreeNode root) {
		int h = getHeight(root);
		if (h == 0) {
			return new Integer[0];
		}

		Integer[] ans = new Integer[(1<< h) -1];
		// do bfs
		// root - i, left - 2 * i + 1, right- 2 * i + 2

		Queue<TreeNode> queue1 = new LinkedList<>();
		Queue<Integer> queue2 = new LinkedList<>();
		queue1.offer(root);
		queue2.offer(0);
//		ans[0] = root.val;
		while (!queue1.isEmpty()) {
			TreeNode node = queue1.poll();
			int index = queue2.poll();
			ans[index] = node.val;
			if (node.left != null) {
				queue1.offer(node.left);
				queue2.offer((index << 1) + 1);
			}

			if (node.right != null) {
				queue1.offer(node.right);
				queue2.offer((index << 1) + 2);
			}
		}

		return ans;
	}

	private int getHeight(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return 1 + Math.max(getHeight(root.left), getHeight(root.right));
	}

	public static void main(String[] args) {
		compressTree sol = new compressTree();
		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(4);
		root.right = new TreeNode(5);
		root.left.left = new TreeNode(6);
		root.right.left = new TreeNode(1);
		root.right.right = new TreeNode(7);
		root.left.left.left = new TreeNode(8);
		root.right.right.right = new TreeNode(9);


		Integer[] result = sol.compressTree(root);

		for (Integer e : result) {
			System.out.print(e + " ");
		}

		System.out.println("");

	}
}
