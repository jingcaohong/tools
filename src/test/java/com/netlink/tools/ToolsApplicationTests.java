package com.netlink.tools;

import com.netlink.tools.model.LogFile;
import com.netlink.tools.service.LogFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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

}
