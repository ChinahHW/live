package com.huwei.live.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StringUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 判断字符串是否为空。空格键也算空
     *
     * @param string 需要监测的字符串
     * @return 结果
     */
    public static Boolean isNotEmpty(String string) {
        boolean result = false;
        if (null != string) {
            string = string.replaceAll("\\s*", "");
            if (!"".equals(string)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 获取现在的日期名
     *
     * @return 示例：2016-12-12
     */
    public static String fileDateName() {
        String result = "";
        try {
            Date date = new Date();
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
            String pathString = sFormat.format(date);
            return pathString;
        } catch (Exception e) {
            logger.error("新建随机日期名失败：" + e.getMessage());
            return result;
        }
    }

    /**
     * 获取现在的年月名
     * @return 示例：2016-12
     */
    public static String getYearMonth(){
        String result = "";
        try {
            Date date = new Date();
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM");
            String pathString = sFormat.format(date);
            return pathString;
        } catch (Exception e) {
            logger.error("新建日期名失败："+e.getMessage());
            return result;
        }
    }

    /**
     * 获取当月天数字符格式
     * @return
     */
    public static String getDay(){
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            int day = calendar.get(Calendar.DATE);
            return day+"";
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }
    /**
     * 获取当天的时间名（取时+分+秒+毫秒+2位随机数字）
     *
     * @return 示例12121242312
     */
    public static String fileTimeName() {
        String result = "";
        Date date = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
            String fileString = simpleDateFormat.format(date);
            String[] fileStrings = fileString.split(":");
            fileString = "";
            for (int i = 0; i < fileStrings.length; i++) {
                fileString = fileString + fileStrings[i];
            }
            fileString = fileString + (int) (Math.random() * 100);
            return fileString;
        } catch (Exception e) {
            logger.error("新建随机时间名失败：" + e.getMessage());
            return result;
        }
    }

    /**
     * 获取bit位随机数字
     *
     * @param bit 多少位随机数字 小于10位
     * @return 随机数字
     */
    public static Integer getIntRandom(int bit) {
        if (bit > 10) {
            return null;
        }
        int i = 1;
        for (int j = 0; j < bit; j++) {
            i = i * 10;
        }
        int result = 0;
        while (result < 100000) {
            result = (int) (i * Math.random());
        }
        return result;
    }

    /**
     * 判断字符串是否含有数字
     *
     * @param str 监测的字符串
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字符串的头部数字
     *
     * @param str 字符串
     * @return null 或获取的 数字 Integer类型
     */
    public static Integer getHeaderNum(String str) {
        Integer result = null;
        if (str == null || "".equals(str)) {
            return result;
        } else {
            char[] chars = str.toCharArray();
            String matchString = "\\d";
            String intString = "";
            for (int i = 0; i < chars.length; i++) {
                String tempString = chars[i] + "";
                if (tempString.matches(matchString)) {
                    intString = intString + tempString;
                } else {
                    break;
                }
            }
            if ("".equals(intString)) {
                return result;
            } else {
                result = new Integer(intString);
                return result;
            }
        }
    }

    /**
     * 将23,234,133.23字符格式金额转换为万元
     * @param str 需要转换的字符串
     * @return 232.34字符串格式
     */
    public static String moneyForm(String str) {
        String matString = "\\d";
        if (null == str || "".equals(str)) {
            return str;
        } else {
            //寻找到小数点位置
            int suffIndex = str.lastIndexOf(".");
            Double newVlaue = null;
            //如果没有找到则判断为没有识别出小数点
            String newNumber = "";
            if (suffIndex == -1) {
                //去除字符串中的非数字元素
                for (int i = 0; i < str.length(); i++) {
                    char tempchar = str.charAt(i);
                    if ((tempchar + "").matches(matString)) {
                        newNumber = newNumber + tempchar;
                    }
                }
                System.out.println(newNumber);
                if (!newNumber.equals("")) {
                    newVlaue = Long.parseLong(newNumber) / 1000000.00;
                } else {
                    newVlaue = new Double("0.00");
                }
            } else {
                //判断其为正常小数点位置,对齐进行切割。去除小数点以及后面的数字
                if ((str.length() - suffIndex) <= 3) {
                    String newStr = str.substring(0, suffIndex);
                    for (int i = 0; i < newStr.length(); i++) {
                        char tempchar = newStr.charAt(i);
                        if ((tempchar + "").matches(matString)) {
                            newNumber += tempchar;
                        }
                    }
                    if (!newNumber.equals("")) {
                        newVlaue = Long.parseLong(newNumber) / 10000.00;
                    } else {
                        newVlaue = new Double("0.00");
                    }
                } else {
                    //小数点不在合理的范围内，则判断小数点识别错误。按没有找到小数点处理
                    for (int i = 0; i < str.length(); i++) {
                        char tempchar = str.charAt(i);
                        if ((tempchar + "").matches(matString)) {
                            newNumber += tempchar;
                        }
                    }
                    if (!newNumber.equals("")) {
                        newVlaue = Long.parseLong(newNumber) / 1000000.00;
                    } else {
                        newVlaue = new Double("0.00");
                    }
                }

            }
            DecimalFormat df = new DecimalFormat("############0.00");

            if (newVlaue != null) {
                if (newVlaue.doubleValue() == 0.00) {
                    logger.info("数字无效转换：" + str + " --> ");
                    return "0.00";
                } else {
                    logger.info("数字转换：" + str + " --> " + df.format(newVlaue));
                    return df.format(newVlaue);
                }
            } else {
                logger.info("数字无效转换：" + str + " --> null");
                return str;
            }
        }

    }



    /**
     * 将文字识别后的字符串转换成数字数据
     * @param matchString 识别后的数据例如：22,212,123.55
     * @return 返回浮点型数据例如：22212123.55
     */
    public static Double ocrWordToDouble(String matchString){
        try {
            if(null != matchString){
                int suffx = matchString.lastIndexOf(".");
                String matchHeader = "";
                String matchFooter = "";
                if(suffx>=0){
                    matchHeader = matchString.substring(0,suffx);
                    matchFooter = matchString.substring(suffx,matchString.length());
                }else{
                    matchHeader = matchString;
                }
                char[] chars = matchHeader.toCharArray();
                String newHeader = "";
                String newFooter = "";
                for (char matchChar : chars){
                   if(Character.isDigit(matchChar)){
                       newHeader += matchChar;
                   }
                }
                chars = matchFooter.toCharArray();
                for (char matchChar: chars){
                    if(Character.isDigit(matchChar)){
                        newFooter += matchChar;
                    }
                }
                if("".equals(newHeader)){
                    newHeader = "0";
                }
                if("".equals(newFooter)){
                    newFooter = "0";
                }
                String newMatchString = newHeader+"."+newFooter;
                //System.out.println(newMatchString);
                return Double.valueOf(newMatchString);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    /**
     * 将Bean类中的字段转换成sql中的字段 例如：userName-->user_name
     * @param orderName 需要转换的字段
     * @return
     */
    public static String fieldToSqlField(String orderName){
        String newName = "";
        try {
            char[] order = orderName.toCharArray();
            for (int i=0;i<order.length;i++){
                if(Character.isUpperCase(order[i])){
                    newName = newName + "_"+Character.toLowerCase(order[i]);
                }else{
                    newName = newName + order[i];
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return newName;
    }

    /**
     * 将sql的字段名称转换为bean类中的字段名称
     * @param sqlField
     * @return
     */
    public static String fieldToBeanField(String sqlField){
        String newName = "";
        try {
            char[] order = sqlField.toCharArray();
            for (int i=0;i<order.length;i++){
                if((order[i]+"").equals("_")){
                    order[i+1] = Character.toUpperCase(order[i+1]);
                }
                newName = newName + order[i];
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return newName.replaceAll("_","");
    }
}
