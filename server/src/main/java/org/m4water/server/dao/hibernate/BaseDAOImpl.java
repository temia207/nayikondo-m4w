package org.m4water.server.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.m4water.server.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import java.io.Serializable;

abstract class BaseDAOImpl<E extends Serializable, ID extends Serializable> extends GenericDAOImpl<E, ID> implements BaseDAO<E,ID> {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    
	@Override
	public List<E> searchByPropertyEqual(String property, Object value){
		Search search = new Search();
		search.addFilterEqual(property, value);
		return search(search);
	}

	@Override
    @SuppressWarnings("unchecked")
	public E searchUniqueByPropertyEqual(String property, Object value) {
        Search search = new Search();
        search.addFilterEqual(property, value);
        return (E) searchUnique(search);
    }
}
