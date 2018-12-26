package org.september.easyconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStartupListener implements CommandLineRunner{

	protected final static Logger             logger             = LoggerFactory.getLogger(ApplicationStartupListener.class);
	
	@Override
	public void run(String... args) throws Exception {
	}
}
