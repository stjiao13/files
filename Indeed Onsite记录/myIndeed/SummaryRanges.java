package myIndeed;

import java.util.*;

/**
 * Created by jiaoshutong on 19/12/2017.
 */

//
//    给⼀一个排序的Integer的array， [1,2,3,5,6,8,9,12]
// 输出⼀一个string: “1->3,5->6,8->9,12′′

//排好序 :one-pass algorithm

//    if not sorted:
//    1)
//      treemap : key - start, val - end
//      num : left  and right range
//      insert:O(log n )
//      show: O(n)

//    2)
//      map + set: set is for elimate duplicate
//      map : key : start/ end ,val - end/start
//      num: num - 1,num + 1
//      insert : o(1)
//      show :o(nlog n)


class SummaryRanges {

    Map<Integer,Integer> map = new HashMap<>();
    Set<Integer> set = new HashSet<>();

    private class Interval {
        int start;
        int end;
        Interval() {start = 0; end = 0;}
        Interval(int s, int e) {start = s; end = e;}
    }


    /** Initialize your data structure here. */
    public SummaryRanges() {

    }

    public void addNum(int val) {
        if (set.contains(val)) {
            return;
        }
        set.add(val);
        Integer leftStart = map.getOrDefault(val - 1,null);
        Integer rightEnd = map.getOrDefault(val + 1,null);

        if (leftStart != null && rightEnd != null) {
            map.remove(val - 1);
            map.remove(val + 1);

            map.put(leftStart,rightEnd);

            map.put(rightEnd,leftStart);
        }
        else if (leftStart != null) {
            map.remove(val - 1);

            map.put(leftStart,val);
            map.put(val,leftStart);

        }
        else if (rightEnd != null) {
            map.remove(val + 1);

            map.put(rightEnd,val);
            map.put(val,rightEnd);
        }
        else {
            map.put(val,val);
        }
    }

    public List<Interval> getIntervals() {
        List<Interval> ans = new ArrayList<>();
        for (int left :
                map.keySet()) {
            int right = map.get(left);
            if (right >= left) {
                ans.add(new Interval(left,right));
            }
        }

        Collections.sort(ans,(o1,o2)-> (o1.start - o2.start));

        return ans;
    }
}

//public class SummaryRanges {
//    TreeMap<Integer,Interval> map;
//
//
//
//    private class Interval {
//        int start;
//        int end;
//        Interval() {start = 0; end = 0;}
//        Interval(int s, int e) {start = s; end = e;}
//    }
//
//
//    public SummaryRanges() {
//        map = new TreeMap<>();
//    }
//
//    public void addNum(int val) {
//        Integer lower = map.lowerKey(val);
//        Integer higher = map.higherKey(val);
////
////        if (lower == null && higher == null) {
////            map.put(val,new Interval(val,val));
////            return;
////        }
//
//        Interval prev = (lower == null) ? null :map.get(lower);
//        Interval after = (higher == null) ? null : map.get(higher);
//
//        if (prev != null && prev.end + 1 == val && after != null && after.start == val + 1) {
//            map.remove(higher);
//            map.put(lower,new Interval(lower,after.end));
//        }
//
//        else if (prev != null && prev.end + 1 == val) {
//            map.put(lower,new Interval(lower,val));
//        }
//
//        else if (after != null && after.start == val + 1) {
//            map.remove(higher);
//            map.put(val, new Interval(val,after.end));
//        }
//        else {
////            if (lower != null) {
////                if (val > prev.end + 1 && !map.containsKey(val)) {
////                    map.put(val, new Interval(val, val));
////                }
////            }
////            else {
////                if (!map.containsKey(val)) {
////                    map.put(val,new Interval(val,val));
////                }
////            }
//
//            if (prev == null || val > prev.end + 1) {
//                map.putIfAbsent(val,new Interval(val,val));
//            }
//        }
//
//
//    }
//
//    public List<Interval> getIntervals() {
//        return new ArrayList<>(map.values());
//    }
//}
