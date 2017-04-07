package cn.gdeng.market.lease.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import cn.gdeng.market.util.ExcelConf;




public class ExcelUtil {
    /**
     * @Description 生成2007 Excel
     * @param list
     *            要写入excel的数据集合
     * @param clazz
     *            集合中的数据类型
     * @param dateStyle
     *            数据中的时间格式 ,字符串,例如 "%tF"=2010-10-4, "%tF %tT"=2010-10-4 17:36:18
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @CreationDate 2016年5月20日 上午11:21:13
     * @Author lidong(dli@gdeng.cn)
     */
    public static XSSFWorkbook buildXSLXExcel(List<?> list, Class<?> clazz)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
        XSSFWorkbook workBook = null;
        List<Map<String, Object>> excelConfList = getExcelConfList(clazz);
        List<String> headers = new ArrayList<>();
        for (Map<String, Object> conf : excelConfList) {
            headers.add((String) conf.get("header"));
        }
        workBook = new XSSFWorkbook();// 创建工作薄
        XSSFSheet sheet = workBook.createSheet();
        // workBook.setSheetName(0, "");// 表单名称
        XSSFCellStyle cellStyle = settingCellStyle(workBook);
        // 创建第一行标题
        XSSFRow titleRow = sheet.createRow(0);// 第一行标题
        for (int i = 0, size = headers.size(); i < size; i++) {
            XSSFCell cell = titleRow.createCell(i, 0);
            cell.setCellStyle(cellStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(headers.get(i));
        }
        // 从第二行开始写入数据
        if (list != null && !list.isEmpty()) {
            for (int i = 0, size = list.size(); i < size; i++) {
                Object object = list.get(i);
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0, size1 = excelConfList.size(); j < size1; j++) {
                    XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
                    Map<String, Object> conf = excelConfList.get(j);
                    Object result = new PropertyDescriptor((String) conf.get("fieldName"), clazz).getReadMethod().invoke(object);
                    if ("java.util.Date".equals((String) conf.get("filedType"))) {
                        if (result != null) {
                            result = String.format("%tF %tT", result, result);
                        }
                    }
                    if (result != null) {
                        cell.setCellValue("" + result);
                    } else {
                        cell.setCellValue("");
                    }
                    result = null;
                    conf = null;
                    cell = null;
                }
                object = null;
            }
        }
        return workBook;
    }
    
    
    /**
     * 新增一个，不影响原来的方法
     * @param excelConfList
     * @param list
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     */
    public static <T> XSSFWorkbook  buildXSLXExcel(List<Map<String, Object>> excelConfList,List<T> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
    	 XSSFWorkbook workBook = null;
         List<String> headers = new ArrayList<>();
         for (Map<String, Object> conf : excelConfList) {
             headers.add((String) conf.get("header"));
         }
         workBook = new XSSFWorkbook();// 创建工作薄
         XSSFSheet sheet = workBook.createSheet();
         // workBook.setSheetName(0, "");// 表单名称
         XSSFCellStyle cellStyle = settingCellStyle(workBook);
         // 创建第一行标题
         XSSFRow titleRow = sheet.createRow(0);// 第一行标题
         for (int i = 0, size = headers.size(); i < size; i++) {
             XSSFCell cell = titleRow.createCell(i, 0);
             cell.setCellStyle(cellStyle);
             cell.setCellType(XSSFCell.CELL_TYPE_STRING);
             cell.setCellValue(headers.get(i));
         }
         // 从第二行开始写入数据
         if (list != null && !list.isEmpty()) {
             for (int i = 0, size = list.size(); i < size; i++) {
                 Object object = list.get(i);
                 XSSFRow row = sheet.createRow(i + 1);
                 for (int j = 0, size1 = excelConfList.size(); j < size1; j++) {
                     XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
                     cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
                     Map<String, Object> conf = excelConfList.get(j);
                     Object result = new PropertyDescriptor((String) conf.get("fieldName"),object.getClass()).getReadMethod().invoke(object);
                     if ("java.util.Date".equals((String) conf.get("filedType"))) {
                         if (result != null) {
                             result = String.format("%tF %tT", result, result);
                         }
                     }
                     if (result != null) {
                         cell.setCellValue("" + result);
                     } else {
                         cell.setCellValue("");
                     }
                     result = null;
                     conf = null;
                     cell = null;
                 }
                 object = null;
             }
         }
         return workBook;
    }

    /**
     * @Description 给Excel设置样式
     * @param workBook
     * @return
     * @CreationDate 2016年5月20日 下午2:43:16
     * @Author lidong(dli@gdeng.cn)
     */
    private static XSSFCellStyle settingCellStyle(XSSFWorkbook workBook) {
        XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
        XSSFFont font = workBook.createFont();
        font.setColor(XSSFFont.COLOR_NORMAL);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    public static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) { // 根据cell中的类型来输出数据
        case HSSFCell.CELL_TYPE_NUMERIC:
            Double value = cell.getNumericCellValue();
            return value;
        case HSSFCell.CELL_TYPE_STRING:
            String value1 = cell.getStringCellValue();
            return value1;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            Boolean value2 = cell.getBooleanCellValue();
            return value2;
        case HSSFCell.CELL_TYPE_FORMULA:
            String value3 = cell.getCellFormula();
            return value3;
        default:
            break;
        }
        return null;
    }

    /**
     * @Description 查找出数据对象中需要导出的字段
     * @param clazz
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @CreationDate 2016年5月20日 上午11:23:59
     * @Author lidong(dli@gdeng.cn)
     */
    @SuppressWarnings("rawtypes")
    private static List<Map<String, Object>> getExcelConfList(Class<?> clazz)
            throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<Map<String, Object>> confList = new ArrayList<Map<String, Object>>();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ExcelConf conf = field.getAnnotation(ExcelConf.class);
                if (conf != null) {
                    Map<String, Object> obj = new HashMap<String, Object>();
                    obj.put(field.getName(), conf);
                    obj.put("fieldName", field.getName());
                    obj.put("header", conf.excelHeader());
                    obj.put("sort", conf.sort());
                    obj.put("filedType", field.getType().getName());
                    confList.add(obj);
                    obj = null;
                }
            }
            Class superclass = clazz.getSuperclass();
            clazz = superclass;
        } while (clazz != null);
        // 对容器进行排序的函数
        Collections.sort(confList, comparator);
        return confList;
    }
    
    public static List<Map<String, Object>> convertExportHeader(String[][] headss){
    	Assert.notNull(headss,"EXCEL头不能为空");
    	List<Map<String, Object>> list = new ArrayList<>();
    	for(String[] heads : headss){
    		Map<String,Object> map = new HashMap<>();
    		map.put("header", heads[0]);
    		map.put("fieldName", heads[1]);
    		map.put("filedType", heads[2]);
    		list.add(map);
    	}
    	return list;
    }

    /**
     * @Fields comparator 集合排序功能重写,对导出的数据类型的字段进行排序
     * @since Ver 2.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    static Comparator<Map<String, Object>> comparator = new Comparator() {
        public int compare(Object a0, Object a1) {
            return (int) ((Map<String, Object>) a0).get("sort") - (int) ((Map<String, Object>) a1).get("sort");
        }
    };
    
    /**
     * 
     * @param response HttpServletResponse
     * @param fileName 导出的文件名
     * @param list 结果集
     * @param clazz
     * @Author gcwu
     */
    public static boolean exportExcel(HttpServletResponse response,String fileName,List<?> list, Class<?> clazs){
    	boolean success=false;
    	OutputStream ouputStream = null;
    	 try {
	            // 设置输出响应头信息，
	            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "ISO-8859-1")+ RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xlsx");
	            ouputStream = response.getOutputStream();
	            // 查询数据
	            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, clazs);
	            workBook.write(ouputStream);
	            success=true;
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            try {
	                ouputStream.flush();
	                ouputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
    	 return success;
    }
}