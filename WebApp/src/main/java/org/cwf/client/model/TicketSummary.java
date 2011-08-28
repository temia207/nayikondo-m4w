package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class TicketSummary extends BaseModel {

    public TicketSummary(String date, String id, String district, String subCounty, String village) {
        setDate(date);
        setId(id);
        setDistrict(district);
        setSubCounty(subCounty);
        setVillage(village);
    }
    public TicketSummary(String id, String district, String subCounty, String village) {
        setId(id);
        setDistrict(district);
        setSubCounty(subCounty);
        setVillage(village);
    }
    public void setDate(String date) {
        set("date", date);
    }

    public void setId(String id) {
        set("id", id);
    }

    public void setDistrict(String district) {
        set("district", district);
    }

    public void setSubCounty(String subCounty) {
        set("subcounty", subCounty);
    }

    public void setVillage(String village) {
        set("village", village);
    }

    public void setLongitude(String longitude) {
        set("longitude", longitude);
    }

    public void setLatitude(String latitude) {
        set("latitude", latitude);
    }

    public String getDate() {
        return get("date");
    }

    public String getId() {
        return get("id");
    }

    public String getDistrict() {
        return get("district");
    }

    public String getSubCounty() {
        return get("subcounty");
    }

    public String getVillage() {
        return get("village");
    }

    public static List<TicketSummary> getOpenTickets() {
        List<TicketSummary> ticketDetails = new ArrayList<TicketSummary>();
        ticketDetails.add(new TicketSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga"));
        return ticketDetails;
    }

    public static List<TicketSummary> getClosedTickets() {
        List<TicketSummary> ticketDetails = new ArrayList<TicketSummary>();
        ticketDetails.add(new TicketSummary("19/5/2011", "UMAS01236", "Masaka", "Kigasa", "Buddi"));
        ticketDetails.add(new TicketSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga"));
        return ticketDetails;
    }

    public static List<TicketSummary> getSuspendedTickets() {
        List<TicketSummary> ticketDetails = new ArrayList<TicketSummary>();
        ticketDetails.add(new TicketSummary("19/5/2011", "UMAS01236", "Masaka", "Kigasa", "Buddi"));
        return ticketDetails;
    }
}
