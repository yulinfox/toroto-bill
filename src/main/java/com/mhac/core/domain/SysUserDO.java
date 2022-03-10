package com.mhac.core.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * @Description 系统用户实体
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
@Data
@TableName("sys_user")
public class SysUserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 是否展示手机号
	 */
	private Integer showPhone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 是否展示邮件
	 */
	private Integer showEmail;

	/**
	 * 微信号
	 */
	private String wechat;

	/**
	 * 是否展示微信号
	 */
	private Integer showWechat;

	/**
	 * 微信群
	 */
	private String wechatGroup;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司简称
	 */
	private String companyShortName;

	/**
	 * 公司省份
	 */
	private String companyProvince;

	/**
	 * 公司城市
	 */
	private String companyCity;

	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 企业性质
	 */
	private String companyType;

	/**
	 * 企业规模
	 */
	private String companyScale;

	/**
	 * 职位或部门
	 */
	private String post;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 盐值
	 */
	private String salt;

	/**
	 * 状态:NORMAL正常  PROHIBIT禁用
	 */
	private String state;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 更新时间
	 */
	private Date gmtUpdate;

	/**
	 * 更新人
	 */
	private String updateUser;

	@Transient
	@JsonIgnore
	public Long getUserId() {
		return id;
	}

}
