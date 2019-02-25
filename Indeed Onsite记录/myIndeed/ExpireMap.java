package myIndeed;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

public class ExpireMap {

	class Node {
		String val;
		String key;
		long expireTime;

		public Node(String key,String val, long expireTime) {
			this.key = key;
			this.val = val;
			this.expireTime = expireTime;
		}
	}

	Map<String,Node> map = new HashMap<>();
	PriorityQueue<Node> minheap = new PriorityQueue<Node>((o1,o2)->
			(Long.compare(o1.expireTime,o2.expireTime))
	);

	public ExpireMap() {

	}

	public String get (String key) {
		long time = System.currentTimeMillis();
		clearMap();

		if (!map.containsKey(key)) {
			return "Key does not exist or have expired";
		}
		Node node = map.get(key);
			return node.val;

	}

	public void put(String key,String value, int duration) {
		long expiredTime = System.currentTimeMillis() + duration;
		map.put(key,new Node(key,value,expiredTime));
		minheap.offer(map.get(key));
	}

	public void clearMap(){
		long time = System.currentTimeMillis();
		while (!minheap.isEmpty() && minheap.peek().expireTime < time) {
			Node expiredNode = minheap.poll();
			map.remove(expiredNode.key);
		}
	}

	public static void main(String[] args) {
		ExpireMap map = new ExpireMap();
		map.put("k1","v1",111);
		System.out.println("map.get(\"k1\") = " + map.get("k1"));
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("map.get(\"k1\") = " + map.get("k1"));

		}catch (Exception e) {
			System.out.println("e = " + e);
		}
	}


}
