package org.example.film_pro;

public class LinkedList <T>{
    Node<T> head = null;

    public void initialize(T data)
    {
        head = new Node<T>(data);
        head.next = null;
    }
    public int elementCount()
    {
        Node temp = head;
        int count = 0;
        while(temp != null)
        {
            count++;
            temp = temp.next;
        }
        return count;
    }
    public void add(T data)
    {
        Node newNode = new Node<T>(data);
        newNode.next = head;
        head = newNode;
    }
    public void addElementLast(T data)
    {
        Node newNode = new Node<T>(data);
        newNode.next = null;
        Node temp = head;
        while(temp.next != null)
        {
            temp = temp.next;
        }
        temp.next = newNode;
    }
    //Attention to index. it starts from 0-(n-1)
    public T getData(int i){
        Node<T> newNode = head;

        for(int counter = 0;counter < i; counter++){
            if(newNode != null){
                newNode = newNode.next;
            }
            else{
                return null;
            }
        }
        return newNode.data;

    }
    public void deleteData(int i){
        if (head == null) {
            // List is empty, nothing to delete
            return;
        }

        if (i == 0) {
            // Special case: deleting the head node
            head = head.next;
            return;
        }

        Node<T> current = head;
        for (int counter = 0; counter < i - 1; counter++) {
            if (current.next == null) {
                // If next node is null, index i is out of bounds
                return;
            }
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        } else {
            // If we're trying to delete beyond the end of the list, do nothing
            return;
        }
    }

}
