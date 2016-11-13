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
	private int queueSize;
	private String word;

	public FindWordTask(String word, int queueSize, PriorityQueue<Node<Integer, String>> similarWords) {
		this.word = word;
		this.queueSize = queueSize;
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
							new CalculateSimilarityTask(word, wordToCompare, queueSize, similarWords)); 
					wordSimilarity.start();

				}
			}
		}
	}

}

