package com.ant.tool;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailToolTest {

    EmailTool emailTool = new EmailTool("abcl100@163.com", "admin123456");

    @Test
    public void sendEmail() throws Exception {
        emailTool.sendEmail("abcl100@163.com", "恭喜大哥，荣获阿克拉奖金", "你好啊！");
    }
}
