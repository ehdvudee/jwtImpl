package net.glaso.jwt.business.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class JwtScheduler {

	private final ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	public JwtScheduler(ThreadPoolTaskScheduler taskScheduler ) {
		this.taskScheduler = taskScheduler;
	}

	@PostConstruct
	public void scheduleRunnableWithCronTrigger() {
		if ( isEnabled() ) {
				taskScheduler.schedule( runner(), getTrigger() );
		}
	}

	public abstract Runnable runner();
	
	public abstract Trigger getTrigger();

	public abstract boolean isEnabled();
}
