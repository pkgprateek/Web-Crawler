import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Prateek Kumar Goel on 07-04-2016.
 */
public class Spider {

    //Fields
    private static final int MAX_SEARCH = 10;
    private List<String> pagesToVisit = new LinkedList<String>();
    private Set<String> pagesVisited = new HashSet<String>();

    //Method
    private String nextURL() {
        String nextURL;

        do {
            nextURL = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextURL));

        this.pagesVisited.add(nextURL);

        return nextURL;
    }

    public void search(String url, String searchWord)
    {
        while(this.pagesVisited.size() < MAX_SEARCH)
        {
            String currentUrl;

            // Lots of stuff happening here. Look at the crawl method in SpiderLeg
            SpiderLeg leg = new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl = this.nextURL();
            }
            leg.crawl(currentUrl);

            boolean success = leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
    }
}

class SpiderTest {
    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.search("http://google.com", "computer");
    }
}