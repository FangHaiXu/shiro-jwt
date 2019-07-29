package com.fhx.userApi.service.impl;

import com.fhx.common.bean.po.User;
import com.fhx.jpa_base.service.impl.common.BaseServiceImpl;
import com.fhx.userApi.repository.UserRepository;
import com.fhx.userApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现类
 * @author fhx
 * @date 2019/7/22 11:03
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
