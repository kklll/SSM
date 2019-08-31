package com.imp;

import com.mapper.PersonMapper;
import com.pojo.Person;
import com.server.PersonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PersonServerImp implements PersonServer {
    //角色DAO
    @Autowired
    private PersonMapper personMapper = null;

    /*
    使用@Cacheable定义缓存策略
    缓存中有值则返回值，否则，访问方法
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Cacheable(value = "redisCacheManager", key = "'redis_person_'+#id")
    @Override
    public Person getRole(int id) {
        return personMapper.getRole(id);
    }

    /*
    插入使用CachePut，这样成功与否都会被插入到缓存中
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CachePut(value = "redisCacheManager", key = "'redis_person_'+#result.id")
    @Override
    public Person insertRole(Person role) {
        personMapper.insertRole(role);
        return role;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CachePut(value = "redisCacheManager", key = "'redis_person_'+#role.id")
    @Override
    public Person updateRole(Person role) {
        personMapper.updateRole(role);
        return role;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "redisCacheManager", key = "'redis_person_'+#id")
    @Override
    public int deleteRole(int id) {
        return personMapper.deleteRole(id);
    }

    @Override
    public List<Person> findRoles() {
        return personMapper.findRoles();
    }
}
