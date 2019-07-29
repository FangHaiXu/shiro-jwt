package com.fhx.jpa_base.bean.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 父实体类
 *
 * @author fhx
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 5370873251517126579L;
    /**
     * id
     * 自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Column(name = "id",columnDefinition ="bigint(10) comment '主键'")
    private Long id;

    /**
     * 创建时间
     * 自动设置
     */
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * 修改时间
     * 自动设置
     */
    @UpdateTimestamp
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    /**
     * 删除标识
     * 0: 未删
     * 1: 已删除
     */
    @Column(name = "delete_type",columnDefinition = "tinyint(1) comment '删除状态(0:未删除; 1:已删除)'")
    private int deleteType = 0;
}
