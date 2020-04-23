package com.tiance.jexplorer.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedQueue<E> {

    private Node<E> first;

    private Node<E> last;

    private int capacity;

    private AtomicInteger count;

    private ReentrantLock putLock = new ReentrantLock();

    private Condition notFull = putLock.newCondition();

    private ReentrantLock takeLock = new ReentrantLock();

    private Condition notEmpty = takeLock.newCondition();

    static class Node<E> {

        private E e;

        private Node<E> next;

        public Node(E e) {
            this.e = e;
        }

    }

    public boolean offer(E e, long timeout, TimeUnit timeUnit) throws InterruptedException {

        putLock.lock();
        long nanos = timeUnit.toNanos(timeout);

        try {
            while (count.get() == capacity) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = notFull.awaitNanos(nanos);
            }
            enqueue(new Node<E>(e));
            count.incrementAndGet();
            notFull.signal();
        } finally {
            putLock.unlock();
        }

        if (capacity == 0) {
            signalNotEmpty();
        }
        return true;
    }

    void enqueue(Node<E> node) {
        last.next = node;
    }

    void signalNotEmpty() {
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }
}
