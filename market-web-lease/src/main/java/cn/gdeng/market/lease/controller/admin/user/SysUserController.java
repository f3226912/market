package cn.gdeng.market.lease.controller.admin.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysPostDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.bean.GuDengPage;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.entity.admin.SysUserEntity;
import cn.gdeng.market.entity.admin.SysUserOrgEntity;
import cn.gdeng.market.entity.admin.SysUserPostEntity;
import cn.gdeng.market.enums.Const;
import cn.gdeng.market.exception.BizException;
import cn.gdeng.market.lease.bean.AjaxResult;
import cn.gdeng.market.lease.controller.base.BaseController;
import cn.gdeng.market.service.admin.LoginService;
import cn.gdeng.market.service.admin.SysDictionaryService;
import cn.gdeng.market.service.admin.SysOrgService;
import cn.gdeng.market.service.admin.SysUserOrgService;
import cn.gdeng.market.service.admin.SysUserPostService;
import cn.gdeng.market.service.admin.SysUserService;
import cn.gdeng.market.util.MD5;

/**
 * 集团公司用户管理控制器
 *
 * @author houxp
 **/
@Controller
@RequestMapping("sysCompanyUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserOrgService sysUserOrgService;

    @Autowired
    private SysUserPostService sysUserPostService;

    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 调转到公司用户管理界面
     *
     * @param mv
     * @return
     * @throws BizException
     */
    @RequestMapping("index")
    public ModelAndView jumpToIndex(ModelAndView mv) throws BizException {
        mv.setViewName("sys/user/sysCompUser");
        return mv;
    }

    /**
     * 我的账号
     *
     * @param mv
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "profileBefore")
    public ModelAndView profile(ModelAndView mv, Integer tab) throws BizException {
        SysUserDTO user = getSysUser();

        List<SysPostDTO> postInfos = sysUserService.getPostInfoByUserId(getUserId());
        if (postInfos != null && postInfos.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (SysPostDTO sysPostDTO : postInfos) {
                sb.append(sysPostDTO.getName() + " ");
            }
            if (StringUtils.isNotEmpty(sb.toString())) {
                user.setPostName(sb.toString());
            }
        }
        SysOrgDTO userGroup = loginUserUtil.getUserGroup(getRequest());
        if (userGroup != null) {
            user.setGroupName(userGroup.getFullName());
        }
        SysOrgDTO userCompany = loginUserUtil.getUserCompany(getRequest());
        if (userCompany != null) {
            user.setCompanyName(userCompany.getFullName());
        }
        SysOrgDTO userMarket = loginUserUtil.getUserMarket(getRequest());
        if (userMarket != null) {
            user.setMarketName(userMarket.getFullName());
        }
        SysOrgDTO userDept = loginUserUtil.getUserDept(getRequest());
        if (userDept != null) {
            user.setDepartmentName((userDept.getFullName()));
        }
        mv.addObject("user", user);
        mv.addObject("tab", tab);// 进入页面打开的选项卡
        mv.setViewName("sys/user/profile");
        return mv;
    }

    /**
     * 我的帐号修改资料
     *
     * @param user
     * @return
     * @throws BizException
     * @author lidong dli@gdeng.cn
     * @time 2016年10月20日 下午8:49:28
     */
    @ResponseBody
    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public AjaxResult<Integer> profile(SysUserEntity entity) throws BizException {
        AjaxResult<Integer> res = new AjaxResult<Integer>();
        res.setIsSuccess(false);
        if (StringUtils.isEmpty(entity.getName())) {
            res.setMsg("请填写姓名");
            return res;
        } else if (StringUtils.isEmpty(entity.getMobile())) {
            res.setMsg("请填写手机号");
            return res;
        }
        res.setIsSuccess(true);
        entity.setUpdateTime(new Date());
        entity.setId(getUserId());
        entity.setUpdateUserId(getUserIdStr());
        int total = sysUserService.dynamicMerge(entity);
        res.setResult(total);
        // 更新登录用户缓存
        SysUserDTO user = getSysUser();
        user.setName(entity.getName());// 更新姓名
        user.setMobile(entity.getMobile());// 更新手机号
        user.setDeptNo(entity.getDeptNo());// 更新缓存工号
        user.setLandline(entity.getLandline());// 座机
        user.setMail(entity.getMail());// 邮箱
        loginService.setUser(getSession().getId(), user);
        return res;
    }

    /**
     * 设置（修改密码）
     *
     * @param entity
     * @return
     * @throws BizException
     * @author lidong dli@gdeng.cn
     * @time 2016年10月20日 下午8:53:42
     */
    @ResponseBody
    @RequestMapping(value = "setting", method = RequestMethod.POST)
    public AjaxResult<Integer> setting(HttpServletRequest request) throws BizException {
        AjaxResult<Integer> res = new AjaxResult<Integer>();
        res.setIsSuccess(false);
        String cPwd = request.getParameter("cPwd");// 当前密码
        String nPwd = request.getParameter("nPwd");// 新密码
        String confPwd = request.getParameter("confPwd");// 确认新密码
        SysUserDTO user = getSysUser();
        if (StringUtils.isEmpty(cPwd)) {
            res.setMsg("请输入当前登录密码");
            return res;
        } else if (StringUtils.isEmpty(nPwd)) {
            res.setMsg("请设置新的登录密码");
            return res;
        } else if (StringUtils.isEmpty(confPwd)) {
            res.setMsg("请确认新的登录密码");
            return res;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", "encode");
        map.put("code", "md5");
        map.put("status", "1");
        String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
        if (!MD5.sign(cPwd, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
            res.setMsg("当前登录密码错误");// 用户密码错误
            return res;
        } else if (MD5.sign(nPwd, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
            res.setMsg("新密码不能与原密码相同");// 新密码不能与原密码相同
            return res;
        } else if (!nPwd.equals(confPwd)) {
            res.setMsg("两次输入的密码不一致");// 两次输入的密码不一致
            return res;
        }
        res.setIsSuccess(true);
        SysUserEntity entity = new SysUserEntity();
        entity.setId(getUserId());
        entity.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));
        entity.setUpdateTime(new Date());
        entity.setUpdateUserId(getUserIdStr());
        int total = sysUserService.dynamicMerge(entity);
        // 更新登录用户缓存
        user.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));// 密码
        loginService.setUser(getSession().getId(), user);
        res.setResult(total);
        return res;
    }

    /**
     * 调转到公司用户新增界面
     *
     * @param mv
     * @return
     * @throws BizException
     */
    @RequestMapping("jumpToAdd")
    public ModelAndView jumpToAdd(ModelAndView mv) throws BizException {
        mv.setViewName("sys/user/sysCompUserAdd");
        return mv;
    }

    /**
     * 调转到公司用户编辑界面
     *
     * @param mv
     * @return
     * @throws BizException
     */
    @RequestMapping("jumpToEdit")
    public ModelAndView jumpToEdit(ModelAndView mv, SysUserDTO dto) throws BizException {
        if (dto.getId() != null) {
            SysUserDTO user = sysUserService.getById(dto.getId());
            mv.addObject("user", user);
        }
        mv.setViewName("sys/user/sysCompUserEdit");
        return mv;
    }

    /**
     * 调转到公司用户详细界面
     *
     * @param mv
     * @return
     * @throws BizException
     */
    @RequestMapping("jumpToDetail")
    public ModelAndView jumpToDetail(ModelAndView mv, SysUserDTO dto) throws BizException {
        SysUserDTO user = sysUserService.getById(dto.getId());
        mv.addObject("user", user);
        mv.setViewName("sys/user/sysCompUserDetail");
        return mv;
    }

    @RequestMapping(value = "modify-pwdBefore")
    public ModelAndView modifypwd(ModelAndView mv) throws BizException {
        mv.setViewName("sys/user/modify-pwd");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "modify-pwd", method = RequestMethod.POST)
    public AjaxResult<Integer> modifypwd(HttpServletRequest request) throws BizException {
        AjaxResult<Integer> res = new AjaxResult<Integer>();
        res.setIsSuccess(false);
        String nPwd = request.getParameter("nPwd");// 新密码
        String confPwd = request.getParameter("confPwd");// 确认新密码
        SysUserDTO user = getSysUser();
        if (StringUtils.isEmpty(nPwd)) {
            res.setMsg("请设置新的登录密码");
            return res;
        } else if (StringUtils.isEmpty(confPwd)) {
            res.setMsg("请确认新的登录密码");
            return res;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", "encode");
        map.put("code", "md5");
        map.put("status", "1");
        String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
        if (!nPwd.equals(confPwd)) {
            res.setMsg("两次输入的密码不一致");// 两次输入的密码不一致
            return res;
        } else if (MD5.sign(nPwd, key, Const.CHARSET_UTF8).equals(user.getPwd())) {
            res.setMsg("新密码不能与原密码相同");// 新密码不能与原密码相同
            return res;
        }
        res.setIsSuccess(true);
        SysUserEntity entity = new SysUserEntity();
        entity.setId(getUserId());
        entity.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));
        entity.setUpdateTime(new Date());
        entity.setUpdateUserId(getUserIdStr());
        int total = sysUserService.dynamicMerge(entity);
        // 更新登录用户缓存
        user.setPwd(MD5.sign(nPwd, key, Const.CHARSET_UTF8));// 密码
        loginService.setUser(getSession().getId(), user);
        res.setResult(total);
        return res;
    }

    /**
     * 新增公司用户
     *
     * @param entity
     * @return
     */
    @RequestMapping("addCompUser")
    @Transactional
    @ResponseBody
    public AjaxResult<Integer> addUser(SysUserEntity userEntity, Integer orgId, String posts) throws Exception {
        logger.info("添加用户基本信息入参:" + JSON.toJSONString(userEntity));
        logger.info("添加用户组织入参:部门ID-" + orgId);
        logger.info("添加用户岗位入参:" + posts);
        // 获取当前用户信息
        SysUserDTO userInfo = super.getSysUser();
        logger.info("获取当前用户信息：" + JSON.toJSONString(userInfo));
        AjaxResult<Integer> res = new AjaxResult<Integer>();
        // 默认密码
        res.setIsSuccess(false);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(userEntity.getCode())) {
            res.setMsg("请填写登录账号");
            return res;
        } else if (StringUtils.isEmpty(userEntity.getName())) {
            res.setMsg("请填写姓名");
            return res;
        } else if (StringUtils.isEmpty(userEntity.getMobile())) {
            res.setMsg("请填写手机号");
            return res;
        } else if (orgId == null) {
            res.setMsg("请选择部门");
            return res;
        } else {
            map.put("code", userEntity.getCode());
            int total = sysUserService.getListTotal(map);
            map.clear();
            if (total > 0) {
                res.setMsg("该账号已存在");
                return res;
            }
        }
        res.setIsSuccess(true);
        map.put("type", Const.DICTIONARY_ENCODE);
        map.put("code", Const.ENCRYPTION_TYPE_MD5);
        map.put("status", Const.USER_STATUS_1);
        String key = sysDictionaryService.getList(map).get(0).getValue();// 字典表用户登录md5加密串
        userEntity.setPwd(MD5.sign(Const.RESET_PASSWROD_VALUE, key, Const.CHARSET_UTF8));
        // 用户类型--普通用户
        userEntity.setType(Const.USER_TYPE_3);
        userEntity.setGroupId(getSysUser().getGroupId());// 集团id
        userEntity.setCreateTime(new Date());
        userEntity.setCreateUserId(getUserIdStr());
        // 新增用户信息
        int userId = sysUserService.persist(userEntity);

        // --------------新增用户组织关联信息--------------begin
        SysOrgEntity org = sysOrgService.querySysOrgById(orgId);
        // 根据用户组织信息获取用户所属公司
        SysOrgDTO compOrgDto = sysUserService.getParOrgByUserId(org.getParentId(), Const.ORG_TYPE_COMPANY);
        // 根据用户组织信息过去用户所属市场
        SysOrgDTO markOrtDto = sysUserService.getParOrgByUserId(org.getParentId(), Const.ORG_TYPE_MARKET);

        // 新增用户所属公司关联信息
        SysUserOrgEntity comOrgEntity = new SysUserOrgEntity();
        comOrgEntity.setOrgId(compOrgDto.getId());
        comOrgEntity.setUserId(userId);
        comOrgEntity.setType(Const.ORG_TYPE_COMPANY);
        comOrgEntity.setCreateUserId(getUserIdStr());
        sysUserOrgService.persist(comOrgEntity);

        // 新增用户所属市场关联信息
        SysUserOrgEntity markOrgEntity = new SysUserOrgEntity();
        if (markOrtDto != null) {
            markOrgEntity.setOrgId(markOrtDto.getId());
            markOrgEntity.setUserId(userId);
            markOrgEntity.setType(Const.ORG_TYPE_MARKET);
            markOrgEntity.setCreateUserId(getUserIdStr());
            sysUserOrgService.persist(markOrgEntity);
        }

        // 新增用户所属部门关联信息
        SysUserOrgEntity deptOrgEntity = new SysUserOrgEntity();
        deptOrgEntity.setOrgId(orgId);
        deptOrgEntity.setUserId(userId);
        deptOrgEntity.setType(Const.ORG_TYPE_DEPT);
        deptOrgEntity.setCreateUserId(getUserIdStr());
        sysUserOrgService.persist(deptOrgEntity);

        // 新增用户所属集团关联信息
        SysUserOrgEntity groupEntity = new SysUserOrgEntity();
        groupEntity.setOrgId(super.getUserGroup().getId());
        groupEntity.setUserId(userId);
        groupEntity.setType(Const.ORG_TYPE_GROUP);
        groupEntity.setCreateUserId(getUserIdStr());
        sysUserOrgService.persist(groupEntity);
        // --------------新增用户组织关联信息--------------end

        // 新增用户岗位关联信息
        if (StringUtils.isNotEmpty(posts)) {
            String[] postAry = posts.split(",");
            for (String post : postAry) {
                SysUserPostEntity entity = new SysUserPostEntity();
                entity.setPostId(Integer.valueOf(post));
                entity.setUserId(userId);
                entity.setCreateUserId(getUserIdStr());
                sysUserPostService.persist(entity);
            }
        }
        res.setResult(userId);
        logger.info("添加用户成功，生成ID=" + userId);
        return res;
    }

    /**
     * 编辑公司用户
     *
     * @param entity
     * @return
     */
    @RequestMapping("editCompUser")
    @Transactional
    @ResponseBody
    public AjaxResult<Integer> editUser(SysUserEntity userEntity, Integer orgId, String posts) throws Exception {
        logger.info("修改用户基本信息入参:" + JSON.toJSONString(userEntity));
        logger.info("修改用户组织入参:部门ID-" + orgId);
        logger.info("修改用户岗位入参:" + posts);
        AjaxResult<Integer> res = new AjaxResult<Integer>();
        // 默认密码
        res.setIsSuccess(false);
        if (StringUtils.isEmpty(userEntity.getName())) {
            res.setMsg("请填写姓名");
            return res;
        } else if (StringUtils.isEmpty(userEntity.getMobile())) {
            res.setMsg("请填写手机号");
            return res;
        } else if (orgId == null) {
            res.setMsg("请选择部门");
            return res;
        }
        res.setIsSuccess(true);
        // 修改用户信息
        Integer userId = userEntity.getId();
        sysUserService.dynamicMerge(userEntity);

        // --------------新增用户组织关联信息--------------begin
        SysOrgEntity org = sysOrgService.querySysOrgById(orgId);
        // 根据用户组织信息获取用户所属公司
        SysOrgDTO compOrgDto = sysUserService.getParOrgByUserId(org.getParentId(), Const.ORG_TYPE_COMPANY);
        // 根据用户组织信息过去用户所属市场
        SysOrgDTO markOrtDto = sysUserService.getParOrgByUserId(org.getParentId(), Const.ORG_TYPE_MARKET);

        // 删除用户-组织关系
        // 删除用户-岗位，
        sysUserOrgService.deleteByUserId(userId);
        sysUserPostService.deleteByUserId(userId);

        // 新增用户所属公司关联信息
        SysUserOrgEntity comOrgEntity = new SysUserOrgEntity();
        comOrgEntity.setOrgId(compOrgDto.getId());
        comOrgEntity.setUserId(userId);
        comOrgEntity.setType(Const.ORG_TYPE_COMPANY);
        comOrgEntity.setUpdateUserId(getUserIdStr());
        sysUserOrgService.persist(comOrgEntity);

        // 新增用户所属市场关联信息
        SysUserOrgEntity markOrgEntity = new SysUserOrgEntity();
        if (markOrtDto != null) {
            markOrgEntity.setOrgId(markOrtDto.getId());
            markOrgEntity.setUserId(userId);
            markOrgEntity.setType(Const.ORG_TYPE_MARKET);
            markOrgEntity.setCreateUserId(getUserIdStr());
            sysUserOrgService.persist(markOrgEntity);
        }

        // 新增用户所属部门关联信息
        SysUserOrgEntity deptOrgEntity = new SysUserOrgEntity();
        deptOrgEntity.setOrgId(orgId);
        deptOrgEntity.setUserId(userId);
        deptOrgEntity.setType(Const.ORG_TYPE_DEPT);
        deptOrgEntity.setCreateUserId(getUserIdStr());
        sysUserOrgService.persist(deptOrgEntity);

        // 新增用户所属集团关联信息
        SysUserOrgEntity groupEntity = new SysUserOrgEntity();
        groupEntity.setOrgId(super.getUserGroup().getId());
        groupEntity.setUserId(userId);
        groupEntity.setType(Const.ORG_TYPE_GROUP);
        groupEntity.setCreateUserId(getUserIdStr());
        sysUserOrgService.persist(groupEntity);

        // --------------新增用户组织关联信息--------------end

        // 新增用户岗位关联信息
        if (StringUtils.isNotEmpty(posts)) {
            String[] postAry = posts.split(",");
            for (String post : postAry) {
                SysUserPostEntity entity = new SysUserPostEntity();
                entity.setPostId(Integer.valueOf(post));
                entity.setUserId(userId);
                entity.setCreateUserId(getUserIdStr());
                sysUserPostService.persist(entity);
            }
        }
        res.setResult(userId);
        logger.info("修改用户成功，ID=" + userId);
        return res;
    }

    /**
     * 查询公司用户列表-分页
     *
     * @param model
     * @return
     */
    @RequestMapping("getCompUserList4Page")
    @ResponseBody
    public AjaxResult<GuDengPage<SysUserDTO>> list(Model model, SysUserDTO dto) throws Exception {
        logger.info("分页查询公司用户入参:" + JSON.toJSONString(dto));
        AjaxResult<GuDengPage<SysUserDTO>> result = new AjaxResult<GuDengPage<SysUserDTO>>();
        // 获取分页信息
        GuDengPage<SysUserDTO> pageInfo = super.getPageInfoByRequest();
//		pageInfo.getParaMap().put("likeCode", dto.getCode());
//		pageInfo.getParaMap().put("likeName", dto.getName());
        pageInfo.getParaMap().put("code", dto.getCode());
        pageInfo.getParaMap().put("name", dto.getName());
        pageInfo.getParaMap().put("postName", dto.getPostName());
        pageInfo.getParaMap().put("groupId", getSysUser().getGroupId());//当前集团
        pageInfo.getParaMap().put("typeList", new String[]{"3"});//普通用户
        // pageInfo.getParaMap().put("orgId", dto.getOrgId());
        // 查询条件
//		pageInfo = sysUserService.getDetailUserListPage(pageInfo);
        pageInfo = sysUserService.getUserListPage(pageInfo);
        logger.info("分页查询公司用户结果:" + JSON.toJSONString(pageInfo));
        result.setResult(pageInfo);
        return result;
    }

    /**
     * 重置密码
     *
     * @param entity
     * @return
     * @throws BizException
     */
    @RequestMapping("resetUserPwd")
    @ResponseBody
    public AjaxResult<Integer> resetPwd(SysUserEntity entity) throws BizException {
        logger.info("用户重置密码入参:" + JSON.toJSONString(entity));
        AjaxResult<Integer> result = new AjaxResult<Integer>();
        // 获取加密方式
        String key = sysDictionaryService.getDicVal(Const.ENCRYPTION_TYPE_MD5);
        entity.setPwd(MD5.sign(Const.RESET_PASSWROD_VALUE, key, Const.CHARSET_UTF8));
        Integer count = sysUserService.dynamicMerge(entity);
        result.setResult(count);
        logger.info("用户重置成功，用户ID" + entity.getId());
        return result;
    }

    /**
     * 获取用户信息根据ID
     *
     * @param entity
     * @return
     * @throws BizException
     */
    @RequestMapping("getUserInfoById")
    @ResponseBody
    public AjaxResult<SysUserDTO> getUserInfoById(SysUserDTO dto) throws BizException {
        logger.info("获取用户信息入参:" + JSON.toJSONString(dto));
        AjaxResult<SysUserDTO> result = new AjaxResult<SysUserDTO>();
        SysUserDTO resDto = sysUserService.getById(Integer.valueOf(dto.getUserId()));
        result.setResult(resDto);
        logger.info("获取用户信息成功，用户ID" + JSON.toJSONString(resDto));
        return result;
    }

    /**
     * 禁用/启用用户账号
     *
     * @param entity
     * @return
     * @throws BizException
     */
    @RequestMapping("disableCompUser")
    @ResponseBody
    public AjaxResult<Integer> disableCompUser(SysUserEntity entity) throws BizException {
        logger.info("用户禁用入参:" + JSON.toJSONString(entity));
        AjaxResult<Integer> result = new AjaxResult<Integer>();
        SysUserDTO dto = sysUserService.getById(entity.getId());
        if (dto.getCode().equals("admin") || dto.getType().equals("2")) {
            result.setIsSuccess(false);
            result.setMsg("不允许禁用管理员账号");
            return result;
        }
        Integer count = sysUserService.dynamicMerge(entity);
        result.setResult(count);
        if (Const.USER_STATUS_3.equals(entity.getStatus())) {
            loginService.removeUser(null, entity.getId());// 如果禁用该用户，则将登录用户常量表中的用户清除
        }
        logger.info("用户禁用成功，用户ID" + entity.getId());
        return result;
    }

    /**
     * 删除用户账号
     *
     * @param entity
     * @return
     * @throws BizException
     */
    @RequestMapping("deleteCompUser")
    @ResponseBody
    public AjaxResult<Integer> deleteCompUser(SysUserEntity entity) throws BizException {
        entity.setStatus(Const.USER_STATUS_0);
        logger.info("用户删除入参:" + JSON.toJSONString(entity));
        AjaxResult<Integer> result = new AjaxResult<Integer>();
        SysUserDTO dto = sysUserService.getById(entity.getId());
        if (dto.getCode().equals("admin") || dto.getType().equals("2")) {
            result.setIsSuccess(false);
            result.setMsg("不允许删除管理员账号");
            return result;
        }
        Integer count = sysUserService.dynamicMerge(entity);
        if (count > 0) {
            // 删除用户-组织关系
            // 删除用户-岗位，
            sysUserOrgService.deleteByUserId(entity.getId());
            sysUserPostService.deleteByUserId(entity.getId());
        }
        result.setResult(count);
        loginService.removeUser(null, entity.getId());// 如果删除该用户，则将登录用户常量表中的用户清除
        logger.info("用户删除成功，用户ID" + entity.getId());
        return result;
    }

    /**
     * 查询用户的岗位集合
     *
     * @param post
     * @return
     * @throws Exception
     * @author lidong dli@gdeng.cn
     * @time 2016年10月18日 下午1:40:01
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public AjaxResult<List<SysPostDTO>> query(Integer userId) throws Exception {
        AjaxResult<List<SysPostDTO>> res = new AjaxResult<>();
        // 获取分页信息
        List<SysPostDTO> list = sysUserService.getPostInfoByUserId(userId);
        res.setResult(list);
        return res;
    }

    @RequestMapping(value = "getUserListByOrg")
    @ResponseBody
    public AjaxResult<List<SysUserEntity>> getUserListByOrg(Integer orgId) throws Exception {
        AjaxResult<List<SysUserEntity>> res = new AjaxResult<>();
        // 获取分页信息
        List<SysUserEntity> list = sysUserService.getUserListByOrg(orgId);
        res.setResult(list);
        return res;
    }
}
