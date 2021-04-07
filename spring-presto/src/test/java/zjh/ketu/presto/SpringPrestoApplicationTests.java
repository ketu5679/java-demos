package zjh.ketu.presto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringPrestoApplicationTests {

    @Autowired
    PrestoService prestoService;

    @Autowired
    HiveService hiveService;

    @Test
    void testPresto() {
		System.out.println(prestoService.findList("with a as (select * from default.t_1 limit 1) select * from a"));
    }

    @Test
    void testHive() {
        System.out.println(hiveService.findList("select * from (select f0 from test)a"));
    }

}
