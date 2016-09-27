package CalculateAccuracy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import NGram.CompareString;
import NGram.Loading;

public class Accuracy {
	Loading ld ;
	public Accuracy (){
		System.out.println("Loading NGram...");
		ld = new Loading();
		ld.LoadNGram("lib/news1gram", "lib/news2gram", "lib/SignInfo.txt", "lib/vocab");
		System.out.println("Done!");
	}
	public double getAccuracy (String fileIn) {
		FileProcessing fp = new FileProcessing();
		String input = fp.readFileNew(fileIn);
		
		input = ld.StandardString(input);
		String[] inputSentence = input.split("[\\.\\!\\,\n\\;\\?]");
		String clearSign = CompareString.getUnsignedString(input).trim();
		//System.out.println(clearSign);
		String out = ld.Processing(clearSign.trim());
		System.out.println(out);
		fp.writeFileNew(out, "lib/out.txt");
		
		String output[] = out.split("\n");
		int countAll = 1;;
		int countMatch = 0;
		int min1 = inputSentence.length;
		if (min1 > output.length) min1 =  output.length;
		for (int i = 0; i < min1; i++) {
			//System.out.println(inputSentence[i] +"\n" + output[i]);
			String wordsIn[] = ld.StandardString(inputSentence[i].trim()).trim().split(" ");
			String wordsOut[] = output[i].trim().split(" ");
			int min = wordsIn.length;
			if (min > wordsOut.length) min =  wordsOut.length;
			for (int j = 0; j < min; j++) {
				if (wordsIn[j].trim().equalsIgnoreCase(wordsOut[j].trim())) countMatch ++;
				else System.out.println(wordsIn[j] + "\t" +wordsOut[j]);
				countAll++;
			}
		}
		System.out.println("Correct:"+countMatch);
		System.out.println("All:"+countAll);
		return new BigDecimal(((double)countMatch*100)/countAll).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) throws IOException {
		/*FileProcessing fp = new FileProcessing();
		String input = fp.readFileNew("lib/test.txt");
		System.out.println(input);*/
		Accuracy a = new Accuracy();
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			System.out.println ("===============================================");
			System.out.print("Nhập vào tên file:");
			String s = br.readLine().trim();
			System.out.print("\nKết quả:");
			System.out.println(a.getAccuracy("lib/"+s+".txt") +"%");
			System.out.println ("===============================================");
		}
	}
	
}
