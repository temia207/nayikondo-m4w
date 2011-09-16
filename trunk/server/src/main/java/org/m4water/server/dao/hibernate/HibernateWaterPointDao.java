/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.WaterPointDao;

import org.springframework.stereotype.Repository;
/**
 *
 * @author victor
 */
@Repository("Water")
public class HibernateWaterPointDao extends BaseDAOImpl<Waterpoint, String> implements WaterPointDao{

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
        //these should be picked from settings file
        Properties dbProps = new Properties();
        String driver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/m4water";

        dbProps.setProperty("user", "root");
        dbProps.setProperty("password", "test");
        List<WaterPointSummary> summary = new ArrayList<WaterPointSummary>();

        try {
            Class.forName(driver).newInstance();
            Connection con = DriverManager.getConnection(dbUrl, dbProps);
            Statement stmt = con.createStatement();
            String query = "select waterpoint.waterpoint_id,village.villagename,parish.parish_name,"
                    + " subcounty.subcounty_name,county.county_name,district.`name`,waterpoint.date_installed from waterpoint"
                    + " inner join village"
                    + " on waterpoint.village_id = village.village_id inner join parish"
                    + " on village.parish_id = parish.parish_id inner join subcounty on"
                    + " parish.subcounty_id = subcounty.subcounty_id inner join county on"
                    + " subcounty.county_id = county.county_id inner join district on"
                    + " county.district_id = district.district_id where district.district_id = '405'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                WaterPointSummary point = new WaterPointSummary();
                point.setWaterPointId(rs.getString("waterpoint_id"));
                point.setVillageName(rs.getString("villagename"));
                point.setParishName(rs.getString("parish_name"));
                point.setSubcountyName(rs.getString("subcounty_name"));
                point.setCountyName(rs.getString("county_name"));
                point.setDistrict(rs.getString("name"));
                point.setDate(rs.getDate("date_installed"));
                summary.add(point);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return summary;
    }
}
