package learning.leetcode;

import java.util.HashSet;

public class Solution {

    public boolean isUnique(String astr) {
        if(astr.length() > 1){
            HashSet<Character> set = new HashSet<>(astr.length());
            for(char c : astr.toCharArray()){
                if(!set.add(c)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 思路： 1、用一个长度256的int数组标识所有ACCII码字符，用来储存每个字符出现的次数
     * 2、遍历字符串s1，字符每出现一次，该字符对应的数组位置加一。 3、遍历完s1后，遍历字符串s2，字符每出现一次，该字符对应的数组位置减一。
     * 4、如果某个字符次数不够减，则返回false，否则直至遍历结束返回true
     */
    public static boolean checkPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int[] temp = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            temp[c]++;
        }
        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            if (temp[c] == 0) {
                return false;
            } else {
                temp[c]--;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(checkPermutation("qqppmmzz", "mmppqqzz"));
    }

}
