package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class UserSummary extends BaseModel {

    private static final long serialVersionUID = -483245623678099579L;

    public UserSummary() {
    }

    public UserSummary(String id, String userName) {
        setId(id);
        setName(userName);
    }

    public void setId(String id) {
        set("id", id);
    }

    public String getId() {
        return get("id");
    }

    public void setName(String userName) {
        set("name", userName);
    }

    public String getName() {
        return get("name");
    }

    public static List<UserSummary> getSampleUsers() {
        List<UserSummary> summary = new ArrayList<UserSummary>();
        summary.add(new UserSummary("000234", "Peter Wakholi"));
        summary.add(new UserSummary("000224", "Fion Ssozi"));
        summary.add(new UserSummary("000142", "Kakama Victor"));
        summary.add(new UserSummary("000153", "Kayindo Ronald"));
        summary.add(new UserSummary("000167", "Katwere James"));
        return summary;
    }
}
