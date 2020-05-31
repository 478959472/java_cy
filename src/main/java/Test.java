import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void transpose(int[][] se) {
        for (int i = 0; i < se[i].length; i++) {
            for (int j = 0; j < se.length; j++) {
                int [][] sr= new int[se[i].length][se.length];//注意行列互换
                sr[i][j] = se[j][i];
            }
        }
    }

    public static String toBinaryString(int N){
        String s = "";
        for (int n = N; n > 0; n /= 2){

            System.out.println(n);
            s = (n % 2) + s;
        }

        return s;
    }

    public static void print2D(int mat[][])
    {
        // Loop through all rows
        for (int[] row : mat)

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

    public static int lg(int N) {
        int k = 0;
        int M = 1;// 此处定义M为2的k次方（从M等于2的0次方开始循环）
        while (M <= N) {
            M = 2 * M;
            k++;
        }
        // 循环结束后说明M>N了，则说明k-1为满足条件的最大正整数
        return k - 1;
    }

    public static String exR1(int n)
    {
        if (n <= 0) return "";
        return exR1(n-3) + n + exR1(n-2) + n;
    }

    public static long F(int N ,long[] res)
    {
        if (N == 0) return 0;
        if (N == 1) return 1;
        if(res[N] != 0) return res[N];
        res[N] = F(N-1 ,res) + F(N-2 ,res);
        return res[N];
    }
    public static void main1(String[] args)
    {
        long[] resF = new long[100];
        for (int N = 0; N < 100; N++)
            StdOut.println(N + " " + F(N ,resF));
    }


    public static void main2(String[] args)
    {
        double n=100;
        StdOut.printf("ln(" +Double.toString(n)+")=%f",ln(n));
        System.out.println("\n");
        System.out.println(Math.log(100));
    }//end main


    private static double ln(double n)
    {
        if(n==1) return 0;
        return Math.log(n)+ln(n-1);
    }

    public static int rank(int key, int[] a)
    { return rank(key, a, 0, a.length - 1); }


    public static int rank(int key, int[] a, int lo, int hi)
    { //如果key存在于a[]中，它的索引不会小于lo且不会大于hi
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) return rank(key, a, lo, mid - 1);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi);
        else return mid;
    }

    public static void main(String[] args) {

    }
}
