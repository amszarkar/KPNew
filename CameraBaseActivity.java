/**
 * <p>DemoActivity Class</p>
 *
 * @author zhuzhenlei 2014-7-17
 * @version V1.0
 * @modificationHistory
 * @modify by user:
 * @modify by reason:
 */
package velociter.kumar.property;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.*;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.Toast;
import com.hikvision.netsdk.*;
import org.MediaPlayer.PlayM4.Player;


/**
 * <pre>
 *  ClassName  DemoActivity Class
 * </pre>
 *
 * @author zhuzhenlei
 * @version V1.0
 * @modificationHistory
 */
public class CameraBaseActivity extends Activity implements Callback{
    private SurfaceView m_osurfaceView = null;
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime
    private int m_iPort = -1; // play port
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number
    private final String TAG = "DemoActivity";
    private boolean m_bNeedDecode = true;
    private boolean m_bStopPlayback = false;
    private Thread thread;
    private Thread thread2;
    private boolean isShow = true;
    private CameraManager h1;

  /*  public final String ADDRESS = "103.220.213.86";
    public final int PORT = 8000;
    public final String USER = "admin";
    public final String PSD = "Prajwal@12345";*/

    public  String ADDRESS ;
    public final int PORT = 8000;
    public  String USER ;
    public  String PSD;
    private Button btnBack;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // CrashUtil crashUtil = CrashUtil.getInstance();

       // HCNetSDK hcNetSDK = HCNetSDK.getInstance();//**

       // crashUtil.init(this);
      //  app = (AppData) getApplication();
        setContentView(R.layout.main);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ADDRESS=getIntent().getStringExtra("ip");
        USER=getIntent().getStringExtra("user");
        PSD=getIntent().getStringExtra("pass");
        if (!initeSdk()) {
            this.finish();
            return;
        }

        if (!initeActivity()) {
            this.finish();
            return;
        }
        // login on the device
        m_iLogID = loginDevice();
        if (m_iLogID < 0) {
            Log.e(TAG, "This device logins failed!");
            new AlertDialog.Builder(this)
                    .setTitle("Kumar Properties")
                    .setMessage("something went wrong please contact us..")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }})
                    .show();
            return;
        } else {
            System.out.println("m_iLogID=" + m_iLogID);
        }
        // get instance of exception callback and set
        ExceptionCallBack oexceptionCbf = getExceptiongCbf();
        if (oexceptionCbf == null) {
            Log.e(TAG, "ExceptionCallBack object is failed!");
            return;
        }

        if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
                oexceptionCbf)) {
            Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
            return;
        }

        //??????
        final NET_DVR_PREVIEWINFO ClientInfo = new NET_DVR_PREVIEWINFO();
        ClientInfo.lChannel = 0;
        ClientInfo.dwStreamType = 0; // substream
        ClientInfo.bBlocked = 1;

        //???????????????
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    SystemClock.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isShow)
                                startSinglePreview();
                          // Toast.makeText(CameraBaseActivity.this, "please Wait", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
        thread.start();

       /* //????????????
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    SystemClock.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isShow)

                                startSinglePreview();
                            Toast.makeText(CameraBaseActivity.this, "hello HiKivison", Toast.LENGTH_LONG).show();
                        *//*    HCNetSDK.getInstance().NET_DVR_SetDVRConfig();
                            HCNetSDK.getInstance().NET_DVR_GetDVRConfig();
                            hcNetSDK.NET_DVR_Mau*//*
                        }
                    });
                }
            }
        });
        thread2.start();*/


}

////////////////////////////////////////////////

    public void btn_onClick(View view){

        Toast.makeText(CameraBaseActivity.this, "hello HiKivison", Toast.LENGTH_LONG).show();
    }

    // @Override
    public void surfaceCreated(SurfaceHolder holder) {
       // m_osurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        m_osurfaceView.getHolder().setFormat(PixelFormat.UNKNOWN);
        Log.i(TAG, "surface is created" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        Surface surface = holder.getSurface();
        if (surface.isValid()) {
            if (!Player.getInstance()
                    .setVideoWindow(m_iPort, 0, holder)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }

    // @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    // @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        if (holder.getSurface().isValid()) {
            if (!Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("m_iPort", m_iPort);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        finish();
       /* m_iPort = savedInstanceState.getInt("m_iPort");
        super.onRestoreInstanceState(savedInstanceState);*/
    }



    /**
     * @return true - success;false - fail
     * @fn initeSdk
     * @author zhuzhenlei
     * @brief SDK init
     */
    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/", true);
        return true;
    }

    // GUI init
    private boolean initeActivity() {
        findViews();
        m_osurfaceView.getHolder().addCallback(this);
        return true;
    }

    // get controller instance
    private void findViews() {


        this.m_osurfaceView = (SurfaceView) findViewById(R.id.sf_VideoMonitor);

    }

   /* @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (!NotNull.isNotNull(h1)) return false;
        Log.d(TAG, "onTouch: ");
        new Thread() {
            @Override
            public void run() {
                switch (v.getId()) {
                    case R.id.btn_Up:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            h1.startMove(8, m_iLogID);
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            h1.stopMove(8, m_iLogID);
                        }
                        break;
                    case R.id.btn_Left:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            h1.startMove(4, m_iLogID);
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            h1.stopMove(4, m_iLogID);
                        }
                        break;
                    case R.id.btn_Right:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            h1.startMove(6, m_iLogID);
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            h1.stopMove(6, m_iLogID);
                        }
                        break;
                    case R.id.btn_Down:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            h1.startMove(2, m_iLogID);
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            h1.stopMove(2, m_iLogID);
                        }
                        break;
                    case R.id.btn_ZoomIn:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            h1.startZoom(1, m_iLogID);
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            h1.stopZoom(1, m_iLogID);
                        }
                        break;
                    case R.id.btn_ZoomOut:
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            h1.startZoom(-1, m_iLogID);
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            h1.stopZoom(-1, m_iLogID);
                        }
                        break;
                    default:
                        break;
                }
            }
        }.start();
        return false;
    }*/


    private AlertDialog getDialongView(View view) {
        final AlertDialog.Builder builder6 = new AlertDialog.Builder(CameraBaseActivity.this);
        builder6.setView(view);
        builder6.create();
        AlertDialog dialog = builder6.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        return dialog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cleanup();
        m_iLogID = -1;
        // whether we have logout
        if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
            Log.e(TAG, " NET_DVR_Logout is failed!");
            return;
        }
        stopSinglePreview();
    }
    private void startSinglePreview() {
        if (m_iPlaybackID >= 0) {
            Log.i(TAG, "Please stop palyback first");
            return;
        }
        RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
        if (fRealDataCallBack == null) {
            Log.e(TAG, "fRealDataCallBack object is failed!");
            return;
        }
        Log.i(TAG, "m_iStartChan:" + m_iStartChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = 0; // substream
        previewInfo.bBlocked = 1;
//
        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID,
                previewInfo, fRealDataCallBack);
        if (m_iPlayID < 0) {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
        isShow = false;
        if (NotNull.isNotNull(thread)) {
            thread.interrupt();
        }
        h1 = new CameraManager();
        h1.setLoginId(m_iLogID);
        Intent intent = getIntent();
       /* if (NotNull.isNotNull(intent) && intent.getIntExtra("INDEX", -1) != -1) {
            int point = app.preferences.getInt("POINT", 0);
            boolean b = HCNetSDK.getInstance().NET_DVR_PTZPreset(m_iPlayID, PTZCommand.GOTO_PRESET,
                    point);
        }*/
    }
    /**
     * @return NULL
     * @fn stopSinglePreview
     * @author zhuzhenlei
     * @brief stop preview
     */
    private void stopSinglePreview() {
        if (m_iPlayID < 0) {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }
        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
            Log.e(TAG, "StopRealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }

        m_iPlayID = -1;
        stopSinglePlayer();
    }

    private void stopSinglePlayer() {
        Player.getInstance().stopSound();
        // player stop play
        if (!Player.getInstance().stop(m_iPort)) {
            Log.e(TAG, "stop is failed!");
            return;
        }

        if (!Player.getInstance().closeStream(m_iPort)) {
            Log.e(TAG, "closeStream is failed!");
            return;
        }
        if (!Player.getInstance().freePort(m_iPort)) {
            Log.e(TAG, "freePort is failed!" + m_iPort);
            return;
        }
        m_iPort = -1;
    }

    /**
     * @return login ID
     * @fn loginNormalDevice
     * @author zhuzhenlei
     * @brief login on device
     */
    private int loginNormalDevice() {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(ADDRESS, PORT,
                USER, PSD, m_oNetDvrDeviceInfoV30);
        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            new AlertDialog.Builder(this)
                    .setTitle("Kumar Properties")
                    .setMessage("something went wrong please contact us..")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }})
                    .show();
            return -1;
        }
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
        }
        Log.i(TAG, "NET_DVR_Login is Successful!");
        return iLogID;
    }

    /**
     * @return login ID
     * @fn loginDevice
     * @author zhangqing
     * @brief login on device
     */
    private int loginDevice() {
        int iLogID = -1;
        iLogID = loginNormalDevice();
        return iLogID;
    }

    /**
     * @return exception instance
     * @fn getExceptiongCbf
     */
    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    /**
     * @return callback instance
     * @fn getRealPlayerCbf
     * @brief get realplay callback instance
     */
    private RealPlayCallBack getRealPlayerCbf() {
        RealPlayCallBack cbf = new RealPlayCallBack() {
            public void fRealDataCallBack(int iRealHandle, int iDataType,
                                          byte[] pDataBuffer, int iDataSize) {
                // player channel 1
                CameraBaseActivity.this.processRealData(iDataType, pDataBuffer,
                        iDataSize, Player.STREAM_REALTIME);
            }
        };
        return cbf;
    }

    /**
     * @param iDataType   - data type [in]
     * @param pDataBuffer - data buffer [in]
     * @param iDataSize   - data size [in]
     * @param iStreamMode - stream mode [in]
     * @return NULL
     * @fn processRealData
     * @author zhuzhenlei
     * @brief process real data
     */
    public void processRealData(int iDataType,
                                byte[] pDataBuffer, int iDataSize, int iStreamMode) {
        if (!m_bNeedDecode) {
            // Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + ",iDataType:" +
            // iDataType + ",iDataSize:" + iDataSize);
        } else {
            if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
                if (m_iPort >= 0) {
                    return;
                }
                m_iPort = Player.getInstance().getPort();
                if (m_iPort == -1) {
                    Log.e(TAG, "getPort is failed with: "
                            + Player.getInstance().getLastError(m_iPort));


                }
                Log.i(TAG, "getPort succ with: " + m_iPort);
                if (iDataSize > 0) {
                    if (!Player.getInstance().setStreamOpenMode(m_iPort,
                            iStreamMode)) // set stream mode
                    {
                        Log.e(TAG, "setStreamOpenMode failed");
                        return;
                    }
                    if (!Player.getInstance().openStream(m_iPort, pDataBuffer,
                            iDataSize, 2 * 1024 * 1024)) // open stream
                    {
                        Log.e(TAG, "openStream failed");
                        return;
                    }
                    if (!Player.getInstance().play(m_iPort,
                            m_osurfaceView.getHolder())) {
                        Log.e(TAG, "play failed");
                        return;
                    }
                    if (!Player.getInstance().playSound(m_iPort)) {
                        Log.e(TAG, "playSound failed with error code:"
                                + Player.getInstance().getLastError(m_iPort));
                        return;
                    }
                }
            } else {
                if (!Player.getInstance().inputData(m_iPort, pDataBuffer,
                        iDataSize)) {
                    // Log.e(TAG, "inputData failed with: " +
                    // Player.getInstance().getLastError(m_iPort));
                    for (int i = 0; i < 4000 && m_iPlaybackID >= 0
                            && !m_bStopPlayback; i++) {
                        if (Player.getInstance().inputData(m_iPort,
                                pDataBuffer, iDataSize)) {
                            break;

                        }

                        if (i % 100 == 0) {
                            Log.e(TAG, "inputData failed with: "
                                    + Player.getInstance()
                                    .getLastError(m_iPort) + ", i:" + i);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * @return NULL
     * @fn Cleanup
     * @author zhuzhenlei
     * @brief cleanup
     */
    public void Cleanup() {
        // release player resource

        Player.getInstance().freePort(m_iPort);
        m_iPort = -1;
        // release net SDK resource
        HCNetSDK.getInstance().NET_DVR_Cleanup();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        thread.stop();
        finish();
    }
}
