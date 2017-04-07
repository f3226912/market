package cn.gdeng.market.service.admin;

import java.util.Map;

import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.exception.BizException;

/**
 * 用户登录相关服务
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月11日 上午11:16:28
 */
public interface LoginService {
	/**
	 * 用户登录接口
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 上午11:14:39
	 */
	public Map<String, Object> login(Map<String, Object> map) throws BizException;
	
	/**
	 * 平台用户登陆服务
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 上午11:14:39
	 */
	public Map<String, Object> login4Platform(Map<String, Object> map) throws BizException;

	/**
	 * 将登录用户存入redis
	 * 
	 * @param key
	 *            根据spring-session生成的sessionid变换得到的key值
	 * @param user
	 *            登录用户
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午6:47:02
	 */
	public String setUser(String key, SysUserDTO user) throws BizException;

	/**
	 * 获取redis中登录用户的信息
	 * 
	 * @param key
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午6:48:43
	 */
	public SysUserDTO getUser(String key) throws BizException;

	/**
	 * 用户登出，清除用户信息
	 * 
	 * @param key
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月11日 下午7:01:51
	 */
	public Long removeUser(String key,Integer userId) throws BizException;

}