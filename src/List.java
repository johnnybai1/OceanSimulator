public class List {
	
	private ListNode head;
	private int size;
	
	public List() {
		size = 0;
		head = null;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int length() {
		return size;
	}
	
	public void insertFront(int life, int type, int length) {
		head = new ListNode(life, type, length, head);
		size ++;
	}
	
	public void insertEnd(int life, int type, int length) {
		if (head == null) {
			head = new ListNode(life, type, length);
		} else {
			ListNode node = head;
			while (node.next != null) {
				node = node.next;
			}
			node.next = new ListNode(life, type, length);
		}
		size ++;
	}

	public Object typeNth(int position) {
	    ListNode currentNode;

	    if ((position < 1) || (head == null)) {
	      return null;
	    } else {
	      currentNode = head;
	      while (position > 1) {
	        currentNode = currentNode.next;
	        if (currentNode == null) {
	          return null;
	        }
	        position--;
	      }
	      return Integer.toString(currentNode.type);
	    }
	}
	
	public Object lengthNth(int position) {
	    ListNode currentNode;

	    if ((position < 1) || (head == null)) {
	      return null;
	    } else {
	      currentNode = head;
	      while (position > 1) {
	        currentNode = currentNode.next;
	        if (currentNode == null) {
	          return null;
	        }
	        position--;
	      }
	      return Integer.toString(currentNode.length);
	    }
	}
}