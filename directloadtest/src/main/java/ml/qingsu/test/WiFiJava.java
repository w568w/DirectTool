package ml.qingsu.test;

import android.app.ListActivity;

public class WiFiJava extends ListActivity {
//	SimpleAdapter sa;
//	List<Map<String, Object>> list;
//	WifiManager wm;
//	WifiSearcher ws;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//		
//		list=new ArrayList<Map<String, Object>>();
//		ws=new WifiSearcher(this, new SearchWifiListener() {
//			@Override
//			public void onSearchWifiFailed(ErrorType errorType) {
//				
//			}
//			@Override
//			public void onSearchWifiSuccess(final List<ScanResult> results) {
//				WiFiJava.this.runOnUiThread(new Runnable() {
//					
//					@Override
//					public void run() {
//						setTitle("扫描完成!");
//						list=getData(results);
//						sa=new SimpleAdapter(WiFiJava.this,list,R.layout.main_activity_items,new String[]{"icon","title","info"}, new int[]{R.id.imageView_title,R.id.textView_title,R.id.textView_title_introduction});
//						setListAdapter(sa);
//					}
//				});
//			}
//		});
//		ws.search();
//		
//		sa=new SimpleAdapter(WiFiJava.this,list,R.layout.main_activity_items,new String[]{"icon","title","info"}, new int[]{R.id.imageView_title,R.id.textView_title,R.id.textView_title_introduction});
//		setListAdapter(sa);
//	}
//	private List<Map<String, Object>> getData(List<ScanResult> results)
//	{
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		List<ScanResult> wifilist=results;
//		Iterator<ScanResult> iter=wifilist.iterator();
//		while(iter.hasNext())
//		{
//			ScanResult sr=iter.next();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("icon", R.drawable.wifi);
//			map.put("title",sr.SSID);
//			map.put("info", sr.BSSID);
//			list.add(map);
//		}
//		return list;
//	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add(Menu.NONE, 2, 2, "刷新");
//		menu.add(Menu.NONE, 4, 4, "到网络设置");
//		menu.add(Menu.NONE, 3, 3, "获取密码");
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch(item.getItemId())
//		{
//		case 2:
//			ws.search();
//			break;
//		case 4:
//			CommonTools.Network_OpenNetworkSettings(this);
//			break;
//		case 3:
//			if(!CommonTools.Network_isAvailable(this))
//			{
//				Intent i=new Intent(this, DialogJava.class);
//				i.putExtra("hint", "您还没有打开其他可用网络。");
//				i.putExtra("button", "转到网络设置");
//				i.putExtra("TurntoNetworkSettings", true);
//				startActivity(i);
//				return super.onOptionsItemSelected(item);
//			}
//			final Iterator<Map<String, Object>> iter=list.iterator();
//			setProgressBarIndeterminateVisibility(true);
//			setTitle("正在获取,完成前请勿退出或重新扫描!");
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					final List<Map<String, Object>> list_t = new ArrayList<Map<String, Object>>();
//					while(iter.hasNext())
//					{
//						Map<String, Object> m=iter.next();
//						String ssid=(String)m.get("title");
//						String bssid=(String)m.get("info");
//						String pass=Wifipasstool.Getpass(bssid, ssid);
//						if(pass==null)
//						{
//							Map<String, Object> map = new HashMap<String, Object>();
//							map.put("icon", R.drawable.wifi);
//							map.put("title",ssid);
//							map.put("info", "未找到密码!");
//							list_t.add(map);
//						}
//						else
//						{
//							Map<String, Object> map = new HashMap<String, Object>();
//							map.put("icon", R.drawable.wifi);
//							map.put("title",ssid);
//							map.put("info", "密码为:"+pass);
//							list_t.add(map);
//						}
//					}
//					WiFiJava.this.runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							setProgressBarIndeterminateVisibility(false);
//							setTitle("获取完成!");
//							sa=new SimpleAdapter(WiFiJava.this,list_t,R.layout.main_activity_items,new String[]{"icon","title","info"}, new int[]{R.id.imageView_title,R.id.textView_title,R.id.textView_title_introduction});
//							setListAdapter(sa);
//						}
//					});
//				}
//			}).start();
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
