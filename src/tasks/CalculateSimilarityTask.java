package tasks;

import java.util.PriorityQueue;

import model.Levenshtein;
import model.Node;


public class CalculateSimilarityTask implements Runnable{
	private String original;
	private String wordToCompare;
	private final PriorityQueue<Node<Integer, String>> similarWords;
	private int queueSize;

	public CalculateSimilarityTask(String original, String wordToCompare, int queueSize, PriorityQueue<Node<Integer, String>> similarWords) {
		this.original = original;
		this.wordToCompare = wordToCompare;
		this.queueSize = queueSize;
		this.similarWords = similarWords;
	}

	@Override
	public void run() {
		int similarity = 0;
		if(wordToCompare.contains(original)) {
			similarity = 0;
		} else {
			similarity = Levenshtein.distance(original.toLowerCase(), wordToCompare.toLowerCase());
		}
		synchronized(this.similarWords) {
			if(similarWords.size() < queueSize) {
				similarWords.add(new Node(similarity, wordToCompare));
			} else if(similarWords.peek().getKey().compareTo(similarity) >= 0) {
				similarWords.poll();
				similarWords.add(new Node(similarity, wordToCompare));
			}

		}
	}

}
