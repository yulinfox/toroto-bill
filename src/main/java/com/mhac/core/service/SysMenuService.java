package com.mhac.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mhac.core.domain.SysMenuDO;

import java.util.List;

/**
 * @Description 权限业务接口
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
public interface SysMenuService extends IService<SysMenuDO> {
    
    
    /**
     * 根据角色查询用户权限
     * @author yulinfu
     * @date 2019/6/19 10:14
     * @Param  roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    List<SysMenuDO> selectSysMenuByRoleId(Long roleId);

}

