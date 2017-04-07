package cn.gdeng.market.entity.lease.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "area")
public class AreaEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7140027719189627574L;

	/**
     *地区表
     */
    private Integer id;

    /**
     *地区id
     */
    private String areaId;

    /**
     *地区名称
     */
    private String areaName;

    /**
     *父地区id
     */
    private String fatherId;

    /**
     *排序字段，数值越大，排越靠后
     */
    private Byte sort;

    /**
     *0为禁用，1为启用
     */
    private Byte status;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "areaId")
    public String getAreaId(){

        return this.areaId;
    }
    public void setAreaId(String areaId){

        this.areaId = areaId;
    }
    @Column(name = "areaName")
    public String getAreaName(){

        return this.areaName;
    }
    public void setAreaName(String areaName){

        this.areaName = areaName;
    }
    @Column(name = "fatherId")
    public String getFatherId(){

        return this.fatherId;
    }
    public void setFatherId(String fatherId){

        this.fatherId = fatherId;
    }
    @Column(name = "sort")
    public Byte getSort(){

        return this.sort;
    }
    public void setSort(Byte sort){

        this.sort = sort;
    }
    @Column(name = "status")
    public Byte getStatus(){

        return this.status;
    }
    public void setStatus(Byte status){

        this.status = status;
    }
}

