/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        String query = "select waterpoint.waterpoint_id,waterpoint.baseline_date,waterpoint.baseline_pending,village.villagename,parish.parish_name,"
                + " subcounty.subcounty_name,county.county_name,district.`name`,waterpoint.date_installed from waterpoint"
                + " inner join village"
                + " on waterpoint.village_id = village.village_id inner join parish"
                + " on village.parish_id = parish.parish_id inner join subcounty on"
                + " parish.subcounty_id = subcounty.id inner join county on"
                + " subcounty.county_id = county.county_id inner join district on"
                + " county.id = district.id";
        SQLQuery createSQLQuery = getSession().createSQLQuery(query);
        List list = createSQLQuery.list();


        for (Object object : list) {
            Object[] strings = (Object[]) object;
            WaterPointSummary point = new WaterPointSummary();
            point.setWaterPointId(strings[0] + "");
            point.setBaselinePending(strings[2] + "");
            point.setVillageName(strings[3] + "");
            point.setParishName(strings[4] + "");
            point.setSubcountyName(strings[5] + "");
            point.setCountyName(strings[6] + "");
            point.setDistrict(strings[7] + "");
            //MM/dd/yyyy
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateInstalled = null;
            java.util.Date baselineDate = null;
            try {
                dateInstalled = df.parse(strings[8] + "");
                baselineDate = df.parse(strings[1] + "");
            } catch (Exception e) {
            }
            point.setDate(dateInstalled);
            point.setBaselineDate(baselineDate);
            summary.add(point);
        }


        return summary;
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

    @Override
    public List<WaterPointSummary> getWaterPointSummaries(String district) {

        List<WaterPointSummary> summary = new ArrayList<WaterPointSummary>();

        String query = "select waterpoint.waterpoint_id,waterpoint.baseline_date,waterpoint.baseline_pending,village.villagename,parish.parish_name,"
                + " subcounty.subcounty_name,county.county_name,district.`name`,waterpoint.date_installed from waterpoint"
                + " inner join village"
                + " on waterpoint.village_id = village.village_id inner join parish"
                + " on village.parish_id = parish.parish_id inner join subcounty on"
                + " parish.subcounty_id = subcounty.id inner join county on"
                + " subcounty.county_id = county.county_id inner join district on"
                + " county.district_id = district.id where district.name = '"+district+"'";
        System.out.println(query);
        SQLQuery createSQLQuery = getSession().createSQLQuery(query);
        List list = createSQLQuery.list();


        for (Object object : list) {
            Object[] strings = (Object[]) object;
            WaterPointSummary point = new WaterPointSummary();
            point.setWaterPointId(strings[0] + "");
            point.setBaselinePending(strings[2] + "");
            point.setVillageName(strings[3] + "");
            point.setParishName(strings[4] + "");
            point.setSubcountyName(strings[5] + "");
            point.setCountyName(strings[6] + "");
            point.setDistrict(strings[7] + "");
            //MM/dd/yyyy
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateInstalled = null;
            java.util.Date baselineDate = null;
            try {
                dateInstalled = df.parse(strings[8] + "");
                baselineDate = df.parse(strings[1] + "");
            } catch (Exception e) {
            }
            point.setDate(dateInstalled);
            point.setBaselineDate(baselineDate);
            summary.add(point);
        }


        return summary;
    }
}
