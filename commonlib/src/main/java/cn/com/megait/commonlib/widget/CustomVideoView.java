package cn.com.megait.commonlib.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import cn.com.megait.commonlib.R;
import cn.com.megait.commonlib.adutil.LogUtils;
import cn.com.megait.commonlib.adutil.Utils;
import cn.com.megait.commonlib.constant.SDKConstants;
import cn.com.megait.commonlib.core.AdParameters;

/**
 * @author TimeW
 * @function
 * @date 2018/11/29
 * TODO 了解
 */
public class CustomVideoView extends RelativeLayout implements View.OnClickListener, MediaPlayer.OnPreparedListener
        , MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener
        , TextureView.SurfaceTextureListener {
    /**
     * Constant
     */
    private static final String TAG = "MraidVideoView";
    private static final int TIME_MSG = 0x01;
    private static final int TIME_MSG_DELAY = 1000;
    private static final int STATE_ERROR = -1;//播放错误
    private static final int STATE_IDLE = 0;//播放处于闲置中
    private static final int STATE_PLAYING = 1;//正在播放
    private static final int STATE_PAUSING = 2;//播放暂停
    private static final int LOAD_TOTAL_COUNT = 3;//总共加载次数
    /**
     * UI
     */
    private ViewGroup mParentContainer;
    private RelativeLayout mPlayerView;
    private TextureView mVideoView;
    private Button mMiniPlayBtn;
    private ImageView mFullBtn;
    private ImageView mLoadingBar;
    private ImageView mFrameView;
    private AudioManager audioManager;
    private Surface videoSurface;

    /**
     * Data
     */
    private String mUrl;
    private String mFrameURI;
    private boolean isMute;
    private int mScreenWidth, mDestinationHeight;

    /**
     * Status状态保护
     */
    private boolean canPlay = true;
    private boolean mIsRealPause;
    private boolean mIsComplete;
    private int mCurrentCount;
    private int playerState = STATE_IDLE;

    private MediaPlayer mediaPlayer;
    private ADVideoPlayerListener listener;
    private ScreenEventReceiver mScreenReceiver;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_MSG:
                    if (isPlaying()) {
                        //还可以在这里更新progressbar
                        //LogUtils.i(TAG, "TIME_MSG");
                        listener.onBufferUpdate(getCurrentPosition());
                        sendEmptyMessageDelayed(TIME_MSG, TIME_MSG_DELAY);
                    }
                    break;
            }
        }
    };

    private ADFrameImageLoadListener mFrameLoadListener;

    public CustomVideoView(Context context, ViewGroup parentContainer) {
        super(context);
        mParentContainer = parentContainer;
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        initData();
        initView();
        registerBroadcastReceiver();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        //设置目标View的高度
        mDestinationHeight = (int) (mScreenWidth * SDKConstants.VIDEO_HEIGHT_PERCENT);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        mPlayerView = (RelativeLayout) inflater.inflate(R.layout.xadsdk_video_player, this);//加载PlayerView,并处于本RelativeLayout中
        mVideoView = mPlayerView.findViewById(R.id.xadsdk_player_video_textureView);
        mVideoView.setOnClickListener(this);
        mVideoView.setKeepScreenOn(true);//保持屏幕长亮
        mVideoView.setSurfaceTextureListener(this);
        initSmallLayoutMode(); //init the small mode
    }


    /**
     * 小窗口播放状态初始化
     */
    private void initSmallLayoutMode() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mScreenWidth, mDestinationHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPlayerView.setLayoutParams(params);
        mMiniPlayBtn =  mPlayerView.findViewById(R.id.xadsdk_small_play_btn);
        mFullBtn =  mPlayerView.findViewById(R.id.xadsdk_to_full_view);
        mLoadingBar =  mPlayerView.findViewById(R.id.loading_bar);
        mFrameView =  mPlayerView.findViewById(R.id.framing_view);
        mMiniPlayBtn.setOnClickListener(this);
        mFullBtn.setOnClickListener(this);
    }

    /**
     * 是否显示全屏按钮
     * @param isShow
     */
    public void isShowFullBtn(boolean isShow) {
        mFullBtn.setImageResource(isShow ? R.mipmap.xadsdk_ad_mini : R.mipmap.xadsdk_ad_mini_null);
        mFullBtn.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否处于暂停状态
     * @return
     */
    public boolean isRealPause() {
        return mIsRealPause;
    }

    /**
     * 是否播放完成
     * @return
     */
    public boolean isComplete() {
        return mIsComplete;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        LogUtils.e(TAG, "onVisibilityChanged" + visibility);
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE && playerState == STATE_PAUSING) {
            if (isRealPause() || isComplete()) {
                pause();
            } else {
                decideCanPlay();
            }
        } else {
            pause();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        LogUtils.i(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 设置音量
     *
     * @param mute  true,表示静音;false,表示全音量
     */
    public void mute(boolean mute) {
        LogUtils.d(TAG, "mute");
        isMute = mute;
        if (mediaPlayer != null && this.audioManager != null) {
            float volume = isMute ? 0.0f : 1.0f;
            mediaPlayer.setVolume(volume, volume);
        }
    }

    /**
     * 是否正在播放
     * @return
     */
    public boolean isPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isFrameHidden() {
        return mFrameView.getVisibility() == View.VISIBLE ? false : true;
    }

    public void setIsComplete(boolean isComplete) {
        mIsComplete = isComplete;
    }

    public void setIsRealPause(boolean isRealPause) {
        this.mIsRealPause = isRealPause;
    }


    @Override
    public void onClick(View v) {
        /**
         * 小屏状态点击播放按钮
         */
        if (v == this.mMiniPlayBtn) {
            if (this.playerState == STATE_PAUSING) {
                if (Utils.getVisiblePercent(mParentContainer)
                        > SDKConstants.VIDEO_SCREEN_PERCENT) {
                    resume();
                    this.listener.onClickPlay();
                }
            } else {
                load();
            }
         //点击全屏按钮
        } else if (v == this.mFullBtn) {
            this.listener.onClickFullScreenBtn();
         //VideoView点击
        } else if (v == mVideoView) {
            this.listener.onClickVideo();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (listener != null) {
            listener.onAdVideoLoadComplete();
        }
        playBack();
        setIsComplete(true);
        setIsRealPause(true);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtils.e(TAG, "do error:" + what);
        this.playerState = STATE_ERROR;
        mediaPlayer = mp;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        if (mCurrentCount >= LOAD_TOTAL_COUNT) {
            showPauseMode(false);
            if (this.listener != null) {
                listener.onAdVideoLoadFailed();
            }
        }
        this.stop();//去重新load
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        LogUtils.i(TAG, "onPrepared");
        showPlayMode();
        mediaPlayer = mp;
        if (mediaPlayer != null) {
            mediaPlayer.setOnBufferingUpdateListener(this);
            mCurrentCount = 0;
            if (listener != null) {
                listener.onAdVideoLoadSuccess();
            }
            //满足自动播放条件，则直接播放
            if (Utils.canAutoPlay(getContext(),
                    AdParameters.getCurrentSetting()) &&
                    Utils.getVisiblePercent(mParentContainer) > SDKConstants.VIDEO_SCREEN_PERCENT) {
                setCurrentPlayState(STATE_PAUSING);
                resume();
            } else {
                setCurrentPlayState(STATE_PLAYING);
                pause();
            }
        }
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    public void setDataSource(String url) {
        this.mUrl = url;
    }

    public void setFrameURI(String url) {
        mFrameURI = url;
    }

    /**
     * 加载
     */
    public void load() {
        if (this.playerState != STATE_IDLE) {
            return;
        }
        LogUtils.d(TAG, "do play url = " + this.mUrl);
        showLoadingMode();
        try {
            setCurrentPlayState(STATE_IDLE);
            checkMediaPlayer();
            mute(true);
            mediaPlayer.setDataSource(this.mUrl);
            mediaPlayer.prepareAsync(); //开始异步加载
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            stop(); //error以后重新调用stop加载
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (this.playerState != STATE_PLAYING) {
            return;
        }
        LogUtils.d(TAG, "do pause");
        setCurrentPlayState(STATE_PAUSING);
        if (isPlaying()) {
            mediaPlayer.pause();
            if (!this.canPlay) {
                this.mediaPlayer.seekTo(0);
            }
        }
        this.showPauseMode(false);
        mHandler.removeCallbacksAndMessages(null);
    }

    //全屏不显示暂停状态,后续可以整合，不必单独出一个方法
    public void pauseForFullScreen() {
        if (playerState != STATE_PLAYING) {
            return;
        }
        LogUtils.d(TAG, "do full pause");
        setCurrentPlayState(STATE_PAUSING);
        if (isPlaying()) {
            mediaPlayer.pause();
            if (!this.canPlay) {
                mediaPlayer.seekTo(0);
            }
        }
        mHandler.removeCallbacksAndMessages(null);
    }

    public int getCurrentPosition() {
        if (this.mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    //跳到指定点播放视频
    public void seekAndResume(int position) {
        if (mediaPlayer != null) {
            showPauseMode(true);
            entryResumeState();
            mediaPlayer.seekTo(position);
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener(){
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    LogUtils.d(TAG, "do seek and resume");
                    mediaPlayer.start();
                    mHandler.sendEmptyMessage(TIME_MSG);
                }
            });
        }
    }

    //跳到指定点暂停视频
    public void seekAndPause(int position) {
        if (this.playerState != STATE_PLAYING) {
            return;
        }
        showPauseMode(false);
        setCurrentPlayState(STATE_PAUSING);
        if (isPlaying()) {
            mediaPlayer.seekTo(position);
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    LogUtils.d(TAG, "do seek and pause");
                    mediaPlayer.pause();
                    mHandler.removeCallbacksAndMessages(null);
                }
            });
        }
    }

    /**
     * VideoView重新聚焦,开始重新播放
     */
    public void resume() {
        //判断播放是否处于暂停状态
        if (this.playerState != STATE_PAUSING) {
            return;
        }
        LogUtils.d(TAG, "do resume");
        if (!isPlaying()) {
            entryResumeState();
            mediaPlayer.setOnSeekCompleteListener(null);
            mediaPlayer.start();
            mHandler.sendEmptyMessage(TIME_MSG);
            showPauseMode(true);
        } else {
            showPauseMode(false);
        }
    }

    /**
     * 进入播放状态时的状态更新
     */
    private void entryResumeState() {
        canPlay = true;
        setCurrentPlayState(STATE_PLAYING);
        setIsRealPause(false);
        setIsComplete(false);
    }

    /**
     * 设置当前播放状态
     * @param state
     */
    private void setCurrentPlayState(int state) {
        playerState = state;
    }

    //播放完成后回到初始状态
    public void playBack() {
        LogUtils.d(TAG, " do playBack");
        setCurrentPlayState(STATE_PAUSING);
        mHandler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null) {
            mediaPlayer.setOnSeekCompleteListener(null);
            mediaPlayer.seekTo(0);
            mediaPlayer.pause();
        }
        this.showPauseMode(false);
    }

    /**
     * 停止
     */
    public void stop() {
        LogUtils.d(TAG, " do stop");
        if (this.mediaPlayer != null) {
            //MediaPlayer之后,要重新设置数据源
            this.mediaPlayer.reset();
            this.mediaPlayer.setOnSeekCompleteListener(null);
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        mHandler.removeCallbacksAndMessages(null);
        setCurrentPlayState(STATE_IDLE);
        if (mCurrentCount < LOAD_TOTAL_COUNT) { //满足重新加载的条件
            mCurrentCount += 1;
            load();
        } else {
            showPauseMode(false); //显示暂停状态
        }
    }

    /**
     * 销毁
     */
    public void destroy() {
        LogUtils.d(TAG, " do destroy");
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setOnSeekCompleteListener(null);
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        setCurrentPlayState(STATE_IDLE);
        mCurrentCount = 0;
        setIsComplete(false);
        setIsRealPause(false);
        unRegisterBroadcastReceiver();
        mHandler.removeCallbacksAndMessages(null); //release all message and runnable
        showPauseMode(false); //除了播放和loading外其余任何状态都显示pause
    }

    public void setListener(ADVideoPlayerListener listener) {
        this.listener = listener;
    }

    public void setFrameLoadListener(ADFrameImageLoadListener frameLoadListener) {
        this.mFrameLoadListener = frameLoadListener;
    }

    private synchronized void checkMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = createMediaPlayer(); //每次都重新创建一个新的播放器
        }
    }

    /**
     * 创建MediaPlayer
     * @return
     */
    private MediaPlayer createMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (videoSurface != null && videoSurface.isValid()) {
            mediaPlayer.setSurface(videoSurface);
        } else {
            stop();
        }
        return mediaPlayer;
    }

    /**
     * 暂停模式是否开启
     * @param show true,开启;false,关闭
     */
    private void showPauseMode(boolean show) {
        //暂停模式下,全屏按钮隐藏,播放按钮显示,加载图画Clear并隐藏
        mFullBtn.setVisibility(show ? View.VISIBLE : View.GONE);
        mMiniPlayBtn.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoadingBar.clearAnimation();
        mLoadingBar.setVisibility(View.GONE);
        if (!show) {
            mFrameView.setVisibility(View.VISIBLE);
            loadFrameImage();
        } else {
            mFrameView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载模式
     */
    private void showLoadingMode() {
        //全屏按钮,FrameView,播放按钮全部隐藏,LoadingBar显示
        mFullBtn.setVisibility(View.GONE);
        mLoadingBar.setVisibility(View.VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) mLoadingBar.getBackground();
        anim.start();
        mMiniPlayBtn.setVisibility(View.GONE);
        mFrameView.setVisibility(View.GONE);
        loadFrameImage();
    }

    /**
     * 进入播放模式
     */
    private void showPlayMode() {
        mLoadingBar.clearAnimation();
        mLoadingBar.setVisibility(View.GONE);
        mMiniPlayBtn.setVisibility(View.GONE);
        mFrameView.setVisibility(View.GONE);
    }

    /**
     * 异步加载定帧图
     */
    private void loadFrameImage() {
        if (mFrameLoadListener != null) {
            mFrameLoadListener.onStartFrameLoad(mFrameURI, new ImageLoaderListener() {
                @Override
                public void onLoadingComplete(Bitmap loadedImage) {
                    if (loadedImage != null) {
                        mFrameView.setScaleType(ScaleType.FIT_XY);
                        mFrameView.setImageBitmap(loadedImage);
                    } else {
                        mFrameView.setScaleType(ScaleType.FIT_CENTER);
                        mFrameView.setImageResource(R.mipmap.xadsdk_img_error);
                    }
                }
            });
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        LogUtils.i(TAG, "onSurfaceTextureAvailable");
        videoSurface = new Surface(surface);
        checkMediaPlayer();
        mediaPlayer.setSurface(videoSurface);
        load();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        LogUtils.i(TAG, "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    private void registerBroadcastReceiver() {
        if (mScreenReceiver == null) {
            mScreenReceiver = new ScreenEventReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            getContext().registerReceiver(mScreenReceiver, filter);
        }
    }

    private void unRegisterBroadcastReceiver() {
        if (mScreenReceiver != null) {
            getContext().unregisterReceiver(mScreenReceiver);
        }
    }

    private void decideCanPlay() {
        if (Utils.getVisiblePercent(mParentContainer) > SDKConstants.VIDEO_SCREEN_PERCENT)
            //来回切换页面时，只有 >50,且满足自动播放条件才自动播放
            resume();
        else
            pause();
    }

    /**
     * 监听锁屏事件的广播接收器
     */
    private class ScreenEventReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //主动锁屏时 pause, 主动解锁屏幕时，resume
            switch (intent.getAction()) {
                //Broadcast Action: Sent when the user is present after device wakes up
                case Intent.ACTION_USER_PRESENT:
                    if (playerState == STATE_PAUSING) {
                        if (mIsRealPause) {
                            //手动点的暂停，回来后还暂停
                            pause();
                        } else {
                            decideCanPlay();
                        }
                    }
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    if (playerState == STATE_PLAYING) {
                        pause();
                    }
                    break;
            }
        }
    }

    /**
     * 供slot层来实现具体点击逻辑,具体逻辑还会变，
     * 如果对UI的点击没有具体监测的话可以不回调
     */
    public interface ADVideoPlayerListener {

        public void onBufferUpdate(int time);

        public void onClickFullScreenBtn();

        public void onClickVideo();

        public void onClickBackBtn();

        public void onClickPlay();

        public void onAdVideoLoadSuccess();

        public void onAdVideoLoadFailed();

        public void onAdVideoLoadComplete();
    }

    public interface ADFrameImageLoadListener {

        void onStartFrameLoad(String url, ImageLoaderListener listener);
    }

    public interface ImageLoaderListener {
        /**
         * 如果图片下载不成功，传null
         *
         * @param loadedImage
         */
        void onLoadingComplete(Bitmap loadedImage);
    }
}
