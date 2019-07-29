package com.fhx.common.bean.po;

import com.fhx.jpa_base.bean.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author fhx
 * @date 2019/7/19 17:33
 */
@Data
@Table
@Entity
public class User extends BaseEntity {

    @Column(columnDefinition = "char(20) COMMENT '用户名'")
    private String username;
    @Column(columnDefinition = "char(50) comment '密码'")
    private String password;
    @Column(columnDefinition ="bigint(10) comment '主键'")
    private Long roleId;

    @Column(columnDefinition = "char(30) comment '邮箱'")
    private String email;

    @Column(columnDefinition = "char(6) COMMENT '用户名'")
    private String salt;
}
