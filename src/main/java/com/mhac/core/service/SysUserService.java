package com.mhac.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mhac.core.domain.SysUserDO;

/**
 * @Description 系统用户业务接口
 * @author yulinfu
 * @date 2019/6/14 15:57
 */
public interface SysUserService extends IService<SysUserDO> {

    /**
     * 根据用户名查询实体
     * @author yulinfu
     * @date 2019/6/14 16:30
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    SysUserDO selectUserByName(String username);

}

