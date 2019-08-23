import java.util.NoSuchElementException;

public class Queue<T> {
    Node<T> front;
    Node<T> back;

    public Queue() {
        this.front = null;
        this.back = null;
    }

    public void enqueue(T value) {
        Node<T> newBack = new Node<>(value);
        if (this.front == null) {
            this.front = newBack;
        } else {
            this.back.next = newBack;
        }
        this.back = newBack;
    }

    public T dequeue() {
        if (this.front == null) {
            throw new NoSuchElementException("cannot dequeue from an empty queue!");
        } else {
            Node<T> temp = this.front;
            this.front = this.front.next;
            return temp.value;
        }
    }

    public T peek() {
        if (this.front == null) {
            throw new NoSuchElementException("cannot peek at an empty queue!");
        } else {
            return this.front.value;
        }
    }

    public boolean isEmpty() {
        return this.front == null;
    }

    public String toString() {
        return "Queue: " + (this.front == null ? "" : this.front.toString());
    }

    public boolean isRotation(Queue<T> otherQueue) {
        // check each possible "starting" position in the other queue, starting at the front
        Node<T> otherQueueStart = otherQueue.front;
        otherStartLoop: while(otherQueueStart != null) {
            // check if starting at otherQueueStart in otherQueue matches starting at the front of thisQueue
            Node<T> thisQueueCurrent = this.front;
            Node<T> otherQueueCurrent = otherQueueStart;
            while (thisQueueCurrent != null) {
                // if we fell off the end of the other queue, reset to look at the front of that queue
                if (otherQueueCurrent == null) {
                    otherQueueCurrent = otherQueue.front;
                }
                // if the two current nodes don't match each other, then we've found a mismatch, this starting position is invalid
                if (!thisQueueCurrent.value.equals(otherQueueCurrent.value)) {
                    // this start location in otherQueue won't work!
                    // move the otherQueueStart forward by one to try again next time through the outer loop.
                    otherQueueStart = otherQueueStart.next;
                    continue otherStartLoop;
                }
                // they matched! we're still good for now. move both pointers forward.
                thisQueueCurrent = thisQueueCurrent.next;
                otherQueueCurrent = otherQueueCurrent.next;
                // if we made it back to the start in otherQueue, but we haven't finished thisQueue, then otherQueue is shorter and they can't be rotations.
                if (otherQueueCurrent == otherQueueStart && thisQueueCurrent != null) {
                    return false;
                }
            }
            // if we made it all the way through that loop, then as long as we've made it back to the start of otherQueue, we win!
            // but if not, then they're different lengths.
            // either way, we have our answer.
            return otherQueueCurrent == otherQueueStart;
        }
        // if no start position in the other queue works, then they are not rotations of each other
        return false;
    }

    // main method for testing
    public static void main(String[] args) {
        Queue<String> q1 = new Queue<>();
        q1.enqueue("hello");
        q1.enqueue("world");
        System.out.println(q1);

        Queue<String> q2 = new Queue<>();
        q2.enqueue("world");
        q2.enqueue("hello");
        q2.enqueue("world");
        System.out.println(q2);

        Queue<String> q3 = new Queue<>();
        q3.enqueue("hello");
        q3.enqueue("world");
        q3.enqueue("world");

        System.out.println(q1.isRotation(q2));
        System.out.println(q2.isRotation(q1));
        System.out.println(q3.isRotation(q2));
        System.out.println(q2.isRotation(q3));
    }
}

class Node<T> {
    T value;
    Node<T> next;

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public Node(T value) {
        this(value, null);
    }

    public String toString() {
        return this.value.toString() + (this.next == null ? "" : "->" + this.next.toString());
    }
}
