package cn.gdeng.market.admin.bean;


import java.io.Serializable;

import cn.gdeng.market.enums.MsgCons;


/** ajax请求的结果返回类。
 * @author wjguo
 * datetime 2016年9月20日 下午4:44:04  
 *
 * @param <T>
 */
public class AjaxResult<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9111052457885041749L;
	private Boolean isSuccess;
	private Integer code;
	private String msg;
	private T result;

	public AjaxResult() {
		this.isSuccess = Boolean.valueOf(true);
		this.code = MsgCons.C_10000;
		this.msg = MsgCons.M_10000;
	}

	public AjaxResult(T result, Integer code, String msg) {
		this.isSuccess = Boolean.valueOf(true);
		this.result = result;
		this.code = code;
		this.msg = msg;
	}
	public boolean isSuccess() {
		return this.isSuccess.booleanValue();
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public T getResult() {
		return (T) this.result;
	}

	public AjaxResult<T> setResult(T result) {
		this.result = result;
		return this;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public AjaxResult<T> setCodeMsg(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		return this;
	}

	public AjaxResult<T> withResult(T result) {
		setIsSuccess(Boolean.valueOf(true));
		setResult(result);
		return this;
	}

	public AjaxResult<T> withError(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		setIsSuccess(Boolean.valueOf(false));
		return this;
	}
}