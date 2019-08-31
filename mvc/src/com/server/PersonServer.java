package com.server;


import com.Pojo.Person;

import java.util.List;

public interface PersonServer {
    public int insertRole(Person role);

    public int updateRole(Person role);

    public int deleteRole(int id);

    public Person getRole(int id);

    public List<Person> findRoles();
}
