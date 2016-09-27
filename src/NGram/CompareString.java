package NGram;
public class CompareString {
	public static String getUnsignedString(String s) {
        StringBuffer unsignedString = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
        	if (s.codePointAt(i) < 700 || s.codePointAt(i) > 850)
            unsignedString.append(getUnsignedChar(s.charAt(i)));
            //System.out.println(s.charAt(i) +":" + s.codePointAt(i));
        }
        return unsignedString.toString();
	}
 /**
  * aàáảạãâầấẩậẫăằắẳặẵ
eèéẻẹẽêềếểệễ
iìíỉịĩ
oòóỏọõôồốổộỗơờớởợỡ
uùúủụũưừứửựữ
yỳýỷỵỹ
dđ
  * @param c
  * @return
  */
	/*public static char getUnsignedChar(char c) {
		switch (c) {
		case'a':
		case'à':
		case'á':
		case'ả':
		case'ạ':
		case'ã':
		case'â':
		case'ầ':
		case'ấ':
		case'ẩ':
		case'ậ':
		case'ẫ':
		case'ă':
		case'ằ':
		case'ắ':
		case'ẳ':
		case'ặ':
		case'ẵ':
		{
			return 'a';
		}
		case'e':
		case'è':
		case'é':
		case'ẻ':
		case'ẹ':
		case'ẽ':
		case'ê':
		case'ề':
		case'ế':
		case'ể':
		case'ệ':
		case'ễ':
		{
			return 'e';
		}
		case'i':
		case'ì':
		case'í':
		case'ỉ':
		case'ị':
		case'ĩ':
		{
			return 'i';
		}
		case'o':
		case'ò':
		case'ó':
		case'ỏ':
		case'ọ':
		case'õ':
		case'ô':
		case'ồ':
		case'ố':
		case'ổ':
		case'ộ':
		case'ỗ':
		case'ơ':
		case'ờ':
		case'ớ':
		case'ở':
		case'ợ':
		case'ỡ':
		{
			return 'o';
		}
		case'u':
		case'ù':
		case'ú':
		case'ủ':
		case'ụ':
		case'ũ':
		case'ư':
		case'ừ':
		case'ứ':
		case'ử':
		case'ự':
		case'ữ':
		{
			return 'u';
		}
		case'y':
		case'ỳ':
		case'ý':
		case'ỷ':
		case'ỵ':
		case'ỹ':
		{
			return 'y';
		}
		case'd':
		case'đ':
		{
			return 'd';
		}
		default: return c;
		}
	}*/
	public static char getUnsignedChar(char c) {
	    if (c=='\u00E1'||c=='\u00E0'||c=='\u1EA3'||c=='\u00E3'||c=='\u1EA1'||
	        c=='\u0103'||c=='\u1EAF'||c=='\u1EB1'||c=='\u1EB3'||c=='\u1EB5'||c=='\u1EB7'||
	        c=='\u00E2'||c=='\u1EA5'||c=='\u1EA7'||c=='\u1EA9'||c=='\u1EAB'||c=='\u1EAD') {
	        return 'a';
	    } else if (c=='\u00C1'||c=='\u00C0'||c=='\u1EA2'||c=='\u00C3'||c=='\u1EA0'||
	        c=='\u0102'||c=='\u1EAE'||c=='\u1EB0'||c=='\u1EB2'||c=='\u1EB4'||c=='\u1EB6'||
	        c=='\u00C2'||c=='\u1EA4'||c=='\u1EA6'||c=='\u1EA8'||c=='\u1EAA'||c=='\u1EAC') {
	        return 'A';
	    } else if (c=='\u00E9'||c=='\u00E8'||c=='\u1EBB'||c=='\u1EBD'||c=='\u1EB9'||
	        c=='\u00EA'||c=='\u1EBF'||c=='\u1EC1'||c=='\u1EC3'||c=='\u1EC5'||c=='\u1EC7') {
	        return 'e';
	    } else if (c=='\u00C9'||c=='\u00C8'||c=='\u1EBA'||c=='\u1EBC'||c=='\u1EB8'||
	        c=='\u00CA'||c=='\u1EBE'||c=='\u1EC0'||c=='\u1EC2'||c=='\u1EC4'||c=='\u1EC6') {
	        return 'E';
	    } else if (c=='\u00ED'||c=='\u00EC'||c=='\u1EC9'||c=='\u0129'||c=='\u1ECB') {
	        return 'i';
	    } else if (c=='\u00CD'||c=='\u00CC'||c=='\u1EC8'||c=='\u0128'||c=='\u1ECA') {
	        return 'I';
	    } else if (c=='\u00F3'||c=='\u00F2'||c=='\u1ECF'||c=='\u00F5'|c=='\u1ECD'||
	        c=='\u00F4'||c=='\u1ED1'||c=='\u1ED3'||c=='\u1ED5'||c=='\u1ED7'||c=='\u1ED9'||
	        c=='\u01A1'||c=='\u1EDB'||c=='\u1EDD'||c=='\u1EDF'||c=='\u1EE1'||c=='\u1EE3') {
	        return 'o';
	    } else if (c=='\u00D3'||c=='\u00D2'||c=='\u1ECE'||c=='\u00D5'|c=='\u1ECC'||
	        c=='\u00D4'||c=='\u1ED0'||c=='\u1ED2'||c=='\u1ED4'||c=='\u1ED6'||c=='\u1ED8'||
	        c=='\u01A0'||c=='\u1EDA'||c=='\u1EDC'||c=='\u1EDE'||c=='\u1EE0'||c=='\u1EE2') {
	        return 'O';
	    } else if (c=='\u00FA'||c=='\u00F9'||c=='\u1EE7'||c=='\u0169'|c=='\u1EE5'||
	        c=='\u01B0'||c=='\u1EE9'||c=='\u1EEB'||c=='\u1EED'||c=='\u1EEF'||c=='\u1EF1') {
	        return 'u';
	    } else if (c=='\u00DA'||c=='\u00D9'||c=='\u1EE6'||c=='\u0168'|c=='\u1EE4'||
	        c=='\u01AF'||c=='\u1EE8'||c=='\u1EEA'||c=='\u1EEC'||c=='\u1EEE'||c=='\u1EF0') {
	        return 'U';
	    } else if (c=='\u00FD'||c=='\u1EF3'||c=='\u1EF7'||c=='\u1EF9'||c=='\u1EF5') {
	        return 'y';
	    } else if (c=='\u00DD'||c=='\u1EF2'||c=='\u1EF6'||c=='\u1EF8'||c=='\u1EF4') {
	        return 'Y';
	    } else if(c=='\u0111'){
	        return 'd';
	    }else if(c=='\u0110'){
	        return 'D';
	    }
	    
	    return c;
	}
	public static boolean compareString(String str1, String str2) {
		/*String regex = " |\n|\t|\\.|,|;\"\'";
		String tmp1 = getUnsignedString(str1).toLowerCase().replaceAll(regex, "");
		String tmp2 = getUnsignedString(str2).toLowerCase().replaceAll(regex, "");*/
		//System.out.println (str1 + "\t" + str2 + ":" +str1.equalsIgnoreCase(str2));
		return str1.trim().equalsIgnoreCase(str2.trim());
	}
	public static void main(String[] args) {
		CompareString cs = new CompareString();
		//System.out.println(cs.getUnsignedString("Tổ chức lưu trữ thông tin trên máy tính và sử dụng máy tính để giải quyết vấn đề thông dụng.".toLowerCase()));
	}
}
