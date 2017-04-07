package cn.gdeng.market.lease.util;

import java.util.ArrayList;
import java.util.List;

import cn.gdeng.market.dto.admin.SysOrgDTO;
import cn.gdeng.market.dto.admin.SysUserDTO;
import cn.gdeng.market.dto.admin.SysUserPostDTO;
import cn.gdeng.market.entity.admin.SysPostEntity;
import cn.gdeng.market.exception.BizException;

public class OrgUserUtil {

	public static void setName4UserPost(List<SysUserPostDTO> userPostList, List<SysPostEntity> postList) {
		for (SysPostEntity post : postList) {
			for (SysUserPostDTO userPost : userPostList) {
				if (userPost.getPostId().intValue() == post.getId()) {
					userPost.setPostName(post.getName());
					break;
				}
			}
		}
	}

	public static void setPostName4User(List<SysUserDTO> userList, List<SysUserPostDTO> userPostList) {
		for (SysUserPostDTO userPost : userPostList) {
			for (SysUserDTO user : userList) {
				if (user.getId().intValue() == userPost.getUserId()) {
					String postId = user.getPostId();
					String postName = user.getPostName();
					if (postId == null) {
						user.setPostId(userPost.getPostId() + "");
						user.setPostName(userPost.getPostName());
					} else {
						user.setPostId(postId + "," + userPost.getPostId() + "");
						user.setPostName(postName + "," + userPost.getPostName());
					}

				}
			}
		}
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
