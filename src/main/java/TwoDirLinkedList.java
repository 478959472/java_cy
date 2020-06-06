import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * @param <Item> 泛型
 * @author 鯉伴MAY
 */
public class TwoDirLinkedList<Item> implements Iterable<Item> {
    private class DoubleNode {
        Item item;
        DoubleNode pre;
        DoubleNode next;
    }

    private DoubleNode first;//指向首节点
    private DoubleNode last;//指向尾结点
    private int N;

    //判断链表是否为空
    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.N;
    }

    public void insertAtFirst(Item item) {
        DoubleNode newNode = new DoubleNode();
        newNode.item = item;
        //处理空链表
        if (isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            newNode.next = this.first;
            first.pre = newNode;
            first = newNode;
        }
        N++;
    }

    public void insertAtLast(Item item) {
        DoubleNode newNode = new DoubleNode();
        newNode.item = item;
        //处理空链表
        if (isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            last.next = newNode;
            newNode.pre = last;
            last = newNode;
        }
        N++;
    }

    public DoubleNode deleteAtFirst() {
        if (first == null) {
            System.out.println("链表已空");
            return null;
        }
        DoubleNode temp;
        temp = first;
        //处理只有一个节点的情况
        if (size() == 1) {
            first = null;
            last = null;
        } else {
            this.first = this.first.next;
            this.first.pre = null;
        }
        N--;
        return temp;
    }

    public DoubleNode deleteAtLast() {
        if (last == null) {
            System.out.println("链表已空");
            return null;
        }
        DoubleNode temp;
        temp = this.last;
        if (size() == 1) {
            first = null;
            last = null;
        } else {
            this.last = this.last.pre;
            this.last.next = null;
        }
        N--;
        return temp;
    }

    /**
     * @param item
     * @return 返回指向查找节点的指针，如果没有，则返回空
     */
    private DoubleNode findNode(Item item) {
        DoubleNode temp = this.first;
        while (temp != null) {
            if (temp.item.equals(item)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean insertAtBefore(Item oldItem, Item newItem) {
        DoubleNode temp = findNode(oldItem);
        //处理几种特殊情况
        if (temp == null) {
            System.out.println("链表中没有指定节点");
            return false;
        } else if (temp.item.equals(first.item)) {
            insertAtFirst(newItem);
            return true;
        }
        DoubleNode newNode = new DoubleNode();
        newNode.item = newItem;
        newNode.next = temp;
        temp.pre.next = newNode;
        newNode.pre = temp.pre;
        temp.pre = newNode;
        N++;
        return true;
    }

    public boolean insertAtBehind(Item oldItem, Item newItem) {
        DoubleNode temp = findNode(oldItem);
        //处理特殊情况
        if (temp == null) {
            System.out.println("链表中没有此节点");
            return false;
        } else if (temp.item.equals(last.item)) {
            insertAtLast(newItem);
            return true;
        }
        DoubleNode newNode = new DoubleNode();
        newNode.item = newItem;
        newNode.next = temp.next;
        temp.next.pre = newNode;
        newNode.pre = temp;
        temp.next = newNode;
        N++;
        return true;
    }

    public boolean deleteAt(Item item) {
        DoubleNode temp = findNode(item);
        //处理特殊情况
        if (temp == null) {
            System.out.println("链表中没有此节点");
            return false;
        } else if (temp.item.equals(first.item)) {
            deleteAtFirst();
            return true;
        } else if (temp.item.equals(last.item)) {
            deleteAtLast();
            return true;
        }
        temp.next.pre = temp.pre;
        temp.pre.next = temp.next;
        temp.pre = null;
        temp.next = null;
        N--;
        return true;
    }

    public void printList() {
        DoubleNode temp = first;
        while (temp != null) {
            System.out.println(temp.item);
            temp = temp.next;
        }
    }


    /**
     * 遍历器
     */
    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new ListIterator();
    }

    /**
     * 扩展实现遍历器
     *
     * @author Administrator
     */
    private class ListIterator implements Iterator<Item> {

        private DoubleNode current = first;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            Item item = current.item;
            current = current.next;
            return item;
        }

        @SuppressWarnings("unused")
        public DoubleNode getCurrent() {
            return current;
        }

    }


    public static <Item> void iteratorList(TwoDirLinkedList<Item> stack) {
        Iterator<Item> iterator = stack.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        TwoDirLinkedList<String> blls = new TwoDirLinkedList<>();
        String[] strs = "abcdefg".split("");
        for (int i = 0; i < strs.length; i++) {
            blls.insertAtFirst(strs[i]);
        }
        iteratorList(blls);
        blls.insertAtFirst("head");
        iteratorList(blls);
        blls.insertAtLast("tail");
        iteratorList(blls);
//        blls.deleteHead();
//        iteratorList(blls);
//        blls.deleteTail();
//        iteratorList(blls);
//        blls.insertBeforeNode("before", blls.getNode(2));
//        iteratorList(blls);
//        blls.insertAfterNode("after", blls.getNode(2));
//        iteratorList(blls);
//        blls.deleteNode(blls.getNode(2));
//        iteratorList(blls);
    }
}

