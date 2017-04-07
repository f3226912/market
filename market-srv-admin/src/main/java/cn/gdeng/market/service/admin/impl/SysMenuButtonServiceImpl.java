package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysMenuButtonDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.admin.SysMenuButtonService;
import cn.gdeng.market.util.SerializeUtil;

/**
 * 菜单按钮服务
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月15日 下午3:20:00
 */
@Service(value = "sysMenuButtonService")
public class SysMenuButtonServiceImpl implements SysMenuButtonService {
	@Autowired
	private BaseDao<?> baseDao;

	private static final String key = Const.BUTTON_PRE + "allButton";
	@Autowired
	private JodisTemplate jodisTemplate;

	/**
	 * 获取按钮
	 * 
	 * @param param
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:22:23
	 */
	public List<SysMenuButtonDTO> queryByCondition(Map<String, Object> param) throws BizException {
		return baseDao.queryForList("SysMenuButton.queryByCondition", param, SysMenuButtonDTO.class);
	}

	/**
	 * 获取所有的按钮
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午9:58:14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuButtonDTO> getAllButtons() throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
		List<SysMenuButtonDTO> list = null;
		Object object = jodisTemplate.getObject(SerializeUtil.serialize(key));
		if (object != null) {
			list = (List<SysMenuButtonDTO>) object;
		}
		if (list == null) {
			list = baseDao.queryForList("SysMenuButton.queryByCondition", map, SysMenuButtonDTO.class);
			jodisTemplate.set(SerializeUtil.serialize(key), SerializeUtil.serialize(list));
		}
		return list;
	}

	/**
	 * 清除codis中所有按钮的缓存
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午10:08:30
	 */
	@Override
	public Long cleanAllButton() throws BizException {
		return jodisTemplate.del(SerializeUtil.serialize(key));
	}
}