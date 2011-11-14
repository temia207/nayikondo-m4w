package org.cwf.server;

import java.util.List;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.cwf.client.service.SettingService;
import org.m4water.server.admin.model.Setting;
import org.m4water.server.admin.model.SettingGroup;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class SettingServiceImpl extends M4waterPersistentRemoteService implements SettingService {

    private org.m4water.server.service.SettingService settingService;

    @Override
    public String getSetting(String name) {
        return getSettingService().getSetting(name);
    }

    @Override
    public List<SettingGroup> getSettings() {
        return getSettingService().getSettings();
    }

    @Override
    public void saveSetting(Setting setting) {
        getSettingService().saveSetting(setting);
    }

    @Override
    public void saveSettingGroup(SettingGroup settingGroup) {
        getSettingService().saveSettingGroup(settingGroup);
    }

    @Override
    public void deleteSetting(Setting setting) {
        getSettingService().deleteSetting(setting);
    }

    @Override
    public void deleteSettingGroup(SettingGroup settingGroup) {
        getSettingService().deleteSettingGroup(settingGroup);
    }

    @Override
    public String getSetting(String settingName, String defaultValue) {
        return getSettingService().getSetting(settingName, defaultValue);
    }

    @Override
    public SettingGroup getSettingGroup(String name) {
        return getSettingService().getSettingGroup(name);
    }

    public org.m4water.server.service.SettingService getSettingService() {
        if (settingService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            settingService  = (org.m4water.server.service.SettingService) ctx.getBean("settingService");
        }
        return settingService ;
    }
}
