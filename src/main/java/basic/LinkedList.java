package basic;

import java.util.Random;


class LinkedList<T> {
static class LinkNode<T> {
    T data;
    LinkNode<T> prev, next;

    public LinkNode(T node) {
        data = node;
    }

    public LinkNode() {
    }
}

LinkNode<T> head, rear;
int cnt;

public LinkedList() {
    head = rear = new LinkNode<T>();
    cnt = 0;
}

void pushHead(T node) {
    LinkNode<T> newNode = new LinkNode<T>(node);
    if (cnt == 0) {
        head.next = newNode;
        newNode.prev = head;
        rear = newNode;
    } else {
        LinkNode<T> temp = head.next;
        head.next = newNode;
        newNode.next = temp;
        temp.prev = newNode;
        newNode.prev = head;
    }
    cnt++;
}

void pushEnd(T node) {
    LinkNode<T> newNode = new LinkNode<T>(node);
    rear.next = newNode;
    newNode.prev = rear;
    cnt++;
    rear = newNode;
}

public static void main(String[] args) {
    LinkedList<Integer> a = new LinkedList<>();
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
        if (random.nextBoolean())
            a.pushEnd(i);
        else
            a.pushHead(i);
    }
    for (LinkNode<Integer> i = a.head.next; i != null; i = i.next) {
        System.out.print(" " + i.data);
    }
    System.out.println("\nhaha\n");
    for (LinkNode<Integer> i = a.rear; i != a.head; i = i.prev) {
        System.out.print(" " + i.data);
    }
}
}