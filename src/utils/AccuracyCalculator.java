package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import accent.prediction.AccentPredictor;

public class AccuracyCalculator {
	AccentPredictor ld ;
	public AccuracyCalculator(boolean loadSmallDatasets) {
		ld = new AccentPredictor(loadSmallDatasets);
	}
	
	public double getAccuracy(String fileIn) {
		FileProcessor fp = new FileProcessor();
		String input = fp.readFileNew(fileIn);
		input = ld.normaliseString(input);
		String[] inputSentence = input.split("[\\.\\!\\,\n\\;\\?]");
		String clearSign = CompareString.getUnsignedString(input).trim();
		String out = ld.predictAccents(clearSign.trim());
		String output[] = out.split("\n");
		int countAll = 1;;
		int countMatch = 0;
		int min1 = inputSentence.length;
		if (min1 > output.length) min1 =  output.length;
		for (int i = 0; i < min1; i++) {
			//System.out.println(inputSentence[i] +"\n" + output[i]);
			String wordsIn[] = ld.normaliseString(inputSentence[i].trim()).trim().split(" ");
			String wordsOut[] = output[i].trim().split(" ");
			int min = wordsIn.length;
			if (min > wordsOut.length) min =  wordsOut.length;
			for (int j = 0; j < min; j++) {
				if (wordsIn[j].trim().equalsIgnoreCase(wordsOut[j].trim())) countMatch ++;
				//else System.out.println(wordsIn[j] + "\t" +wordsOut[j]);
				countAll++;
			}
		}
		System.out.println("Correct:"+countMatch);
		System.out.println("All:"+countAll);
		return new BigDecimal(((double)countMatch*100)/countAll).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) throws IOException {
		AccuracyCalculator a = new AccuracyCalculator(false);
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			System.out.println ("===============================================");
			System.out.print("Nhập vào tên file:");
			String s = br.readLine().trim();
			System.out.print("\nKết quả:\n");
			System.out.println("Accuracy:" + a.getAccuracy("datasets/"+s+".txt") +"%");
			System.out.println ("===============================================");
		}
	}
	
}
