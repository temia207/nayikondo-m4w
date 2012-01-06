/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 *
 * @author victor
 */
public class District  extends BaseModel {

    public District() {
    }

    public District(String name) {
        setName(name);
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName() {
        return get("name");
    }

}
