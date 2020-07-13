package com.yww.doggie.Util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class SanUtil {

	public static String createOnlyOrderId() {
		int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
	}
	
	public static String createStarOid() {
		
		String timeStr = (new java.text.SimpleDateFormat("yyyyMMddhhmmss")).format(new Date());
		String oid = timeStr+String.valueOf((new Random()).nextInt(10000));
		
        return oid;
	}
	
	public static String createStarCommentOid(int user_oid) {
		
		String timeStr = (new java.text.SimpleDateFormat("yyyyMMddhhmmss")).format(new Date());
		String oid = String.valueOf(user_oid)+timeStr;
		
        return oid;
	}
	
}
