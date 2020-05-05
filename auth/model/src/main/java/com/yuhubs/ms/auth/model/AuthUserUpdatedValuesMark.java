package com.yuhubs.ms.auth.model;

public class AuthUserUpdatedValuesMark {

	protected static final int PERMISSIONS_UPDATED = 0x01;
	protected static final int PASSWORD_UPDATED = 0x02;
	protected static final int ACCOUNT_STATUS_UPDATED = 0x04;

	protected static final int USER_GROUPS_UPDATED = 0x10;


	protected int marks;


	public final boolean hasUpdated() {
		return (this.marks != 0);
	}

	public final boolean isPermissionsUpdated() {
		return (this.marks & PERMISSIONS_UPDATED) == PERMISSIONS_UPDATED;
	}

	public final boolean isPasswordUpdated() {
		return (this.marks & PASSWORD_UPDATED) == PASSWORD_UPDATED;
	}

	public final boolean isAccountStatusUpdated() {
		return (this.marks & ACCOUNT_STATUS_UPDATED) == ACCOUNT_STATUS_UPDATED;
	}

	public final boolean isUserGroupsUpdated() {
		return (this.marks & USER_GROUPS_UPDATED) == USER_GROUPS_UPDATED;
	}


	public final AuthUserUpdatedValuesMark reset() {
		this.marks = 0;
		return this;
	}


	public final AuthUserUpdatedValuesMark setPermissionsUpdated() {
		this.marks |= PERMISSIONS_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark unsetPermissionsUpdated() {
		this.marks &= ~PERMISSIONS_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark setPasswordUpdated() {
		this.marks |= PASSWORD_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark unsetPasswordUpdated() {
		this.marks &= ~PASSWORD_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark setAccountStatusUpdated() {
		this.marks |= ACCOUNT_STATUS_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark unsetAccountStatusUpdated() {
		this.marks &= ~ACCOUNT_STATUS_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark setUserGroupsUpdated() {
		this.marks |= USER_GROUPS_UPDATED;
		return this;
	}

	public final AuthUserUpdatedValuesMark unsetUserGroupsUpdated() {
		this.marks &= ~USER_GROUPS_UPDATED;
		return this;
	}

}
