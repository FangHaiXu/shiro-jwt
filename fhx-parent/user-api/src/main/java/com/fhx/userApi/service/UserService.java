package com.fhx.userApi.service;

import com.fhx.common.bean.po.User;
import com.fhx.jpa_base.service.common.BaseService;

/**
 * 用户接口
 * @author fhx
 * @date 2019/7/22 11:01
 */
public interface UserService extends BaseService<User> {

    /**
     * 通过用户名查询
     * @param username 用户名
     * @return  user对象
     */
    User findByUserName(String username);
}
