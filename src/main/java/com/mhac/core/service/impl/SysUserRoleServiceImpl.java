package com.mhac.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mhac.core.dao.SysUserRoleDao;
import com.mhac.core.domain.SysUserRoleDO;
import com.mhac.core.service.SysUserRoleService;

/**
 * @Description 用户与角色业务实现
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleDO> implements SysUserRoleService {

}