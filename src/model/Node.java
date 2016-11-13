package model;

public class Node<K extends Comparable, T> implements Comparable<Node>{
		private K key;
		private T value;
		
		public Node(K key, T value) {
			this.key = key;
			this.value = value;
		}

		public Node() {
			this(null, null);
		}

		public K getKey() {
			return key;
		}
		
		public T getValue() {
			return value;
		}
		
		public int compareTo(Node n) {
			return key.compareTo(n.key);
		}
}
