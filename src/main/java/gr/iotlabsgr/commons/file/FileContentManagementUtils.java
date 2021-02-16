package gr.iotlabsgr.commons.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileContentManagementUtils {

    public static int linesCounter(String filepath) throws IOException {
	int linesCounter = 0;
	/* source the data from the file */
	FileInputStream fis = new FileInputStream(filepath);
	DataInputStream dis = new DataInputStream(fis);
	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	/* access the file contents line by line */
	while (br.readLine() != null) {
	    linesCounter++;
	}
	dis.close();
	return linesCounter;
    }

    public static int wordsCounter(String filepath) throws IOException {
	String line;
	int wordsCounter = 0;
	/* source the data from the file */
	FileInputStream fis = new FileInputStream(filepath);
	DataInputStream dis = new DataInputStream(fis);
	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	/* access the file contents line by line */
	while ((line = br.readLine()) != null) {
	    wordsCounter += line.split(" ").length;
	}
	dis.close();
	return wordsCounter;
    }

    /**
     * This method takes as input the path of a text file and a word, and it checks
     * whether the word belongs to the text file or not.
     * 
     * @param filepath
     * @param seekWord
     * @return
     * @throws IOException
     */
    public static boolean wordExists(String filepath, String seekWord) throws IOException {
	String line;
	boolean found = false;
	/* source the data from the file */
	FileInputStream fis = new FileInputStream(filepath);
	DataInputStream dis = new DataInputStream(fis);
	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	/* access the file contents line by line */
	while ((line = br.readLine()) != null) {
	    if (new ArrayList<String>(Arrays.asList(line.split(" "))).contains(seekWord.trim())) {
		break;
	    }
	}
	dis.close();
	return found;
    }

    /**
     * THis method takes as input a text file and it counts the occurrences of a
     * word, that is given as input as well.
     * 
     * @param filepath
     * @param seekWord
     * @return
     * @throws IOException
     */
    public static int wordOccurrencesInTextFile(String filepath, String lookupWord) throws IOException {
	String line;
	int seekWordCounter = 0;
	/* source the data from the file */
	FileInputStream fis = new FileInputStream(filepath);
	DataInputStream dis = new DataInputStream(fis);
	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	/* access the file contents line by line */
	while ((line = br.readLine()) != null) {
	    if (new ArrayList<String>(Arrays.asList(line.split(" "))).contains(lookupWord.trim())) {
		seekWordCounter++;
	    }
	}
	dis.close();
	return seekWordCounter;
    }

    /**
     * This method takes as input a text file and it returns a Map<String,Integer>
     * that corresponds to the word and its occurrence in the given text file.
     * 
     * @param filepath
     * @return
     * @throws IOException
     */
    public static Map<String, Integer> wordsCounterInTextFile(String filepath) throws IOException {
	String line;
	List<String> words;
	Integer wordCounter;
	Map<String, Integer> wordOccrrenceMap = new LinkedHashMap<String, Integer>();
	/* source the data from the file */
	FileInputStream fis = new FileInputStream(filepath);
	DataInputStream dis = new DataInputStream(fis);
	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	/* access the file contents line by line */
	while ((line = br.readLine()) != null) {
	    words = new ArrayList<String>(Arrays.asList(line.split(" ")));
	    /* iterate on words */
	    for (String word : words) {
		wordCounter = wordOccrrenceMap.get(word) != null ? wordOccrrenceMap.get(word) + 1 : 1;
		wordOccrrenceMap.put(word, wordCounter);
	    }
	}
	dis.close();
	return wordOccrrenceMap;
    }

}
