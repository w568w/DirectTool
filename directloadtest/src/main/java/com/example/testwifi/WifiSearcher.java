package com.example.testwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WifiSearcher {
    private static final int WIFI_SEARCH_TIMEOUT = 20; //扫描WIFI的超时时间
    private final Context mContext;
    private final WifiManager mWifiManager;
    private final WifiSearcher.WiFiScanReceiver mWifiReceiver;
    private final Lock mLock;
    private final Condition mCondition;
    private final WifiSearcher.SearchWifiListener mSearchWifiListener;
    private boolean mIsWifiScanCompleted;

    public enum ErrorType {
        SEARCH_WIFI_TIMEOUT, //扫描WIFI超时(一直搜不到结果)
        NO_WIFI_FOUND, //扫描WIFI结束，没有找到任何WIFI信号
    }

    //扫描结果通过该接口返回给Caller
    public interface SearchWifiListener {
        void onSearchWifiFailed(WifiSearcher.ErrorType errorType);

        void onSearchWifiSuccess(List<ScanResult> results);
    }

    public WifiSearcher(Context context, WifiSearcher.SearchWifiListener listener) {
        this.mContext = context;
        this.mSearchWifiListener = listener;
        this.mLock = new ReentrantLock();
        this.mCondition = this.mLock.newCondition();
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
        this.mWifiReceiver = new WifiSearcher.WiFiScanReceiver();
    }

    public void search() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//如果WIFI没有打开，则打开WIFI
                if (!WifiSearcher.this.mWifiManager.isWifiEnabled()) {
                    WifiSearcher.this.mWifiManager.setWifiEnabled(true);
                }
//注册接收WIFI扫描结果的监听类对象
                WifiSearcher.this.mContext.registerReceiver(WifiSearcher.this.mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//开始扫描
                WifiSearcher.this.mWifiManager.startScan();
                WifiSearcher.this.mLock.lock();
//阻塞等待扫描结果
                try {
                    WifiSearcher.this.mIsWifiScanCompleted = false;
                    WifiSearcher.this.mCondition.await(WifiSearcher.WIFI_SEARCH_TIMEOUT, TimeUnit.SECONDS);
                    if (!WifiSearcher.this.mIsWifiScanCompleted) {
                        WifiSearcher.this.mSearchWifiListener.onSearchWifiFailed(WifiSearcher.ErrorType.SEARCH_WIFI_TIMEOUT);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WifiSearcher.this.mLock.unlock();
//删除注册的监听类对象
                try {
                    WifiSearcher.this.mContext.unregisterReceiver(WifiSearcher.this.mWifiReceiver);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //系统WIFI扫描结果消息的接收者
    protected class WiFiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context c, Intent intent) {
//提取扫描结果
            List<String> ssidResults = new ArrayList<String>();
            List<ScanResult> scanResults = WifiSearcher.this.mWifiManager.getScanResults();
            for (ScanResult result : scanResults) {
                ssidResults.add(result.SSID);
            }
//检测扫描结果
            if (ssidResults.isEmpty()) {
                WifiSearcher.this.mSearchWifiListener.onSearchWifiFailed(WifiSearcher.ErrorType.NO_WIFI_FOUND);
            } else {
                WifiSearcher.this.mSearchWifiListener.onSearchWifiSuccess(scanResults);
            }
            WifiSearcher.this.mLock.lock();
            WifiSearcher.this.mIsWifiScanCompleted = true;
            WifiSearcher.this.mCondition.signalAll();
            WifiSearcher.this.mLock.unlock();
        }
    }
}