package net.glaso.jwt.business.scheduler.bean;

import net.glaso.jwt.business.scheduler.service.JwtScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan( basePackageClasses = { JwtScheduler.class })
public class JwtSchedulerConfig {

	@Bean(name="scheduler")
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {

		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize( 2 );
		threadPoolTaskScheduler.setThreadNamePrefix( "ThreadPoolTaskScheduler" );

		return threadPoolTaskScheduler;
	}
}
