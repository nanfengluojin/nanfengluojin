package com.wzp.nflj.util;

import com.wzp.nflj.config.CustomConfig;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/21 10:45
 */
public class IpUtil {


    /**
     * 获取客户端Detail信息
     * 包括客户端ip地址 用户token等信息
     * IP地址 remoteAddress
     * token值 tokenValue
     * token头 tokenType
     *
     * @return
     */
    public static Map getDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object details = authentication.getDetails();
        //反射获取值
        Class jsonClass = details.getClass();
        // 得到类中的所有属性集合
        Field[] fs = jsonClass.getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        for (Field f : fs) {
            f.setAccessible(true); //设置属性为可以访问的
            try {
                // 得到此属性的值
                Object val = f.get(details);
                if (!StringUtils.isEmpty(val)) {
                    map.put(f.getName(), val.toString());
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.contains(",")) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(",")[0];
        }
        if (checkIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (checkIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (checkIp(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        return ip;
    }

    private static boolean checkIp(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }


    /**
     * 获取ip地址所在的省和市
     *
     * @param ip
     * @return
     */
    public static Map<String, String> getProvinceAndCity(String ip) {
        Map<String, String> map = new HashMap<>(2);
        String[] splitIpString = splitIpString(ip);
        String province = splitIpString[2].replaceAll("\\|", "");
        String city = splitIpString[3].replaceAll("\\|", "");
        // 因为中国香港、澳门、台湾的ip地址解析后，城市显示位置与中国大陆省份同级，故需要向前取一位，
        // 例如: 中国|0|四川|成都|电信，中国|0|香港|0|电讯盈科，中国|0|澳门|0|澳门电讯，中国|0|台湾省|0|0，美国|0|犹他|盐湖城|0
        if (city.equals("0")) {
            city = splitIpString[2].replaceAll("\\|", "");
        }
        map.put("province", province);
        map.put("city", city);
        return map;
    }


    /**
     * 对ip进行相应处理
     *
     * @param ip
     * @return
     */
    public static String[] splitIpString(String ip) {
        String cityIpString = getCityInfo(ip);
        return cityIpString.split("\\|");
    }

    public static String getCityInfo(String ip) {
        //db文件下载地址，https://gitee.com/lionsoul/ip2region/tree/master/data 下载下来后解压，db文件在data目录下
        String dbPath = "G:\\IpData\\ip2region.db";
        File file = new File(dbPath);
        if (!file.exists()) {
            System.err.println("不存在ip2region.db文件");
        }
        if (!Util.isIpAddress(ip)) {
            System.err.println("无效的ip地址");
        }
        //查询算法默认采用 B-tree 对应的method为btreeSearch，此外还有binary 对应method为binarySearch；memory对应method为memorySearch
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            searcher.close();
            return dataBlock.getRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取客户端的公网ip
     *
     * @param ip
     * @return
     */
    public static String publicNetWork(String ip) {
        String publicNetWork = CustomConfig.publicNetWork;
        StringBuilder inputLine = new StringBuilder();
        BufferedReader in = null;
        try {
            URL url = new URL(publicNetWork);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            String read = "";
            while ((read = in.readLine()) != null) {
                inputLine.append(read).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("<dd class=\"fz24\">(.*?)</dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipStr = m.group(1);
            System.out.println(ipStr);
            return ipStr;
        }
        return null;
    }

}
