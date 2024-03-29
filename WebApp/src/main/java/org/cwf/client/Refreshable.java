/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client;

/**
 * Refreshable interface to implement programatic refreshing
 * (when an action has been performed by the user
 * another part of the UI in another part must refresh)
 *
 * For example -  if a user captures some data, the number of
 * responses in the form list view must be updated.
 *
 * Views that can be refreshed must implement this interface
 * and check if they can handle the event
 *
 * @author kakavi
 */
public interface Refreshable {

    void refresh(RefreshableEvent event);
}
