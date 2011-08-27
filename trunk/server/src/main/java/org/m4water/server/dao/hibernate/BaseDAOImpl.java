package org.m4water.server.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.m4water.server.admin.model.Editable;
import org.m4water.server.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

abstract class BaseDAOImpl<T extends Editable> extends GenericDAOImpl<T, Long> implements BaseDAO<T> {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    
	@Override
	public List<T> searchByPropertyEqual(String property, Object value){
		Search search = new Search();
		search.addFilterEqual(property, value);
		return search(search);
	}

	@Override
    @SuppressWarnings("unchecked")
	public T searchUniqueByPropertyEqual(String property, Object value) {
        Search search = new Search();
        search.addFilterEqual(property, value);
        return (T) searchUnique(search);
    }
}
