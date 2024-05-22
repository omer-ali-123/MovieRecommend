package org.example.film_pro;

public class StackList <T>{
    Node<T> head;
    public StackList(){
        this.head = null;
    }
    public void add(T data){
        Node<T> newNode=new Node(data);
        newNode.next=head;
        head=newNode;
    }
    public T delete(){
        T silinen=head.data;
        head=head.next;
        return silinen;
    }
}
