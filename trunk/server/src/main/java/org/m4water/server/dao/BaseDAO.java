package org.m4water.server.dao;

import java.util.List;


import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import java.io.Serializable;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;

public interface BaseDAO<E extends Serializable, ID extends Serializable> extends GenericDAO<E, ID> {

    List<E> searchByPropertyEqual(String property, Object value);

    E searchUniqueByPropertyEqual(String property, Object value);

    PagingLoadResult<E> findAllByPage(PagingLoadConfig pagingLoadConfig, String defaultSortField);
}
