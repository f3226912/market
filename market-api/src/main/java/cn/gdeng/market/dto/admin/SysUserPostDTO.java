package cn.gdeng.market.dto.admin;

import cn.gdeng.market.entity.admin.SysUserPostEntity;

public class SysUserPostDTO extends SysUserPostEntity {
	private static final long serialVersionUID = -6144999839639728351L;

	private String postName;

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	
}
