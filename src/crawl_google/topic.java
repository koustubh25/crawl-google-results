package crawl_google;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crawl_google.googleResults;

public class topic {
	
	static final int list_found_score = 10;
	
	//Initialize vectors
	static Map<String, Integer> fashion_vector = new HashMap<String, Integer>();
	static Map<String, Integer> music_vector = new HashMap<String, Integer>();
	static Map<String, Integer> politics_vector = new HashMap<String, Integer>();
	
	static List<String> fashion = new ArrayList<String>();
	static List<String> music = new ArrayList<String>();
	static List<String> politics = new ArrayList<String>();

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//Enter search terms here
		String[] words = {"etude","shoes","water","clothes"};

		//read the file and add to list 
		for (String line : Files.readAllLines(Paths.get("resources/fashion.txt"),Charset.forName("ISO-8859-1")))
			fashion.add(line);  //adding elements in the list
		
		
		//read the file and add to list 
		for (String line : Files.readAllLines(Paths.get("resources/politics.txt"),Charset.forName("ISO-8859-1")))
			politics.add(line);  //adding elements in the list
		
				
		//read the file and add to list 
		for (String line : Files.readAllLines(Paths.get("resources/music.txt"),Charset.forName("ISO-8859-1")))
			music.add(line);  //adding elements in the list
			
		
		//initialise all vector values to 0 inititally
		
		for(String el : fashion)
			fashion_vector.put(el, 0);

		for(String el : music)
			music_vector.put(el, 0);

		for(String el : politics)
			politics_vector.put(el, 0);
		
		//call the function
		topic(words);
		
	}
	public static void  topic(String[] words) throws Exception
	{
		List<Map<String, String>> gresults = new ArrayList<Map<String, String>>();
		String abstract_results;

		for(String word : words)
		{
			//first check if the word is present in any of the word_lists, one list at a time. 
			//Assign a fixed score if found
			if(fashion.contains(word))
				fashion_vector.put(word, list_found_score + fashion_vector.get(word));
			
			if(music.contains(word))
				music_vector.put(word, list_found_score + music_vector.get(word));
			
			if(politics.contains(word))
				politics_vector.put(word, list_found_score + politics_vector.get(word));
			
			
			//Test the vector words to google search
			
			gresults = googleResults.google_results(word, 10);
			Map[] google_results = gresults.toArray(new HashMap[gresults.size()]); 
			
			//check the results
			abstract_results ="";
			for(int i=0;i<google_results.length;i++)
				abstract_results = abstract_results + google_results[i].get("abstract");
				
			System.out.println(abstract_results);
			
			for (Map.Entry<String, Integer> element : fashion_vector.entrySet()) 
			{
				if(element.getValue() == 0)
					element.setValue(element.getValue() + countMatches(abstract_results, element.getKey().toString() ));				
			}
			
			for (Map.Entry<String, Integer> element : music_vector.entrySet()) 
			{
				if(element.getValue() == 0)
				element.setValue(element.getValue() + countMatches(abstract_results, element.getKey().toString() ));
			}
			
			for (Map.Entry<String, Integer> element : politics_vector.entrySet()) 
			{
				if(element.getValue() == 0)
				element.setValue(element.getValue() + countMatches(abstract_results, element.getKey().toString() ));
			}
			
			//print the vectors here and confirm the observations
			
			 for (Map.Entry<String, Integer> entry : music_vector.entrySet()) 
			 {
				 String key = entry.getKey().toString();;
				 Integer value = entry.getValue();
				 System.out.println( key + "\t" +  value );
			 }
			 
			 
			 //Remove this break to continue with the next words in the list
			 break;
		}
	
	}
	
	//counts the number of time a String is present in another string
	public static int countMatches(String str, String sub) {
		
		str = str.toLowerCase();
		sub = sub.toLowerCase();
	      if (isEmpty(str) || isEmpty(sub)) {
	          return 0;
	      }
	      int count = 0;
	      int idx = 0;
	      while ((idx = str.indexOf(sub, idx)) != -1) {
	          count++;
	          idx += sub.length();
	      }
	      return count;
	  }
	
	 public static boolean isEmpty(String str) {
	      return str == null || str.length() == 0;
	  }
}
