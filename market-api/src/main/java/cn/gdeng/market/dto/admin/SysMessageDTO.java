package cn.gdeng.market.dto.admin;

import cn.gdeng.market.entity.admin.SysMessageEntity;

public class SysMessageDTO extends SysMessageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 发送者名称
	 */
	private String senderName;

	public String getSenderName() {
		if(senderName == null || "".equals(senderName)){
			return "系统";
		}
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


}
