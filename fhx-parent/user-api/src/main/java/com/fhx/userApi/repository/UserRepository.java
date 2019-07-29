package com.fhx.userApi.repository;

import com.fhx.common.bean.po.User;
import com.fhx.jpa_base.repository.common.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户持久层
 * @author fhx
 * @date 2019/7/22 10:58
 */
@Repository
public interface UserRepository extends BaseRepository<User,Long> {

    /**
     * 通过用户名查询
     * @param username 用户名
     * @return  user对象
     */


    User findByUsername(String username);

}
