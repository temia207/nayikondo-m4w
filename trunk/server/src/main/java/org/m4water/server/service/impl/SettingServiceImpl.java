package org.m4water.server.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.m4water.server.admin.model.District;

import org.m4water.server.admin.model.Setting;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.admin.model.WaterUserCommittee;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.WaterpointTypes;
import org.m4water.server.dao.SettingDAO;
import org.m4water.server.dao.SettingGroupDAO;
import org.m4water.server.service.DistrictService;
import org.m4water.server.service.SettingService;
import org.m4water.server.service.WaterPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation for <code>Setting Service</code>.
 * 
 *
 */
@Service("settingService")
@Transactional
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingDAO settingDAO;
    @Autowired
    private SettingGroupDAO settingGroupDAO;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private WaterPointService waterPointService;
    private WaterpointTypes waterpointType;

    public void setSettingDAO(SettingDAO settingDAO) {
	this.settingDAO = settingDAO;
    }

    /* (non-Javadoc)
     * @see org.openxdata.server.service.SettingService#deleteSetting(org.openxdata.server.admin.model.Setting)
     */
    @Override
//    @Secured("Perm_Delete_Settings")
    public void deleteSetting(Setting setting) {
	settingDAO.deleteSetting(setting);
    }

    /* (non-Javadoc)
     * @see org.openxdata.server.service.SettingService#deleteSettingGroup(org.openxdata.server.admin.model.SettingGroup)
     */
    @Override
//    @Secured("Perm_Delete_SettingsGroup")
    public void deleteSettingGroup(SettingGroup settingGroup) {
	settingGroupDAO.deleteSettingGroup(settingGroup);
    }

    /* (non-Javadoc)
     * @see org.openxdata.server.service.SettingService#getSetting(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    // note: no security required to read settings
    public String getSetting(String name) {
	return settingDAO.getSetting(name);
    }

    /* (non-Javadoc)
     * @see org.openxdata.server.service.SettingService#getSettings()
     */
    @Override
    @Transactional(readOnly = true)
    // note: no security required to read settings
    public List<SettingGroup> getSettings() {
	return settingGroupDAO.getSettingGroups();
    }

    /* (non-Javadoc)
     * @see org.openxdata.server.service.SettingService#saveSetting(org.openxdata.server.admin.model.Setting)
     */
    @Override
//    @Secured("Perm_Add_Settings")
    public void saveSetting(Setting setting) {
	settingDAO.saveSetting(setting);
    }

    /* (non-Javadoc)
     * @see org.openxdata.server.service.SettingService#saveSettingGroup(org.openxdata.server.admin.model.SettingGroup)
     */
    @Override
//    @Secured("Perm_Add_SettingsGroup")
    public void saveSettingGroup(SettingGroup settingGroup) {
	settingGroupDAO.saveSettingGroup(settingGroup);
    }

    @Override
    @Transactional(readOnly = true)
    // note: no security required to read settings
    public String getSetting(String name, String defaultValue) {
	return settingDAO.getSetting(name, defaultValue);
    }

    @Override
    @Transactional(readOnly = true)
    // note: no security required to read settings
    public SettingGroup getSettingGroup(String name) {
	return settingGroupDAO.getSettingGroup(name);
    }

    @Override
    public void exportSettingGroupToWaterPoint(SettingGroup group) {
	Waterpoint waterPoint = new Waterpoint();
	waterPoint.setId(group.getName());
	List<Setting> settings = group.getSettings();
	District district = districtService.getDistrictByName((SettingGroup.getSetting(settings, "district")).getValue());
	Subcounty subcounty = district.getSubcouty((SettingGroup.getSetting(settings, "subcounty")).getValue());
	for (Setting setting : settings) {
	    if (setting.getName().equalsIgnoreCase("id")) {
		waterPoint.setWaterpointId(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("village")) {
		waterPoint.setVillage(subcounty.getVillage((SettingGroup.getSetting(settings, "village")).getValue()));
	    } else if (setting.getName().equalsIgnoreCase("waterpointType")) {
		waterPoint.setWaterpointTypes(waterPointService.getWaterPointType(setting.getValue()));
	    } else if (setting.getName().equalsIgnoreCase("waterpointname")) {
		waterPoint.setName(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("dateInstalled")) {
		waterPoint.setDateInstalled(parseStringToDate(setting.getValue()));
	    } else if (setting.getName().equalsIgnoreCase("fundingSource")) {
		waterPoint.setFundingSource(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("ownership")) {
		waterPoint.setOwnership(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("nohousehold")) {
		waterPoint.setHouseholds(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("typeOfManagement")) {
		waterPoint.setTypeOfMagt(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("eastings")) {
		waterPoint.setEastings(setting.getValue());
	    } else if (setting.getName().equalsIgnoreCase("norhtings")) {
		waterPoint.setNorthings(setting.getValue());
	    }
	}
	//needs more work here
	waterPoint.setBaselinePending("F");
	waterPoint.setBaselineDate(new Date());
	
	List<SettingGroup> groups = group.getGroups();
	for (SettingGroup x : groups) {
	    if (x.getName().equalsIgnoreCase("functionality")) {
		exportFromSettingToFunctionality(waterPoint, x);
	    } else if (x.getName().equalsIgnoreCase("waterusercommittee")) {
		exportFromSettingToWUC(waterPoint, x);
	    }
	}
	waterPointService.saveWaterPoint(waterPoint);
	settingGroupDAO.deleteSettingGroup(group);
    }
    private void exportFromSettingToFunctionality(Waterpoint waterPoint,SettingGroup group){
	List<Setting> functionality = group.getSettings();
	WaterFunctionality funx = new WaterFunctionality();
	funx.setDate(parseStringToDate(SettingGroup.getSetting(functionality, "date").getValue()));
        funx.setWaterpoint(waterPoint);
        funx.setFunctionalityStatus(SettingGroup.getSetting(functionality, "functionalityStatus").getValue());
        funx.setDayNonFunctional(parseStringToDate(SettingGroup.getSetting(functionality,
		"dayNonFunctional").getValue()));
	funx.setReason(SettingGroup.getSetting(functionality, "reasonNonFunctional").getValue());
	funx.setDateLastRepaired(parseStringToDate(SettingGroup.getSetting(functionality,
		"dateLastRepair").getValue()));
	funx.setDetailsLastRepair(SettingGroup.getSetting(functionality, "detailsLastRepair").getValue());
	funx.setDateLastMinorService(parseStringToDate(SettingGroup.getSetting(functionality,
		"dateLastMinorService").getValue()));
	funx.setDateLastMajorService(parseStringToDate(SettingGroup.getSetting(functionality,
		"dateLastMajorService").getValue()));
	waterPoint.getWaterFunctionalities().add(funx);
    }
     private void exportFromSettingToWUC(Waterpoint waterPoint,SettingGroup group){
	List<Setting> functionality = group.getSettings();
	 WaterUserCommittee wuc = new WaterUserCommittee();
	 wuc.setId(java.util.UUID.randomUUID().toString());
	 wuc.setWaterpoint(waterPoint);
	 wuc.setCommissioned(SettingGroup.getSetting(functionality, "commissioned").getValue());
	 wuc.setCollectFees(SettingGroup.getSetting(functionality, "collectFees").getValue());
	 wuc.setNoActiveMembers(SettingGroup.getSetting(functionality, "noActiveMembers").getValue());
	 wuc.setNoOfWomen(SettingGroup.getSetting(functionality, "numberOfWomen").getValue());
	 wuc.setRegularService(SettingGroup.getSetting(functionality, "regualarService").getValue());
	 wuc.setRegularMeeting(SettingGroup.getSetting(functionality, "regularMeeting").getValue());
	 wuc.setTrained(SettingGroup.getSetting(functionality, "trained").getValue());
	 wuc.setNoOfWomenKeypos(SettingGroup.getSetting(functionality, "womenKeyPosition").getValue());
	 wuc.setYrEstablished(SettingGroup.getSetting(functionality, "yearEstablished").getValue());
         waterPoint.getWaterUserCommittees().add(wuc);
    }
    private Date parseStringToDate(String date){
	try {
	    //MM/dd/yyyy
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    return df.parse(date);
	} catch (ParseException ex) {
	    return new Date(1);
	}
    }
}
