package org.m4water.server.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;
import org.m4water.server.admin.model.Setting;
import org.m4water.server.dao.SettingDAO;

/**
 * Provides a hibernate implementation
 * of the <code>SettingDAO</code> data access <code> interface.</code>
 * 
 *
 */
@Repository("settingDAO")
public class HibernateSettingDAO extends BaseDAOImpl<Setting,String> implements SettingDAO {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	@Override
	public void deleteSetting(Setting setting) {
		remove(setting);
	}

	@Override
	public List<Setting> getSettings() {		
		return findAll();
	}

	@Override
	public void saveSetting(Setting setting){
		save(setting);
	}
	
	@Override
	public String getSetting(String name){
		Setting setting = searchUnique(new Search().addFilterEqual("name", name));
		if(setting != null)
			return setting.getValue();
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see org.openxdata.server.dao.SettingDAO#getSetting(java.lang.String, java.lang.String)
	 */
	@Override
	public String getSetting(String name, String defaultValue) {
		try{
			String value = getSetting(name);
			if(value == null)
				return defaultValue;
			
			return value;
		}
		catch(Exception ex){
			return defaultValue;
		}
	}
}
