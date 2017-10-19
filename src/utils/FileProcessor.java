package utils;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;

public class FileProcessor {
	
	/**================================================================
	 * 
	 * @param content of file
	 * @param fileName output file
	 ================================================================*/
	public void writeFile (String content, String fileName) {
		try {
			
			FileOutputStream fos = new FileOutputStream (fileName);
			fos.write(new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF });
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
	
	/**================================================================
	 * 
	 * @param content of file
	 * @param fileName output file
	 ================================================================*/
	public void writeFileNew (String content, String fileName) {
		try {
			
			FileOutputStream fos = new FileOutputStream (fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
	/**========================================================================
	//Read data from  file
	/*========================================================================*/
	public Vector<String> readFile (String filePath) {
		File f = new File (filePath);
		try {
			FileInputStream fis = new FileInputStream (f);
			InputStreamReader isr = new InputStreamReader (fis,"UTF8");
			BufferedReader br = new BufferedReader (isr);
			Vector <String> result = new Vector <String> ();
			while (br.ready()) {
				result.add(br.readLine());
			}
			return result;
		}catch (IOException e) {
			
		}
		return new Vector <String> ();
	}

	public String readFileNew(String string) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer ("");
		File f = new File (string);
		try {
			FileInputStream fis = new FileInputStream (f);
			InputStreamReader isr = new InputStreamReader (fis,"UTF8");
			BufferedReader br = new BufferedReader (isr);
			Vector <String> result = new Vector <String> ();
			while (br.ready()) {
				sb.append(br.readLine() + "\n");
			}
			return sb.toString();
		}catch (IOException e) {
			
		}
		return null;
	}
}
