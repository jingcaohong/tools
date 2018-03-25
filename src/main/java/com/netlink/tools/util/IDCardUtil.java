package com.netlink.tools.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * 身份证号码验证
 * 1、公民身份证号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
 *		三位数字顺序码和一位数字校验码。
 * 2、地址码(前六位数）表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 3、出生日期码（第七位至十四位）表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 4、顺序码（第十五位至十七位）表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
 *		顺序码的奇数分配给男性，偶数分配给女性。
 * 5、校验码（第十八位数）（1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16，先对前17位数字的权求和,
 * 		Ai:表示第i位置上的身份证号码数字值，Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2。
 * 		（2）计算模 Y = mod(S, 11)（3）通过模得到对应的校验码，余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字，
 * 		其分别对应身份证最后一位的号码为1 0 X 9 8 7 6 5 4 3 2
 */
public class IDCardUtil {

	/**
	 * 地区编码
	 */
	private static final Map<String, String> REGION_CODE = new HashMap<>(64);

	static {
		REGION_CODE.put("11", "北京");
		REGION_CODE.put("12", "天津");
		REGION_CODE.put("13", "河北");
		REGION_CODE.put("14", "山西");
		REGION_CODE.put("15", "内蒙古");
		REGION_CODE.put("21", "辽宁");
		REGION_CODE.put("22", "吉林");
		REGION_CODE.put("23", "黑龙江");
		REGION_CODE.put("31", "上海");
		REGION_CODE.put("32", "江苏");
		REGION_CODE.put("33", "浙江");
		REGION_CODE.put("34", "安徽");
		REGION_CODE.put("35", "福建");
		REGION_CODE.put("36", "江西");
		REGION_CODE.put("37", "山东");
		REGION_CODE.put("41", "河南");
		REGION_CODE.put("42", "湖北");
		REGION_CODE.put("43", "湖南");
		REGION_CODE.put("44", "广东");
		REGION_CODE.put("45", "广西");
		REGION_CODE.put("46", "海南");
		REGION_CODE.put("50", "重庆");
		REGION_CODE.put("51", "四川");
		REGION_CODE.put("52", "贵州");
		REGION_CODE.put("53", "云南");
		REGION_CODE.put("54", "西藏");
		REGION_CODE.put("61", "陕西");
		REGION_CODE.put("62", "甘肃");
		REGION_CODE.put("63", "青海");
		REGION_CODE.put("64", "宁夏");
		REGION_CODE.put("65", "新疆");
		REGION_CODE.put("71", "台湾");
		REGION_CODE.put("81", "香港");
		REGION_CODE.put("82", "澳门");
		REGION_CODE.put("91", "国外");
	}

	/**
	 * 18位身份证前17位的权重值
	 */
	private static final int[] WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 校验码可取值
	 */
	private static final String[] CHECK_CODE = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };

	/**
	 * 旧身份证号长度
	 */
	private static final int OLD_LENGTH = 15;

	/**
	 * 新身份证号长度
	 */
	private static final int NEW_LENGTH = 18;

	/**
	 * 成年年龄
	 */
	private static final int ADULT_AGE = 18;

	/**
	 * 开始身份证号验证
	 *
	 * @param idString cardNo
	 * @return boolean
	 */
	public static boolean validate(String idString) {

		// 验证空
		if (StringUtils.isEmpty(idString)) {
			return Boolean.FALSE;
		}

		// 长度校验
		if (idString.length() != OLD_LENGTH && idString.length() != NEW_LENGTH) {
			return Boolean.FALSE;
		}

		if (idString.length() == OLD_LENGTH) {
			// 15位身份证号
			if (isDigital(idString)) {
				idString = convert15To18(idString);
				if (idString == null) {
					// 15位转成18位失败
					return Boolean.FALSE;
				}
			} else {
				return Boolean.FALSE;
			}
		}

		if (idString.length() == NEW_LENGTH) {
			if (!isDigital(idString.substring(0, NEW_LENGTH - 1))) {
				return Boolean.FALSE;
			}
		}

		// 验证地区码，出生日期，校验位
		if (isEffectiveRegionCode(idString) && isEffectiveBirthday(idString) && isValidCheckCode(idString)) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * 是否已经成年
	 *
	 * @param idString cardNo
	 * @return boolean
	 */
	public static boolean isAdult(String idString) {
		if (idString.length() == OLD_LENGTH) {
			// 15位身份证
			return true;
		}

		if (idString.length() == NEW_LENGTH) {
			// 年
			String strYear = idString.substring(6, 10);
			// 月
			String strMonth = idString.substring(10, 12);
			// 日
			String strDay = idString.substring(12, 14);

			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			if (year - Integer.parseInt(strYear) < NEW_LENGTH) {
				// 年份差小于18，未成年
				return false;
			} else if (year - Integer.parseInt(strYear) == ADULT_AGE) {
				// 年份差等于18，则比较月份
				// 月份从0开始计数
				int month = now.get(Calendar.MONTH);
				if (month + 1 < Integer.parseInt(strMonth)) {
					// 当前月份小于出生月份，未成年
					return false;
				} else if (month + 1 == Integer.parseInt(strMonth)) {
					// 月份也相等，则比较日期
					int day = now.get(Calendar.DAY_OF_MONTH);
					if (day <= Integer.parseInt(strDay)) {
						// 年龄等于18，月份相等，当前日期小于等于出生日期，未成年
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * 校验身份证号码最后一位
	 */
	private static boolean isValidCheckCode(String idString) {
		// 算最后一位校验码
		int checkSum = 0;
		for (int i = 0; i < NEW_LENGTH - 1; i++) {
			int ai = Integer.parseInt(String.valueOf(idString.charAt(i)));
			checkSum = checkSum + ai * WEIGHT[i];
		}

		int checkNum = checkSum % 11;
		String checkChar = CHECK_CODE[checkNum].toUpperCase(Locale.CHINA);

		return idString.toUpperCase(Locale.CHINA).endsWith(checkChar) ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * 检查出生日期
	 */
	private static boolean isEffectiveBirthday(String idString) {
		// 年
		String strYear = idString.substring(6, 10);
		// 月
		String strMonth = idString.substring(10, 12);
		// 日
		String strDay = idString.substring(12, 14);

		if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
			return Boolean.FALSE;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if ((calendar.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (calendar.getTime().getTime()
					- dateFormat.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			return Boolean.FALSE;
		}

		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) <= 0) {
			return Boolean.FALSE;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) <= 0) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * 是否是日期
	 */
	private static boolean isDate(String strDate) {
		String reg = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern = compile(reg);
		Matcher m = pattern.matcher(strDate);
		return m.matches();
	}

	/**
	 * 检查地区码
	 */
	private static boolean isEffectiveRegionCode(String idString) {
		String regionCode = idString.substring(0, 2);

		return REGION_CODE.get(regionCode) == null ? Boolean.FALSE : Boolean.TRUE;
	}

	/**
	 * 判断字符串是否为数字
	 */
	private static boolean isDigital(String str) {
		Pattern pattern = compile("[0-9]*");
		Matcher isDigital = pattern.matcher(str);

		return isDigital.matches();
	}

	/**
	 * 将15位的身份证转成18位身份证
	 * 
	 * 15位身份证号码各位的含义: 1-2位省、自治区、直辖市代码； 3-4位地级市、盟、自治州代码； 5-6位县、县级市、区代码；
	 * 7-12位出生年月日，比如670401代表1967年4月1日，与18位的第一个区别； 13-15位为顺序号，其中15位单数为男，双数为女；
	 */
	private static String convert15To18(String idCard) {

		// 获取出生年月日
		String birthdayStr = idCard.substring(6, 12);
		Date birthday;
		try {
			birthday = new SimpleDateFormat("yyMMdd").parse(birthdayStr);
		} catch (ParseException e) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		String year = String.valueOf(calendar.get(Calendar.YEAR));

		String idCard17 = idCard.substring(0, 6) + year + idCard.substring(8);

		// 算最后一位校验码
		int checkSum = 0;
		for (int i = 0; i < NEW_LENGTH - 1; i++) {
			int ai = Integer.parseInt(String.valueOf(idCard17.charAt(i)));
			checkSum = checkSum + ai * WEIGHT[i];
		}

		int checkNum = checkSum % 11;
		String checkChar = CHECK_CODE[checkNum];

		return idCard17 + checkChar;
	}

}
