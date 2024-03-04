
package os.project;

public class Node {
    PCB data;
    Node next;

    public Node(PCB data) {
        this.data = data;
        this.next = null;
    }
}
