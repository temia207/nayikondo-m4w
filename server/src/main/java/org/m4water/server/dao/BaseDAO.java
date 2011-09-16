package org.m4water.server.dao;

import java.util.List;


import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import java.io.Serializable;

public interface BaseDAO<T extends Serializable, S extends Serializable> extends GenericDAO<T, S> {

    List<T> searchByPropertyEqual(String property, Object value);

    T searchUniqueByPropertyEqual(String property, Object value);
}
