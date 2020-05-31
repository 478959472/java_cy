import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class BinarySearch {
    public static int rank(int key, int[] a)
    {

        // 数组必须是有序的
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi)
        { // 被查找的键要么不存在，要么必然存在于a[lo..hi] 之中
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
    public static int rank2(int key, int[] a)
    {
        for (int i = 0; i < a.length; i++)
            if (a[i] == key) return i;
        return -1;
    }

    public static void main(String[] args)
    {
        int[] whitelist = In.readInts(args[0]);
        Arrays.sort(whitelist);
        while (!StdIn.isEmpty())
        { // 读取键值，如果不存在于白名单中则将其打印
            int key = StdIn.readInt();
            long startTime=System.nanoTime();;   //获取开始时间
            int res = rank(key, whitelist);
            long endTime=System.nanoTime();; //获取结束时间
            System.out.println("二分 程序运行时间： "+(endTime-startTime)+"ns");
            startTime=System.nanoTime();;   //获取开始时间
            res = rank2(key, whitelist);
            endTime=System.nanoTime();; //获取结束时间
            System.out.println("暴力 程序运行时间： "+(endTime-startTime)+"ns");
            if (res < 0)
                System.out.println(key);
        }
    }
}
