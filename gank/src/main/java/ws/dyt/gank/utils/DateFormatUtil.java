package ws.dyt.gank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @function:
 * @author YangXiaoWei
 * @date 2012-5-25 下午1:07:32
 */
public class DateFormatUtil {
	public static String formatTo(String tpl, String dateStr){
		if(!RegUtils.regTimestamp13Or10(dateStr)){return null;}
		String e = dateStr;
		if(10 == dateStr.length()){
			e = dateStr + "000";
		}
		return formatTo(tpl, new Date(Long.valueOf(e)));
	}

	public static String formatTo(String tpl, Date date){
		java.text.DateFormat dateFormat1 = new SimpleDateFormat(tpl, Locale.getDefault());
		String newdate = dateFormat1.format(date);

		return newdate;
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年11月12日	下午3:29:49
	 * @function
	 *
	 * @param dateStr
	 * @return  11月04日
	 */
	public static String formatTo_MM_dd(String dateStr){
		if(!RegUtils.regTimestamp13Or10(dateStr)){return null;}
		String e = dateStr;
		if(10 == dateStr.length()){
			e = dateStr + "000";
		}
		return formatTo_MM_dd(new Date(Long.valueOf(e)));
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年11月12日	下午3:31:17
	 * @function
	 *
	 * @param date
	 * @return	11月04日
	 */
	public static String formatTo_MM_dd(Date date){
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("MM月dd日", Locale.getDefault());
		String newdate = dateFormat1.format(date);

		return newdate;
	}

	public static String formatTo_MM(String dateStr){
		if(!RegUtils.regTimestamp13Or10(dateStr)){return null;}
		String e = dateStr;
		if(10 == dateStr.length()){
			e = dateStr + "000";
		}
		return formatTo_yyyy_MM_dd_HH_mm_ss(new Date(Long.valueOf(e)));
	}
	public static String formatTo_MM(Date date){
		java.text.DateFormat format = new SimpleDateFormat("MM", Locale.getDefault());
		return format.format(date);
	}


	/**
	 * @function 格式化成yyyy_MM_dd_HH_mm_ss
	 * @param dateStr	支持毫秒、秒级别转化
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd_HH_mm_ss(String dateStr){
		if(!RegUtils.regTimestamp13Or10(dateStr)){return null;}
		String e = dateStr;
		if(10 == dateStr.length()){
			e = dateStr + "000";
		}
		Date date1 = new Date(Long.valueOf(e));
//		String newdate = dateFormat1.format(date1);

		return formatTo_yyyy_MM_dd_HH_mm_ss(date1);
	}
	/**
	 * @function 格式化成yyyy_MM_dd_HH_mm_ss
	 * @param date
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd_HH_mm_ss(Date date){
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		String newdate = dateFormat1.format(date);
		return newdate;
	}

	public static String formatTo_yyyy_MM_dd_HH_mm_2(String dateStr){
		if(!RegUtils.regTimestamp13Or10(dateStr)){return null;}
		String e = dateStr;
		if(10 == dateStr.length()){
			e = dateStr + "000";
		}
		Date date1 = new Date(Long.valueOf(e));

		return formatTo_yyyy_MM_dd_HH_mm_2(date1);
	}

	public static String formatTo_yyyy_MM_dd_HH_mm_2(Date date){
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
		String newdate = dateFormat1.format(date);

		return newdate;
	}


	public static String formatTo_yyyy_MM_dd(Date date){
		if(null == date){return null;}
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		return dateFormat1.format(date);
	}

	/**
	 * @function 格式化成yyyy年MM月dd日
	 * @param dateStr
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd_1(String dateStr){
		if(!RegUtils.regTimestamp13Or10(dateStr)){return null;}
		String e = dateStr;
		if(10 == dateStr.length()){
			e = dateStr + "000";
		}
		return formatTo_yyyy_MM_dd_1(new Date(Long.valueOf(e)));
	}

	/**
	 * @function 格式化成yyyy年MM月dd日
	 * @param date
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd_1(Date date){
		if(null == date){return null;}
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
		return dateFormat1.format(date);
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月12日	下午6:55:23
	 * @function 格式化成yyyy_MM_dd
	 *
	 * @param date	长整型的时间戳  支持毫秒、秒级别转化
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd(String date){
		if(null == date){return null;}
		if(!RegUtils.regTimestamp13Or10(date)){return null;}
		String e = date;
		if(10 == date.length()){
			e = date + "000";
		}
		return formatTo_yyyy_MM_dd(new Date(Long.valueOf(e)));
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月15日	上午10:25:52
	 * @function 格式化成	2014年6月6日21:30
	 *
	 * @param date
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd_HH_mm(Date date){
		if(null == date){return null;}
		java.text.DateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日HH:mm", Locale.getDefault());
		return dateFormat1.format(date);
	}


	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年8月12日	下午6:55:23
	 * @function 格式化成	2014年6月6日21:30
	 *
	 * @param date	长整型的时间戳    支持毫秒、秒级别转化
	 * @return
	 */
	public static String formatTo_yyyy_MM_dd_HH_mm(String date){
		if(null == date){return null;}
		if(!RegUtils.regTimestamp13Or10(date)){return null;}
		String e = date;
		if(10 == date.length()){
			e = date + "000";
		}
		return formatTo_yyyy_MM_dd_HH_mm(new Date(Long.valueOf(e)));
	}

	/**
	 * @author	零纪年	杨小伟
	 * @date	2014年10月1日	下午3:32:17
	 * @function 	将一个日期转化为13位ms表示【只兼容php过来的10位】
	 *
	 * @param date
	 * @return
	 */
	public static String formatTo13(String date){
		if(null == date){return null;}
		if(!RegUtils.regUint(date)){return null;}
		String e = date;
		if(10 == date.length()){
			e = date + "000";
		}
		return e;
	}
}
