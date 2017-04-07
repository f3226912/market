package cn.gdeng.market.dao.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.beans.BeanUtils;

/** 实体工具类
 * 
 * @author wjguo
 *
 * datetime:2016年9月30日 下午4:51:17
 */
public final class EntityHelper {
	/**获取动态插入sql语句
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static String getDyNamicInsertSql(Object entity) throws Exception {
		Class<?> entityClass = entity.getClass();
		
		//属性名称列表
		List<String> columnNameList = new ArrayList<String>();
		//字段名称列表
		List<String> fieldNameList = new ArrayList<String>();
		//数据库表名
		String tableName = getDBTableName(entityClass);
		
		Method[] methods = entityClass.getMethods();
		Field[] fields = entityClass.getDeclaredFields();
		Field[] superFields = entityClass.getSuperclass().getDeclaredFields();
		for (Method method : methods) {
			if ((method.isAnnotationPresent(Column.class))) {
				PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(method);
				if (isTransient(fields, descriptor.getName())) {
					continue;
				}
				if (isTransient(superFields, descriptor.getName())) {
					continue;
				}
					
				Field field = entityClass.getDeclaredField(descriptor.getName());
				if (isNullVal(entity, field)) {
					continue;
				}
				
				Column columnAnnoation = (Column) method.getAnnotation(Column.class);
				columnNameList.add(columnAnnoation.name());
				fieldNameList.add(descriptor.getName());
			}
		}
		
		//不允许没有任何的属性值
		if (columnNameList.size() == 0) {
			throw new IllegalArgumentException("整个实体的属性值均为空，生成动态插入sql失败！");
		}
		//生成sql语句
		return generalInsertSql(tableName, columnNameList, fieldNameList);
	}
	
	
	/**是否 transient 属性
	 * @param fields
	 * @param fileName
	 * @return
	 */
	private static boolean isTransient(Field[] fields, String fileName) {
		for (Field field : fields) {
			if ((field.getName().equals(fileName)) && (Modifier.isTransient(field.getModifiers()))) {
				return true;
			}
		}
		return false;
	}
	
	/** 判断当前属性值是否为空。
	 * @param entity
	 * @param field
	 * @return true表示为空，反之false。
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static boolean isNullVal(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		return field.get(entity) == null;
	}
	
	/** 获取数据库表名
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String getDBTableName(Class entityClass) {
		Entity entityAno = (Entity) entityClass.getAnnotation(Entity.class);
		if (entityAno == null) {
			throw new IllegalArgumentException(entityClass.getName() + " 不是Entity对象! ");
		}
		return entityAno.name().toUpperCase();
	}
	
	/** 生成插入的sql语句。
	 * @param tableName 表名
	 * @param columnNameList 属性名称集合
	 * @param fieldNameList 字段名称集合
	 * @return
	 */
	private static String generalInsertSql(String tableName, List<String> columnNameList, 
			List<String> fieldNameList) {
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		sql.append(tableName).append("(");
		for (String column : columnNameList) {
			sql.append(column).append(", ");
		}
		//去除多余的逗号和空格
		sql.deleteCharAt(sql.length() - 2);
		sql.append(") VALUES (");
		
		for (String field : fieldNameList) {
			sql.append(":").append(field);
			sql.append(", ");
		}
		//去除多余的逗号和空格
		sql.deleteCharAt(sql.length() - 2);
		sql.append(")");
		
		return sql.toString();
	}
}
