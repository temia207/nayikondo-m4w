
package org.m4water.server.dao.hibernate;

import org.hibernate.Query;
import org.m4water.server.admin.model.Smsmessagelog;
import org.m4water.server.dao.SmsMessageLogDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kay
 */
@Repository("smsMessageLogDao")
public class SmsMessageLogDAOImpl extends BaseDAOImpl<Smsmessagelog, String> implements SmsMessageLogDao{
    @Override
    public long getHighestMsgId() {

      Query query = getSession().createQuery("select max(textmeId) from Smsmessagelog") ;

        Long highestId = (Long) query.uniqueResult();

        return highestId;
    }
}
