package cn.gdeng.market.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.market.dto.admin.SysMenuButtonDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 菜单按钮服务
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月15日 下午3:20:00
 */
public interface SysMenuButtonService {

	/**
	 * 获取按钮
	 * 
	 * @param param
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月14日 上午11:22:23
	 */
	public List<SysMenuButtonDTO> queryByCondition(Map<String, Object> param) throws BizException;

	/**
	 * 获取所有的按钮,并存入codis缓存
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午9:58:14
	 */
	public List<SysMenuButtonDTO> getAllButtons() throws BizException;

	/**
	 * 清除codis中所有按钮的缓存
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月20日 上午10:08:30
	 */
	public Long cleanAllButton() throws BizException;
}