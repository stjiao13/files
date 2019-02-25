package myIndeed;


import java.util.*;
import java.util.LinkedList;

class GitNode{
	int id;
	List<GitNode> parents;
	public GitNode(int id){
		this.id = id;
		this.parents = new ArrayList<>();
	}
}

// 1 . traverse one node by bfs, recording the dist from the root to other node,
// bfs another node and update the minimum distance we have found
// 2. bidiretion bfs

public class Git_Commit {


	/* =============================================================================
Question Description
=============================================================================*/
/* =============================================================================
code
=============================================================================*/



		//其实是找到了所有的parents,所以假设拿到的是最新的gitNode
		//只有这种难度，才能大部分人都秒做吧。
		public List<GitNode> findAllCommits(GitNode node){
			List<GitNode> ans  = new ArrayList<>();
			helper(ans,node);
			return ans;

		}

		private void helper(List<GitNode> ans, GitNode node) {
			if (node == null) {
				return;
			}

			ans.add(node);
			if (node.parents == null) {
				return;
			}
			for (GitNode p :
					node.parents) {
				helper(ans,p);
			}
		}

	/* =============================================================================
	Follow Up code
	=============================================================================*/
	public GitNode findLCA(GitNode node1, GitNode node2){
		Map<GitNode,Integer> distToNode1 = new HashMap<>();

		Queue<GitNode> queue = new LinkedList<>();
		queue.offer(node1);
		distToNode1.put(node1,0);


		int dist = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				GitNode cur = queue.poll();
				for (GitNode p :
						cur.parents) {
					if (!distToNode1.containsKey(p)) {
						distToNode1.put(p,dist + 1);
						queue.offer(p);
					}
				}

			}
			dist++;
		}

		int minDist = Integer.MAX_VALUE;
		GitNode lca = null;

		queue.offer(node2);
		Set<GitNode> visited = new HashSet<>();

		dist = 1;
		if (distToNode1.containsKey(node2)) {
			lca = node2;
			minDist = distToNode1.get(node2);
		}
		visited.add(node2);

		while (!queue.isEmpty() && dist <= minDist) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				GitNode cur = queue.poll();
				for (GitNode p :
						cur.parents) {

					if (distToNode1.containsKey(p)) {
						int curDist = dist + distToNode1.get(p);
						if (curDist < minDist) {
							minDist = curDist;
							lca = p;
						}
					}

					if (!visited.contains(p)) {
						visited.add(p);
						queue.offer(p);
					}
				}
			}
			dist++;
		}

		System.out.println("minDist = " + minDist);
		return lca;


	}

	public GitNode findLCA2(GitNode node1, GitNode node2){
		Map<GitNode,Integer> distToNode1 = new HashMap<>();
		Map<GitNode,Integer> distToNode2 = new HashMap<>();
		distToNode1.put(node1,0);
		distToNode2.put(node2,0);


		Queue<GitNode> queue1 = new LinkedList<>();
		Queue<GitNode> queue2 = new LinkedList<>();
		Queue<GitNode>[] qArr = new Queue[]{queue1,queue2};

		queue1.offer(node1);
		queue2.offer(node2);
		if (node1.parents.contains(node2)) {
			return node2;
		}

		if (node2.parents.contains(node1)) {
			return node1;
		}


		int dist = 1;

		GitNode lca = null;
		int minDist = Integer.MAX_VALUE;

		while (!queue1.isEmpty() || !queue2.isEmpty()) {
			for (int i = 0; i < qArr.length; i++) {
				int size = qArr[i].size();
				for (int j = 0; j < size; j++) {
					GitNode cur = qArr[i].poll();
					for (GitNode node :
							cur.parents) {

						if (i == 0) {
							if (distToNode2.containsKey(node)) {
								int curDist = dist + distToNode2.get(node);
								if (curDist < minDist) {
									lca = node;
									minDist = curDist;
								}
							}
							if (!distToNode1.containsKey(node)) {
								distToNode1.put(node, dist);
								qArr[i].offer(node);
							}
						} else {
							if (distToNode1.containsKey(node)) {
								int curDist = dist + distToNode1.get(node);
								if (curDist < minDist) {
									lca = node;
									minDist = curDist;
								}
							}
							if (!distToNode2.containsKey(node)) {
								distToNode2.put(node, dist);
								qArr[i].offer(node);
							}
						}
					}
				}
			}

			dist++;

			if (dist > minDist) {
				break;
			}
		}

		System.out.println("minDist = " + minDist);
		System.out.println("lca = " + lca.id);

		return lca;



	}


	public static void main(String[] args) {
		Git_Commit test = new Git_Commit();
		/*
		 *
		 *   5 <-  4  <- 2
		 *    \       \
		 *     \ <- 3 <- 1
		 * */
		GitNode g1 = new GitNode(1);
		GitNode g2 = new GitNode(2);
		GitNode g3 = new GitNode(3);
		GitNode g4 = new GitNode(4);
		GitNode g5 = new GitNode(5);
		GitNode g6 = new GitNode(6);
		GitNode g7 = new GitNode(7);
		GitNode g8 = new GitNode(8);
		GitNode g9 = new GitNode(9);

//		g1.parents.add(g3);
//		g1.parents.add(g4);
//		g2.parents.add(g4);
//		g3.parents.add(g5);
//		g4.parents.add(g5);
//
//		g2.parents.add(g5);

		g1.parents.add(g2);
		g2.parents.add(g3);
		g3.parents.add(g4);

		g7.parents.add(g6);
		g6.parents.add(g5);
		g5.parents.add(g4);

		g4.parents.add(g8);
		g7.parents.add(g8);

		// 1->2->3->4<-5<-6<-7
		// 4->8<-7


		GitNode res = test.findLCA2(g1, g7);
//		System.out.println(res.id);
	}

}

