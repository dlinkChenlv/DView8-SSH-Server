package dlink.ssh;

import dlink.ssh.Server.SshThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SshServerApplication implements ApplicationRunner {
	private static final Logger LOG = LoggerFactory.getLogger(SshServerApplication.class);
	@Override
	public void run(ApplicationArguments var1) throws Exception{
		LOG.info("ssh server is running now ++++++++++++++++++++++++++++++++++++++++");
		SshThread st=new SshThread();
		st.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(SshServerApplication.class, args);
	}
}
