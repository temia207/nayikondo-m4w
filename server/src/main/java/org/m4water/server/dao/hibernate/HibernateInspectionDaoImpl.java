/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import org.m4water.server.admin.model.Inspection;
import org.m4water.server.dao.InspectionDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("inspection")
public class HibernateInspectionDaoImpl extends BaseDAOImpl<Inspection, Long> implements InspectionDao{

    @Override
    public List<Inspection> getInspections() {
        return findAll();
    }

    @Override
    public Inspection getInspection(String id) {
        return searchUniqueByPropertyEqual("id", id);
    }

    @Override
    public void saveInspection(Inspection inspection) {
        save(inspection);
    }

}
