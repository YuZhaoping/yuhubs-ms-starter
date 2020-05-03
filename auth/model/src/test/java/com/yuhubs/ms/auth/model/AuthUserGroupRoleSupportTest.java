package com.yuhubs.ms.auth.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthUserGroupRoleSupportTest {

	private AuthUserGroupRoleSupport groupRoleSupport;


	@Before
	public void setUp() {
		this.groupRoleSupport = new AuthUserGroupRoleSupport();
	}


	@Test
	public void testGetRolesByGroups() {
		assertTrue(StringUtils.isEmpty(groupRoleSupport.getRolesByGroups("NOT-EXISTS")));
		assertEquals("USER", groupRoleSupport.getRolesByGroups("USER"));
		assertEquals("ADMIN,USER", groupRoleSupport.getRolesByGroups("ADMIN, USER"));
	}

	@Test
	public void testGetPermissionsByGroups() {
		assertTrue(StringUtils.isEmpty(groupRoleSupport.getPermissionsByGroups("NOT-EXISTS")));
		assertEquals("USE-*", groupRoleSupport.getPermissionsByGroups("USER"));
		assertEquals("ADMIN-USER,USE-*", groupRoleSupport.getPermissionsByGroups("ADMIN, USER"));
	}

	@Test
	public void testGetPermissionsByRoles() {
		assertTrue(StringUtils.isEmpty(groupRoleSupport.getPermissionsByRoles("NOT-EXISTS")));
		assertEquals("USE-*", groupRoleSupport.getPermissionsByRoles("USER"));
		assertEquals("ADMIN-USER,USE-*", groupRoleSupport.getPermissionsByRoles("ADMIN, USER"));
	}

}
