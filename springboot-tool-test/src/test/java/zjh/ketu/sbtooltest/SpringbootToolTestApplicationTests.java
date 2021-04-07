package zjh.ketu.sbtooltest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.reference.Log4JLogFactory;
import org.owasp.esapi.reference.Log4JLoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootToolTestApplicationTests {


	@Test
	void contextLoads() {
//		System.out.println(new int[]{1,}.length);
		System.out.println(ESAPI.encoder().canonicalize("test"));
//		System.out.println(ESAPI.encoder().canonicalize("test周俊豪"));
//		Logger logger = Log4JLogFactory.getInstance().getLogger(SpringbootToolTestApplicationTests.class);
//		logger.info(Logger.EVENT_SUCCESS, "fff");

		List<Map<String,Integer>> res = new ArrayList<>();
		res.add(new HashMap<>());
		res.get(0).put("A", 1);
		System.out.println(res);
//		System.out.println(BeanTools.copyBeans(res, Object.class));
		System.out.println(res);


		System.out.println(StringHelpers.canonicalize("*dm*,*ddd*"));
	}

}
