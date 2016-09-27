package NGram;

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

public class Loading {
	Set<String> VOCAB;
	Map<String, Integer> _1Grams;
	Map<String, Integer> _2Grams;
	Set<String> signs;
	int max = 18;
	double MIN = -1000;
	Set<String> PosibleChange = new HashSet<String>();

	public void GetPosibleChange(String input, int index,
			Set<String> posibleChange, Set<String> voCab) {
		if (index > input.length())
			return;
		else if (index == input.length()) {
			if (voCab.contains(input))
				PosibleChange.add(input);
			return;
		}
		char[] charSeq = input.toCharArray();
		boolean check = false;
		for (String s : posibleChange) {
			if (s.indexOf(charSeq[index]) != -1) {
				for (int i = 0; i < s.length(); i++) {
					char[] tmp = input.toCharArray();
					tmp[index] = s.charAt(i);
					String sTmp = "";
					for (int j = 0; j < input.length(); j++) {
						sTmp += tmp[j] + "";
					}

					GetPosibleChange(sTmp, index + 1, posibleChange, voCab);
				}
				check = true;
			}

		}
		if (!check)
			GetPosibleChange(input, index + 1, posibleChange, voCab);
	}

	long sizeNGram = 0;
	long totalcountNGram = 0;

	public Map<String, Integer> ReadNgramFile(String fileIn,
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

	public Set<String> ReadSignInfo(String fileIn) {
		File f = new File(fileIn);
		Set<String> output = new HashSet();
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

	public Set<String> ReadVocab(String fileIn) {
		File f = new File(fileIn);
		Set<String> output = new HashSet();
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

	public String StandardString(String input) {
		String in = input.replaceAll("[\t\"\':\\(\\)]", " ").replaceAll(
				"\\s{2,}", " ");
		in = in.toLowerCase();
		return in;
	}

	public int GetGramCount(String ngramWord, Map<String, Integer> ngrams) {
		if (!ngrams.containsKey(ngramWord))
			return 0;
		int output = ngrams.get(ngramWord);
		return output;
	}

	int maxn = 100;
	int maxp = 100;

	public void LoadNGram(String _1GramFile, String _2GramFile,
			String signInfo, String voCabFile) {
		VOCAB = ReadVocab(voCabFile);
		_1Grams = ReadNgramFile(_1GramFile, true, true);
		_2Grams = ReadNgramFile(_2GramFile, false, false);
		signs = ReadSignInfo(signInfo);
	}

	public String Processing(String inputContent) {
		String[] inputSentence = inputContent.split("[\\.\\!\\,\n\\;\\?]");
		String output = "";
		Date d1 = new Date();
		for (String input : inputSentence) {
			String in = StandardString(input).trim();
			String[] words = in.split(" ");
			Integer numberP[] = new Integer[words.length];
			Integer trace[][] = new Integer[words.length][maxp];
			Double[][] Q = new Double[words.length][maxp];
			String[][] possibleChange = new String[words.length][maxp];
			for (int i = 0; i < words.length; i++) {
				PosibleChange = new HashSet<String>();
				GetPosibleChange(words[i], 0, signs, VOCAB);
				if (PosibleChange.size() == 0)
					PosibleChange.add(words[i]);
				numberP[i] = PosibleChange.size();
				PosibleChange.toArray(possibleChange[i]);
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

					int number1GRam = GetGramCount(possible, _1Grams);

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
							int number2GRam = GetGramCount(_old + " " + _new,
									_2Grams);

							int number1GRam = GetGramCount(_old, _1Grams);
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
								int number2GRam = GetGramCount(_old + " "
										+ _new, _2Grams);

								int number1GRam = GetGramCount(_old, _1Grams);

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
				output += result + "\n";
			}
		}
		Date d2 = new Date();
		 System.out.println(d2.getTime() - d1.getTime());
		return output;
	}

	public Set<String> getPosibleChange() {
		return PosibleChange;
	}

	public void setPosibleChange() {
		PosibleChange.clear();
		PosibleChange = new HashSet<String>();
	}

	public static void main(String[] args) throws IOException {
		Loading ld = new Loading();
		System.out.println("Loading NGram...");
		ld.LoadNGram("lib/news1gram", "lib/news2gram", "lib/SignInfo.txt",
				"lib/vocab");
		System.out.println("Done!");
		/*
		 * System.out .println(ld .Processing( CompareString.getUnsignedString(
		 * "Một là dùng máy bay trực thăng du lịch kéo tấm lưới bọc cụ rùa đưa lên tháp Rùa; hai là dùng ca-nô quân sự có cần trục máy 5 - 7 tạ để trục nhấc tấm lưới bọc cụ rùa đưa lên bờ.Ông Khôi cho biết thêm, việc bắt cụ rùa dưới nước bằng lưới không khó bằng việc đưa cụ rùa từ dưới nước lên bờ. Đây là công việc cần phải rất cẩn trọng, không thể dùng sức người đưa cụ rùa lên bằng tay được, vì không cẩn thận sẽ gây thương tích hoặc làm gãy chân cụ rùa.Cũng theo ông Khôi, phương án dùng máy bay trực thăng đưa cụ rùa lên bờ là an toàn nhất. Tất cả những đề xuất trên sẽ được lãnh đạo TP Hà Nội quyết định trong cuộc họp ngày"
		 * ), "lib/news1gram", "lib/news2gram", "lib/SignInfo.txt",
		 * "lib/vocab"));
		 */

		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "utf8"));
			System.out
					.println("===============================================");
			System.out.print("Nhập vào chuỗi ký tự:");
			String s = (br.readLine());
			System.out.println((s));
			System.out.print("\nKết quả:");
			System.out.println(ld.Processing(s));
			System.out
					.println("===============================================");
		}
	}
}
