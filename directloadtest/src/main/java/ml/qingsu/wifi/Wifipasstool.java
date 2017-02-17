package ml.qingsu.wifi;

public class Wifipasstool {
//	public static String Getpass(String bssid,String ssid) {
//		if(bssid.isEmpty()||ssid.isEmpty())return null;
//		Random r=new Random();
//		String sn=request(bssid, ssid,MD5.stringMD5(r.nextInt(10000)+""));
//		
//		try {
//			JSONObject jo=new JSONObject(sn);
//			String ret=request(bssid, ssid,jo.getString("retSn"));
//			
//			jo=new JSONObject(ret);
//			JSONObject psws=jo.getJSONObject("qryapwd").getJSONObject("psws");
//			@SuppressWarnings("rawtypes")
//			Iterator i=psws.keys();
//			while(i.hasNext())
//			{
//				String key=(String)i.next();
//				JSONObject temp_item=psws.getJSONObject(key);
//				String temp_coded_pass=temp_item.getString("pwd");
//				String decoded=decode_pass(temp_coded_pass);
//				String passlength_str=decoded.substring(0, 3);
//				int passlength=Integer.valueOf(passlength_str).intValue();
//				String temp_pass=decoded.substring(3, 3+passlength);
//				return temp_pass;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		return null;
//		
//	}
//	private static String request(String bssid,String ssid,String salt)
//	{
//		final String dhid="ff8080814cc5798a014ccbbdfa375369";
//		final String apiUrl="http://wifiapi02.51y5.net/wifiapi/fa.cmd";
//		List <BasicNameValuePair> params=null; 
//		HttpResponse httpResponse = null; 
//		params=new ArrayList<BasicNameValuePair>(); 
//		WifiManager mWifi = (WifiManager) MyApplication.getContextObject().getSystemService(Context.WIFI_SERVICE);
//		String mac=mWifi.getConnectionInfo().getMacAddress().replace(":", "").toLowerCase();
//		params.add(new BasicNameValuePair("appid", "0008"));
//		params.add(new BasicNameValuePair("bssid", bssid));
//		params.add(new BasicNameValuePair("chanid", "gw"));
//		params.add(new BasicNameValuePair("dhid", dhid));
//		params.add(new BasicNameValuePair("ii", MD5.stringMD5(mac)));
//		params.add(new BasicNameValuePair("lang", "cn"));
//		params.add(new BasicNameValuePair("mac", mac));
//		params.add(new BasicNameValuePair("method", "getDeepSecChkSwitch"));
//		params.add(new BasicNameValuePair("pid", "qryapwd:commonswitch"));
//		params.add(new BasicNameValuePair("ssid", ssid));
//		params.add(new BasicNameValuePair("st","m"));
//		params.add(new BasicNameValuePair("uhid","a0000000000000000000000000000001"));
//		params.add(new BasicNameValuePair("v","324"));
//		String sign=sign(new String[]{"0008",bssid,"gw",dhid,MD5.stringMD5(mac),"cn",mac,"getDeepSecChkSwitch","qryapwd:commonswitch",ssid,"m","a0000000000000000000000000000001","324"}, salt);
//		params.add(new BasicNameValuePair("sign",sign));
//		HttpPost hp=new HttpPost(apiUrl);
//		try {
//			hp.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//			HttpClient hc=new DefaultHttpClient();
//			hc.getParams().setParameter(HttpProtocolParams.USER_AGENT,"WiFiMasterKey/1.1.0 (Mac OS X Version 10.10.3 (Build 14D136))");
//			httpResponse=hc.execute(hp);  
//			 if (httpResponse.getStatusLine().getStatusCode() == 200) { 
//	                String result = EntityUtils.toString(httpResponse.getEntity()); 
//	                
//	                return result;
//	            } 
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//		
//	}
//	private static String sign(String[] array,String salt)
//	{
//		String request_str="";
//		for (String str : array) {
//			request_str+=str;
//		}
//		
//		String sign=MD5.stringMD5(request_str+salt);
//		return sign.toUpperCase();
//	}
//	private static String decode_pass(String codedpass)
//	{
//		
//		try {
//			return desEncrypt(codedpass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	private static String desEncrypt(String data) throws Exception {
//        try
//        {
//            
//         
//            return decrypt(str2pack(data));
//            
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//	private static byte[] str2pack(String str) {  
//	    int nibbleshift = 4;  
//	    int position = 0;  
//	    int len = str.length()/2 + str.length()%2;  
//	      
//	    byte[] output = new byte[len];  
//	    for (char v : str.toCharArray()) {  
//	        byte n = (byte) v;  
//	        if (n >= '0' && n <= '9') {  
//	            n -= '0';  
//	        } else if (n >= 'A' && n <= 'F') {  
//	            n -= ('A' - 10);  
//	        } else if (n >= 'a' && n <= 'f') {  
//	            n -= ('a' - 10);  
//	        } else {  
//	            continue;  
//	        }  
//	          
//	        output[position] |= (n << nibbleshift);  
//	  
//	        if (nibbleshift == 0) {  
//	            position++;  
//	        }  
//	        nibbleshift = (nibbleshift + 4) & 7;  
//	    }  
//	      
//	    return output;  
//	}    
//	private static String decrypt(byte[] decoded) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//		final String iv="y!0Oe#2Wj#6Pw!3V";
//		final String key="k%7Ve#8Ie!5Fb&8E";
//		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());  
//		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);  
//		byte[] decrypted = cipher.doFinal(decoded);  
//		return new String(decrypted);
//	}
}
