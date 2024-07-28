package com.yupi.springbootinit.manager;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;

import javax.annotation.Resource;

@SpringBootTest
class demo {



    @Test
    void test() {
        // 测试代码
        String demo ="{\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"用户数\"]},\"grid\":{\"left\":\"3%\",\"right\":\"4%\",\"bottom\":\"3%\",\"containLabel\":true},\"toolbox\":{\"feature\":{\"saveAsImage\":{}}},\"xAxis\":{\"type\":\"category\",\"boundaryGap\":false,\"data\":[\"1号\",\"2号\",\"3号\"]},\"yAxis\":{\"type\":\"value\"},\"series\":[{\"name\":\"用户数\",\"type\":\"line\",\"data\":[10,20,30]}]}";
        JSON parse = JSONUtil.parse(demo);
        System.out.println(parse);

    }
}