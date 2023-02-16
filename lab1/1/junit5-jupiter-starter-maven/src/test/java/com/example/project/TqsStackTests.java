package com.example.project;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TqsStackTests {

    @DisplayName("a) Stack is empty on construction")
    @Test
    void testEmptyOnConstruction() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        assertTrue(stack.isEmpty());
    }

    @DisplayName("b) Stack has Size 0 on construction")
    @Test
    void testSize0OnConstruction() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        assertEquals(0, stack.size());
    }

    @DisplayName("c) Size is N after N pushes")
    @Test
    void testSizeIsNAfterNPushes() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(20);
        stack.push(40);
        stack.push(60);
        stack.push(500);

        assertFalse(stack.isEmpty());
        assertEquals(4, stack.size());

    }

    @DisplayName("d) Push then pop")
    @Test
    void testPushThenPop() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(20);
        int popped = stack.pop();
        assertEquals(20, popped);
    }

    @DisplayName("e) Push x then peek x then size stays same")
    @Test
    void testPushThenPeekThenSize() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(16);
        assertEquals(stack.size(), 1);
        int peeked = stack.peek();
        assertEquals(peeked, 16);
        assertEquals(stack.size(), 1);
    }

    @DisplayName("f) Size is n then pop n times then size is 0")
    @Test
    void testSizeNThenPopNTimesThenSize0()
    {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        assertEquals(5, stack.size());
        for (int i = 0; i < 5; i++)
            stack.pop();
        assertEquals(0, stack.size());

    }


    @DisplayName("g) Popping empty stack throws NoSuchElementException")
    @Test
    void testPopEmpty() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });

    }

    @DisplayName("h) Peeking empty stack throws NoSuchElementException")
    @Test
    void testPeekEmpty() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });

    }

}