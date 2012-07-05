package org.m4water.server.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.m4water.server.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import java.io.Serializable;
import org.m4water.server.admin.model.paging.FilterComparison;
import org.m4water.server.admin.model.paging.FilterConfig;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;

abstract class BaseDAOImpl<E extends Serializable, ID extends Serializable> extends GenericDAOImpl<E, ID> implements BaseDAO<E, ID> {

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<E> searchByPropertyEqual(String property, Object value) {
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

    @Override
    public PagingLoadResult<E> findAllByPage(PagingLoadConfig pagingLoadConfig, String defaultSortField) {
        Search userSearch = getSearchFromLoadConfig(pagingLoadConfig, defaultSortField);
        SearchResult<E> result = searchAndCount(userSearch);
        return getPagingLoadResult(pagingLoadConfig, result);
    }

	/**
	 * @param entityClass
	 *            the class to search for
	 * @param loadConfig
	 * @return the configured Search
	 */
	public Search getSearchFromLoadConfig(PagingLoadConfig loadConfig, String defaultSortField) {

		Search search = new Search();

		// sorting
		String sortField = loadConfig.getSortField();
		sortField = (sortField == null ? defaultSortField : sortField);
		if (sortField != null && loadConfig.isSortDescending() != null) {
			search.addSort(sortField, loadConfig.isSortDescending());
		}

		// filtering
		List<FilterConfig> filterConfigs = loadConfig.getFilters();
		if (filterConfigs != null) {
			Filter[] filters = new Filter[filterConfigs.size()];
			for (int i = 0, j = filterConfigs.size(); i < j; i++) {
				FilterConfig filter = filterConfigs.get(i);
				if (filter.getComparison() == FilterComparison.LIKE) {
					filters[i] = Filter.ilike(filter.getField(), "%" + filter.getValue() + "%");
				} else if (filter.getComparison() == FilterComparison.GREATER_THAN) {
					filters[i] = Filter.greaterThan(filter.getField(), filter.getValue());
				} else if (filter.getComparison() == FilterComparison.LESS_THAN) {
					filters[i] = Filter.lessThan(filter.getField(), filter.getValue());
				} else if (filter.getComparison() == FilterComparison.EQUAL_TO) {
					filters[i] = Filter.equal(filter.getField(), filter.getValue());
				}else if (filter.getComparison() == FilterComparison.GREATER_THAN_OR_EQUAL_TO) {
					filters[i] = Filter.greaterOrEqual(filter.getField(), filter.getValue());
				}else if (filter.getComparison() == FilterComparison.LESS_THAN_OR_EQUAL_TO) {
					filters[i] = Filter.lessOrEqual(filter.getField(), filter.getValue());
				}
			}
			search.addFilterAnd(filters);
		}

		// paging
		search.setMaxResults(loadConfig.getLimit());
		search.setFirstResult(loadConfig.getOffset());

		return search;
	}

	protected PagingLoadResult<E> getPagingLoadResult(PagingLoadConfig loadConfig, SearchResult<E> searchResult) {
		List<E> list = searchResult.getResult();
		int totalNum = searchResult.getTotalCount();
		int offset = loadConfig == null ? 0 : loadConfig.getOffset();
		return new PagingLoadResult<E>(list, offset, list.size(), totalNum);
	}

}
