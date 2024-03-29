package org.m4water.server.admin.model;

import java.io.Serializable;
import java.util.Date;

import org.m4water.server.admin.model.state.EditableState;

/**
 * This interface is implemented by objects which can be edited.
 */
public interface Editable <T extends Serializable> extends Serializable{
	
	/**
	 * Checks if the object is new. New objects do not exist in the database.
	 * 
	 * @return true if the object is new, else false.
	 */
//	public boolean isNew();
	
	/**
	 * Checks if the object has been changed and the changes have not yet been 
	 * persisted to the database.
	 * 
	 * @return true the object has unsaved changes, else false.
	 */
	public boolean isDirty();
	
	/**
	 * Flags the object as having been changed and which changes have not yet been
	 * persisted to the database.
	 * 
	 * @param dirty
	 */
	public void setDirty(boolean dirty);
	
	/**
	 * Sets the user who changed the object.
	 * 
	 * @param changedBy the user.
	 */
	public void setChangedBy(User changedBy);
	
	/** 
	 * Sets the date when the object was changed.
	 * 
	 * @param dateChanged the date.
	 */
	public void setDateChanged(Date dateChanged);
	
	/**
	 * Checks if the object has validation errors.
	 * 
	 * @return true if the object has, else false.
	 */
	public boolean hasErrors();
	
	/**
	 * Sets that the object has validation errors and hence should 
	 * not be persisted to the database.
	 * 
	 * @param hasErrors set this to true if the object has errors, else false.
	 */
	public void setHasErrors(boolean hasErrors);
	
	public void setId(T id);
	
	/**
	 * Returns the id of this editable item. This method should be prefered over the hard code Objected id like getFooId().
	 * @return the object unique id.
	 */
	public T getId();
	
	/**
	 * Sets the current state of the object.
	 * @param state the object state.
	 */
	public void setState(EditableState state);
	
	/**
	 * Returns the objects state to aid in state transition.
	 * 
	 * @return the current object state.
	 */
	public EditableState getState();
	
	
}
