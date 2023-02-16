package com.example.project;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private ArrayList<T> stack;

    public TqsStack() {
        this.stack = new ArrayList<>();
    }

    public void push(T val) {
        stack.add(val);
    }

    public T pop() {
        if (stack.size() == 0)
            throw new NoSuchElementException();
        T obj = stack.remove(stack.size() - 1);
        return obj;
    }

    public T peek() {
        if (stack.size() == 0)
            throw new NoSuchElementException();
        return stack.get(stack.size() - 1);
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.size() == 0;
    }
}
