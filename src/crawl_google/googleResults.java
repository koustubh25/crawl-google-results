package crawl_google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class googleResults {

		
	public static List<Map<String,String>> google_results(String keyword, int no_of_results) throws Exception
	{
		List<Map<String,String>> gresults = new ArrayList<Map<String,String>>(no_of_results);
		
		//Replace space by + in the keyword as in the google search url
		keyword = keyword.replace(" ", "+");
		
		String url = "https://www.google.co.in/search?q=" + keyword + "&num=" + String.valueOf(no_of_results);
	
		//Connect to the url and obain HTML response
		Document doc = Jsoup
				.connect(url)
				.userAgent("Mozilla")
				.timeout(5000).get();
		
	
		//parsing HTML after examining DOM
		Elements els  = doc.select("li.g");
		
		for(Element el : els)
		{
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("title", el.getElementsByTag("h3").text());
			map.put("site", el.getElementsByTag("cite").text());
			map.put("abstract", el.getElementsByTag("span").text());
			gresults.add(map);
			
			//Print title, site and abstract
			//System.out.println("Title : " + el.getElementsByTag("h3").text());
			//System.out.println("Site : " +  el.getElementsByTag("cite").text());
			//System.out.println("Abstract : " + el.getElementsByTag("span").text() + "\n");
		}

		
		return gresults;
	}

}
