package com.hzy.config;

import com.hzy.pojo.User;
import com.hzy.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义realm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.queryUserByName(token.getUsername());
        if (user == null) {
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        currentSubject.getSession().setAttribute("loginUser",user);
        // 密码认证shiro做

        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // info.addStringPermission("user:add");

        // 拿到当前用户的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal(); //拿到user对象
        // 设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

}
