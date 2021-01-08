import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    /**
     * 1. 找到极值点（极值点也是用二分法找到的）(extreme point, binary method)
     * 2. 根据极值点将数组分为两部分，分别进行二分搜索，查找key值
     * according to the extreme point, divide the array into two parts
     * use Binary search respectively find the key value
     * @param a
     * @param key
     * @return
     */
    public static int find2(int[] a, int key){
        int lo = 0;
        int hi = a.length-1;
        int mid = -1;
        while(lo <= hi){
            mid = (lo + hi) / 2;
            if(a[mid] > a[mid - 1]){
                lo = mid + 1;
            }else{
                hi = mid - 1;
            }
        }
        System.out.println("lo:"+lo +" hi:"+hi+" mid:"+mid);
        int leftArray[] = Arrays.copyOfRange(a, 0, hi);
        int rightArray[] = Arrays.copyOfRange(a, lo - 1, a.length - 1);
        int left = BinarySearch.rank(key, leftArray);
        rightArray= reverse3(rightArray);
        int right = BinarySearch.rank(key, rightArray);

        return left > right ? left : right;
    }

    public static void main(String[] args) {
        int[] a = { 2,3, 4,5,6,10,13,15,14,12,11,9};

        System.out.println( find2(a, 12) );


    }

    public static int[] reverse3(int[] nums) {
        int[] reversed = new int[nums.length];
        for (int i=0; i<nums.length; i++) {
            reversed[i] = nums[nums.length - 1 - i];
        }
        return reversed;
    }
}
