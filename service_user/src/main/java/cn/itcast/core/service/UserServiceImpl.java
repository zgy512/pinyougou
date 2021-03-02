package cn.itcast.core.service;

public class UserServiceImpl implements UserService{
    @Override
    public void sendCode(String phone) {
        // 1 生成一个随机的6位数，作为验证码
        // 2 手机号作为key 验证码作为value 保存到 redis
        // 3 将手机号，短信内容，模板 签名 等封装成map 发送给消息服务器
    }
}
