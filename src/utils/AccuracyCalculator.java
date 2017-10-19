package utils;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import accent.prediction.AccentPredictor;

public class AccuracyCalculator {
	AccentPredictor ld ;
	public AccuracyCalculator() {
		ld = new AccentPredictor();
	}
	
	public double getAccuracy(String fileIn) {
		FileProcessor fp = new FileProcessor();
		String input = fp.readFileNew(fileIn);
		input = Utils.normaliseString(input);
		String[] inputSentence = input.split("[\\.\\!\\,\n\\;\\?]");
		String clearSign = CompareString.getUnsignedString(input).trim();
		Date start = new Date();
		String out = ld.predictAccents(clearSign.trim());
		double processedTime = (new Date().getTime() - start.getTime())*1.0/1000;
		System.out.println("Processed time: " + processedTime + " seconds");
		String output[] = out.split("\n");
		System.out.println("Speed: " + output.length*1.0/processedTime + " sents/second");
		System.out.println("Speed: " + out.split("\\s+").length*1.0/processedTime + " words/second");
		int countAll = 1;;
		int countMatch = 0;
		
		for (int i = 0; i < inputSentence.length; i++) {
			//System.out.println(inputSentence[i] +"\n" + output[i]);
			String wordsIn[] = Utils.normaliseString(inputSentence[i]).trim().split(" ");
			String wordsOut[] = output[i].trim().split(" ");
			if (wordsIn.length == wordsOut.length) 
				for (int j = 0; j < wordsOut.length; j++) {
					if (wordsIn[j].trim().equalsIgnoreCase(wordsOut[j].trim())) countMatch ++;
				
					countAll++;
				}
		}
		System.out.println("Correct:"+countMatch);
		System.out.println("All:"+countAll);
		return new BigDecimal(((double)countMatch*100)/countAll).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) throws IOException {
		AccuracyCalculator aC = new AccuracyCalculator();
		System.out.println("Accuracy:" + aC.getAccuracy("datasets/test.txt") +"%");
	}
	
}
