package org.m4water.server.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.m4water.server.admin.model.County;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Parish;
import org.m4water.server.admin.model.Setting;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.service.SettingService;
import org.m4water.server.service.WaterPointService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet that handles export of items.
 */
public class ItemExportServlet extends HttpServlet {

	private County county;
	private District district;
	private Parish parish;
	private Subcounty subcounty;
	private Village village;
	private WaterPointService waterpointService;
	private SettingService settingsService;
	private static int NEW_WATER_POINTS = 0;
	private static int ALL_WATERPOINTS = 1;
	private static int BASELINE_PENDING = 2;
	private static int BASELINE_DONE = 3;
	private static int BASELINE_NOT_DONE = 4;

	@Override
	public void init() throws ServletException {
		ServletContext sctx = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
		waterpointService = (WaterPointService) ctx.getBean("waterpointService");
		settingsService = (SettingService) ctx.getBean("settingService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println("================================ " + type);
		String filename = request.getParameter("filename");
		int length = 0;
		ServletOutputStream op = response.getOutputStream();

		filename = filename.trim();

		File file = null;
		try {
			if (type == NEW_WATER_POINTS) {
				file = exportNewWaterpointsToexcell();
			} else if (type == BASELINE_NOT_DONE) {
				file = exportWaterpointsToexcell(BASELINE_NOT_DONE);
			} else if (type == BASELINE_PENDING) {
				file = exportWaterpointsToexcell(BASELINE_PENDING);
			} else if (type == BASELINE_DONE) {
				file = exportWaterpointsToexcell(BASELINE_DONE);
			}else if (type == ALL_WATERPOINTS) {
				file = exportWaterpointsToexcell(ALL_WATERPOINTS);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.setContentType("application/octet-stream");
		response.setContentLength((int) file.length());
		response.setDateHeader("Expires", -1);
		response.setHeader("Content-Disposition", "attachment; filename=" + file);

		byte[] bbuf = new byte[200];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		while ((in != null) && ((length = in.read(bbuf)) != -1)) {
			op.write(bbuf, 0, length);
		}
		in.close();
		op.flush();
		op.close();
	}

	private File exportWaterpointsToexcell(int type) throws Exception {
		List<Waterpoint> waterpoints = null;
		File file = null;
		if (type == BASELINE_PENDING) {
			waterpoints = waterpointService.getWaterpoints("T");
			file = new File("baseline_pending.xls");
		} else {
			//these can either be baseline done or not done
			waterpoints = waterpointService.getWaterpoints("F");
			file = new File("baseline_done.xls");
		}
		FileWriter out = new FileWriter(file);
		String[] headers = {"Date", "ID", "Waterpoint Name", "Village", "Parish", "Subcounty", "County", "District", "Baseline Status"};

		for (int i = 0; i < headers.length; i++) {
			out.write(headers[i] + "\t");
		}

		out.write("\n");
		for (int i = 0; i < waterpoints.size(); ++i) {
			village = waterpoints.get(i).getVillage();
			parish = village.getParish();
			subcounty = parish.getSubcounty();
			county = subcounty.getCounty();
			district = county.getDistrict();
			out.write(waterpoints.get(i).getDateInstalled().toString() + "\t");
			out.write(waterpoints.get(i).getWaterpointId() + "\t");
			out.write(waterpoints.get(i).getName() + "\t");
			out.write(village.getVillagename() + "\t");
			out.write(parish.getParishName() + "\t");
			out.write(subcounty.getSubcountyName() + "\t");
			out.write(county.getCountyName() + "\t");
			out.write(district.getName() + "\t");
			out.write(waterpoints.get(i).getBaselinePending());
			out.write("\n");
		}
		out.close();
		System.out.println("write out to: " + file);
		return file;
	}

	private File exportNewWaterpointsToexcell() throws Exception {
		List<WaterPointSummary> waterpoints = convertGroupToWaterPoint();
		File file = new File("new_waterpoint.xls");
		FileWriter out = new FileWriter(file);
		String[] headers = {"Date", "ID", "Village", "Parish", "Subcounty", "County", "District"};

		for (int i = 0; i < headers.length; i++) {
			out.write(headers[i] + "\t");
		}

		out.write("\n");
		for (int i = 0; i < waterpoints.size(); ++i) {
			out.write(waterpoints.get(i).getDate().toString() + "\t");
			out.write(waterpoints.get(i).getWaterPointId() + "\t");
			out.write(waterpoints.get(i).getVillageName() + "\t");
			out.write(waterpoints.get(i).getParishName() + "\t");
			out.write(waterpoints.get(i).getSubcountyName() + "\t");
			out.write(waterpoints.get(i).getCountyName() + "\t");
			out.write(waterpoints.get(i).getDistrict() + "\t");
			out.write("\n");
		}
		out.close();
		System.out.println("write out to: " + file);
		return file;
	}

	private List<WaterPointSummary> convertGroupToWaterPoint() {
		List<WaterPointSummary> newWaterPoints = new ArrayList<WaterPointSummary>();
		SettingGroup group = settingsService.getInitializedSettingGroup("waterpoints");
		List<SettingGroup> newWaterPointGroup = group.getGroups();
		for (SettingGroup waterPoint : newWaterPointGroup) {
			newWaterPoints.add(getModelFromGroup(waterPoint));
		}
		return newWaterPoints;
	}

	private WaterPointSummary getModelFromGroup(SettingGroup group) {
		WaterPointSummary waterpoint = new WaterPointSummary();
		List<Setting> settings = group.getSettings();
		for (Setting setting : settings) {
			if (setting.getName().equalsIgnoreCase("waterpointname")) {
				waterpoint.setWaterPointId(setting.getValue());
			} else if (setting.getName().equalsIgnoreCase("village")) {
				waterpoint.setVillageName(setting.getValue());
			} else if (setting.getName().equalsIgnoreCase("parish")) {
				waterpoint.setParishName(setting.getValue());
			} else if (setting.getName().equalsIgnoreCase("subcounty")) {
				waterpoint.setSubcountyName(setting.getValue());
			} else if (setting.getName().equalsIgnoreCase("county")) {
				waterpoint.setCountyName(setting.getValue());
			} else if (setting.getName().equalsIgnoreCase("district")) {
				waterpoint.setDistrict(setting.getValue());
			} //			else if (setting.getName().equalsIgnoreCase("waterpointType")) {
			//				waterpoint.setWaterpointTypes(setting.getValue());
			//			}
			else if (setting.getName().equalsIgnoreCase("waterpointname")) {
				waterpoint.setWaterpointName(setting.getValue());
			} else if (setting.getName().equalsIgnoreCase("dateInstalled")) {
				DateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss z YYYY");
				try {
					Date date = format.parse(setting.getValue());
					//new Date("");
					waterpoint.setDate(date);
				} catch (ParseException parseException) {
					System.out.println("Cannot parse new waterpoint data:["+setting.getValue()+"]: "+parseException.getMessage());
					waterpoint.setDate(new Date());
				}
				
			}
		}
		return waterpoint;
	}
}
