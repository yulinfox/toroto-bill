package com.mhac.core.dao;

import com.mhac.core.domain.SysRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * @Description 角色DAO
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
public interface SysRoleDao extends BaseMapper<SysRoleDO> {

    /**
     * 通过用户ID查询角色集合
     * @author yulinfu
     * @date 2019/6/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleDO> selectSysRoleByUserId(Long userId);
	
}
