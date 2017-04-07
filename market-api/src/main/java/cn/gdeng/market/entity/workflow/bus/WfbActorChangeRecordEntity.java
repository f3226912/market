package cn.gdeng.market.entity.workflow.bus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 工作流任务责任人替换记录实体
 * @author wjguo
 *
 * datetime:2016年10月17日 下午7:28:23
 */
@Entity(name = "wfb_actor_change_record")
public class WfbActorChangeRecordEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5524359086412273337L;

	/**
     *主键id
     */
    private Integer id;

    /**
     *旧的任务参与者id
     */
    private Integer oldActorId;

    /**
     *新参数者id
     */
    private Integer newActorId;

    /**
     *创建时间
     */
    private Date createTime;

    /**
     *操作人
     */
    private Integer operatorId;

    /**
     *关联的任务id
     */
    private String taskId;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "oldActorId")
    public Integer getOldActorId(){

        return this.oldActorId;
    }
    public void setOldActorId(Integer oldActorId){

        this.oldActorId = oldActorId;
    }
    @Column(name = "newActorId")
    public Integer getNewActorId(){

        return this.newActorId;
    }
    public void setNewActorId(Integer newActorId){

        this.newActorId = newActorId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "operatorId")
    public Integer getOperatorId(){

        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){

        this.operatorId = operatorId;
    }
    @Column(name = "taskId")
    public String getTaskId(){

        return this.taskId;
    }
    public void setTaskId(String taskId){

        this.taskId = taskId;
    }
}

