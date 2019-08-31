package com.mapper;

import com.pojo.Person;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonMapper {
    public int insertRole(Person role);

    public int updateRole(Person role);

    public int deleteRole(int id);

    public Person getRole(int id);

    public List<Person> findRoles();
}
