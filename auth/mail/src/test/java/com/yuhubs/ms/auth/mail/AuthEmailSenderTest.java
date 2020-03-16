package com.yuhubs.ms.auth.mail;

import com.yuhubs.ms.auth.ConfiguredTestBase;
import com.yuhubs.ms.mail.MailSender;
import com.yuhubs.ms.security.auth.event.ConfirmEmailEvent;
import com.yuhubs.ms.security.auth.event.data.ConfirmEmailData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthEmailSenderTest extends ConfiguredTestBase {

	private static final class Interceptor implements AuthEmailSender.Interceptor {

		private final CountDownLatch doneSignal;

		private volatile MimeMessage mimeMessage;


		Interceptor() {
			this.doneSignal = new CountDownLatch(1);
			this.mimeMessage = null;
		}


		@Override
		public void onSendEmail(MailSender sender, MimeMessage message) throws MailException  {
			this.mimeMessage = message;
			this.doneSignal.countDown();
		}

		public MimeMessage getMimeMessage() {
			return this.mimeMessage;
		}

		public void waitForDone(int seconds) throws InterruptedException {
			this.doneSignal.await(seconds, TimeUnit.SECONDS);
		}

	}


	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AuthEmailSender authEmailSender;


	@Test
	public void testVerifyEmail() throws InterruptedException, MessagingException {
		final Interceptor interceptor = new Interceptor();
		authEmailSender.setInterceptor(interceptor);

		ConfirmEmailData data = new ConfirmEmailData("user01@yuhubs.com",
				ConfirmEmailData.Type.VERIFY_EMAIL);

		data.setUsername("user01")
				.setConfirmUrl("/auth/users/1/verify_email")
				.setToken("XXXX");

		applicationContext.publishEvent(new ConfirmEmailEvent(data));

		interceptor.waitForDone(1);

		MimeMessage mimeMessage = interceptor.getMimeMessage();
		assertNotNull(mimeMessage);
		assertEquals("Verify email for user01", mimeMessage.getSubject());
	}

	@Test
	public void testResetPassword() throws InterruptedException, MessagingException {
		final Interceptor interceptor = new Interceptor();
		authEmailSender.setInterceptor(interceptor);

		ConfirmEmailData data = new ConfirmEmailData("admin@yuhubs.com",
				ConfirmEmailData.Type.RESET_PASSWORD);

		data.setUsername("admin")
				.setConfirmUrl("/auth/users/1/reset_password")
				.setToken("XXXX");

		applicationContext.publishEvent(new ConfirmEmailEvent(data));

		interceptor.waitForDone(1);

		MimeMessage mimeMessage = interceptor.getMimeMessage();
		assertNotNull(mimeMessage);
		assertEquals("Reset password for admin", mimeMessage.getSubject());
	}

}
