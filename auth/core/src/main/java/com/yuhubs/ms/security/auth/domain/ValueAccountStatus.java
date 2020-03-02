package com.yuhubs.ms.security.auth.domain;

import com.yuhubs.ms.security.auth.AccountStatus;

public class ValueAccountStatus extends AccountStatus {

	public static final int ACCOUNT_EXPIRED = 0x01;
	public static final int ACCOUNT_LOCKED = 0x02;
	public static final int CREDENTIALS_EXPIRED = 0x04;
	public static final int ACCOUNT_DISABLED = 0x08;

	public static final int EMAIL_NOT_VERIFIED = 0x10;


	protected int value;


	public ValueAccountStatus() {
		this.value = 0;
	}

	public ValueAccountStatus(int value) {
		this.value = value;
	}


	@Override
	public boolean isAccountNonExpired() {
		return (value & ACCOUNT_EXPIRED) == 0x00;
	}

	@Override
	public boolean isAccountNonLocked() {
		return (value & ACCOUNT_LOCKED) == 0x00;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return (value & CREDENTIALS_EXPIRED) == 0x00;
	}

	@Override
	public boolean isEnabled() {
		return value == 0;
	}

	@Override
	public boolean isEmailVerified() {
		return (value & EMAIL_NOT_VERIFIED) == 0x00;
	}


	public ValueAccountStatus reset() {
		this.value = 0;
		return this;
	}

	public ValueAccountStatus setAccountNonExpired() {
		this.value &= ~ACCOUNT_EXPIRED;
		return this;
	}

	public ValueAccountStatus setAccountExpired() {
		this.value |= ACCOUNT_EXPIRED;
		return this;
	}

	public ValueAccountStatus setAccountNonLocked() {
		this.value &= ~ACCOUNT_LOCKED;
		return this;
	}

	public ValueAccountStatus setAccountLocked() {
		this.value |= ACCOUNT_LOCKED;
		return this;
	}

	public ValueAccountStatus setCredentialsNonExpired() {
		this.value &= ~CREDENTIALS_EXPIRED;
		return this;
	}

	public ValueAccountStatus setCredentialsExpired() {
		this.value |= CREDENTIALS_EXPIRED;
		return this;
	}

	public ValueAccountStatus setAccountEnabled() {
		this.value = 0;
		return this;
	}

	public ValueAccountStatus setAccountDisabled() {
		this.value |= ACCOUNT_DISABLED;
		return this;
	}

	public ValueAccountStatus setEmailVerified() {
		this.value &= ~EMAIL_NOT_VERIFIED;
		return this;
	}

	public ValueAccountStatus setEmailNotVerified() {
		this.value |= EMAIL_NOT_VERIFIED;
		return this;
	}


	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
