package accent.prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccentPredictor {
	Set<String> VOCAB;
	Map<String, Integer> _1Gram; // 1-gram
	Map<String, Integer> _2Grams; // 2-grams
	Set<String> accents;
	int max = 18;
	double MIN = -1000;
	long sizeNGram = 0;
	long totalcountNGram = 0;
	Set<String> globalPosibleChanges = new HashSet<String>();
	
	public AccentPredictor(String _1GramFile, String _2GramsFile, String vocabFile) {
		System.out.println("Loading NGrams...");
		loadNGram(_1GramFile, _2GramsFile, "datasets/AccentInfo.txt", vocabFile);
		System.out.println("Done!");
	}
	
	public AccentPredictor(boolean loadingSmallDatasets) {
		System.out.println("Loading NGrams...");
		if(loadingSmallDatasets)
			loadNGram("datasets/news1gram_small", "datasets/news2grams_small", "datasets/AccentInfo.txt", "datasets/vocab");
		else loadNGram("datasets/news1gram_large", "datasets/news2grams_large", "datasets/AccentInfo.txt", "datasets/vocab");
		System.out.println("Done!");
	}
	
	public void getPosibleChanges(String input, int index,
			Set<String> posibleChanges, Set<String> voCab) {
		if (index > input.length())
			return;
		else if (index == input.length()) {
			if (voCab.contains(input))
				globalPosibleChanges.add(input);
			return;
		}
		char[] charSeq = input.toCharArray();
		boolean check = false;
		for (String s : posibleChanges) {
			if (s.indexOf(charSeq[index]) != -1) {
				for (int i = 0; i < s.length(); i++) {
					char[] tmp = input.toCharArray();
					tmp[index] = s.charAt(i);
					String sTmp = "";
					for (int j = 0; j < input.length(); j++) {
						sTmp += tmp[j] + "";
					}

					getPosibleChanges(sTmp, index + 1, posibleChanges, voCab);
				}
				check = true;
			}

		}
		if (!check)
			getPosibleChanges(input, index + 1, posibleChanges, voCab);
	}

	public Map<String, Integer> getNgrams(String fileIn,
			boolean recalculateSize, boolean recalculateTotal) {
		File f = new File(fileIn);
		Map<String, Integer> ngrams = new HashMap<String, Integer>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		long size = 0, counts = 0;
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis, "UTF8");
			br = new BufferedReader(isr);
			while (br.ready()) {
				String line = br.readLine();
				int indexSpace = line.lastIndexOf(' ');
				int indexTab = line.lastIndexOf('\t');
				if (indexTab < indexSpace)
					indexTab = indexSpace;
				String ngramWord = line.substring(0, indexTab).toLowerCase();
				size++;
				int ngramCount = Integer.parseInt(line.substring(indexTab + 1));
				counts += ngramCount;
				ngrams.put(ngramWord, ngramCount);
			}

		} catch (IOException e) {

		} finally {
			try {
				fis.close();
				br.close();
				isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println(size +"\t" + counts);
		if (recalculateSize)
			sizeNGram = size;
		if (recalculateTotal)
			totalcountNGram = counts;
		return ngrams;
	}

	public Set<String> getAccentInfo(String fileIn) {
		File f = new File(fileIn);
		Set<String> output = new HashSet<String>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis, "UTF8");
			br = new BufferedReader(isr);
			while (br.ready()) {
				String line = br.readLine();
				if (line != null && line.trim().length() > 0)
					output.add(line);
			}
		} catch (IOException e) {

		}
		return output;
	}

	public Set<String> getVocab(String fileIn) {
		File f = new File(fileIn);
		Set<String> output = new HashSet<String>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis, "UTF8");
			br = new BufferedReader(isr);
			while (br.ready()) {
				String line = br.readLine();
				if (line != null && line.trim().length() > 0)
					output.add(line);
			}
		} catch (IOException e) {
		}
		return output;
	}

	public String normaliseString(String input) {
		String in = input.replaceAll("[\t\"\':\\(\\)]", " ").replaceAll(
				"\\s{2,}", " ");
		
		return in;
	}

	public int getGramCount(String ngramWord, Map<String, Integer> ngrams) {
		if (!ngrams.containsKey(ngramWord))
			return 0;
		int output = ngrams.get(ngramWord);
		return output;
	}

	int maxn = 100;
	int maxp = 100;

	public void loadNGram(String _1GramFile, String _2Gram2File,
			String accentInfoFile, String vocabFile) {
		VOCAB = getVocab(vocabFile);
		_1Gram = getNgrams(_1GramFile, true, true);
		_2Grams = getNgrams(_2Gram2File, false, false);
		accents = getAccentInfo(accentInfoFile);
	}

	public Set<String> getPosibleChanges() {
		return globalPosibleChanges;
	}

	public void setPosibleChanges() {
		globalPosibleChanges.clear();
		globalPosibleChanges = new HashSet<String>();
	}
	
	public String predictAccents(String inputContent) {
		String[] inputSentence = inputContent.split("[\\.\\!\\,\n\\;\\?]");
		String output = "";
		for (String input : inputSentence) {
			String in = normaliseString(input).trim();
			String lowercaseIn = in.toLowerCase();
			String[] words = lowercaseIn.split(" ");
			Integer numberP[] = new Integer[words.length];
			Integer trace[][] = new Integer[words.length][maxp];
			Double[][] Q = new Double[words.length][maxp];
			String[][] possibleChange = new String[words.length][maxp];
			for (int i = 0; i < words.length; i++) {
				globalPosibleChanges = new HashSet<String>();
				getPosibleChanges(words[i], 0, accents, VOCAB);
				if (globalPosibleChanges.size() == 0)
					globalPosibleChanges.add(words[i]);
				numberP[i] = globalPosibleChanges.size();
				globalPosibleChanges.toArray(possibleChange[i]);
			}
			for (int i = 0; i < words.length; i++) {
				for (int j = 0; j < maxp; j++) {
					trace[i][j] = 0;
				}
			}

			for (int i = 0; i < numberP[0]; i++)
				Q[0][i] = 0.0;

			if (words.length == 1) {
				int max = 0;
				String sure = words[0];
				for (int i = 0; i < numberP[0]; i++) {
					String possible = possibleChange[0][i];

					int number1GRam = getGramCount(possible, _1Gram);

					if (max < number1GRam) {
						max = number1GRam;
						sure = possible;
					}
				}
				output = output + "\n" + sure;
			} else {
				for (int i = 1; i < words.length; i++) {
					int recentPossibleNum = numberP[i];
					int oldPossibleNum = numberP[i - 1];
					for (int j = 0; j < recentPossibleNum; j++) {
						Q[i][j] = MIN;
						boolean has = false;
						double temp = MIN;
						int count = 0;
						for (int k = 0; k < oldPossibleNum; k++) {
							String _new = possibleChange[i][j];
							String _old = possibleChange[i - 1][k];
							int number2GRam = getGramCount(_old + " " + _new,
									_2Grams);

							int number1GRam = getGramCount(_old, _1Gram);
							if (number2GRam > 0 && number1GRam > 0)
								has = true;
							double log = Math.log((double) (number2GRam + 1)
									/ (number1GRam + totalcountNGram));
							if (temp != Q[i - 1][k]) {
								count++;
								if (temp == MIN)
									temp = Q[i - 1][k];
							}
							double value = Q[i - 1][k] + log;
							if (Q[i][j] < value) {
								Q[i][j] = value;

								trace[i][j] = k;
							}
						}
						if (has == false && count == 1) {
							Q[i][j] = -MIN;
							for (int k = 0; k < oldPossibleNum; k++) {
								String _new = possibleChange[i][j];
								String _old = possibleChange[i - 1][k];
								int number2GRam = getGramCount(_old + " "
										+ _new, _2Grams);

								int number1GRam = getGramCount(_old, _1Gram);

								double log = Math
										.log((double) (number2GRam + 1)
												/ (number1GRam + totalcountNGram));
								double value = log;
								if (Q[i][j] > value) {
									Q[i][j] = value;

									trace[i][j] = k;
								}

								// Q[i - 1][k] +
							}
							Q[i][j] = Q[i][j] + Q[i - 1][trace[i][j]];
						}
					}
				}
				double max = MIN;
				int k = 0;
				for (int i = 0; i < numberP[words.length - 1]; i++) {

					if (max <= Q[words.length - 1][i]) {
						max = Q[words.length - 1][i];
						k = i;
					}
				}
				String result = possibleChange[words.length - 1][k];
				k = trace[words.length - 1][k];
				int i = words.length - 2;

				while (i >= 0) {
					result = possibleChange[i][k] + " " + result;
					k = trace[i--][k];
				}
				output += processOutput(in, result) + "\n";
			}
		}
		return output.trim();
	}

	private String processOutput(String input, String output) {
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < input.length(); i++) {
			char inputChar = input.charAt(i);
			char outputChar = output.charAt(i);
			if(Character.isUpperCase(inputChar))
				str.append(Character.toUpperCase(outputChar));
			else str.append(outputChar);
			
		}	
		return str.toString();
	}
	
	public static void main(String[] args) throws IOException {
		AccentPredictor ap = new AccentPredictor(false); // Load large n-grams files (~ higher accuracy)
		//AccentPredictor ap = new AccentPredictor("datasets/news1gram_small", "datasets/news2grams_small", "datasets/vocab"); // Using your own data
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "utf8"));
			System.out.println("===============================================");
			System.out.print("Nhập vào chuỗi ký tự:");
			String s = (br.readLine());
			System.out.println((s));
			System.out.print("\nKết quả:");
			System.out.println(ap.predictAccents(s));
			System.out.println("===============================================");
		}
	}
}
