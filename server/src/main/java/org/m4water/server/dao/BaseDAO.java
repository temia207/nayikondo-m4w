package org.m4water.server.dao;

import java.util.List;

import org.m4water.server.admin.model.Editable;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface BaseDAO<T extends Editable> extends GenericDAO<T, Long> {

    List<T> searchByPropertyEqual(String property, Object value);

    T searchUniqueByPropertyEqual(String property, Object value);
}
