package org.m4water.server.dao;

import java.util.List;

import org.m4water.server.admin.model.Locale;

/**
 *
 */
public interface LocaleDAO extends BaseDAO<Locale> {
	
	/**
	 * Gets a list of locales.
	 * 
	 * @return the locale list.
	 */	
	List<Locale> getLocales();
	
	/**
	 * Deletes a locale from the database.
	 * 
	 * @param locale the locale to delete.
	 */
	void deleteLocale(Locale locale);
	
	/**
	 * Saves a list of locales.
	 * 
	 * @param locales the locale list.
	 */
	void saveLocale(List<Locale> locales);

}
