package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysMenuDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 菜单服务
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月15日 下午3:15:41
 */
public interface SysMenuService {

	/**
	 * 获取菜单
	 * 
	 * @param param
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:22:23
	 */
	public List<SysMenuDTO> queryByCondition(Map<String, Object> param) throws BizException;

	/**
	 * 获取数据库所有的菜单集合,并将该集合存入codis
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午9:56:56
	 */
	public List<SysMenuDTO> getAllMenus() throws BizException;

	/**
	 * 清除codis中所有菜单的缓存
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午10:08:30
	 */
	public Long cleanAllMenu() throws BizException;

}