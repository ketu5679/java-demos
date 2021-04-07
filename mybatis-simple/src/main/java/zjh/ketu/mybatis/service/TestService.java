package zjh.ketu.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zjh.ketu.mybatis.dao.TestMapper;
import zjh.ketu.mybatis.entity.Test;
import zjh.ketu.mybatis.entity.TestExample;

import java.util.List;

/**
 * @Author: zjh
 * @Date: 2020/11/16 15:46
 */
@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public void add(Test test) {
        testMapper.insert(test);
    }

    public List<Test> listAll() {
        return testMapper.selectByExample(new TestExample());
    }
}
