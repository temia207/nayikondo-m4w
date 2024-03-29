/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Event that is triggered by a Refresh and indicates
 * what type of refresh should take place.
 *
 * Also allows data to be passed along to the View
 * consuming the event.
 *
 * @author kakavi
 */
public class RefreshableEvent implements Serializable {

    private static final long serialVersionUID = 7549150917841490121L;

    public static enum Type {

        WATERPOINT_CHANGES, NAME_CHANGE, RELOAD_WATERPOINTS,NEW_WATER_POINTS,ALL_WATER_POINTS, DELETE,TICKET_UPDATE,RELOAD,
		RESPONSE_TIME,DISTRICT_SUMMARIES,WUC_MANAGEMENT,BASELINE_PENDING,BASELINE_NOT_DONE,BASELINE_COMPLETE
    };
    private Type eventType;
    private Map<String, Object> data = new HashMap<String, Object>();

    public RefreshableEvent(Type eventType) {
        this.eventType = eventType;
    }

    public RefreshableEvent(Type eventType, Object data) {
        this(eventType);
        this.data.put("data", data);
    }

    @SuppressWarnings("unchecked")
    public <X> X getData() {
        return (X) data.get("data");
    }

    @SuppressWarnings("unchecked")
    public <X> X getData(String key) {
        return (X) data.get(key);
    }

    public void setData(Object data) {
        this.data.put("data", data);
    }

    public void setData(String key, Object data) {
        this.data.put(key, data);
    }

    public Type getEventType() {
        return eventType;
    }
}
