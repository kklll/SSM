package com.server;

import com.pojo.Person;

import java.util.List;

public interface PersonServer {
    public Person insertRole(Person role);

    public Person updateRole(Person role);

    public int deleteRole(int id);

    public Person getRole(int id);

    public List<Person> findRoles();
}