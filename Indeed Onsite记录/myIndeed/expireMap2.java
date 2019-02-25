package myIndeed;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by stjiao on 2018/9/27.
 * 如果读过Java Guava里的local cache的实现，那就更有思路了
 * 要达成constant time的put和get显然是需要一个hashmap.来存放具体的key value pair.
 * 然后entry class里面放一个timestamp用来存放写入时间和TTL
 *
 * 另外搞一个hashmap
 * key是TTL
 * value是Doubly linked list.
 * 每一次写入的时候你都要根据TTL找到对应的List, 把entry append 到最后。
 * 这个list就是按照写入时间排序的，越前面的肯定越早写入，也应该越早被expire掉。
 * 每次set的时候都扫一遍对应的list并且do expiration直到发现没有expired的值。
 * 每次get的的时候如果发现entry已经expired，remove掉这个entry并且同时Remove掉同一list中之前的也就是比这个entry更早写入的那些entry.
 *
 * 这样就不需要另启一个thread去做clean up了，扫全表的clean up开销太大了。
 * 同时在大多数情况写读写都是O(1)的，只有在某一个duration很久没有被Set或者get了才会是O(n)其中n也只是那个list的长度。而且这种时候的lock也只需要加在其中一个list上，对全局的性能并不影响。
 * https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=203157&extra=&page=2
 *
 *
 * Time complexity:
 * Assume we do 100n put 100n get, and each 100 operation is of equal distance 1ms, That is to say
 * we have 100put/get in one milisecond
 * the duration of each node is n/2 ms
 * So and the order is such put - put-put -...-get-get-get
 *
 * Time Complexity for put operation:
 * just consider first milisecond, we have 100 put operation
 * the total cost is 100 * constant factor
 * for the n/2 milisecond
 * the first put cost o(n/2),the rest is O(1)
 * So we can conclude that if we have many operations happening in one time unit
 * the time complexity is O(1) averagely
 *
 * Time complexity for get operation
 * first get operation:O(1)
 *  total cost of expire operation = o(num of put operation) = O(100n)
 *  we also have 100n get operation, so the cost of expire is O(1) averagely
 *
 *  Optimize Reheapify process
 *
 */
public class expireMap2 {

	class Node {
		String key;
		String val;
		long expireTime;

		Node prev;
		Node next;
	}

	Node dummyHead;
	Node dummyTail;
	Map<String,Node> keyMap;
	Map<Long,Node> timeMap;

	public expireMap2 () {
		dummyHead = new Node();
		dummyTail = new Node();
		dummyHead.next = dummyTail;
		dummyTail.prev = dummyHead;


		keyMap = new HashMap<>();
		timeMap = new HashMap<>();
	}
	public String get (String key) {
		expire();
//		long curTime = System.currentTimeMillis();
		if (!keyMap.containsKey(key)) {
			return "not exist or not valid";
		}

		Node node = keyMap.get(key);
			return node.val;
	}

	// need to do expire
	public void put(String key,String value, int duration) {
		Node node = new Node();
		node.key = key;
		node.val = value;
		expire();
		node.expireTime = System.currentTimeMillis() + duration;
		keyMap.put(key,node);
		if (timeMap.containsKey(node.expireTime)) {
			Node prev = timeMap.get(node.expireTime);
			Node next = prev.next;

			prev.next = node;
			node.prev = prev;

			node.next = next;
			next.prev = node;
		}
		else {
			timeMap.put(node.expireTime,node);
				Node iter = dummyHead.next;
				Node prev = dummyHead;
				while (iter != dummyTail && iter.expireTime < node.expireTime) {
					prev = iter;
					iter = iter.next;
				}

				prev.next = node;
				node.prev = prev;

				node.next = iter;
				iter.prev = node;
			}

//		}
	}

	// remove expired nodes
	private void expire(){
		long curTime = System.currentTimeMillis();
		Node node = dummyHead.next;
		while (node != dummyTail && node.expireTime <= curTime) {
			keyMap.remove(node.key);
			timeMap.remove(node.expireTime);
			node = node.next;
		}
		dummyHead.next = node;
		node.prev = dummyHead;
	}

	public static void main(String[] args) {
		expireMap2 map = new expireMap2();
		map.put("k1","v1",1);
		map.put("k2","v2",10);
		map.put("k3","v2",100);
		map.put("k4","v4",100);
		map.put("k5","v2",100);
		map.put("k6","v2",100);
		map.put("k7","v7",2000);

		System.out.println("map.get(\"k1\") = " + map.get("k1"));
		System.out.println("map.get(\"k4\") = " + map.get("k4"));

		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("map.get(\"k7\") = " + map.get("k7"));
			System.out.println(map.keyMap.size());
			System.out.println("map = " + map.timeMap.size());

		}catch (Exception e) {
			System.out.println("e = " + e);
		}
	}

}
