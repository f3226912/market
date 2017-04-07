package cn.gdeng.market.util;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 配置实体类Excel导出配置
 * @Project gd-supplier-intf
 * @ClassName ExcelConf.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年5月19日 下午5:09:02
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelConf {
    /**
     * @Description 导出的字段对应的Excel表格头
     * @return
     * @CreationDate 2016年5月20日 上午9:55:44
     * @Author lidong(dli@gdeng.cn)
     */
    public String excelHeader() default "";

    /**
     * @Description 导出的字段在excel表格中的排序，升序排列
     * @return
     * @CreationDate 2016年5月20日 上午9:56:03
     * @Author lidong(dli@gdeng.cn)
     */
    public int sort() default 0;
}
