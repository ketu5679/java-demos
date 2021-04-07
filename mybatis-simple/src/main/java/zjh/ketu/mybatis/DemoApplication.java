package zjh.ketu.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	/**
	 * 1min
	 * @throws InterruptedException
	 */
	@Scheduled(fixedRate = 1000*60)
    public void test() throws InterruptedException {
		Date date = new Date();
		TimeUnit.SECONDS.sleep(3);
		log.info("now={}", date);
    }

}
