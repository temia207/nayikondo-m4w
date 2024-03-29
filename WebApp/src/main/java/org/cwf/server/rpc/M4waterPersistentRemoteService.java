package org.cwf.server.rpc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.SessionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Base class for all remote service implementations to set the PersistentBeanManager
 * as required by Gilead.
 * Victor
 */
public class M4waterPersistentRemoteService extends PersistentRemoteService{

	private static final long serialVersionUID = -3187795188510247069L;

	public M4waterPersistentRemoteService(){}
	
	/**
	 * Servlet initialisation
	 */
	@Override
	public void init() throws ServletException
	{
		super.init();
		
		WebApplicationContext ctx = getApplicationContext();
	    
		HibernateUtil hibernateUtil = new HibernateUtil();    
	    hibernateUtil.setSessionFactory((SessionFactory)ctx.getBean("sessionFactory"));
		PersistentBeanManager persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(hibernateUtil);
		setBeanManager(persistentBeanManager);
        getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
	}
	
	protected WebApplicationContext getApplicationContext() {
		ServletContext sctx = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
		return ctx;
	}
}
