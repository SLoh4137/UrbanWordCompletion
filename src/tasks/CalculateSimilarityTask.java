package tasks;

import java.util.PriorityQueue;
import java.util.TreeMap;

import model.Levenshtein;
import model.Node;


public class CalculateSimilarityTask implements Runnable{
	private String original;
	private String wordToCompare;
	//private TreeMap<Integer, String> allWords;
	private final PriorityQueue<Node<Integer, String>> similarWords;


	public CalculateSimilarityTask(String original, String wordToCompare, PriorityQueue<Node<Integer, String>> similarWords) {
		this.original = original;
		this.wordToCompare = wordToCompare;
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
			if(similarWords.size() < FindWordTask.QUEUE_SIZE) {
				similarWords.add(new Node(similarity, wordToCompare));
			} else if(similarWords.peek().getKey().compareTo(similarity) >= 0) {
				similarWords.poll();
				similarWords.add(new Node(similarity, wordToCompare));
			}

		}
	}

}
