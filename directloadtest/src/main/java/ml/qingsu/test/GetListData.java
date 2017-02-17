package ml.qingsu.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetListData {
	public static List<Map<Integer, Object>> getQQData()
	{

		List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		
		map.put(1, "QQ空间自定义打赏");
		map.put(2, "假装打赏(只能用在QQ空间说说评论里!)");
		list.add(map);

		map=new HashMap<Integer, Object>();
		
		map.put(1, "QQ拉圈圈");
		map.put(2, "获取QQ个性名片赞...接口有限，请勿滥用！");	
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "QQ撤回消息自定义");
		map.put(2, "[姓名]撤回了一条消息[动作]");
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "QQ音乐加速");
		map.put(2, "一次加速2小时...接口有限，请勿滥用！");	
		list.add(map);
		
        map = new HashMap<Integer, Object>();
        
        map.put(1, "QQ聊天字数突破");
        map.put(2, "灵感来自AD盒子作者#(滑稽)"); 
        list.add(map);		
		return list;
	}
	public static List<Map<Integer, Object>> getSoData()
	{
		List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, "手机乐园搜索");
		map.put(2, "你见过体积这么小的搜索吗?");		
		list.add(map);
	
		map = new HashMap<Integer, Object>();
		
		map.put(1, "酷安应用搜索");
		map.put(2, "你见过体积这么小的搜索吗?");		
		list.add(map);
		
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "百度网盘搜索");
		map.put(2, "好像挂了，节哀顺变~~~");
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "种子搜索");
		map.put(2, "蛤?#(滑稽)#(阴险)");
		list.add(map);
//
		return list;
	}	
	public static List<Map<Integer, Object>> getSystemData()
	{
		List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		
		map.put(1, "SELinux开关");
		map.put(2, "使V4A可正常运行");
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "去除通知栏感叹号");
		map.put(2, "去除Android5以上通知栏感叹号，需ROOT权限!");
		list.add(map);
		
		return list;
	}
	public static List<Map<Integer, Object>> getHappyData()
	{
		List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		
		map.put(1, "simsimi");
		map.put(2, "与某人聊天←_←");	
		list.add(map);

		map = new HashMap<Integer, Object>();
		map.put(1, "压力测试");
		map.put(2, "通过不断新建窗口，达到跑分的效果!");
		list.add(map);

		map = new HashMap<Integer, Object>();
		map.put(1, "虚拟短信");
		map.put(2, "感谢一个木函开发者的指导(PY)!");
		list.add(map);

		
		map = new HashMap<Integer, Object>();
		map.put(1, "素数判断");
		map.put(2, "灵感来自网友xzr467706992!");	
		list.add(map);

		map = new HashMap<>();
		
		map.put(1, "获取Bilibili封面");
		map.put(2, "获取AV封面...");
		list.add(map);

		map = new HashMap<>();
		
		map.put(1, "烧机");
		map.put(2, "灵感来自网友xzr467706992!\n(好吧是我自己的灵感)");
		list.add(map);

		map = new HashMap<Integer, Object>();
		
		map.put(1, "停止运行");
		map.put(2, "灵感来自网友hjthjthjt!");	
		list.add(map);	
		
		map = new HashMap<Integer, Object>();

		map.put(1, "Booru壹图");
		map.put(2, "随机选择一张konachan.net上的图片供欣赏!");	
		list.add(map);	
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "蛤蛤蛤");
		map.put(2, "蛤蛤蛤蛤蛤蛤蟆");	
		list.add(map);
		return list;
	}
	public static List<Map<Integer, Object>> getData()
	{
		List<Map<Integer, Object>> list = new ArrayList<>();
		

		Map<Integer, Object> map = new HashMap<>();
		
		map.put(1, "WiFi密码查看");
		map.put(2, "便捷查看WiFi密码");
		list.add(map);

		map = new HashMap<>();
		
		map.put(1, "Todo List");
		map.put(2, "一个简单的TODO~");
		list.add(map);

		map = new HashMap<>();
		
		map.put(1, "手电筒");
		map.put(2, "最简单的手电筒,点击即可开关!");
		list.add(map);
		
		map = new HashMap<>();
		
		map.put(1, "快速翻译");
		map.put(2, "有道每小时仅1000次用量，建议用自己的APIkey!");
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "网易云音乐启动背景替换");
		map.put(2, "自定义你的背景图片");
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "关机重启");
		map.put(2, "最小关机重启");
		list.add(map);		
		

	
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "天气预报");
		map.put(2, "你见过这么小的天气预报么?");	
		list.add(map);
		
		
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "生成二维码");
		map.put(2, "完全不知道要这东西干嘛");	
		list.add(map);

		map = new HashMap<Integer, Object>();
		
		map.put(1, "生成短网址");
		map.put(2, "生成短网址~");
		list.add(map);
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "一键锁屏");
		map.put(2, "创建一键锁屏快捷方式!");	
		list.add(map);	

		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "轰炸机");
		map.put(2, "调用网页，勿滥用!");	
		list.add(map);	
		

		
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "测量小工具");
		map.put(2, "分贝计、指南针之类的");	
		list.add(map);

		map = new HashMap<>();
		
		map.put(1, "MD5加密");
		map.put(2, "38a57032085441e7!");
		list.add(map);
		
		
		map = new HashMap<Integer, Object>();
		
		map.put(1, "设置");
		map.put(2, "一些设置");			
		list.add(map);
		
		
		return list;
	}
}
