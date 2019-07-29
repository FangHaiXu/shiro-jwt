package com.fhx.jpa_base.service.impl.common;

import com.fhx.jpa_base.repository.common.BaseRepository;
import com.fhx.jpa_base.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * 封装公共基础实现类，实现基本CRUD操作，避免代码冗余
 *
 * @param <E> 实体类
 * @author fhx
 * @date 2019/07/04
 */
@Service
public class BaseServiceImpl<E> implements BaseService<E> {
    @PersistenceContext
    EntityManager em;

    @Autowired
   private BaseRepository<E,Long> repository;

    @Override
    @Transactional
    public E save(E e) {
        Assert.notNull(e, "实体不能为空！");
        return repository.save(e);
    }

    @Override
    @Transactional
    public List<E> saveAll(Iterable<E> entity) {
        Assert.notNull(entity, "实体不能为空！");
        return repository.saveAll(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "id 不能为null");
        if (existsById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public E findById(Long id) {
        Assert.notNull(id, "id 不能是null");
        Optional<E> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean existsById(Long id) {
        Assert.notNull(id, "id 不能是null");
        return repository.existsById(id);
    }

    /**
     * 根据domain class创建JPA repository 实现类SimpleJpaRepository
     * @return SimpleJpaRepository对象
     */
   /* private SimpleJpaRepository<E, Long> repository() {
        this.init();
        return new SimpleJpaRepository<>(eClass,em);
    }*/

    /**
     * 初始化， 通过反射获取泛型E的Class
     */
      /*@SuppressWarnings("unchecked")
  private void init() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        eClass = (Class<E>) type.getActualTypeArguments()[0];
    }*/
}
