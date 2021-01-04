import com.example.app.AppConfig;
import com.example.dao.CityDao;
import com.example.service.CityService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx
                = new AnnotationConfigApplicationContext(AppConfig.class);
        CityService cs = ctx.getBean(CityService.class);
        cs.query();

//        ctx.getBeanFactory().registerSingleton();


        SqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // mapper 接口实例化
        // 接口如何实例化出来 cglib, jdk?

        // 1. jdk Proxy 生成mapper实例
        CityDao mapper = sqlSession.getMapper(CityDao.class);
        // 2. proxyObject 放入spring容器
        mapper.query();
    }
}
