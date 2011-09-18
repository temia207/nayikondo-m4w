/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.hibernate.SQLQuery;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.WaterPointDao;

import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("Water")
public class HibernateWaterPointDao extends BaseDAOImpl<Waterpoint, String> implements WaterPointDao {

    @Override
    public List<Waterpoint> getWaterPoints() {
        return findAll();
    }

    @Override
    public Waterpoint getWaterPoint(String waterpointId) {
        return searchUniqueByPropertyEqual("waterpointId", waterpointId);
    }

    @Override
    public void saveWaterPoint(Waterpoint waterPoint) {
        save(waterPoint);
    }

    @Override
    public List<WaterPointSummary> getWaterPointSummaries() {

        List<WaterPointSummary> summary = new ArrayList<WaterPointSummary>();

        String query = "select waterpoint.waterpoint_id,village.villagename,parish.parish_name,"
                + " subcounty.subcounty_name,county.county_name,district.`name`,waterpoint.date_installed from waterpoint"
                + " inner join village"
                + " on waterpoint.village_id = village.village_id inner join parish"
                + " on village.parish_id = parish.parish_id inner join subcounty on"
                + " parish.subcounty_id = subcounty.subcounty_id inner join county on"
                + " subcounty.county_id = county.county_id inner join district on"
                + " county.district_id = district.district_id where district.district_id = '405'";
        SQLQuery createSQLQuery = getSession().createSQLQuery(query);
        List list = createSQLQuery.list();


        for (Object object : list) {
            Object[] strings = (Object[]) object;
            WaterPointSummary point = new WaterPointSummary();
            point.setWaterPointId(strings[0]+"");
            point.setVillageName(strings[1]+"");
            point.setParishName(strings[2]+"");
            point.setSubcountyName(strings[3]+"");
            point.setCountyName(strings[4]+"");
            point.setDistrict(strings[5]+"");
            //MM/dd/yyyy
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parse = null;
            try {
                parse = df.parse(strings[6]+"");
            } catch (Exception e) {
            }
            point.setDate(parse);
            summary.add(point);
        }


        return summary;
    }
}
