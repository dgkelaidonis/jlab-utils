package gr.dgk.utils.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractCrawler {
	private String websiteRootUrl = null;

	public AbstractCrawler(String webpageUrl) {
		setWebsiteRootUrl(websiteRootUrl);
	}

	public Document getHtmlPageData(String subDomainUrl) {
		try {
			return Jsoup.connect(getWebsiteRootUrl().concat(subDomainUrl)).ignoreHttpErrors(true).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getWebpageContainerData(String parrentElementId, int childElementDepth, String textDataElementTag) {
		return null;
	}

	public String getWebsiteRootUrl() {
		return websiteRootUrl;
	}

	public void setWebsiteRootUrl(String websiteRootUrl) {
		this.websiteRootUrl = websiteRootUrl;
	}

}
