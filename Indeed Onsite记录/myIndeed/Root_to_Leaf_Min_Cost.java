package myIndeed;

import java.util.*;
import java.util.LinkedList;

// Why toposort:
// costmap stores mincost from root node to any other node
// we will update them when we do the toposort
class Edge{
	Node2 node; //表示这个edge的尾巴指向哪里。
	int cost;
	public Edge(Node2 n, int cost) {
		this.node = n;
		this.cost = cost;
	}
}
class Node2 {
	List<Edge> edges; //表示从这个头出发的所有edge
	int id;
	public Node2(int id){
		this.edges = new ArrayList<>();
		this.id = id;
	}
}

public class Root_to_Leaf_Min_Cost{
	int minCost = Integer.MAX_VALUE;
	//返回最短路径上面的所有Edge
	public int getMinPathCost(Node2 root){

		Map<Node2,Integer> indegreeMap = new HashMap<>();
		Set<Node2> visited = new HashSet<>();
		visited.add(root);
		Queue<Node2 > queue = new LinkedList<>();
		queue.offer(root);

		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node2 cur = queue.poll();
				for (Edge e :
						cur.edges) {
					Node2 next = e.node;
					if (!visited.contains(next)) {
						visited.add(next);
						queue.offer(next);
					}
					indegreeMap.put(next,indegreeMap.getOrDefault(next,0) + 1);
				}
			}
		}

		Map<Node2,Integer> costMap = new HashMap<>();
		queue.offer(root);
		int mincost = Integer.MAX_VALUE;
		costMap.put(root,0);
		for (Node2 node:
			 indegreeMap.keySet()) {
			System.out.println("node. = " + node.id);
			System.out.println("node = " + indegreeMap.get(node));
		}

		while (!queue.isEmpty()) {
			Node2 node = queue.poll();
			if (node.edges.size() == 0) {
				mincost = Math.min(mincost,costMap.get(node));
			}
			for (Edge e:
			node.edges){
				Node2 next = e.node;
				indegreeMap.put(next,indegreeMap.get(next) - 1);
				if (indegreeMap.get(next) == 0) {
					indegreeMap.remove(next);
					queue.offer(next);
				}
				int oldCost = costMap.getOrDefault(next,Integer.MAX_VALUE);
				int curCost = costMap.get(node) + e.cost;

				if (curCost < oldCost) {
					costMap.put(next,curCost);
				}
			}
		}

		return mincost;

	}
	//就是普通的DFS
	public void dfs(List<Edge> res, List<Edge> temp, Node2 root, int curCost){
		if (root == null){
			return;
		}
		if (root.edges.size() == 0){
			if (curCost < minCost){
				minCost = curCost;
				res.clear();
				res.addAll(temp);
				return;
			}
		}
		for (Edge e : root.edges){
			Node2 next = e.node;
			temp.add(e);
			dfs(res, temp, next, curCost+e.cost);
			temp.remove(temp.size()-1);
		}
	}
	//这个只返回个最小cost
	public int getMinCost(Node2 root){
		if (root == null) {
			return 0;
		}
		helper(root, 0);
		return minCost;
	}
	public void helper(Node2 root, int curCost){
		if (root.edges.size() == 0){
			minCost = Math.min(minCost, curCost);
			return;
		}
		for (Edge e : root.edges){
			Node2 next = e.node;
			helper(next, curCost + e.cost);
		}
	}


	public static void main(String[] args) {
		Root_to_Leaf_Min_Cost test = new Root_to_Leaf_Min_Cost();
		/*
		 *       n1
		 *   e1 /  \ e3
		 *     n2   n3
		 * e2 /
		 *   n4
		 *
		 * */
		Node2 n1 = new Node2(1);
		Node2 n2 = new Node2(2);
		Node2 n3 = new Node2(3);
		Node2 n4 = new Node2(4);
		Node2 n5 = new Node2(5);

		Edge e1 = new Edge(n2,4);
		Edge e2 = new Edge(n4,2);
		Edge e3 = new Edge(n3,2);
		Edge e4 = new Edge(n5,5);

		n1.edges.add(e1);
		n1.edges.add(e3);
		n2.edges.add(e2);
		n3.edges.add(e2);
		n1.edges.add(e4);
		int res = test.getMinPathCost(n1);
		System.out.println("3 = "+res);
	}
}