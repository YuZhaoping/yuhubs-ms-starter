package com.yuhubs.ms.event;

import com.yuhubs.ms.util.ConcurrentExecutorQueue;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.AbstractApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.util.ErrorHandler;

import java.util.Collection;
import java.util.concurrent.Executor;

public class ConcurrentEventMulticaster extends AbstractApplicationEventMulticaster {

	private static final class EventWrapper {

		private ApplicationEvent event;
		private ResolvableType eventType;

		EventWrapper(ApplicationEvent event, ResolvableType eventType) {
			this.event = event;
			this.eventType = eventType;
		}
	}

	private static final class EventHandler implements ConcurrentExecutorQueue.Handler<EventWrapper> {

		private final ConcurrentEventMulticaster multicaster;

		EventHandler(ConcurrentEventMulticaster multicaster) {
			this.multicaster = multicaster;
		}

		@Override
		public void executeHandle(EventWrapper eventWrapper) {
			this.multicaster.doMulticastEvent(eventWrapper.event, eventWrapper.eventType);
		}
	}

	private static final class DirectExecutor implements Executor {
		public void execute(Runnable r) {
			r.run();
		}
	}


	private final EventHandler eventHandler;
	private final ConcurrentExecutorQueue<EventWrapper> queue;

	private ErrorHandler errorHandler;


	public ConcurrentEventMulticaster(Executor executor) {
		this.eventHandler = new EventHandler(this);

		this.queue = new ConcurrentExecutorQueue<>(
				(executor != null ?  executor : new DirectExecutor()),
				this.eventHandler);
	}

	public ConcurrentEventMulticaster() {
		this(null);
	}


	@Override
	public final void multicastEvent(ApplicationEvent event) {
		multicastEvent(event, resolveDefaultEventType(event));
	}

	@Override
	public final void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
		this.queue.offer(new EventWrapper(event, eventType));
	}

	private final ResolvableType resolveDefaultEventType(ApplicationEvent event) {
		return ResolvableType.forInstance(event);
	}


	private final void doMulticastEvent(final ApplicationEvent event, ResolvableType eventType) {
		ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));

		Collection<ApplicationListener<?>> listeners = getApplicationListeners(event, type);
		for (ApplicationListener<?> listener : listeners) {
			invokeListener(listener, event);
		}
	}

	protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
		try {
			doInvokeListener(listener, event);
		} catch (Throwable err) {
			ErrorHandler errorHandler = getErrorHandler();
			if (errorHandler != null) {
				errorHandler.handleError(err);
			}
		}
	}

	private final void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
		try {
			listener.onApplicationEvent(event);
		} catch (ClassCastException ex) {
			String msg = ex.getMessage();
			if (msg == null || matchesClassCastMessage(msg, event.getClass())) {
				// Possibly a lambda-defined listener which we could not resolve the generic event type for
				// -> let's suppress the exception and just log a debug message.
			} else {
				throw ex;
			}
		}
	}

	private final boolean matchesClassCastMessage(String classCastMessage, Class<?> eventClass) {
		// On Java 8, the message starts with the class name: "java.lang.String cannot be cast..."
		if (classCastMessage.startsWith(eventClass.getName())) {
			return true;
		}
		// On Java 11, the message starts with "class ..." a.k.a. Class.toString()
		if (classCastMessage.startsWith(eventClass.toString())) {
			return true;
		}
		// On Java 9, the message used to contain the module name: "java.base/java.lang.String cannot be cast..."
		int moduleSeparatorIndex = classCastMessage.indexOf('/');
		if (moduleSeparatorIndex != -1 &&
				classCastMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1)) {
			return true;
		}
		// Assuming an unrelated class cast failure...
		return false;
	}


	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	protected ErrorHandler getErrorHandler() {
		return errorHandler;
	}

}
