package ml.qingsu.test;

import android.app.Activity;

public class IceboxJava extends Activity {
//	private final static String[] showdisable=new String[]{"pm","list","packages","-d"};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		SharedPreferences sp=getSharedPreferences("icebox", MODE_PRIVATE);
//		super.onCreate(savedInstanceState);
//		//加载已禁用的APP
//		CommandResult cr=ShellUtils.execCommand(showdisable, true, true);
//		if(cr.result==-1)
//		{
//			CommonTools.Toast_long(IceboxJava.this, "错误!ROOT了吗?");
//			return;
//		}
//		else
//		{
//			String[] results=cr.successMsg.split("\n");
//			for (int i = 0; i < results.length; i++) {
//				results[i]=results[i].substring(results[i].indexOf("=")+1);
//				try {
//					CommonTools.System_Get_Disable_App_Info(IceboxJava.this, results[i]);
//				} catch (NameNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		}
//	}
//
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//
//		super.onListItemClick(l, v, position, id);
//
//	}
//
//
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add("添加新应用");
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if(item.getTitle().equals("添加新应用"))
//		{
//
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//
//		return super.onTouchEvent(event);
//	}
//	private SimpleAdapter prase(String org) {
//		String[] apps=org.split("||");
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//		if(apps==null||apps.length==0)
//			return null;
//		for (String app : apps)
//		{
//			String[] ap=app.split(",,,",2);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("icon",R.drawable.ic_launcher);
//			map.put("title", ap[0]);
//			map.put("info", ap[1]);
//			list.add(map);
//		}
//		return new SimpleAdapter(IceboxJava.this, list, R.layout.main_activity_items, new String[]{"icon","title","info"}, new int[]{R.id.imageView_title,R.id.textView_title,R.id.textView_title_introduction});
//	}

}
