package com.dhsr.smartid.util;

import java.io.UnsupportedEncodingException;

public class Tranform {
	 //中文转换成UTF-8编码（16进制字符串)，每个汉字3个字节  
	  public String Chinese2UTF_8(String chineseStr)throws Exception {  
	    StringBuffer utf8Str = new StringBuffer();  
	    byte[] utf8Decode = chineseStr.getBytes("utf-8");  
	    for (byte b : utf8Decode)   
	        utf8Str.append(Integer.toHexString(b&0xFF));  
	    return utf8Str.toString().toUpperCase();  
	  }   
	      
	  //中文转换成GBK码(16进制字符串)，每个汉字2个字节  
	  public String Chinese2GBK(String chineseStr)throws Exception {  
	    StringBuffer GBKStr = new StringBuffer();  
	    byte[] GBKDecode = chineseStr.getBytes("gbk");  
	    for (byte b : GBKDecode)   
	        GBKStr.append(Integer.toHexString(b&0xFF));  
	    return GBKStr.toString().toUpperCase();  
	    }  
	      
	      
	  //16进制GBK字符串转换成中文  
	  public String GBK2Chinese(String GBKStr)throws Exception{  
	    byte[] b = HexString2Bytes(GBKStr);  
	    String chineseStr = new String(b, "gbk");//输入参数为字节数组  
	    return chineseStr;  
	   }  
	    
	  //把16进制字符串转换成字节数组  
	  public byte[] HexString2Bytes(String hexStr) {  
	     byte[] b = new byte[hexStr.length() / 2];  
	     for (int i = 0; i < b.length; i++)   
	       b[i]=(byte) Integer.parseInt(hexStr.substring(2*i,2*i+2),16);  
	    return b;  
	  }  
	  
	      
	   //把字节数组转换成16进制字符串  
	   public static final String bytesToHexString(byte[] byteArray){  
	     StringBuffer hexStr = new StringBuffer(byteArray.length*2);  
	     for (int i = 0; i < byteArray.length; i++) {  
	         String sTemp= Integer.toHexString(0xFF& byteArray[i]);  
	         int j=0;  
	         while(j<2-sTemp.length())  
	             {sTemp="0"+sTemp;j++;}  
	         hexStr.append(sTemp.toUpperCase());  
	       }  
	      return hexStr.toString();  
	    }  
	   public static String toGBK(String source) throws UnsupportedEncodingException {  
	        StringBuilder sb = new StringBuilder();  
	        byte[] bytes = source.getBytes("GBK");  
	        for(byte b : bytes) {  
	            sb.append("%" + Integer.toHexString((b & 0xff)).toUpperCase());  
	        }  
	          
	        return sb.toString();  
	    }  
	   public static void main(String args[]) throws Exception {  
	        String chineseStr="我们";  
	        Tranform t=new Tranform();  
	        System.out.println(Tranform.toGBK(chineseStr));  
	        System.out.println(t.Chinese2GBK(chineseStr));  
	        System.out.println(t.GBK2Chinese(t.Chinese2GBK(chineseStr)));  
	    }  
	  
}
