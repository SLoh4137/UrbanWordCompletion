package model;

import java.util.PriorityQueue;
import tasks.FindWordTask;

public class WebCrawler {
	public static String getWords(String word, int queueSize) {
		StringBuffer results = new StringBuffer("");
		PriorityQueue<Node<Integer, String>> similarWords = new PriorityQueue<Node<Integer, String>>();
		Thread t = new Thread(new FindWordTask(word, queueSize, similarWords));
		t.start();
		try {
			t.join(0);
			results.append("Similar Words\n");
			int counter = 1;
			synchronized(similarWords) {
				for(Node<Integer, String> n : similarWords) {
					results.append(counter + " " + n.getValue() + "\n");
					counter++;
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Interuppted");
		}
		return results.toString();

	}
}
