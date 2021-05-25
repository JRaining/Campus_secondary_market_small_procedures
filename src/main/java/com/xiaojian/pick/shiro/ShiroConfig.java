package com.xiaojian.pick.shiro;

import com.xiaojian.pick.shiro.MyRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/9/15 - 17:14
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean  shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        // 添加 Shiro 内置过滤器
        /**
         * Shiro 内置过滤器，可以实现权限相关的拦截器
         *  常用拦截器：
         *      anon：无需认证（登录）可以访问
         *      authc：必须认证才可以访问
         *      user：如果使用 rememberMe 的功能可以直接访问
         *      perms：该资源必须得到资源权限才可以访问
         *      role：该资源必须得到角色权限才可以访问
         */
        // 添加 Shiro 内置过滤器
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/","authc");
        filterMap.put("/css/**","anon");
        filterMap.put("/js/**","anon");
        filterMap.put("/layui/**","anon");
        filterMap.put("/images/**","anon");
        filterMap.put("/icons/**","anon");
        filterMap.put("/toLogin","anon");
        filterMap.put("/login","anon");

        // 前端请求数据
        // 登录
//        filterMap.put("/user/login/*","anon");
//        filterMap.put("/user/updateOne","anon");
//        filterMap.put("/user/updateComplete","anon");
        filterMap.put("/category/getAllCategory","anon");
        filterMap.put("/commodity/swiperCommodity","anon");
        filterMap.put("/commodity/pageOfCommodity","anon");
        filterMap.put("/commodity/commodityDetail/*","anon");
        filterMap.put("/commodity/commodityByCategory/*","anon");
        filterMap.put("/commodity/updateClickCount/*","anon");
        filterMap.put("/seek/pageOfSeek","anon");
        filterMap.put("/seek/uploadSeek","anon");
        filterMap.put("/seek/search","anon");
        filterMap.put("/commodity/uploadData","anon");
        filterMap.put("/commodity/uploadCoverImg","anon");
        filterMap.put("/commodity/uploadFile","anon");
        filterMap.put("/commodity/search","anon");
        filterMap.put("/inform/commodityInform","anon");
        filterMap.put("/inform/seekInform","anon");
        filterMap.put("/inform/topicInform","anon");
        filterMap.put("/user/**","anon");
        filterMap.put("/topic/pageOfTopic","anon");
        filterMap.put("/topic/topicDetail/*","anon");
        filterMap.put("/topic/uploadData","anon");
        filterMap.put("/topic/uploadFile","anon");
        filterMap.put("/topic/updateClickCount/*","anon");
        filterMap.put("/topic/addComment","anon");
        filterMap.put("/topic/delComment","anon");
        filterMap.put("/topic/search","anon");

//        filterMap.put("/user/getCommodityList/*","anon");
//        filterMap.put("/user/getSeekList/*","anon");
//        filterMap.put("/user/getCollectList/*","anon");
//        filterMap.put("/user/deleteCollect/*","anon");
//        filterMap.put("/user/deleteSeek/*","anon");
//        filterMap.put("/user/updateCommodityState","anon");
//        filterMap.put("/user/delCommodity/*","anon");
//        filterMap.put("/user/getMessageList/*","anon");
//        filterMap.put("/user/updateMessageState","anon");

        // 登出
        filterMap.put("/logout","logout");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 登录页面，点击需要验证的链接，跳转到
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        return securityManager;
    }
    /**
     * 创建Realm
     */
    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }

    /**
     * Shiro 生命周期
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启 Shiro 的注解，（@RequireRole,@RequirePermissions），借助 Spring 的AOP扫描使用 Shiro 注解的类，并在需要时进行安全逻辑验证
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor attributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager());
        return attributeSourceAdvisor;
    }
}
