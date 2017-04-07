package cn.gdeng.market;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**Spring表达式测试
 * 
 * @author wjguo
 *
 * datetime:2016年10月23日 上午9:58:05
 */
public class SpelExpressionTest {
	ExpressionParser parser;
	EvaluationContext context;
	
	@Before
	public void setUp() {
		parser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
	}
	
	@Test
	public void testSpl() {
		
		//String expr = "#chargingWays == 1 ? (#freeArea > 1000 ? 'path22' : 'path20'):(#billingArea == 0 ? (#freeArea > 1000 ? 'path22' : 'path20') : (#billingArea == 1 ? (#floorArea > 1000 ? 'path22' : 'path20') : (#innerArea > 1000 ? 'path22' : 'path20')))";
		String expr = "#statementsType == 0 ? 'path21' : (#chargingWays == 1 ? (#freeArea < 1000 ? 'path20' : 'path21'):(#billingArea == 0 ? (#freeArea < 1000 ? 'path20' : 'path21') : (#billingArea == 1 ? (#floorArea < 1000 ? 'path20' : 'path21') : (#innerArea < 1000 ? 'path20' : 'path21'))))";
		//String expr = "#freeDays < 5 ? 'path37' : 'path38'";
		
		context.setVariable("day3", 1.2);
		context.setVariable("day", new Date());
		context.setVariable("day2", new Date());
		String value = parser.parseExpression("#d <= 100").getValue(context, String.class);
		System.out.println(value);

	}

}
