package cn.gdeng.market.service.workflow.bus.impl;

import java.util.HashMap;
import java.util.Map;

import org.snaker.engine.entity.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.enums.WfDataBaseNextValEnum;
import cn.gdeng.market.service.workflow.bus.WfSequenceService;
import cn.gdeng.market.service.workflow.core.SnakerProcessService;

/**工作流序列号实现类
 * 
 * @author wjguo
 *
 * datetime:2016年10月19日 下午12:21:18
 */
@Service(value = "wfSequenceService")
public class WfSequenceServiceImpl implements WfSequenceService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private SnakerProcessService snakerProcessService;
	
	@Override
	public String generalProcessName() {
		return getSequence(WfDataBaseNextValEnum.PROCESS_NAME_SEQUENCE.getCode());
	}


	@Override
	public String generalOrderNoForContractInfo(String processId) {
		return generalOrderNo(processId, WfDataBaseNextValEnum.CONTRACT_INFO_SEQUENCE.getCode());
	}

	@Override
	public String generalOrderNoForContractChanged(String processId) {
		return generalOrderNo(processId, WfDataBaseNextValEnum.CONTRACT_CHANGED_SEQUENCE.getCode());
	}

	@Override
	public String generalOrderNoForContractClose(String processId) {
		return generalOrderNo(processId, WfDataBaseNextValEnum.CONTRACT_CLOSE_SEQUENCE.getCode());
	}
	
	/**生成流程实例名称
	 * @param processId 流程id
	 * @param wfDataBaseNextValEnumCode  枚举值的code码
	 * @return
	 */
	private String generalOrderNo(String processId, String wfDataBaseNextValEnumCode) {
		Process process = snakerProcessService.getProcessById(processId);
		String displayName = process.getDisplayName();
		String sequence = getSequence(wfDataBaseNextValEnumCode);
		return displayName + sequence;
	}
	
	/** 获取序列
	 * @param wfDataBaseNextValEnumCode 枚举值的code码
	 * @return
	 */
	private String getSequence(String wfDataBaseNextValEnumCode) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("name", wfDataBaseNextValEnumCode);
		return baseDao.queryForObject("WfNextVal.getNextValByName", params, String.class);
	}

}
