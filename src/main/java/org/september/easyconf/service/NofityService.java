package org.september.easyconf.service;

import org.september.easyconf.entity.ConfigUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NofityService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Value(value="${spring.mail.username}")
	private String from;
	
	private static final  String From_Aliace="Easy Config";
	
	public void userAccountSetup(ConfigUser user , String password) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(From_Aliace+"<"+from+">");
        message.setTo(user.getEmail());
        message.setSubject("创建新账号");
        message.setText("系统管理员已经为你添加了新的EasyConfig账号，用户名："+user.getUsername()+"，密码："+password+"。请妥善保管。Good Luck!");
        mailSender.send(message);
	}
	
	public void passwodReseted(ConfigUser user , String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(From_Aliace+"<"+from+">");
        message.setTo(user.getEmail());
        message.setSubject("重置密码");
        message.setText("系统管理员已经为你重置了新的EasyConfig登录密码："+password+"。请妥善保管。Good Luck!");
        mailSender.send(message);
	}
}
