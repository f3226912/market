package cn.gdeng.market.lease.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.market.dto.admin.SysMenuDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.SysMenuButtonService;
import cn.gdeng.market.service.admin.SysMenuService;

/**
 * 系统菜单管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年10月10日 下午1:55:15
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysMenuButtonService sysMenuButtonService;

	/**
	 * 菜单路由前缀
	 */
	private static final String ROUTE_PRE = "index#";

	/**
	 * 获取左侧菜单栏路由
	 * 
	 * @return
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 上午8:43:44
	 */
	@RequestMapping("route")
	@ResponseBody
	public AjaxResult<List<MenuItem>> getRoute() throws BizException {
		AjaxResult<List<MenuItem>> res = new AjaxResult<>();
		List<MenuItem> menus = new ArrayList<>();
		SysUserDTO user = getSysUser();
		if (user != null) {
			List<SysMenuDTO> tMenus = user.getMenus();// 用户所有菜单
			List<SysMenuDTO> level_1_menus = getMenusByLevel(tMenus, 1);
			List<SysMenuDTO> level_2_menus = getMenusByLevel(tMenus, 2);
			if (level_1_menus.size() > 0) {
				for (SysMenuDTO sysMenuDTO : level_1_menus) {
					if (containSubMenu(level_2_menus, sysMenuDTO.getId())) {
						// 如果有子菜单
						MenuItem item = new MenuItem();
						List<String> title = new ArrayList<>();
						title.add(sysMenuDTO.getName());
						title.add(sysMenuDTO.getIcons());
						item.setTitle(title);

						List<SysMenuDTO> level_1_2_menus = getMenusByPid(level_2_menus, sysMenuDTO.getId());
						List<List<String>> sub = new ArrayList<>();
						if (level_1_2_menus.size() > 0) {
							for (SysMenuDTO menuDTO : level_1_2_menus) {
								List<String> sub0 = new ArrayList<>();
								sub0.add(menuDTO.getName());
								sub0.add(ROUTE_PRE + menuDTO.getCode());
								sub.add(sub0);
							}
						}
						item.setSub(sub);
						menus.add(item);
					} else {
						// 没有子菜单
						MenuItem item = new MenuItem();
						List<String> title = new ArrayList<>();
						title.add(sysMenuDTO.getName());
						title.add(ROUTE_PRE + sysMenuDTO.getCode());
						title.add(sysMenuDTO.getIcons());
						item.setTitle(title);
						menus.add(item);
					}
				}
			}
		}
		res.setResult(menus);
		return res;
	}

	/**
	 * 菜单路由 [ { "title": [ "系统管理", "fa-home" ], "sub": [ [ "公司管理", "index#3" ], [ "Demo", "index#2" ] ] }, { "title": [ "登录", "page/login.html", "fa-power-off" ] } ]
	 * 
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月10日 下午2:02:12
	 */
	class MenuItem implements Serializable {
		private static final long serialVersionUID = -1054443675914926253L;
		private List<String> title;// 菜单
		private List<List<String>> sub;// 子菜单

		public List<String> getTitle() {
			return title;
		}

		public void setTitle(List<String> title) {
			this.title = title;
		}

		public List<List<String>> getSub() {
			return sub;
		}

		public void setSub(List<List<String>> sub) {
			this.sub = sub;
		}
	}

	/**
	 * 根据菜单级别筛选菜单集合
	 * 
	 * @param menus
	 *            源菜单集合
	 * @param level
	 *            菜单级别
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 上午8:49:38
	 */
	private List<SysMenuDTO> getMenusByLevel(List<SysMenuDTO> menus, int level) {
		List<SysMenuDTO> result = new ArrayList<>();
		if (menus != null) {
			for (SysMenuDTO sysMenuDTO : menus) {
				if (sysMenuDTO.getLevel() != null) {
					if (sysMenuDTO.getLevel().intValue() == level) {
						result.add(sysMenuDTO);
					}
				}

			}
		}
		return result;
	}

	/**
	 * 根据父级菜单id查找子菜单集合
	 * 
	 * @param menus
	 * @param pid
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 上午8:54:23
	 */
	private List<SysMenuDTO> getMenusByPid(List<SysMenuDTO> menus, int pid) {
		List<SysMenuDTO> result = new ArrayList<>();
		if (menus != null) {
			for (SysMenuDTO sysMenuDTO : menus) {
				if (sysMenuDTO.getPid() != null) {
					if (sysMenuDTO.getPid().intValue() == pid) {
						result.add(sysMenuDTO);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 判断指定菜单下是否含有子菜单
	 * 
	 * @param menus
	 * @param menuId
	 * @return
	 * @author lidong dli@gdeng.cn
	 * @time 2016年10月12日 上午8:56:52
	 */
	private boolean containSubMenu(List<SysMenuDTO> menus, int menuId) {
		if (menus != null) {
			for (SysMenuDTO sysMenuDTO : menus) {
				if (sysMenuDTO.getPid() != null) {
					if (sysMenuDTO.getPid().intValue() == menuId) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 清除codis中的菜单缓存、按钮缓存
	 * 
	 * @param request
	 * @param response
	 * @throws BizException
	 * @author lidong dli@gdeng.cn
	 * @throws IOException
	 * @time 2016年10月20日 上午10:30:55
	 */
	@RequestMapping("clean")
	public void cleanCodisMenusAndButtons(HttpServletRequest request, HttpServletResponse response) throws BizException, IOException {
		sysMenuService.cleanAllMenu();
		sysMenuButtonService.cleanAllButton();
		PrintWriter pw = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>");
		sb.append("<html lang='en'>");
		sb.append("<head>");
		sb.append("<meta charset=utf-8>");
		sb.append("<title>刷新codis菜单、按钮缓存</title>");
		sb.append("</head>");
		sb.append("<body style='text-align:center;'>");
		sb.append("<h1>刷新codis菜单、按钮缓存完毕</h1>");
		sb.append("<h2>如果还存在缓存，请继续刷新</h2>");
		sb.append("</body>");
		sb.append("</html>");
		pw.println(sb.toString());
		pw.flush();
		pw.close();
		return;
	}

}
