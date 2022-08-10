package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (size == index) {
            add(value);
        } else {
            Node<T> nextNode = getNode(index);
            Node<T> newNode = new Node<>(nextNode.prev, value, nextNode);
            newNode.next.prev = newNode;
            if (index == 0) {
                head = newNode;
            } else {
                newNode.prev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        removeNode(node);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if ((object == node.value) || (object != null && object.equals(node.value))) {
                removeNode(node);
                size--;
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index, int bound) {
        if (index < 0 || index > bound) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index, size - 1);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void removeNode(Node<T> node) {
        if (node == head) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
