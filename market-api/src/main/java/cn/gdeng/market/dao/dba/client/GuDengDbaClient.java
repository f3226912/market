package cn.gdeng.market.dao.dba.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gudeng.framework.dba.client.support.DefaultDbaClient;
import com.gudeng.framework.dba.dao.support.value.ValueParser;
import com.gudeng.framework.dba.exception.DalException;

import cn.gdeng.market.dao.helper.EntityHelper;


/** 谷登专用db客户端
 * 
 * @author wjguo
 *
 * datetime:2016年9月30日 下午5:34:16
 */
public class GuDengDbaClient extends DefaultDbaClient {
	
	private Logger logger = LoggerFactory.getLogger(GuDengDbaClient.class);
	
	long beginDate = System.currentTimeMillis();
	
	/** 实现动态插入。
	 * @see com.gudeng.framework.dba.dao.DataBaseOperation#persist(java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T> T persist(Object entity, Class<T> requiredType) {
		String insertSQL;
		try {
			insertSQL = EntityHelper.getDyNamicInsertSql(entity);
			logger.debug("dynamic general insert sql : \n {}" , insertSQL);
			
			
			Map<String, Object> paramMap = ValueParser.parser(entity);
			
			long beginDate = System.currentTimeMillis();
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getValidateJdbcTemplate(paramMap, ValueParser.parseSqlBean(entity, Boolean.FALSE)).update(insertSQL,
					new MapSqlParameterSource(paramMap), keyHolder);
			
			logger.debug(" persist method executeTime: {} ms", System.currentTimeMillis() - beginDate);
			
			return (T) keyHolder.getKey();
		} catch (Exception e) {
			throw new DalException(e);
		}
	}
	
	private void logSqlMsg(String method, String sql, long executeTime) {
		logger.error(method + " method executeTime:" + executeTime + "ms");
	}
	
}
