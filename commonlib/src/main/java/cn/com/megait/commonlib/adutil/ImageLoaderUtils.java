package cn.com.megait.commonlib.adutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author TimeW
 * @function UniversalImageLoader工具类
 * @date 2018/11/14
 */
public class ImageLoaderUtils {
    private static final int THREAD_COUNT=3;//表示UniversalImageLoader加载最多的线程
    private static final int PRIORITY=2;//优先级
    private static final int READ_TIME_OUT=30*1000;//读取超时时间30S
    private static final int CONNECTION_TIME_OUT=5*1000;//连接超时时间5S
    private static final int MEMORY_CACHE_SIZE=2*1024*1024;//内存缓存 2M
    private static final int DISK_CACHE_SIZE=50*1024*1024;//磁盘缓存 50M
    private static final int FADE_IN_DISPLAY=400;

    private static ImageLoaderUtils mInstance;
    private static ImageLoader mImageLoader;

    public static ImageLoaderUtils getInstance(Context context){
        if(mInstance!=null){
            synchronized (ImageLoaderUtils.class){
                if(mInstance!=null){
                    mInstance=new ImageLoaderUtils(context);
                }
            }
        }
        return mInstance;
    }

    private ImageLoaderUtils(Context context){
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(THREAD_COUNT)
                .threadPoolSize(Thread.NORM_PRIORITY-PRIORITY)
                .diskCacheSize(DISK_CACHE_SIZE)
                .memoryCacheSize(MEMORY_CACHE_SIZE)
                .denyCacheImageMultipleSizesInMemory()//不缓存同一图片不同尺寸在内存中
                .memoryCache(new WeakMemoryCache())
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的URI用MD5加密缓存
                .tasksProcessingOrder(QueueProcessingType.FIFO)//任务处理先进先出
                .imageDownloader(new BaseImageDownloader(context,CONNECTION_TIME_OUT,READ_TIME_OUT))
                .writeDebugLogs()//写入Debug日志
                .defaultDisplayImageOptions(getDefaultOptions())
                .build();
        mImageLoader=ImageLoader.getInstance();
        mImageLoader.init(configuration);
    }

    public void displayImage(ImageView imageView, String path, DisplayImageOptions displayImageOptions, ImageLoadingListener imageLoadingListener){
        if(mImageLoader!=null){
            mImageLoader.displayImage(path,imageView,displayImageOptions,imageLoadingListener);
        }
    }

    public void displayImage(ImageView imageView, String path, ImageLoadingListener imageLoadingListener){
        if(mImageLoader!=null){
            mImageLoader.displayImage(path,imageView,imageLoadingListener);
        }
    }

    public void displayImage(ImageView imageView, String path){
        if(mImageLoader!=null){
            mImageLoader.displayImage(path,imageView);
        }
    }

    public DisplayImageOptions getDefaultOptions(){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)//是否内存缓存
                .cacheOnDisk(true)//是否磁盘缓存
                .considerExifParams(true)//是否考虑JPEG中EXIF参数(翻转,旋转)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//图片显示的编码方式
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .decodingOptions(new BitmapFactory.Options())//设置图片的解码配置
                .resetViewBeforeLoading(true)//设置图片是否在下载前重复,服务
                .displayer(new FadeInBitmapDisplayer(FADE_IN_DISPLAY))
                .build();
    }

    public DisplayImageOptions getNoCacheOptions(){
        return  new DisplayImageOptions.Builder()
                //.cacheInMemory(true)//是否内存缓存
                //.cacheOnDisk(true)//是否磁盘缓存
                .considerExifParams(true)//是否考虑JPEG中EXIF参数(翻转,旋转)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//图片显示的编码方式
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .decodingOptions(new BitmapFactory.Options())//设置图片的解码配置
                .resetViewBeforeLoading(true)//设置图片是否在下载前重复,服务
                .displayer(new FadeInBitmapDisplayer(FADE_IN_DISPLAY))
                .build();
    }

    /**
     *
     * @param emptyUri 请求为空时默认图片显示样式
     * @param failedUri 请求失败时默认图片显示样式
     * @return
     */
    public DisplayImageOptions getNoCacheOptions(int emptyUri,int failedUri){
        return new DisplayImageOptions.Builder()
                //.cacheInMemory(true)//是否内存缓存
                //.cacheOnDisk(true)//是否磁盘缓存
                .showImageForEmptyUri(emptyUri)
                .showImageOnFail(failedUri)
                .considerExifParams(true)//是否考虑JPEG中EXIF参数(翻转,旋转)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//图片显示的编码方式
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .decodingOptions(new BitmapFactory.Options())//设置图片的解码配置
                .resetViewBeforeLoading(true)//设置图片是否在下载前重复,服务
                .displayer(new FadeInBitmapDisplayer(FADE_IN_DISPLAY))
                .build();
    }

}
