package org.cwf.client;

import org.cwf.client.controllers.LoginController;
import org.m4water.server.admin.model.exception.M4waterSecurityException;
import org.m4water.server.admin.model.exception.M4waterSessionExpiredException;


import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Emit application AsynCallback that automatically handles errors that occur
 * 
 * @author dagmar@cell-life.org.za
 * @param <T>
 */
public abstract class M4waterAsyncCallback<T> implements AsyncCallback<T> {
    
    AppMessages appMessages = GWT.create(AppMessages.class);

    /**
     * Implements some auto error handling for SpringSecurityException and general errors.
     */
    @Override
	final public void onFailure(Throwable throwable) {
        GWT.log("Error caught while performing an action on the server: "+throwable.getMessage(), throwable);
        if (throwable instanceof M4waterSessionExpiredException) {
            // allow the user to login again (show a login popup so they can continue where they left off)
            Dispatcher.get().dispatch(LoginController.SESSIONTIMEOUT);
        } else if (throwable instanceof M4waterSecurityException) {
            // access denied
            MessageBox.alert(appMessages.error(), appMessages.accessDeniedError(), null);
        } else {
            // all other errors
        	MessageBox.alert(appMessages.error(), appMessages.pleaseTryAgainLater(throwable.getMessage()), null);
        }
        onFailurePostProcessing(throwable);
    }
    
    /**
     * Method to override if there is some custom post exception handling work to be done.
     * Called after client has been notified of errors.
     * @param throwable
     */
    public void onFailurePostProcessing(Throwable throwable) {
    	
    }
}