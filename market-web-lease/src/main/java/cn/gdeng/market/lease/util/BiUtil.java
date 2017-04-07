package cn.gdeng.market.lease.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
图表工具类
 */
public class BiUtil {
   
	/**
	 * 
	 * @param date 传入字符串类型时间
	 * @param num 需要想加减的月份
	 * @return 返回字符串类型的时间（yyyy-MM-dd）
	 */
    public static String dateByMonthUtil(String date,Integer num) {
    	SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dft.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, num);
		String startDate = dft.format(calendar.getTime());
        return startDate;
    }

    public static void main(String[] args) {
    	System.out.println(dateByMonthUtil("2016-01-01",-1));
	}
}