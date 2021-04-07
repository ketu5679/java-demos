package com.example.mybatisplustest.service.impl;

import com.example.mybatisplustest.entity.DumUser;
import com.example.mybatisplustest.mapper.DumUserMapper;
import com.example.mybatisplustest.service.IDumUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjh
 * @since 2021-01-06
 */
@Service
public class DumUserServiceImpl extends ServiceImpl<DumUserMapper, DumUser> implements IDumUserService {

}
