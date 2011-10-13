package org.m4water.server.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;

/**
 * Extends the Spring Security User in order 
 * to add OpenXData specific user details (such as the <tt>User</tt> specific salt).
 * 
 * An immutable object (i.e. no setters)
 *
 * @author dagmar@cell-life.org.za
 */
public class M4waterUserDetails extends User {
    
    private static final long serialVersionUID = 2323963197126198664L;

    private org.m4water.server.admin.model.User m4waterUser;

    public M4waterUserDetails(org.m4water.server.admin.model.User oxdUser,
            boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            GrantedAuthority[] authorities) throws IllegalArgumentException {
    	
        super(oxdUser.getUserId(), oxdUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.m4waterUser = oxdUser;
    }

    public org.m4water.server.admin.model.User getM4WaterUser() {
        return m4waterUser;
    }



    
    /**
     * <tt>Overrides super.equals.</tt>
     * <p>
     * We do not equality on instances of <tt>super class</tt> to ignore the identity of the <tt>subclass and the added fields.</tt>
     * <p>Object</tt> we checking for equality.
     * 
     * @param arg <tt>Ob
     * @return the result of invoking super.equals(arg).
     */
    @Override
	public boolean equals(Object arg){
    	return super.equals(arg);
    }
}
