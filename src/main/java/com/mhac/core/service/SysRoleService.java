package com.mhac.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mhac.core.domain.SysRoleDO;
import java.util.List;

/**
 * @Description 角色业务接口
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
public interface SysRoleService extends IService<SysRoleDO> {

    /**
     * 通过用户ID查询角色集合
     * @author yulinfu
     * @date 2019/6/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleDO> selectSysRoleByUserId(Long userId);
}

