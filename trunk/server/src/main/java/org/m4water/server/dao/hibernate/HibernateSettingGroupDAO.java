package org.m4water.server.dao.hibernate;

import java.util.List;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.dao.SettingGroupDAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository("settingGroupDAO")
public class HibernateSettingGroupDAO extends BaseDAOImpl<SettingGroup,String> implements SettingGroupDAO{

	@Override
	public void deleteSettingGroup(SettingGroup settingGroup) {
		SettingGroup parent = settingGroup.getParentSettingGroup();
		parent.removeSettingGroup(settingGroup);
		save(parent);
//		remove(settingGroup);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SettingGroup> getSettingGroups() {
		return findAll();
	}

	@Override
	public void saveSettingGroup(SettingGroup settingGroup) {
		save(settingGroup);		
	}

	@Override
//	@Transactional(readOnly=true)
	public SettingGroup getSettingGroup(String name) {
		return searchUniqueByPropertyEqual("name", name);
	}
}
