package com.mhac.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mhac.core.dao.SysRoleMenuDao;
import com.mhac.core.domain.SysRoleMenuDO;
import com.mhac.core.service.SysRoleMenuService;

/**
 * @Description 角色与权限业务实现
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuDO> implements SysRoleMenuService {

}