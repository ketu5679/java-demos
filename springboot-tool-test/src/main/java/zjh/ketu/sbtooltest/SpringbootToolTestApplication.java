package zjh.ketu.sbtooltest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringbootToolTestApplication {
	Logger logs = LoggerFactory.getLogger(SpringbootToolTestApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringbootToolTestApplication.class, args);
	}
	/**
	 * 1. ESAPI
	 */

}
