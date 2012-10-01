package org.m4water.server.security.util;

/**
 *
 * @author kay
 */
public class M4wUtil {

	public static boolean validWaterPointID(String sourceId) {
		return sourceId != null
			&& sourceId.matches("[0-9]{3}[a-zA-Z]{2}[0-9]{3}");
	}
}
