package os.project;

public class Queue {
      Node head, tail;

    public Queue() {
        head = tail = null;
    }

    public void enqueue(PCB pcb) {
        Node temp = new Node(pcb);
        if (tail == null) {
            head = tail = temp;
            return;
        }
        tail.next = temp;
        tail = temp;
    }

    public PCB dequeue() {
        if (head == null) {
            return null;
        }
        Node temp = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return temp.data;
    }

    boolean isEmpty() {
    return head == null && tail == null;
    }

  
}



