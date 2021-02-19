package cn.itcast.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{
    @Override
    public void showName() {

    }
}
