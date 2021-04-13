package com.dhsr.smartid.base.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.stereotype.Controller;

/**
 * 基础Controller，其它Controller都要继承它
 * 
 * @author qidong
 *
 */
@Controller
public class BaseController {
	// 操作员SESSION
	public static final String Operator_SESSION = "operatorSession";
	// 返回前台信息
	public static final String ACTION_RESULT = "actionResult";

	// 返回前台的包装方法
	public void renderText(HttpServletResponse response, Object result) throws IOException {
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		out.print(result);
		out.flush();
		out.close();
	}

	@Test
	public void getUserInfo(){
		String token = "3212313231";
		long time = new Date().getTime();
		String a = "{\"token\":\""+token+"\"}";
		String str = "";
		try {
			//HttpSSL.solveSSL();
            URL url = new URL("http://localhost:8002/token/"+token);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
            conn.setRequestMethod("GET");
            //设置通用的请求属性
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数即数据
           // out.print(a);
            //缓冲数据
            out.flush();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
