package com.cwa.test.simuiation;

import org.apache.log4j.xml.DOMConfigurator;

import com.cwa.component.task.quartz.QuartzTaskManager;
import com.cwa.component.task.quartz.config.TaskTypeConfigFactory;
import com.cwa.simuiationimpl.ServerSimuiation;
import com.cwa.simuiationimpl.manage.SobMgr;

public class MainTest {
	public static void main(String[] args) {
		DOMConfigurator.configureAndWatch("propertiesconfig/log4j.xml");

		QuartzTaskManager taskManager = new QuartzTaskManager();
		TaskTypeConfigFactory factory = new TaskTypeConfigFactory();

		String battleKey = "battle_test";
		// 初始化仿真服务
		ServerSimuiation simuiation = new ServerSimuiation(battleKey);

		simuiation.setAreaMgr(null);
		simuiation.setSobMgr(new SobMgr());

//		ScheduleMgr scheduleMgr = new ScheduleMgr();
//		scheduleMgr.setSimuiation(simuiation);
//		scheduleMgr.setTaskManager(taskManager);
//		scheduleMgr.setTaskTypeConfigFactory(factory);
//
//		simuiation.setScheduleMgr(scheduleMgr);

		simuiation.startup();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		simuiation.shutdown();
	}
}
