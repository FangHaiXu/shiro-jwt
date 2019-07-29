package com.fhx.jpa_base.service.common;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 封装公共基础操作接口，定义基本CRUD操作，避免代码冗余
 *
 * @param <E> Entity实体类
 * @author fhx
 * @date 2019/07/04
 */
public interface BaseService<E> {

   /**
    * 添加实体
    * @param e 实体对象
    * @return 返回创建的实体
    */
   E save(E e);

   /**
    * 批量添加实体
    * @param entity 实体集合
    * @return 返回创建的实体集合
    */
   List<E> saveAll(Iterable<E> entity);

   /**
    * 通过id删除
    * @param id 实体主键
    */
   void deleteById(Long id);

   /**
    * 查询所有
    * @return 所有数据
    */
   List<E> findAll();

   /**
    * 通过给定id查询实体
    * @param id 实体主键
    * @return 实体
    */
   E findById(Long id);

   /**
    * 通过id 检测实体是否存在
    * @param id  实体主键
    * @return  是否存在
    */
   boolean existsById(Long id);
}
