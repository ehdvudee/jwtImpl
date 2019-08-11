package net.glaso.jwt.business.scheduler.service;

import net.glaso.jwt.business.key.dao.KeyDao;
import net.glaso.jwt.business.key.service.KeyService;
import net.glaso.jwt.business.key.vo.KeyPairVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class UpdateJwtKeyScheduler extends JwtScheduler {

	private Logger logger = Logger.getLogger( UpdateJwtKeyScheduler.class );
	private final KeyDao keyDao;
	private final KeyService keyService;

	@Autowired
	public UpdateJwtKeyScheduler(ThreadPoolTaskScheduler taskScheduler, KeyDao keyDao, KeyService keyService ) {
		super( taskScheduler );
		this.keyDao = keyDao;
		this.keyService = keyService;
	}

	@Override
	public Runnable runner() {
		return new Thread(() -> {
			KeyPairVo currentVo = keyDao.selectKeyPairInfoTrueLastOne();
			KeyPairVo newVo = keyDao.selectKeyPairInfoFalseLastOne();

			if ( newVo == null || currentVo.getSeqId() > newVo.getSeqId() ) {
				logger.info( "don't have to update key." );
				return;
			}

			String currentKid = keyService.getCurrentKid();

			keyService.putOldKid( currentKid );

			try {
				keyService.getInstanceJwtKey( newVo );
			} catch ( Exception e ) {
				throw new RuntimeException( "scheduler error updating key.", e );
			}

			keyDao.updateKeyPairUsageOne( newVo.getSeqId() );
			logger.info( "success update jwt key." );
		});
	}

	@Override
	public Trigger getTrigger() {
		return new CronTrigger( new StringBuffer().append( "0 0 0/" )
				.append( "24" )
				.append( " * * ?").toString() );
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
