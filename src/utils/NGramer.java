package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Vector;

/**
 * This class is to do n-grams statistic
 * @author thanhvu
 *
 */
public class NGramer {
	String folderPath;
	public NGramer(String folderPath) {
		this.folderPath = folderPath;
	}
	
	public void statisticNGrams(int nFileToProcess, boolean lowerCase, String _1GramFile, String _2GramsFile) {
		HashMap<String, Integer> _1GramMap = new HashMap<String, Integer>();
		HashMap<String, Integer> _2GramsMap = new HashMap<String, Integer>();
		System.out.println(this.folderPath);
		String[] fileList = new File(this.folderPath).list();
		FileProcessor fileProcessor = new FileProcessor();
		int count = 0;
		if(nFileToProcess < 0) nFileToProcess = fileList.length + 5;
		for(String fileName : fileList) {
			count ++;
			if(count > nFileToProcess) break;
			System.out.println(fileName);
			Vector<String> lines = fileProcessor.readFile(this.folderPath + "/" + fileName);
			for(String line : lines) {
				if(lowerCase) line = line.toLowerCase();
				String[] syllables = line.replace("_", " ").split("\\s+");
				for(int i = 0; i < syllables.length; i++) {
					String _1Gram = syllables[i];
					
					if(_1GramMap.containsKey(_1Gram)) {
						_1GramMap.put(_1Gram, _1GramMap.get(_1Gram) + 1);
					}
					else
						_1GramMap.put(_1Gram, 1);
					
					if(i < syllables.length - 1) {
						String _2Grams = syllables[i] + " " + syllables[i + 1];
						if(_2GramsMap.containsKey(_2Grams)) {
							_2GramsMap.put(_2Grams, _2GramsMap.get(_2Grams) + 1);
						}
						else
							_2GramsMap.put(_2Grams, 1);
					}
				}
			}
		}
		writeToFile(_1GramMap, _1GramFile);
		writeToFile(_2GramsMap, _2GramsFile);
	}
	
	private void writeToFile(HashMap<String, Integer> map, String fileOut) {
		try {
			
			FileOutputStream fos = new FileOutputStream (fileOut);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			for(String ngrams : map.keySet()) {
				out.write(ngrams + "\t" + map.get(ngrams) + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		new NGramer("/Users/thanhvu/Documents/PhDCode/BaomoiData/vnexpress").
		statisticNGrams(2000, true, "datasets/news1gram_vnexpress", "datasets/news2grams_vnexpress");
		
	}
}
