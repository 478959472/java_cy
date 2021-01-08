package learning;

public class FixedCapacityStackOfStrings {

    private String[] stack;
    private int N;
    FixedCapacityStackOfStrings(int cap){
        stack = new String[cap];
    }
    public String pop(){
        return stack[--N];
    }
    public boolean isEmpty(){
        return N==0;
    }
    public int size(){
        return N;
    }
    public void push(String s){
        stack[N++] = s;
    }
}
