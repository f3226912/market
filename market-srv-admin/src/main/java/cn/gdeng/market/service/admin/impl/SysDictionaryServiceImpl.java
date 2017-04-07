package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.entity.admin.SysDictionaryEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.enums.MsgCons;
import cn.gdeng.market.enums.MyConst;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.util.SerializeUtil;

/**
 * 
 * 
 * @author lidong
 *
 */
@Service(value = "sysDictionaryService")
public class SysDictionaryServiceImpl implements SysDictionaryService {

	private String redis_Pre = MyConst.appid + "SysDictionaryServiceImpl";
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private JodisTemplate jodisTemplate;

	@Override
	public SysDictionaryEntity getById(String id) throws BizException {
		Object object = jodisTemplate.getObject(SerializeUtil.serialize(redis_Pre + id));
		if (object != null) {
			return (SysDictionaryEntity) object;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		SysDictionaryEntity dictionary = baseDao.queryForObject("SysDictionary.getById", map, SysDictionaryEntity.class);
		if (dictionary != null) {
			jodisTemplate.set(SerializeUtil.serialize(redis_Pre + id), SerializeUtil.serialize(dictionary));
		}
		return dictionary;
	}

	@Override
	public List<SysDictionaryEntity> getList(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysDictionary.getList", map, SysDictionaryEntity.class);
	}

	@Override
	public int deleteById(String id) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		jodisTemplate.del(SerializeUtil.serialize(redis_Pre + id));
		return baseDao.execute("SysDictionary.deleteById", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public int deleteBatch(List<String> list) throws BizException {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
			jodisTemplate.del(SerializeUtil.serialize(redis_Pre + list.get(i)));
		}
		return baseDao.batchUpdate("SysDictionary.deleteById", batchValues).length;
	}

	@Override
	public int update(SysDictionaryEntity t) throws BizException {
		jodisTemplate.del(SerializeUtil.serialize(redis_Pre + t.getId()));
		return baseDao.execute("SysDictionary.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws BizException {
		return baseDao.queryForObject("SysDictionary.getTotal", map, Integer.class);
	}

	@Override
	public Long persist(SysDictionaryEntity entity) throws BizException {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<SysDictionaryEntity> getListPage(Map<String, Object> map) throws BizException {
		return baseDao.queryForList("SysDictionary.getListPage", map, SysDictionaryEntity.class);
	}

	@Override
	public Integer insert(SysDictionaryEntity t) throws BizException {
		return baseDao.execute("SysDictionary.insert", t);
	}

	/**
	 * 根据code值获取value
	 * 
	 * @param code
	 * @return value
	 */
	public String getDicVal(String code) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", Const.DICTIONARY_ENCODE);
		Map<String, Object> tempMap = new HashMap<String, Object>();
		List<SysDictionaryEntity> list = getList(map);
		for (SysDictionaryEntity val : list) {
			tempMap.put(val.getCode(), val.getValue());
		}
		String val = (String) tempMap.get(code);
		if (val == null) {
			throw new BizException(MsgCons.C_30001, MsgCons.M_30001);
		}
		return val;
	}

	@Override
	public SysDictionaryEntity getEnableDictionary(String type, String code) throws BizException {
		Map<String,Object> map = new HashMap<>();
		map.put("type", type);
		map.put("code", code);
		map.put("status", Const.USER_STATUS_1);
		List<SysDictionaryEntity> list = getList(map);
		if(null == list || list.size() == 0){
			throw new BizException(MsgCons.C_20000, "数据字典[type="+type+"],[code="+code+"]不存在");
		}
		return list.get(0);
	}
}