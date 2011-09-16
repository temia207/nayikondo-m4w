
package org.m4water.server.dao.hibernate;

import org.m4water.server.admin.model.Smsmessagelog;
import org.m4water.server.dao.SmsMessageLogDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kay
 */
@Repository("smsMessageLogDao")
public class SmsMessageLogDAOImpl extends BaseDAOImpl<Smsmessagelog, String> implements SmsMessageLogDao{
}
