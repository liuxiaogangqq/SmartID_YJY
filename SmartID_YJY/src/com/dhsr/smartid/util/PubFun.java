package com.dhsr.smartid.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;









import net.sf.json.JSONObject;


public class PubFun {
	LdapContext ctx;

	/*
	 * private static ClassPathXmlApplicationContext context = new
	 * ClassPathXmlApplicationContext( "classpath:applicationContext.xml");
	 */

	// jdk代理
	// private static BaseService baseService = (BaseService)
	// context.getBean("baseServiceImpl");
	public PubFun() {
	}

	/**
	 * 得到当前系统日期
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		// String pattern = "yyyy-MM-dd";
		// SimpleDateFormat df = new SimpleDateFormat(pattern);
		// Date today = new Date();
		// String tString = df.format(today);
		// return tString;
		// 运用下面这个方法可以使效率提高
		GregorianCalendar tGCalendar = new GregorianCalendar();
		int sYears = tGCalendar.get(Calendar.YEAR);
		int sMonths = tGCalendar.get(Calendar.MONTH) + 1;
		int sDays = tGCalendar.get(Calendar.DAY_OF_MONTH);
		StringBuffer tStringBuffer = new StringBuffer(10);
		tStringBuffer.append(sYears);
		tStringBuffer.append('-');
		if (sMonths < 10)
			tStringBuffer.append('0');
		tStringBuffer.append(sMonths);
		tStringBuffer.append('-');
		if (sDays < 10)
			tStringBuffer.append('0');
		tStringBuffer.append(sDays);
		return tStringBuffer.toString();
		// 运用下面这个方法可以使效率提高
	}

	/**
	 * 得到当前系统时间
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		// String pattern = "HH:mm:ss";
		// SimpleDateFormat df = new SimpleDateFormat(pattern);
		// Date today = new Date();
		// String tString = df.format(today);
		// return tString;
		// 运用下面这个方法可以使效率提高
		GregorianCalendar tGCalendar = new GregorianCalendar();
		int sHOUR = tGCalendar.get(Calendar.HOUR_OF_DAY);
		int sMINUTE = tGCalendar.get(Calendar.MINUTE);
		int sSECOND = tGCalendar.get(Calendar.SECOND);
		StringBuffer tStringBuffer = new StringBuffer(8);
		if (sHOUR < 10)
			tStringBuffer.append('0');
		tStringBuffer.append(sHOUR);
		tStringBuffer.append(':');
		if (sMINUTE < 10)
			tStringBuffer.append('0');
		tStringBuffer.append(sMINUTE);
		tStringBuffer.append(':');
		if (sSECOND < 10)
			tStringBuffer.append('0');
		tStringBuffer.append(sSECOND);
		return tStringBuffer.toString();
		// 运用下面这个方法可以使效率提高
	}

	// @Test
	public static Date getTimeAll() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = getCurrentDate() + " " + getCurrentTime();
		Date d = null;
		try {
			d = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// // System.out.println(d.toLocaleString());
		return d;
	}

	public static Date getDate() {
		java.sql.Date dat = new java.sql.Date(new java.util.Date().getTime());
		return dat;
	}

	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");// 只有时分秒
		return sdf.format(date);
	}

	public static String getIp() {
		Enumeration allNetInterfaces;
		InetAddress ip = null;
		String myip = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();

			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						myip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myip;
	}

	public static Date getEndDate(Date d) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy MM-dd");
		Calendar date = Calendar.getInstance();
		Date endDate = null;
		date.setTime(d);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endDate;
	}

	/**
	 * 获取两个日期之间的所有日期（yyyy-MM-dd）
	 * 
	 * @Description TODO
	 * @param begin
	 * @param end
	 * @return
	 * @author XuJD
	 * @date 2017-4-1
	 */
	public static List<Date> getBetweenDates(Date begin, Date end) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin);
		/*
		 * Calendar tempEnd = Calendar.getInstance();
		 * tempStart.add(Calendar.DAY_OF_YEAR, 1); tempEnd.setTime(end); while
		 * (tempStart.before(tempEnd)) { result.add(tempStart.getTime());
		 * tempStart.add(Calendar.DAY_OF_YEAR, 1); }
		 */
		while (begin.getTime() <= end.getTime()) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			begin = tempStart.getTime();
		}
		return result;
	}

	// JAVA获取某段时间内的所有日期
	public static int compareDates(String dateFormate, String dBegin,
			String dEnd) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat(dateFormate);
		int result = -1;
		Date dt1 = df.parse(dBegin);// 将字符串转换为date类型
		Date dt2 = df.parse(dEnd);
		if (dt1.getTime() > dt2.getTime())// 比较时间大小,如果dt1大于dt2
		{
			result = 0;
		} else {
			result = 1;
		}
		return result;
	}

	/*
	 * Java文件操作 获取文件扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	

	/**
	 * 生成前缀编号方法
	 * 
	 */
	/*
	 * public static String setNumber(String type,String prefix){
	 * 
	 * Ldmax entity = (Ldmax)baseService.getObjectById(Ldmax.class, type);
	 * Integer count = entity.getFieldMax(); Integer length =
	 * entity.getFieldLength();
	 * 
	 * entity.setFieldMax(count+1); baseService.saveOrUpdateObject(entity);
	 * 
	 * if((length - ("" + count).length())>0){ for (int j = 0; j < (length - (""
	 * + count).length()); j++) { prefix += "0"; } } return prefix + count; }
	 *//**
	 * 生成无前缀编号方法
	 * 
	 */
	/*
	 * public static String setNumber(String type){ String number = ""; Ldmax
	 * entity = (Ldmax)baseService.getObjectById(Ldmax.class, type); Integer
	 * count = entity.getFieldMax(); Integer length = entity.getFieldLength();
	 * 
	 * entity.setFieldMax(count+1); baseService.saveOrUpdateObject(entity);
	 * 
	 * if((length - ("" + count).length())>0){ for (int j = 0; j < (length - (""
	 * + count).length()); j++){ number += "0"; } } return number + count; }
	 */

	/**
	 * 获取类文件名的方法
	 * 
	 */
	public static String getClassFileName(Object o) {
		String fileName = o.getClass().getName();
		fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
		return fileName;
	}

	public static String scoketInfo(String params, String dk) {
		// 向服务器端发送请求，服务器IP地址和服务器监听的端口号
		Socket socket;
		// ,String dkIp,int dkPort
		String rec = "";
		try {
			if (dk != "") {
				// //System.out.println(dk+"dk");
				String[] ip_port = dk.split(":");

				// socket = new Socket("10.152.1.156", 7771);
				// socket = new Socket("10.152.1.90", 7771);
				// socket = new Socket(dkIp, dkPort);
				socket = new Socket(ip_port[0], Integer.parseInt(ip_port[1]));
				// 客户端的输出流
				OutputStream os = socket.getOutputStream();
				socket.setSoTimeout(3000);
				// 将信息写入流,把这个信息传递给服务器
				os.write(params.getBytes());

				// 从服务器端接收信息

				InputStream is = socket.getInputStream();

				byte[] buffer = new byte[200];

				int length = is.read(buffer);
				String str = new String(buffer, 0, length);
				// System.out.println(str);
				rec = str;
				if (length > 0) {
					// 关闭资源
					is.close();
					os.close();
					socket.close();
				}
			} else {
				// System.out.println("--ip地址或端口错误---");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rec;

		/*
		 * //通过printWriter 来向服务器发送消息 PrintWriter printWriter = new
		 * PrintWriter(client.getOutputStream());
		 * //System.out.println("连接已建立...");
		 * 
		 * //发送消息 printWriter.println(params);
		 * 
		 * printWriter.flush();
		 * 
		 * //InputStreamReader是低层和高层串流之间的桥梁
		 * //client.getInputStream()从Socket取得输入串流 InputStreamReader streamReader
		 * = new InputStreamReader(client.getInputStream());
		 * 
		 * //链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
		 * BufferedReader reader = new BufferedReader(streamReader); String
		 * advice =reader.readLine();
		 * 
		 * 
		 * //System.out.println("接收到服务器的消息 ："+advice); rec=advice;
		 * reader.close(); // return rec; } catch (UnknownHostException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } return rec;
		 */
	}

	// 去除数组中重复的记录
	public static String array_unique(String a) {
		// array_unique
		String[] systemA = a.split(",");
		String list = "";
		// List<String> list = new LinkedList<String>();
		for (int i = 0; i < systemA.length; i++) {
			if (!list.contains(systemA[i])) {
				// list.add(a[i]);
				list += systemA[i] + ",";
			}
		}
		return list;
	}

	public static String vfor(String issue_params) {
		String[] state = issue_params.split("&");
		String[] params = state[1].split(";");

		return params[0];
	}

	public static String vforT(String issue_params) {
		String[] state = issue_params.split("&");
		String[] params = state[1].split(";");

		return params[1];
	}

	public static String vforTLength(String issue_params) {
		String[] state = issue_params.split("&");
		if (state.length > 1) {
			return state[1];
		} else {
			return "error";
		}

	}

	public static String convertString(String gbk) {
		String utf8 = "";
		try {
			utf8 = new String(gbk2utf8(gbk), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return utf8;
	}

	public static byte[] gbk2utf8(String chenese) {
		char c[] = chenese.toCharArray();
		byte[] fullByte = new byte[3 * c.length];
		for (int i = 0; i < c.length; i++) {
			int m = (int) c[i];
			String word = Integer.toBinaryString(m);

			StringBuffer sb = new StringBuffer();
			int len = 16 - word.length();
			for (int j = 0; j < len; j++) {
				sb.append("0");
			}
			sb.append(word);
			sb.insert(0, "1110");
			sb.insert(8, "10");
			sb.insert(16, "10");

			String s1 = sb.substring(0, 8);
			String s2 = sb.substring(8, 16);
			String s3 = sb.substring(16);

			byte b0 = Integer.valueOf(s1, 2).byteValue();
			byte b1 = Integer.valueOf(s2, 2).byteValue();
			byte b2 = Integer.valueOf(s3, 2).byteValue();
			byte[] bf = new byte[3];
			bf[0] = b0;
			fullByte[i * 3] = bf[0];
			bf[1] = b1;
			fullByte[i * 3 + 1] = bf[1];
			bf[2] = b2;
			fullByte[i * 3 + 2] = bf[2];

		}
		return fullByte;
	}

	public static String[] intersect(String[] arr1, String[] arr2) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		LinkedList<String> list = new LinkedList<String>();
		for (String str : arr1) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}

		if (arr2 == null || "".equals(arr2)) {
			// System.out.println("---arr2kong");
		} else {
			for (String str : arr2) {
				if (map.containsKey(str)) {
					map.put(str, Boolean.TRUE);
				}
			}
		}

		for (Entry<String, Boolean> e : map.entrySet()) {
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}

		String[] result = {};
		return list.toArray(result);
	}

	public static String getProper(String key) {
		InputStream inputStream = PubFun.class.getClassLoader()
				.getResourceAsStream("system.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException ioE) {
			ioE.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties.getProperty(key);
	}

	// 通过截取cmd流方式得到计算机的配置信息(不好)
	public static List<String> getIpAddress() {
		Process p = null;
		List<String> address = new ArrayList<String>();
		try {
			p = new ProcessBuilder("ipconfig", "/all").start();
		} catch (Exception e) {
			return address;
		}
		StringBuffer sb = new StringBuffer();
		// 读取进程输出值
		InputStream inputStream = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		String s = "";
		try {
			while ((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println(sb);
		return address;
	}

	public static void getIpconfig() {
		Map<String, String> map = System.getenv();
		// System.out.println(map.get("USERNAME"));//获取用户名
		// System.out.println(map.get("COMPUTERNAME"));//获取计算机名
		// System.out.println(map.get("USERDOMAIN"));//获取计算机域名
	}

	// 得到计算机的ip地址和mac地址
	// @Test
	public static String getConfig() {
		String sMAC = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			// ni.getInetAddresses().nextElement().getAddress();
			byte[] mac = ni.getHardwareAddress();
			String sIP = address.getHostAddress();

			Formatter formatter = new Formatter();
			for (int i = 0; i < mac.length; i++) {
				sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : "").toString();

			}
			return sMAC;
			// //System.out.println("IP：" + sIP);
			// //System.out.println("MAC：" + sMAC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sMAC;
	}

	// 得到计算机的ip,名称,操作系统名称,操作系统版本
	public static void Config() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString(); // 获取本机ip
			String hostName = addr.getHostName().toString(); // 获取本机计算机名称
			// System.out.println("本机IP："+ip+"\n本机名称:"+hostName);
			Properties props = System.getProperties();
			// System.out.println("操作系统的名称："+props.getProperty("os.name"));
			// System.out.println("操作系统的版本："+props.getProperty("os.version"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// java环境
	public static void all() {
		Properties props = System.getProperties();
		// System.out.println("Java的运行环境版本："+props.getProperty("java.version"));
		// System.out.println("Java的运行环境供应商："+props.getProperty("java.vendor"));
		// System.out.println("Java供应商的URL："+props.getProperty("java.vendor.url"));
		// System.out.println("Java的安装路径："+props.getProperty("java.home"));
		// System.out.println("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version"));
		// System.out.println("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor"));
		// System.out.println("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name"));
		// System.out.println("Java的虚拟机实现版本："+props.getProperty("java.vm.version"));
		// System.out.println("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor"));
		// System.out.println("Java的虚拟机实现名称："+props.getProperty("java.vm.name"));
		// System.out.println("Java运行时环境规范版本："+props.getProperty("java.specification.version"));
		// System.out.println("Java运行时环境规范供应商："+props.getProperty("java.specification.vender"));
		// System.out.println("Java运行时环境规范名称："+props.getProperty("java.specification.name"));
		// System.out.println("Java的类格式版本号："+props.getProperty("java.class.version"));
		// System.out.println("Java的类路径："+props.getProperty("java.class.path"));
		// System.out.println("加载库时搜索的路径列表："+props.getProperty("java.library.path"));
		// System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));
		// System.out.println("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs"));
		// System.out.println("操作系统的名称："+props.getProperty("os.name"));
		// System.out.println("操作系统的构架："+props.getProperty("os.arch"));
		// System.out.println("操作系统的版本："+props.getProperty("os.version"));
		// System.out.println("文件分隔符："+props.getProperty("file.separator"));//在
		// unix 系统中是＂／＂
		// //System.out.println("路径分隔符："+props.getProperty("path.separator"));//在
		// unix 系统中是＂:＂
		// //System.out.println("行分隔符："+props.getProperty("line.separator"));//在
		// unix 系统中是＂/n＂
		// //System.out.println("用户的账户名称："+props.getProperty("user.name"));
		// System.out.println("用户的主目录："+props.getProperty("user.home"));
		// System.out.println("用户的当前工作目录："+props.getProperty("user.dir"));
	}

	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	public static List<String> getDates(String fLeaveStartTime,
			String fLeaveEndTime) {
		List<String> times = new ArrayList<>();
		fLeaveStartTime = fLeaveStartTime.substring(0, 10) + " 00:00:00";
		fLeaveEndTime = fLeaveEndTime.substring(0, 10) + " 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time2 = 0;
		long time3 = 0;
		try {
			time2 = sdf.parse(fLeaveStartTime).getTime();
			time3 = sdf.parse(fLeaveEndTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Long oneDay = 1000 * 60 * 60 * 24L;

		while (time2 < time3) {
			Date d = new Date(time2);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			times.add(df.format(d));
			time2 += oneDay;
		}
		return times;
	}
	
	

	public static Map<String, Object> getCameraUrl(String cookie,
			String strURL, Map<String, Object> params, String msg) {
		Map<String, Object> mso = new HashMap<String, Object>();
		try {
			System.out.println(strURL);
			String result = "";
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.setRequestProperty("user-agent", "Koala Admin"); // 设置用户代理
			connection.setRequestProperty("Cookie", cookie); // 设置cookie
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"); // utf-8编码
			
			JSONObject jsonObject = JSONObject.fromObject(params);  
			System.out.println(msg+"输出1--"+jsonObject.toString());
			out.write(jsonObject.toString());
			out.flush();
			out.close();

			int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			mso.put("code", code + "");
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码

			}

			String sessionId = "";
			String cookieVal = "";
			String key = null;
			// 取cookie
			for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
				if (key.equalsIgnoreCase("set-cookie")) {
					cookieVal = connection.getHeaderField(i);
					cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
					sessionId = sessionId + cookieVal + ";";
				}
			}

			mso.put("result", result);
			mso.put("cookie", sessionId);

		} catch (Exception e) {
			LogInfo.logError(PubFun.class, "getCameraUrl", e, msg);
			mso.put("result", "error");
		}
		return mso; // 自定义错误信息
	}

	public static Map<String, Object> getCameraImageHeader(String cookie,
			String strURL, String imageUrl,Map<String, Object> params, String msg,String photo) {
		Map<String, Object> mso = new HashMap<String, Object>();
		try {
			String result = "";
		    String Enter = "\r\n";  
			String BOUNDARY = "Boundary-b1ed-4060-99b9-fca7ff59c113";
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+BOUNDARY); // 设置发送数据的格式
			//connection.setRequestProperty("user-agent", "Koala Admin"); // 设置用户代理
			//connection.setRequestProperty("", "Koala Admin"); // 设置用户代理
			//connection.setRequestProperty("Cookie", cookie); // 设置cookie
			connection.connect();
			
			byte[] bImg = getCameraImageURL(imageUrl);
			
		//	params.put("photo", bImg);
			
			String urlImage = (String) params.get("locationUrl");
			
			if(urlImage!=null && !urlImage.equals("")){
			
			String[] imageName =  urlImage.split("\\\\");
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			
			 String part1 =  "--" + BOUNDARY + Enter  
	                    + "Content-Type: application/octet-stream" + Enter  
	                    + "Content-Disposition: form-data; filename=\""+imageName[2]+"\"; name=\""+photo+"\"" + Enter + Enter;  
	          //part 2  
	          /*  String part2 = Enter  
	                    + "--" + BOUNDARY + Enter  
	                    + "Content-Type: text/plain" + Enter  
	                    + "Content-Disposition: form-data; name=\"subject_id\"" + Enter + Enter  
	                    + params.get("subject_id") + Enter  
	                    + "--" + BOUNDARY + "--";  */
			
			//OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
	          //  fis.read(xmlBytes);  
	              
	            dos.writeBytes(part1);  
	            dos.write(bImg);  
	          //  dos.writeBytes(part2);  
	              
	            dos.flush();  
	            dos.close();  
	           
			
			
			
			
			}
			
			
			
			
			int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			mso.put("code", code + "");
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				
			}
			mso.put("result", result);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogInfo.logError(PubFun.class, "getCameraUrl", e, msg);
			mso.put("result", "error");
		}
		return mso; // 自定义错误信息
	}
	public static Map<String, Object> getCameraImageHeaderMap(String strURL,Map<String, Object> params, String msg,String photo) {
		Map<String, Object> mso = new HashMap<String, Object>();
		try {
			String result = "";
			String Enter = "\r\n";  
			String BOUNDARY = "Boundary-b1ed-4060-99b9-fca7ff59c113";
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+BOUNDARY); // 设置发送数据的格式
			connection.setRequestProperty("connection", "Keep-Alive");  
			connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");  
			//connection.setRequestProperty("user-agent", "Koala Admin"); // 设置用户代理
			//connection.setRequestProperty("", "Koala Admin"); // 设置用户代理
			//connection.setRequestProperty("Cookie", cookie); // 设置cookie
			connection.connect();
			String urlImage = (String) params.get("UserId");
			String ImageUrl = (String) params.get("ImageUrl");
			String locationUrl = (String) params.get("locationUrl");
			//MultipartFile fileInfo =  (MultipartFile) params.get("file");
		//	byte[] bImg = fileInfo.getBytes(); 
			byte[] bImg = getCameraImageURL("http://127.0.0.1:8080/SmartID_YKT/"+ImageUrl);	
			//	params.put("photo", bImg);
			
		
			
			
				DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

				
				
				String part1 =  "--" + BOUNDARY + Enter  
						+ "Content-Type: application/octet-stream" + Enter  
						+ "Content-Disposition: form-data; filename=\""+locationUrl+"\"; name=\""+photo+"\"" + Enter + Enter
						; 
				 byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
				dos.writeBytes(part1);  
				dos.write(bImg);  
				
				 dos.write(endData);
				
				dos.flush();  
				dos.close();  
				
				
				
				
				
			
			
			
			
			
			int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			mso.put("code", code + "");
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				
			}
			mso.put("result", result);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			LogInfo.logError(PubFun.class, "getCameraUrl", e, msg);
			mso.put("result", "error");
		}
		return mso; // 自定义错误信息
	}
	public static byte[] getCameraImageURL(String imgurl) {
	
		byte[] getData = null;
		try {
			//imgurl = handleUrl(imgurl);
			
			imgurl = imgurl.replaceAll("\\\\", "//");
			
			
			URL url = new URL(imgurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
			getData = readInputStream(inputStream); // 获得图片的二进制数据
			
		} catch (Exception e) {
			e.printStackTrace();
			LogInfo.logError(PubFun.class, "getCameraUrl", e, "获取远程连接");
		}
		return getData; // 自定义错误信息
	}

	
	   /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  
   
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String cookie) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent", "Koala Admin"); // 设置用户代理
			//connection.setRequestProperty("", "Koala Admin"); // 设置用户代理
			connection.setRequestProperty("Cookie", cookie); // 设置cookie
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static Map<String, Object> sendGet(String url,String method) {
    	String result = "";
    	//BufferedReader in = null;
    	Map<String, Object> mso = new HashMap<String, Object>();
    	try {
    		
    		String urlNameString = url;
    		URL realUrl = new URL(urlNameString);
    		// 打开和URL之间的连接
    		HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
    		connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
    		// 设置通用的请求属性
    		connection.setRequestMethod(method); // 设置请求方式
    		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
    		connection.setRequestProperty("Content-Type", "application/json");//设置编码格式
    		//connection.setRequestProperty("connection", "Keep-Alive");  
    		// 建立实际的连接
    		connection.connect();
    		int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			mso.put("code", code + "");
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				
			}
			mso.put("result", result);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			LogInfo.logError(PubFun.class, "sendGet", e, "");
			mso.put("result", "{'error': 'get: 异常'}");
		}
		return mso; // 自定义错误信息
    	
    }

    
    
    
    
    
    
    
    
    
    
    
	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String dateFormate,String specifiedDay,int addDate,int dateF) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(dateFormate).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(dateF);
		c.set(dateF, day + addDate);
//Calendar.DATE
		String dayAfter = new SimpleDateFormat(dateFormate).format(c.getTime());
		return dayAfter;
	}
	
	
	
	/** 
	 * 获取精确到秒的时间戳 
	 * @return 
	 */  
	public static int getSecondTimestamp(Date date){  
	    if (null == date) {  
	        return 0;  
	    }  
	    String timestamp = String.valueOf(date.getTime());  
	    int length = timestamp.length();  
	    if (length > 3) {  
	        return Integer.valueOf(timestamp.substring(0,length-3));  
	    } else {  
	        return 0;  
	    }  
	}  
	  public static String decode(String url)     
	     {     
	               try {     
	                    String prevURL="";     
	                    String decodeURL=url;     
	                    while(!prevURL.equals(decodeURL))     
	                    {     
	                         prevURL=decodeURL;     
	                         decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );     
	                    }     
	                    return decodeURL;     
	               } catch (UnsupportedEncodingException e) {     
	                    return "Issue while decoding" +e.getMessage();     
	               }     
	     }     
	     public static String encode(String url)     
	     {     
	               try {     
	                    String encodeURL=URLEncoder.encode( url, "UTF-8" );     
	                    return encodeURL;     
	               } catch (UnsupportedEncodingException e) {     
	                    return "Issue while encoding" +e.getMessage();     
	               }     
	     }     
	public static void main(String[] args) throws UnsupportedEncodingException {

		// PubFun tPunFun = new PubFun();
		// String recString = PubFun.scoketInfo("<READCARD|T8|0>",
		// "127.0.0.1:2000");
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<Date> dateList = getBetweenDates(
					sdf.parse("2018-03-21 19:00:37.0"),
					sdf.parse("2018-03-22 19:00:40.0"));

			for (Date date : dateList) {

				System.out.println(sdf.format(date));

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String urlAddress = Constant.Camera_Url + "/g/人像2/";
		   Map<String, Object> mso = PubFun.sendGet(urlAddress,"DELETE");
		//Map<String, Object> mso = PubFun.sendGet(urlAddress,"DELETE");
	/*	
		String result = (String) mso.get("result");
		System.out.println(result);*/
		// System.out.println(recString);
		// // System.out.println(tPunFun.getCurrentTime());

	}
}
