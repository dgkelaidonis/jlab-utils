package gr.dgk.utils.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
//Importing Apache POI classes
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

public class TextUtils {

	/**
	 * Use this method to extract full text from PDF.
	 * 
	 * @param fullFilepath: The full path that the PDF file is located.
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public static String extractTextFromPdf(String fullFilepath) throws IOException, SAXException, TikaException {
		BodyContentHandler contenthandler = new BodyContentHandler();
		File f = new File(fullFilepath);
		FileInputStream fstream = new FileInputStream(f);
		Metadata data = new Metadata();
		ParseContext context = new ParseContext();
		PDFParser pdfparser = new PDFParser();
		pdfparser.parse(fstream, contenthandler, data, context);
		return contenthandler.toString().replace("\n", " ").replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String extractTextFromWebsite(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		String text = doc.body().text();
		return Jsoup.parse(text).text().replace("\n", " ").replace("\'", "\\'").replaceAll("[^\\p{ASCII}]", "");
	}

	public static void main(String[] args) throws Exception {
		System.out
				.println(TextUtils.extractTextFromPdf("C:/Users/dimikelaidon/Downloads/PGX_PROTEASOME_INHIBITORS.pdf"));
		// System.out.println(TextUtils.extractTextFromWebsite("https://en.wikipedia.org/wiki/Amfissa"));
	}
}
