package cn.gdeng.market.service.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gdeng.market.dao.base.BaseDao;
import cn.gdeng.market.dto.admin.SysMenuDTO;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.redis.support.JodisTemplate;
import cn.gdeng.market.service.admin.SysMenuService;
import cn.gdeng.market.util.SerializeUtil;

/**
 * 菜单服务
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月15日 下午3:15:41
 */
@Service(value = "sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private BaseDao<?> baseDao;

	private static final String key = Const.MENU_PRE + "allMenu";
	@Autowired
	private JodisTemplate jodisTemplate;

	/**
	 * 获取菜单
	 * 
	 * @param param
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:22:23
	 */
	public List<SysMenuDTO> queryByCondition(Map<String, Object> param) throws BizException {
		return baseDao.queryForList("SysMenu.queryByCondition", param, SysMenuDTO.class);
	}

	/**
	 * 获取数据库所有的菜单
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午9:56:56
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenuDTO> getAllMenus() throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 1);
		List<SysMenuDTO> list = null;
		Object object = jodisTemplate.getObject(SerializeUtil.serialize(key));
		if (object != null) {
			list = (List<SysMenuDTO>) object;
		}
		if (list == null) {
			list = baseDao.queryForList("SysMenu.queryByCondition", map, SysMenuDTO.class);
			jodisTemplate.set(SerializeUtil.serialize(key), SerializeUtil.serialize(list));
		}
		return list;
	}

	/**
	 * 清除codis中所有菜单的缓存
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午10:08:30
	 */
	public Long cleanAllMenu() throws BizException {
		return jodisTemplate.del(SerializeUtil.serialize(key));
	}
}