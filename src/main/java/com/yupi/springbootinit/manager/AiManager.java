package com.yupi.springbootinit.manager;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.common.ErrorCode;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

//用来对接第三方接口
@Service
public class AiManager {
    String accessKey = "vij9uih5c1hii2k0f6n9snrl738qqjk8";
    String secretKey = "tyk55i5kre68pee0rb3a1gufvbxhulya";
    YuCongMingClient yuCongMingClient = new YuCongMingClient(accessKey, secretKey);
//AI对话

    public  String doChat(long modelId,String message) throws BindException {
        DevChatRequest devChatRequest = new DevChatRequest();
        //尾部加L表示转化为Long
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        // 设置其他参数，如模型ID、会话ID等
        // ...
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if (response==null){
            throw new BindException(ErrorCode.SYSTEM_ERROR,"AI响应错误");
        }
        if(response.getData()==null){
            throw new BindException(ErrorCode.SYSTEM_ERROR,"获取ai数据内容错误,message:"+message);
        }

        return response.getData().getContent();
    }
}
