package cn.gdeng.market.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.entity.admin.SysOrgEntity;
import cn.gdeng.market.enums.SysOrgEnum;
import cn.gdeng.market.exception.BizException;

public class OrgUserUtil {


    /**
     * 过滤，查找所有祖辈组织为orgId的部门,因为只有部门下才有人员,或者=orgId的部门
     */
    public static Set<Integer> getDepartmentSubOrgIds(List<SysOrgEntity> allOrg, int ancestorId) {
        Set<Integer> orgIds = new HashSet<>();
        for (SysOrgEntity sysOrg : allOrg) {
            if (StringUtils.equals(sysOrg.getType(), SysOrgEnum.TYPE.DEPARTMENT) &&
                    isFromAncestor(allOrg, sysOrg, ancestorId)) {
                orgIds.add(sysOrg.getId());
            }
        }
        return orgIds;
    }

    private static boolean isFromAncestor(List<SysOrgEntity> allOrg, SysOrgEntity org, int ancestorId) {
        do {
            if (org == null) {
                return false;
            } else if (org.getId() == ancestorId) {
                return true;
            } else if (org.getParentId() == null) {
                return false;
            } else if (org.getId().intValue() == org.getParentId().intValue()) {
                //防止错误数据产生导致死循环
                //父节点为自身跳出循环
                return false;
            } else {
                if (org.getParentId() == ancestorId) {
                    return true;
                }
            }
            org = getParentSysOrg(allOrg, org.getParentId());

        } while (true);
    }

    private static SysOrgEntity getParentSysOrg(List<SysOrgEntity> nodes, int pid) {
        for (SysOrgEntity org : nodes) {
            if (org.getId() == pid) {
                return org;
            }
        }
        return null;
    }

    /**
     * 根据pid获取所有的组织，一直递归到最底层节点
     *
     * @param orgs
     * @param pid
     * @return
     * @author lidong dli@gdeng.cn
     * @time 2016年10月14日 下午4:18:01
     */
    public static List<SysOrgDTO> getOrgsByPid(List<SysOrgDTO> orgs, Integer pid) {
        List<SysOrgDTO> children = new ArrayList<>();
        if (orgs != null && orgs.size() > 0) {
            for (SysOrgDTO org : orgs) {
                if (org.getParentId() != null) {
                    if (org.getParentId().intValue() == pid.intValue()) {
                        children.add(org);
                        List<SysOrgDTO> orgsSub = getOrgsByPid(orgs, org.getId());
                        children.addAll(orgsSub);
                    }
                }
            }
        }
        return children;
    }

    /**
     * 根据pid获取所有的下级组织，并返回指定类型的组织
     *
     * @param orgs
     * @param pid
     * @param type
     * @return
     * @author lidong dli@gdeng.cn
     * @time 2016年10月19日 上午9:04:45
     */
    public static List<SysOrgDTO> getOrgsByType(List<SysOrgDTO> orgs, Integer pid, String type) {
        List<SysOrgDTO> children = new ArrayList<>();
        if (pid == null) {
            return children;
        }
        List<SysOrgDTO> orgsP = getOrgsByPid(orgs, pid);
        for (SysOrgDTO sysOrgDTO : orgsP) {
            if (sysOrgDTO.getType().equals(type)) {
                children.add(sysOrgDTO);
            }
        }
        return children;
    }

    /**
     * 获取用户的集团
     *
     * @param orgs
     * @return
     * @throws BizException
     * @author lidong dli@gdeng.cn
     * @time 2016年10月19日 上午9:07:42
     */
    public static SysOrgDTO getUserGroup(List<SysOrgDTO> orgs) throws BizException {
        if (orgs != null) {
            for (SysOrgDTO sysOrgDTO : orgs) {
                if (sysOrgDTO.getParentId() == null) {
                    return sysOrgDTO;
                }
            }
        }
        return null;
    }
}
