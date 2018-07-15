package com.master.demo.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseExcelUtil {
    public <T_T> Map<String, List<T_T>> getDataBeans(File file, Class<T_T> tClass) throws FileNotFoundException,
            IOException,
            SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            InvocationTargetException {
        if (!file.exists()) {
            return null;
        }
        Workbook wb = null;
        if (file.getName().endsWith(".xlsx")) {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } else if (file.getName().endsWith(".xls")) {
            wb = new HSSFWorkbook(new FileInputStream(file));
        } else {
            return null;
        }
        Class<?>[] clsAry = {
                tClass
        };
        Map<String, List<T_T>> map = new HashMap<String, List<T_T>>();
//        for (int i = 0; i < clsAry.length; i++) {
//            map.put(clsAry[i].getSimpleName(), new ParseExcel().getBeansFromExcel(wb, clsAry[i], i));
//        }
        map.put(tClass.getSimpleName(), new ParseExcelUtil().getBeansFromExcel(wb, tClass, 0));
        return map;
    }

    public <T_T> List<T_T> getBeansFromExcel(Workbook book, Class<T_T> cls, int index) throws InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Sheet sheet = book.getSheetAt(index);
        Field[] fs = cls.getDeclaredFields();
        String[] names = new String[fs.length];
        //获取列名
        for (int i = 0; i < fs.length; i++) {
            names[i] = fs[i].getName();
        }
        int rowIndex = 1;
        List<T_T> list = new ArrayList<T_T>();
        while (true) {
            Row row = sheet.getRow(rowIndex);
            if (null == row) {
                break;
            }
            T_T obj = cls.newInstance();
            Method[] methods = cls.getMethods();
            Map<String, Method> mdMap = this.getMethodMap(methods);
            //判断是否存在数据
            Boolean isEmpty = true;
            for (int i = 0; i < names.length; i++) {
                String value = this.getCellValue(row.getCell(i));
                if(StringUtils.isEmpty(value)){
                    continue;
                }
                if (null != value) {
                    isEmpty = false;
                }
                String setMethod = this.wrapSetField(names[i]);
                Method md = mdMap.get(setMethod);
                md.invoke(obj, value);
            }
            if (isEmpty) {
                break;
            }
            list.add(obj);
            rowIndex++;
        }
        return list;
    }

    /**
     *  
     *      * 获取类方法键值对 
     *      * @param ms 
     *      * @return 
     *      
     */
    private Map<String, Method> getMethodMap(Method[] ms) {
        Map<String, Method> map = new HashMap<String, Method>();
        for (Method m : ms) {
            map.put(m.getName(), m);
        }
        return map;
    }

    /**
     * 获取set方法名称
     *
     * @param name
     * @return
     */
    private String wrapSetField(String name) {
        String firstChar = String.valueOf(name.charAt(0)).toUpperCase();
        name = firstChar + name.substring(1);
        return "set" + name;
    }

    private String getCellValue(Cell cell) {
        NumberFormat nf = NumberFormat.getInstance();
        if (null == cell) {
            return "";
        }
        int type = cell.getCellType();
        if (type == Cell.CELL_TYPE_NUMERIC) {
            //return cell.getNumericCellValue() + "";
            String s = nf.format(cell.getNumericCellValue());
            if (s.indexOf(",") >= 0) {
                s = s.replace(",", "");
            }
            return s;
        }
        if (type == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
    }
}
