class ListNode {
	
	int life;
	int type;
	int length;
	ListNode next;
	
	ListNode(int life, int type, int length, ListNode next) {
		this.life = life;
		this.type = type;
		this.length = length;
		this.next = next;
	}
	
	ListNode(int life, int type, int length) {
		this(life, type, length, null);
	}
}