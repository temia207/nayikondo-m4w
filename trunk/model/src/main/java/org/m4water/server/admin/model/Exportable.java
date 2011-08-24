package org.m4water.server.admin.model;

/**
 * Wraps around an Editable that can be exported.
 *
 */
public interface Exportable {
	
	int getId();
	
	String getType();
	
	String getName();
}
