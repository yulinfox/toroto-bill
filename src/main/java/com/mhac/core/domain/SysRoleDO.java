package com.mhac.core.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;


/**
 * @Description 角色实体
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
@Data
@TableName("sys_role")
public class SysRoleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	@TableId
	private Long roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
}
