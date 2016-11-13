package tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.PriorityQueue;
import java.util.Scanner;

import model.Node;

public class FindWordTask implements Runnable{
	private String urbanDictionary = "https://www.urbandictionary.com/popular.php?character=";
	private final PriorityQueue<Node<Integer, String>> similarWords;
	public static final int QUEUE_SIZE = 10;
	private String word;

	public FindWordTask(String word, PriorityQueue<Node<Integer, String>> similarWords) {
		this.word = word;
		this.urbanDictionary += Character.toUpperCase(word.charAt(0));
		//this.allWords = new TreeMap<Integer, String>();
		this.similarWords = similarWords;
	}

	@Override
	public void run() {

		try {
			URL url = new URL(urbanDictionary);
			URLConnection connection = url.openConnection();
			InputStream input = connection.getInputStream();
			Scanner scanner = new Scanner(input);
			addWordsToQueue(scanner);
			scanner.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private void addWordsToQueue(Scanner in) {
		synchronized(this.similarWords){
			while(in.hasNext()) {
				String line = in.nextLine();
				StringBuffer currLine = new StringBuffer(line);
				if(line.contains("<a class=\"popular\"")) {
					
					String wordToCompare = currLine.substring(currLine.indexOf(">") + 1, 
							currLine.lastIndexOf("<"));
					//System.out.println(wordToCompare);
					Thread wordSimilarity = new Thread(
							new CalculateSimilarityTask(word, wordToCompare, similarWords)); 
					wordSimilarity.start();

				}
			}
		}
	}

	public PriorityQueue<Node<Integer, String>> getPriorityQueue() {
		return similarWords;
	}

	/*private class CreateCalculateTask implements Runnable {
		private boolean runTask = true;
		private Thread[] threads;
		public CreateCalculateTask() {
			threads = new Thread[5];
			for(int i = 0; i < threads.length; i++) {
				threads[i] = new Thread();
			}
		}

		public void stopLoop() {
			runTask = false;
		}

		@Override
		public void run() {
			while(runTask) {

				if(!queueOfWords.isEmpty()) {
					String word = queueOfWords.pop();
					boolean addedToThread = false;
					while(!addedToThread) {
						for(int i = 0; i < threads.length; i++) {
							if(!threads[i].isAlive()) {

							}
						}
					}
				}

			}

		}
	}*/

}

