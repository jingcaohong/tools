package com.netlink.tools;

import com.alibaba.fastjson.JSONObject;
import com.netlink.tools.client.HttpClientHelper;
import com.netlink.tools.model.LogFile;
import com.netlink.tools.service.LogFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolsApplicationTests {

	@Autowired
	private LogFileService logFileService;

	@Test
	public void testSaveLogFile() {
		LogFile logFile = new LogFile();
		logFile.setIp("127.0.0.1");
		logFile.setAgentType("monitor");
		logFile.setAppName("stargateweb");
		logFile.setProjectName("stargate-web");
		logFile.setFilePath("/alidata1/admin/logs/stargate-web/stargate-web-ss_micro_app_stargateweb_lt_error.log");
		logFile.setSource("microLog");
		logFile.setGmtCreated(new Date());
		logFileService.save(logFile);
	}

	@Test
	public void testPost() throws Exception{
		Map<String, String> postData = createMailPostData();
		HttpClientHelper.postFormData("http://10.253.8.229:32363/com.netlink.pigeon.service.MessageSendService:1.0.0/sendEmail", postData);
	}

	public Map<String, String> createMailPostData() throws Exception{
		Map<String,String> params = new HashMap<>(4);
		params.put("content", "测试专用"); // 模板参数

		Map<String, Object> map = new HashMap<>(16);
		map.put("parameters", java.net.URLEncoder.encode(JSONObject.toJSONString(params), "utf-8"));
		map.put("isNeedTrans","1");
		map.put("fromMail", "cs@mailserver.netlink.com");
		map.put("templateNo", "zis_1608005");
		map.put("userName", "监控平台");
		map.put("toMails", "fubencheng@netlink.com");
		map.put("title", "故障台告警信息");

		Object[] obj = new Object[] { map };
		Object[] type = new Object[] { "java.lang.String" };
		Map<String, String> postData = new HashMap<>(4);
		postData.put("ArgsObjects", JSONObject.toJSONString(obj));
		postData.put("ArgsTypes", JSONObject.toJSONString(type));
		return postData;
	}

}
