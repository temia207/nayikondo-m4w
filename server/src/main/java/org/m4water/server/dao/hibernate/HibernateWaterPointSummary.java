package org.m4water.server.dao.hibernate;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.paging.FilterComparison;
import org.m4water.server.admin.model.paging.FilterConfig;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;
import org.m4water.server.dao.WaterPointSummaryDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("WaterpointSummary")
public class HibernateWaterPointSummary extends BaseDAOImpl<WaterPointSummary, String> implements WaterPointSummaryDao {

    @Override
    public PagingLoadResult<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType, PagingLoadConfig loadConfig) {
        Search waterpointSearch = getSearchFromLoadConfig(loadConfig, "district");
        SearchResult<WaterPointSummary> result = searchAndCount(waterpointSearch);
        return getWaterpointSummaryPagingLoadResult(loadConfig, result);

    }

    private PagingLoadResult<WaterPointSummary> getWaterpointSummaryPagingLoadResult(PagingLoadConfig loadConfig,
            SearchResult<WaterPointSummary> searchResult) {
        List<WaterPointSummary> list = searchResult.getResult();
        List<WaterPointSummary> summaryList = new ArrayList<WaterPointSummary>();
        if (list != null) {
            for (WaterPointSummary fd : list) {
                summaryList.add(fd);
            }
        }
        int totalNum = searchResult.getTotalCount();
        int offset = loadConfig == null ? 0 : loadConfig.getOffset();
        return new PagingLoadResult<WaterPointSummary>(summaryList, offset, list.size(), totalNum);
    }

    @Override
    public Date getBaselineSetDate() {
        Date date = null;
        String query = "SELECT * FROM baselinedate WHERE id = (SELECT MAX( id ) FROM baselinedate) ";
        System.out.println(query);
        SQLQuery createSQLQuery = getSession().createSQLQuery(query);
        List list = createSQLQuery.list();
        String datevalue = ((Object[]) list.get(0))[1] + "";
        //MM/dd/yyyy
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df.parse(datevalue);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }
}
