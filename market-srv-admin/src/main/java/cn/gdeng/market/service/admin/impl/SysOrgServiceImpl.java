package cn.gdeng.market.service.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.dto.bean.Node;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.lease.settings.MarketEntity;
import cn.gdeng.market.enums.MarketEnum;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.enums.SysOrgTreeEnum;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.admin.SysPostService;
import cn.gdeng.market.service.admin.SysUserOrgService;
import cn.gdeng.market.service.admin.SysUserPostService;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.util.Assert;
import cn.gdeng.market.util.DateUtil;
import cn.gdeng.market.util.TreeUtils;

/**
 * 
 * 
 * @author lidong
 *
 */
@Service(value = "sysOrgService")
public class SysOrgServiceImpl implements SysOrgService {

	@Autowired
	private BaseDao<SysOrgEntity> baseDao;
	
	@Resource
	private SysUserOrgService sysUserOrgService;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysUserPostService sysUserPostService;

	
	@Resource
	private SysPostService sysPostService;
	
	private Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

	@Override
	@Transactional
	public int addSysOrg(SysOrgDTO entity) throws BizException {
		
		//组织类型不能为空
		Assert.notNull(entity.getType(), "组织类型不能为空");
		Assert.notEmpty(entity.getFullName(), "名称不能为空");
		Map<String,Object> param = new HashMap<>();
		param.put("fullName", entity.getFullName());
		
		//持久化前需要处理， level的问题，如果超过3级则不让添加
		if(entity.getParentId() == null){
			//集团公司处理
			entity.setLevel(1);
			param.put("topOrg", true);
			//顶级公司，fullName 不能相同 shortName为空
			int count = queryCountByCondition(param);
			if(count > 0){
				throw new BizException(MsgCons.C_20000, "["+entity.getFullName()+"]已存在");
			}
		} else {
			//非集团公司处理
			//上级类型与本级类型相同  level+1 否则就是1
			SysOrgEntity org = querySysOrgById(entity.getParentId());
			if(org == null){
				throw new BizException(MsgCons.C_20000,"父组织["+entity.getParentId()+"]不存在");
			}
			if(org.getType().equals(entity.getType())){
				entity.setLevel(org.getLevel()+1);
			} else {
				entity.setLevel(1);
			}
			//设置topId
			if(org.getParentId() == null){
				entity.setTopId(org.getId());
			} else {
				Assert.notNull(org.getTopId(), "数据结构错误,组织结构ID["+entity.getParentId()+"]不存在topId");
				entity.setTopId(org.getTopId());
			}
			//非顶级公司，同一个集团下的fullName不能相同 ,只需校验市场
			if(SysOrgEnum.TYPE.MARKET.equals(entity.getType())){
				param.put("topId", entity.getTopId());
				//param.put("type", SysOrgEnum.TYPE.MARKET);
				int count = queryCountByCondition(param);
				if(count > 0){
					throw new BizException(MsgCons.C_20000, "该["+entity.getFullName()+"]已存在");
				}

			}
			
		}
		param.clear();
		if(entity.getLevel() > 3){
			throw new BizException(MsgCons.C_20000, "当前组织级别大于3，不能再新增该类型的组织");
		}
		//转Entity
		SysOrgEntity sysOrgEntity = new SysOrgEntity();
		BeanUtils.copyProperties(entity, sysOrgEntity);
		long id = baseDao.persist(sysOrgEntity, Long.class);
		if(sysOrgEntity.getTopId() == null && sysOrgEntity.getParentId() == null){
			sysOrgEntity.setTopId((int)id);
			sysOrgEntity.setId((int)id);
			baseDao.dynamicMerge(sysOrgEntity);
		}
		if(StringUtils.equals(SysOrgEnum.TYPE.MARKET, entity.getType())){
			//填充市场 
			MarketEntity market = entity.getMarKetEntity();
			market.setId((int)id);
			market.setOrgId((int)id);
			market.setStatus(MarketEnum.STATUS.NORMAL);
			market.setName(entity.getFullName());
			market.setNameShort(entity.getShortName());
			market.setCreateTime(new Date());
			market.setUpdateTime(new Date());
			market.setCreateUserId(entity.getCreateUserId());
			market.setUpdateUserId(entity.getUpdateUserId());
			baseDao.persist(market, Long.class);
			
			param.put("marketId", id);
			baseDao.execute("Market.batchInsertEXP", param);
			baseDao.execute("Market.batchInsertMeaType", param);
			baseDao.execute("Market.batchInsertResType", param);
		}
		return (int)id;
	}

	@Override
	@Transactional
	public void deleteSysOrg(SysOrgEntity entity) throws BizException {
		//
		Map<String,Object> param = new HashMap<>();
		param.put("parentId", entity.getId());
		int count = queryCountByCondition(param);
		if(count > 0){
			//throw new BizException(MsgCons.C_20000, "该组织["+entity.getId()+"]下存在子机构,不能被删除");
			throw new BizException(MsgCons.C_20000, "当前组织关联有子组织，不能删除。");
		}
		
		//如果有关联用户不能被删除
		param.clear();
		param.put("orgId", entity.getId());
		count = sysUserOrgService.getTotal(param);
		if(count > 0){
			//throw new BizException(MsgCons.C_20000, "该组织["+entity.getId()+"]下存在用户,不能被删除");
			throw new BizException(MsgCons.C_20000, "当前组织关联有用户信息，如需删除，请先将关联的用户移到其他组织。");
		}
		entity.setStatus(SysOrgEnum.STATUS.DELETED);
		if(StringUtils.equals(SysOrgEnum.TYPE.MARKET, entity.getType())){
			//如果存在区域不能删除
			
			param.put("marketId",  entity.getId());
			count = baseDao.queryForObject("Market.getMarketAreaTotal", param, Integer.class);
			if(count > 0){
				throw new BizException(MsgCons.C_20000, "市场下存在区域不能被删除");
			}
			//删除市场
			param.put("status", MarketEnum.STATUS.DELETED);
			param.put("updateUserId", entity.getUpdateUserId());
			baseDao.execute("Market.updateByOrgId", param);
		}
		baseDao.dynamicMerge(entity);
	}

	@Override
	@Transactional
	public void updateSysOrg(SysOrgDTO entity) throws BizException {
		// TODO Auto-generated method stub
		entity.setStatus(SysOrgEnum.STATUS.NORMAL);
		if(StringUtils.equals(SysOrgEnum.TYPE.MARKET, entity.getType())){
			Map<String,Object> param = new HashMap<>();
			param.put("fullName", entity.getFullName());
			SysOrgEntity org = querySysOrgById(entity.getId());
			param.put("topId",org.getTopId());
			param.put("notId", entity.getId());
			//param.put("type", SysOrgEnum.TYPE.MARKET);
			int count = queryCountByCondition(param);
			if(count > 0){
				throw new BizException(MsgCons.C_20000, "该["+entity.getFullName()+"]已存在");
			}
			//填充市场 
			MarketEntity market = entity.getMarKetEntity();
			market.setName(entity.getFullName());
			market.setNameShort(entity.getShortName());
			market.setUpdateUserId(entity.getUpdateUserId());
			market.setOrgId(entity.getId());
			//处理openDate
			market.setOpenTime(DateUtil.getNow(market.getOpenTime()));
			baseDao.execute("Market.updateByOrgId", market);
		}
		//转Entity
		SysOrgEntity sysOrgEntity = new SysOrgEntity();
		BeanUtils.copyProperties(entity, sysOrgEntity);
		baseDao.dynamicMerge(sysOrgEntity);
	}

	@Override
	public GuDengPage<SysOrgDTO> queryTopOrgByPage(GuDengPage<SysOrgDTO> page) throws BizException {
		// TODO Auto-generated method stub
		Map<String,Object> param = page.getParaMap();
		param.put("topOrg", true);

		//查询总页数
		int count = queryTopOrgCount(param);
		page.setTotal(count);
		List<SysOrgDTO> list = null;
		if(count>0){
			list = baseDao.queryForList("SysOrg.queryByConditionPage", param, SysOrgDTO.class);
		}
		page.setRecordList(list);
		return page;
	}

	@Override
	public SysOrgDTO querySysOrg(SysOrgEntity entity) throws BizException {
		entity = baseDao.find(entity);
		SysOrgDTO dto = new SysOrgDTO();
		BeanUtils.copyProperties(entity, dto);
		if(StringUtils.equals(SysOrgEnum.TYPE.MARKET, entity.getType())){
			Map<String,Object> param = new HashMap<>();
			param.put("orgId", entity.getId());
			MarketEntity market = baseDao.queryForObject("Market.getByOrgId", param, MarketEntity.class);
			dto.setMarKetEntity(market);
		}
		
		return dto;
	}

	@Override
	public int queryCountByCondition(Map<String, Object> param) throws BizException {
		int count = baseDao.queryForObject("SysOrg.countByCondition", param,Integer.class);
		return count;
	}

	@Override
	public int queryTopOrgCount(Map<String, Object> param) throws BizException {
		if(null == param){
			param = new HashMap<>();
		}
		param.put("topOrg", true);
		int count = queryCountByCondition(param);
		return count;
	}

	@Override
	public List<Node> queryChildrenById(String id) throws BizException {
		//
		Assert.notNull(id, "组织ID不能为空");
		Map<String,Object> param = new HashMap<>();
		param.put("parentId", id);
		param.put("status", SysOrgEnum.STATUS.NORMAL);
		List<SysOrgEntity> list = baseDao.queryForList("SysOrg.queryByCondition", param,SysOrgEntity.class);
		//
		List<Node> nodes = new ArrayList<>();
		if(list != null && list.size() > 0){
			for(SysOrgEntity org : list){
				Node node = TreeUtils.convertNode(org);
				nodes.add(node);
			}
		}
		return nodes;
	}

	@Override
	public List<Node> initOrgTree(String companyId,SysOrgTreeEnum level) throws BizException {
		
		//查出该集团下的所有组织
		Map<String,Object> param = new HashMap<>();
		param.put("topId", companyId);
		param.put("status", SysOrgEnum.STATUS.NORMAL);
		List<SysOrgEntity> list = baseDao.queryForList("SysOrg.queryByCondition", param,SysOrgEntity.class);

		List<Node> nodes = TreeUtils.convertNode(list);
		if(SysOrgTreeEnum.MARKET == level){
			//所有含有市场的节点都展开
			for(Node node : nodes){
				if(StringUtils.equals(node.getType(), SysOrgEnum.TYPE.MARKET)){
					//所有父节点全部展开
					Node pnode = getParentNode(nodes,node.getPId());
					while(pnode != null && !pnode.isOpen()){
						pnode.setOpen(true);
						pnode = getParentNode(nodes,pnode.getPId());
					}

				}
			}
		}
	
		//构建树
		nodes = TreeUtils.buildTree(nodes, companyId);
		
		//加入当前公司
		SysOrgEntity currentCompany = querySysOrgById(Integer.parseInt(companyId));
		Node currentNode = TreeUtils.convertNode(currentCompany);
		currentNode.setOpen(true);
		currentNode.setChildren(nodes);
		List<Node> newNodes = new ArrayList<>();
		newNodes.add(currentNode);
		return newNodes;
	}
	
	@Override
	public GuDengPage<SysUserDTO> queryUserPageByOrgId(GuDengPage<SysUserDTO> page,int orgId) throws BizException {
		long begin = System.currentTimeMillis();
		page.getParaMap().put("subOrgId", orgId);
		page = sysUserService.getDetailUserListPage(page);
		long end = System.currentTimeMillis();
		logger.info("根据组织查询用户耗时:"+(end-begin)+"ms");
		return page;
	}
	
	/**
	 * 获取所有的组织
	 * @param param
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:22:23
	 */
	@Override
	public List<SysOrgDTO> queryByCondition(Map<String,Object> param) throws BizException{
		return baseDao.queryForList("SysOrg.queryByCondition", param,SysOrgDTO.class);
	}

	@Override
	public SysOrgEntity querySysOrgById(int id) throws BizException {
		SysOrgEntity entity = new SysOrgEntity();
		entity.setId(id);
		return baseDao.find(entity);
	}

	private Node getParentNode(List<Node> nodes,String pid){
		for(Node node : nodes){
			if(StringUtils.equals(node.getId(), pid)){
				return node;
			}
		}
		return null;
	}


}