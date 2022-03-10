package com.mhac.common.shiro;

import com.mhac.core.domain.SysMenuDO;
import com.mhac.core.domain.SysRoleDO;
import com.mhac.core.domain.SysUserDO;
import com.mhac.core.service.SysMenuService;
import com.mhac.core.service.SysRoleService;
import com.mhac.core.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yulinfu
 * @Description Shiro权限匹配和账号密码匹配
 * @date 2019/6/15 11:27
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 授权权限
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     *
     * @author yulinfu
     * @date 2019/6/12 11:44
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户ID
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUserDO sysUserEntity = (SysUserDO) principalCollection.getPrimaryPrincipal();
        Long userId = sysUserEntity.getId();
        //这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限(这里根据业务自行查询)
        List<SysRoleDO> sysRoleDOList = sysRoleService.selectSysRoleByUserId(userId);
        for (SysRoleDO sysRoleDO : sysRoleDOList) {
            rolesSet.add(sysRoleDO.getRoleName());
            List<SysMenuDO> sysMenuDOList = sysMenuService.selectSysMenuByRoleId(sysRoleDO.getRoleId());
            for (SysMenuDO sysMenuDO : sysMenuDOList) {
                permsSet.add(sysMenuDO.getPerms());
            }
        }
        //将查到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setStringPermissions(permsSet);
        authorizationInfo.setRoles(rolesSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     *
     * @author yulinfu
     * @date 2019/6/12 12:36
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到进行验证
        //实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        SysUserDO user = sysUserService.selectUserByName(username);
        //判断账号是否存在
        if (user == null) {
            throw new AuthenticationException();
        }
        //判断账号是否被冻结
        if (user.getState() == null || user.getState().equals("PROHIBIT")) {
            throw new LockedAccountException();
        }
        //进行验证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,                                  //用户名
                user.getPassword(),                    //密码
                ByteSource.Util.bytes(user.getSalt()), //设置盐值
                getName()
        );
        //验证成功开始踢人(清除缓存和Session)
//        ShiroUtils.deleteCache(username, true);
        return authenticationInfo;
    }
}
