package cn.gdeng.market.lease.util;

/*
 * 文 件 名:  RandomId.java
 * 版    权:  Tydic Technologies Co., Ltd. Copyright 1993-2012,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  panmin
 * 修改时间:  2013-4-14
 * 跟踪单号:  <需求跟踪单号>
 * 修改单号:  <需求修改单号>
 * 修改内容:  <修改内容>
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.gdeng.market.util.DateUtil;

/**
 * 随机唯一ID产生器
 * 
 * @author panmin
 */
public class RandomIdGenerator {

	private static Random random;

	private static String table;

	static {
		random = new Random();
		table = "0123456789";
	}

	public synchronized static String randomId(long id) {
		String ret = null;
		String num = String.format("%05d", id);
		int key = random.nextInt(10), seed = random.nextInt(100);
		CaesarUtil caesar = new CaesarUtil(table, seed);
		num = caesar.encode(key, num);
		ret = num + String.format("%01d", key) + String.format("%02d", seed);
		return ret;
	}

	public synchronized static String random() {
		Random random = new Random();
		return DateUtil.getDate(new Date(), "yyMMddHHmmssSSS") + random.nextInt(1000);
	}

	public synchronized static String random(String pattern) {
		Random random = new Random();
		return DateUtil.getDate(new Date(), pattern) + random.nextInt(1000);
	}

	public synchronized static String generatorOrderNumber(long seqNo) {
		return DateUtil.getDate(new Date(), "yyww") + randomId(seqNo);
	}

	public synchronized static String generatorGiftNumber(Integer maxId) {
		if (maxId == null) {
			maxId = 0;
		}
		String giftNumber = String.format("%04d", maxId + 1);
		giftNumber = DateUtil.getDate(new Date(), "yyyyMMdd")
				+ giftNumber.substring(giftNumber.length() - 4, giftNumber.length());
		return giftNumber;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		RandomIdGenerator r = new RandomIdGenerator();
		Map<String, String> map = new HashMap<String, String>();
		int size = 1000000;
		for (int i = 0; i < size; i += 1) {
			String orderNumber = r.randomId(i);
			// System.out.println(i);
			if (map.containsKey(orderNumber)) {
				System.out.println("发现重复数" + orderNumber);
				return;
			} else {
				map.put(orderNumber, orderNumber);
			}
			if (i == size - 1) {
				System.out.println("finish");
			}
		}
	}
}
