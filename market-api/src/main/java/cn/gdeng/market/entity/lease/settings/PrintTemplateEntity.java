package cn.gdeng.market.entity.lease.settings;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "print_template")
public class PrintTemplateEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1496901790178131048L;

	/**
     *主键id
     */
    private Integer id;
    /**
     *模板编码
     */
    private String templateCode;
    /**
     *模板名称
     */
    private String templateName;
    /**
     *模板包
     */
    private String templateFile;
    
    /**
     * 模板包文件目录URL
     */
    private String templateUrl;
    
    /**
     * 模板DOC Byte[]
     */
    private byte[] templateDoc;
    /**
     *备注
     */
    private String info;
    /**
     *是否删除(0:未删除;1:已删除)
     */
    private Byte isDeleted;
    /**
     *创建人员ID
     */
    private String createUserId;
    /**
     *创建时间
     */
    private Date createTime;
    /**
     *修改人员ID
     */
    private String updateUserId;
    /**
     *修改时间
     */
    private Date updateTime;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "templateCode")
    public String getTemplateCode(){
        return this.templateCode;
    }
    public void setTemplateCode(String templateCode){
        this.templateCode = templateCode;
    }
    @Column(name = "templateName")
    public String getTemplateName(){
        return this.templateName;
    }
    public void setTemplateName(String templateName){
        this.templateName = templateName;
    }
    @Column(name = "templateFile")
    public String getTemplateFile(){
        return this.templateFile;
    }
    public void setTemplateFile(String templateFile){
        this.templateFile = templateFile;
    }
    @Column(name = "templateUrl")
    public String getTemplateUrl() {
		return templateUrl;
	}
	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}
    @Column(name = "templateDoc")
	public byte[] getTemplateDoc() {
		return templateDoc;
	}
	public void setTemplateDoc(byte[] templateDoc) {
		this.templateDoc = templateDoc;
	}
	@Column(name = "info")
    public String getInfo(){
        return this.info;
    }
    public void setInfo(String info){
        this.info = info;
    }
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){
        return this.isDeleted;
    }
    public void setIsDeleted(Byte isDeleted){
        this.isDeleted = isDeleted;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
