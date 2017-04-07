package cn.gdeng.market.lease.util;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

/**
 * @Description 数据字典标签库工具类
 * @Project gd-home-web
 * @ClassName DictDataUtil.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 下午6:04:37
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service
public class TagUtil {
    /**
     * @Description formatNumber 数字格式化，将数值大于10000的值转化为以万为单位，保留两位小数，最后以为小数四舍五入，123456.56=12.35万
     * @param price
     * @return
     * @CreationDate 2015年11月3日 下午12:20:44
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String formatNumber(Double number) {
        String numberString = null;
        if (number == null || number <= 0) {
            return "面议";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (number < 10000) {
            numberString = "" + df.format(number);
        } else if (number < 100000000) {
            number = Math.floor((number / 10000.0 * 100)) / 100.0;
            numberString = df.format(number) + "万";
        } else {
            number = Math.floor((number / 100000000.0 * 100)) / 100.0;
            numberString = df.format(number) + "亿";
        }
        // else if (number < 10000000000L) {
        // number = Math.floor((number / 1000000000.0 * 100)) / 100.0;
        // numberString = df.format(number) + "十亿";
        // }
        return numberString;
    }

    /**
     * @Description formatNumber2 数字格式化，将小数末尾的0去掉，1234.450=1234.45，123.00=123
     * @param number
     * @return
     * @CreationDate 2015年11月3日 下午5:09:23
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String formatNumber2(Double number) {
        String numberString = null;
        if (number != null) {
            char[] chs = number.toString().toCharArray();
            for (int i = chs.length - 1; i >= 0; i--) {
                if (chs[i] == '0') {
                    chs[i] = ' ';
                    continue;
                } else if (chs[i] == '.') {
                    chs[i] = ' ';
                    break;
                } else {
                    break;
                }
            }
            numberString = new String(chs).trim();
        }
        return numberString;
    }

    public static void main(String[] args) {
        System.out.println(formatNumber(123.5));
        System.out.println(formatNumber(123446.56));
        System.out.println(formatNumber(123456.56));
        System.out.println(formatNumber(9999999.99999));
        System.out.println(formatNumber(0.5600));
        System.out.println(formatNumber(0.0));
        System.out.println(formatNumber(00000d));
        System.out.println(formatNumber(0.00));
        System.out.println(formatNumber(99999999.99999));
        System.out.println(formatNumber(999999999.99999));
        System.out.println(formatNumber(9999999999.99999));
    }

}