package cn.gdeng.market.util;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.exception.BizException;

public class Assert {

	public static void notNull(Object obj,String msg) throws BizException{
		if(obj == null){
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	
	public static void notEmpty(String obj,String msg) throws BizException{
		if(obj == null || "".equals(obj)){
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	
	public static void notNull(Object obj,Integer code,String msg) throws BizException{
		if(obj == null){
			throw new BizException(code, msg);
		}
	}
	
	/**断言不为空白字符串。 包括 null、""、"   " 均属于空字白符串。
	 * @param str
	 * @param msg
	 * @throws BizException
	 */
	public static void notBlankStr(CharSequence str,String msg) throws BizException{
		if (StringUtils.isBlank(str)) {
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	

	
	/** 断言两个对象是相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果不相等，抛出此此异常。
	 */
	public static void isEquals(byte one, byte two,String msg) throws BizException{
		isEquals(Byte.valueOf(one), Byte.valueOf(two), msg);
	}
	
	/** 断言两个对象是相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果不相等，抛出此此异常。
	 */
	public static void isEquals(int one, int two,String msg) throws BizException{
		isEquals(Integer.valueOf(one), Integer.valueOf(two), msg);
	}
	
	/** 断言两个对象是相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果不相等，抛出此此异常。
	 */
	public static void isEquals(long one, long two,String msg) throws BizException{
		isEquals(Long.valueOf(one), Long.valueOf(two), msg);
	}
	
	/** 断言两个对象是相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果不相等，抛出此此异常。
	 */
	public static void isEquals(double one, double two,String msg) throws BizException{
		isEquals(Double.valueOf(one), Double.valueOf(two), msg);
	}
	
	/** 断言两个对象是相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果不相等，抛出此此异常。
	 */
	public static void isEquals(String one, String two,String msg) throws BizException{
		if (!one.equals(two)) {
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	
	/** 断言两个对象是相等的。ps：此方法设置为private不对外开放，因为直接两个Object对象比较时，对于数值比较会出问题。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果不相等，抛出此此异常。
	 */
	private static void isEquals(Object one, Object two,String msg) throws BizException{
		if (!one.equals(two)) {
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	
	/** 断言两个对象是否不相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果相等，抛出此此异常。
	 */
	public static void notEquals(byte one, byte two,String msg) throws BizException{
		notEquals(Byte.valueOf(one), Byte.valueOf(two), msg);
	}
	
	/** 断言两个对象是否不相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果相等，抛出此此异常。
	 */
	public static void notEquals(int one, int two,String msg) throws BizException{
		notEquals(Integer.valueOf(one), Integer.valueOf(two), msg);
	}
	
	/** 断言两个对象是否不相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果相等，抛出此此异常。
	 */
	public static void notEquals(long one, long two,String msg) throws BizException{
		notEquals(Long.valueOf(one), Long.valueOf(two), msg);
	}
	
	/** 断言两个对象是否不相等的。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果相等，抛出此此异常。
	 */
	public static void notEquals(double one, double two,String msg) throws BizException{
		notEquals(Double.valueOf(one), Double.valueOf(two), msg);
	}
	
	/** 断言两个对象是否不相等的。ps：此方法设置为private不对外开放，因为直接两个Object对象比较时，对于数值比较会出问题。
	 * @param one 比较的对象
	 * @param two 比较的对象
	 * @param msg 异常消息
	 * @throws BizException 如果相等，抛出此此异常。
	 */
	private static void notEquals(Object one, Object two,String msg) throws BizException{
		if (one.equals(two)) {
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	
 	/** 断言val值为true。
	 * @param val
	 * @param msg
	 * @throws BizException  如果为false，则抛出此异常。
	 */
	public static void isTrue(boolean val, String msg) throws BizException{
		if (!val) {
			throw new BizException(MsgCons.C_20000, msg);
		}
	}
	
}
