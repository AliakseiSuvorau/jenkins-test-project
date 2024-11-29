package testClasses;

import com.google.gson.annotations.Expose;

import java.util.*;

public class Collections {
    @Expose
    public ArrayList<Integer> arrayList = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Expose
    public Vector<Integer> vector = new Vector<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Expose
    public Stack<Integer> stack = new Stack<>();

    @Expose
    public LinkedList<Integer> linkedList = new LinkedList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Expose
    public ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Expose
    public PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

    @Expose
    public TreeSet<Integer> treeSet = new TreeSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Expose
    public LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Expose
    public HashSet<Integer> hashSet = new HashSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    public void init() {
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        stack.push(8);
        stack.push(9);

        priorityQueue.add(0);
        priorityQueue.add(1);
        priorityQueue.add(2);
        priorityQueue.add(3);
        priorityQueue.add(4);
        priorityQueue.add(5);
        priorityQueue.add(6);
        priorityQueue.add(7);
        priorityQueue.add(8);
        priorityQueue.add(9);
    }
}
