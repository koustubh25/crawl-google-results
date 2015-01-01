package crawl_google;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class googleResults {

	public static void main(String[] args) throws Exception{
		
		//pass the search query and the number of results as parameters
		google_results("Natural Language Processing", 10);
	}
	
	public static void google_results(String keyword, int no_of_results) throws Exception
	{
		//Replace space by + in the keyword as in the google search url
		keyword = keyword.replace(" ", "+");
		
		String url = "https://www.google.com/search?q=" + keyword + "&num=" + String.valueOf(no_of_results);
	
		//Connect to the url and obain HTML response
		Document doc = Jsoup
				.connect(url)
				.userAgent("Mozilla")
				.timeout(5000).get();
		
	
		//parsing HTML after examining DOM
		Elements els  = doc.select("li.g");
		for(Element el : els)
		{
			//Print title, site and abstract
			System.out.println("Title : " + el.getElementsByTag("h3").text());
			System.out.println("Site : " +  el.getElementsByTag("cite").text());
			System.out.println("Abstract : " + el.getElementsByTag("span").text() + "\n");
		}

		
	}

}
