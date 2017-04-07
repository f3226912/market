package cn.gdeng.market.dto.lease.settings;

import cn.gdeng.market.entity.lease.settings.PrintSettingEntity;
import cn.gdeng.market.enums.PrintBizTypeEnum;

public class PrintSettingDTO extends PrintSettingEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6734412481779284853L;
	
	private String marketName;
	
	private String templateCode;
	
	private String templateName;
	
	private String templateFile;
	
	private String templateUrl;
	
	private byte[] templateDoc;

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public String getBizTypeStr(){
		return PrintBizTypeEnum.getNameByCode(getBizType());
	}

	public byte[] getTemplateDoc() {
		return templateDoc;
	}

	public void setTemplateDoc(byte[] templateDoc) {
		this.templateDoc = templateDoc;
	}

}
