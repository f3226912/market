package cn.gdeng.market.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gdeng.market.dto.lease.contract.ContractMainDTO;
import cn.gdeng.market.entity.lease.contract.ContractTaskEntity;
import cn.gdeng.market.enums.ContractTaskStatusEnum;
import cn.gdeng.market.enums.MarketResourceLeaseStatusEnum;
import cn.gdeng.market.service.lease.contract.ContractManageService;
import cn.gdeng.market.service.lease.contract.ContractTaskService;
import cn.gdeng.market.service.lease.resources.MarketResourceService;



/**合同资源状态修改
 * 
 * @author kwang
 *
 * datetime:2016年10月19日 上午15:28:52
 */
public class ContractResourceLeaseStatusTask {
	private Logger logger = LoggerFactory.getLogger(ContractResourceLeaseStatusTask.class);

	
    
	@Resource	
	private ContractManageService contractManageService;
	@Resource	
	private MarketResourceService marketResourceService;
	@Resource	
	private ContractTaskService contractTaskService; 
	
	
	/**
	 * 定时方法
	 */
	public void execute(){  
		logger.info("-----------ContractResourceLeaseStatusTask begin---------");
		System.out.println("-----------ContractResourceLeaseStatusTask begin---------");
		Long beginTime=System.currentTimeMillis();
		Map<String,Object> paramMap=new HashMap<String, Object>();
		try {
			List<ContractMainDTO> list=contractManageService.queryByLeaseStatusTask(paramMap);
			Map<String,Object> map=new HashMap<String, Object>();
			ContractTaskEntity taskEntity=null;
			for ( ContractMainDTO dto:list) {
				taskEntity=new ContractTaskEntity();
				byte taskStatus=ContractTaskStatusEnum.ERROR.getCode();
				String [] ids=dto.getLeasingResourceIds().split(",");
				List<Map<String,Object>>  listMap=new ArrayList<Map<String,Object>> ();
				for (String id:ids) {
					Map<String,Object> resourceMap=new HashMap<String, Object>();
					resourceMap.put("id",id);
					resourceMap.put("oldStatus", MarketResourceLeaseStatusEnum.WAIT_FOR_RENT.getStatus());
					resourceMap.put("leaseStatus", MarketResourceLeaseStatusEnum.RENTED.getStatus());
					listMap.add(resourceMap);
				}
				int updateRe=0;
			  try {
				 updateRe=	marketResourceService.updateBatch(listMap);
			  } catch (Exception e) {
				  taskEntity.setRemark("接口调用异常！");
			  }	
			  taskEntity.setRemark("修改失败！");
			 if(updateRe>0){
				 taskEntity.setRemark("修改成功!");
				 taskStatus=ContractTaskStatusEnum.SUCCESS.getCode();
			 }
			 taskEntity.setContractId(dto.getId());
			 taskEntity.setTaskStatus(taskStatus);
			 contractTaskService.insertContractTask(taskEntity);
			 map.put("id", dto.getId());
			 map.put("taskStatus",taskStatus);
			 contractManageService.updateContractTaskStatusById(map);
			}
		} catch (Exception e) {
			logger.error("DemoTask execute failure", e);
		}
        Long consumedTime=System.currentTimeMillis() - beginTime;
        logger.info("-----------ContractResourceLeaseStatusTask end, time consume:{}ms ---------", consumedTime);
    }  
}
