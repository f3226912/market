package cn.gdeng.market.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**测试任务
 * 
 * @author wjguo
 *
 * datetime:2016年10月9日 上午10:28:52
 */
public class DemoTask {
	private Logger logger = LoggerFactory.getLogger(DemoTask.class);

	/**
	 * 定时方法
	 */
	public void execute(){  
		logger.info("-----------DemoTask begin---------");
		Long beginTime=System.currentTimeMillis();
		
		try {
			System.out.println("execute DemoTask!!!!");
		} catch (Exception e) {
			logger.error("DemoTask execute failure", e);
		}
		
        Long consumedTime=System.currentTimeMillis() - beginTime;
        logger.info("-----------DemoTask end, time consume:{}ms ---------", consumedTime);
    }  
}
