package zjh.ketu.presto;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zjh
 * @Date: 2020/11/17 10:49
 */
@Service
public class HiveService {

    private final JdbcTemplate hiveTemplate;

    public HiveService(@Qualifier("hiveTemplate") JdbcTemplate hiveTemplate) {
        this.hiveTemplate = hiveTemplate;
    }

    public List<Map<String, Object>> findList(String sql) {
        return hiveTemplate.queryForList(sql);
    }
}
