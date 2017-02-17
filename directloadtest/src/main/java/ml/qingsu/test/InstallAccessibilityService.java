package ml.qingsu.test;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by http://blog.csdn.net/guolin_blog/article/details/47803149
 */
public class InstallAccessibilityService extends AccessibilityService {
    Map<Integer, Boolean> handledMap = new HashMap<>();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 16) {
            AccessibilityNodeInfo nodeInfo = accessibilityEvent.getSource();
            if (nodeInfo != null) {
                int eventType = accessibilityEvent.getEventType();
                if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                        eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                    if (handledMap.get(accessibilityEvent.getWindowId()) == null) {
                        boolean handled = iterateNodesAndHandle(nodeInfo);
                        if (handled) {
                            handledMap.put(accessibilityEvent.getWindowId(), true);
                        }
                    }
                }
            }
        }
    }

    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        if (Build.VERSION.SDK_INT >= 16) {
            if (nodeInfo != null) {
                int childCount = nodeInfo.getChildCount();
                if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                    String nodeContent = nodeInfo.getText().toString();
                    if ("安装".equals(nodeContent)
                            || "完成".equals(nodeContent)
                            || "确定".equals(nodeContent)) {
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        return true;
                    }
                } else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                }
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                    if (iterateNodesAndHandle(childNodeInfo)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }
}
