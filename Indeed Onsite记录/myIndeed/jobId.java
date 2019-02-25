package myIndeed;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


// bloom filter 或者interval
public class jobId {
	BitSet bitset = new BitSet();
	int k = 3;
	int capcaity;
	public jobId() {
//		capcaity = 16 * 8 * Short.MAX_VALUE;
		capcaity = Short.MAX_VALUE;
	}

	public void add(long id) {
		long hash = id;
		for (int i = 0; i < k; i++) {
			int code = Long.hashCode(hash);
			hash = code;
			bitset.set(code % capcaity);
		}
	}

//	public void expire (long id) {
//
//	}

	// true if it is  used
	public boolean check(long id ) {
		long hash = id;
		for (int i = 0; i < k; i++) {
			int code = Long.hashCode(hash);
			hash = code;
			if (bitset.get(code % capcaity)) {
				continue;
			}
			else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		jobId test = new jobId();
		Random rd = new Random();

		Set<Integer> set = new HashSet<>();
		int count = 0;

		for (int i = 0; i < 1000000; i++) {
			if (rd.nextInt(100) == 1) {
				test.add(i);
				set.add(i);
			}
		}

		for (int i = 0; i < 1000000; i++) {
//			System.out.println("test.check(i) = " + test.check(i));
//			System.out.println("set = " + set.contains(i));
			if (test.check(i) && !set.contains(i)) {
//				System.out.println("i = " + i);
				count++;
			}
		}
		System.out.println("count = " + count);
	}
}
