package ws.dyt.gank.utils;

public class RegUtils {

	public static boolean isStrNullOrEmpty(String str){
		return null == str ? true : false;
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月28日	上午11:12:21
	 * @function 校验字符串是否是数字类型【最多有一个.号且.号不能出现在开头结尾】,即：无符号整形、无符号浮点型（双精度型）
	 *
	 * @param testStr
	 * @return
	 */
	public static boolean regUintUFloat(String testStr){
		if(isStrNullOrEmpty(testStr)){return false;}
		return testStr.matches("^([1-9]\\d*\\.?\\d+)|0|(^[1-9]+)|(0\\.\\d*[1-9])|([0]\\.\\d+)$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月13日	上午11:48:25
	 * @function 字符是否为数字或者.号
	 *
	 * @param testChar
	 * @return
	 */
	public static boolean regDigitOrDot(char testChar){
		String testStr = String.valueOf(testChar);
		if(isStrNullOrEmpty(testStr)){return false;}
		return testStr.matches("[0-9]|\\.");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月27日	下午1:00:58
	 * @function 字符是否为.号
	 *
	 * @param testChar
	 * @return
	 */
	public static boolean regDot(char testChar){
		return testChar == '.';
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年9月10日	下午6:56:33
	 * @function 校验字符串是否是数字类型,即：无符号整形
	 *
	 * @param testStr
	 * @return
	 */
	public static boolean regUint(String testStr){
		if(isStrNullOrEmpty(testStr)){return false;}
		return testStr.matches("^[1-9]\\d*|0$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年11月18日	上午10:57:39
	 * @function 校验字符串是否是数字类型[非0开头，非0的正数]
	 *
	 * @param ts
	 * @return
	 */
	public static boolean regUintStartWithNot0(String ts){
		if(isStrNullOrEmpty(ts)){return false;}
		return ts.matches("^[1-9]\\d*$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月28日	上午11:33:45
	 * @function 校验输入字符串是否为13位或10时间戳
	 *
	 * @param str
	 * @return
	 */
	public static boolean regTimestamp13Or10(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("\\d{10}") || str.matches("\\d{13}");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月21日	下午2:58:40
	 * @function 用户名正则 	【用户名由汉字、字母、数字至少二种组合，字母区分大小写】
	 *
	 * @param str
	 * @return
	 */
	public static boolean regUserName(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("^(?![a-zA-Z]+$)(?![0-9]+$)(?![\u4E00-\u9FA5]+$)[a-zA-Z0-9\u4E00-\u9FA5]{6,20}$");
//		str = str.trim();
//		if(str.length() < 6 || str.length() > 20){return false;}
//		if(str.matches("[0-9]+")){return false;}
//		if(str.matches("[a-zA-Z]+")){return false;}
//		if(str.matches("[\u4E00-\u9FA5]+")){return false;}
//		return true;
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月23日	下午4:40:07
	 * @function 中国人姓名正则
	 *
	 * @param str
	 * @return
	 */
	public static boolean regRealName(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("^[\u4E00-\u9FA5]{2,6}$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月21日	下午3:02:52
	 * @function 密码正则	【密码必须为6-20位数字和字母的组合，字母区分大小写】
	 *
	 * @param str
	 * @return
	 */
	public static boolean regPswd(String str){
		if(isStrNullOrEmpty(str)){return false;}
		if(str.matches("[a-zA-Z]{1,}")){System.err.println(1);}
		if(str.matches("[0-9]{1,}")){System.err.println(2);}
		return str.matches("[a-zA-Z]{1,}") || str.matches("[0-9]{1,}") || (str.length() < 6 || str.length() > 20) ? false : true;
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月21日	下午3:11:39
	 * @function 手机号正则
	 *
	 * @param str
	 * @return
	 */
	public static boolean regPhone(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("^1[0-9]{10}$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年11月27日	下午2:01:27
	 * @function 固话号码正则
	 *
	 * @param str
	 * @return
	 */
	public static boolean regTelephone(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年11月27日	下午2:02:55
	 * @function QQ号码正则
	 *
	 * @param str
	 * @return
	 */
	public static boolean regQQ(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("^[1-9][0-9]{4,}$");
	}
	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年11月27日	下午1:58:51
	 * @function 验证邮箱正则
	 *
	 * @param str
	 * @return
	 */
	public static boolean regEmail(String str){
		if(isStrNullOrEmpty(str)){return false;}
		return str.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月21日	下午3:12:32
	 * @function 身份证正则
	 *
	 * @param str
	 * @return
	 */
	public static boolean regIdCard(String str){
		if(isStrNullOrEmpty(str)){return false;}
		if(!str.matches("^[1-9][0-9]{16}[0-9xX]$")){return false;}
		int sum = 0;
		char[] rem = new char[]{'1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2'};
		int[] mul = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
		char[] idcardNo = str.toCharArray();
		for(int key = 0; key < mul.length; key++){
			if(Character.toLowerCase(idcardNo[key]) == 'x'){
				sum += 10 * mul[key];
				continue ;
			}
			sum += ((idcardNo[key] - 48) * (int) mul[key]);
		}

		return Character.toLowerCase(idcardNo[17]) == rem[sum % 11];
	}
}