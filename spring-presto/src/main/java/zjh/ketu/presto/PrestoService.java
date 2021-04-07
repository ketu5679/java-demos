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
public class PrestoService {

    private final JdbcTemplate prestoTemplate;

    public PrestoService(@Qualifier("prestoTemplate") JdbcTemplate prestoTemplate) {
        this.prestoTemplate = prestoTemplate;
    }

    public List<Map<String, Object>> findList(String sql) {
        return prestoTemplate.queryForList(sql);
    }
}
