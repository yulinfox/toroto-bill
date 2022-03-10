package com.mhac.core.dao;

import com.mhac.core.domain.SysMenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * @Description 权限DAO
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
public interface SysMenuDao extends BaseMapper<SysMenuDO> {

    /**
     * 根据角色查询用户权限
     * @author yulinfu
     * @date 2019/6/19 10:14
     * @Param  roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    List<SysMenuDO> selectSysMenuByRoleId(Long roleId);
	
}
