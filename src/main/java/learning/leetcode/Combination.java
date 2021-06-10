package learning.leetcode;

/**
 *
 * @author xy.hong 输出一个字符串里面所有字符的排列组合
 */

public class Combination {
    private StringBuilder myString; // 字符集合作为字符串
    private int length; // 字符集合的数量
    private int num = 0; // 记录排列的序号，用于验证排列的数量是否正确，已知字符数，则排列数量可求。从而验证程序有无错误

    public Combination(StringBuilder s) { // 构造函数
        myString = s;
        length = s.length();
    }

    public void printStrings() { // 调用递归函数
        comb(0, new StringBuilder(""));
    }

    /*
     * void comb( 下标, 已经排列好的某个数列) //方法说明
     */
    private void comb(int idex, StringBuilder sb) {
        StringBuilder s = new StringBuilder(sb);

        if (idex == length) { // 如果字符已经取完，则可以输出
            System.out.print("" + (++num) + ":" + s + " ");
            return;
        }
        int l = sb.length(); // 已排序好的字符串的长度
        for (int i = 0; i <= l; i++) { // 使用循环分别插入空挡
            s.insert(i, myString.charAt(idex)); // 插入字符
            comb(idex + 1, s); // 递归
            s.deleteCharAt(i); // 取出插入字符，插入到下个位置

        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StringBuilder s = new StringBuilder("abc"); // 构造要排列的字符
        Combination a = new Combination(s); // 构造对象
        a.printStrings(); // 输出字符
    }

}
