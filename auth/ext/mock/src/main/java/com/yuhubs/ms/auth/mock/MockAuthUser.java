package com.yuhubs.ms.auth.mock;

import com.yuhubs.ms.auth.model.AuthUserGeneralValue;
import com.yuhubs.ms.auth.model.GenericAuthUser;

public class MockAuthUser extends GenericAuthUser {

	public MockAuthUser(Long id, String permissions) {
		super(new AuthUserGeneralValue());

		this.generalValue.setId(id);
		this.generalValue.setPermissions(permissions);
	}

}
