package com.fhx.common.bean.po;

import com.fhx.jpa_base.bean.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author fhx
 * @date 2019/7/23 11:12
 */
@Data
@Entity
public class Role extends BaseEntity {
    @Column(columnDefinition = "char(20) COMMENT '权限名称'")
    private String roleName;
}
