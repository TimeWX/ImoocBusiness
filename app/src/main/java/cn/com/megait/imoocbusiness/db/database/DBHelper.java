package cn.com.megait.imoocbusiness.db.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cn.com.megait.imoocbusiness.application.ImoocApplication;

/**
 * @author lenovo
 * @function
 * @date 2018/10/8
 */
public class DBHelper extends SQLiteOpenHelper {

    private static Context mContext;
    private static DBHelper dbHelper = null;
    private static final String DB_NAME = "imooc_db";
    private static final int DB_VERSION = 1;

    /**
     * 字段公共类型
     */
    private static final String INTEGER_TYPE = "integer";
    private static final String TEXT_TYPE = "TEXT";
    private static final String DESC = "DESC";

    /**
     * 表公共字段
     */
    private static final String ID = "_id";
    private static final String TIME = "time";

    /**
     * 货物记录表
     */
    public static final String GOODS_LIST_TABLE = "goodsListTable";
    public static final String GOODS_CODE = "goodsCode";
    public static final String ABBREV = "abbrev";
    public static final String SPELL = "spell";
    public static final String GOODS_TYPE = "goodsType";

    /**
     * 获取浏览历史记录表.与记录表字段一样
     */
    public static final String GOODS_BROWER_TABLE = "goodsBrowerTable";

    /**
     * 第三方登录表
     */
    public static final String THRID_LOGIN_TABLE = "thirdLoginTable";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String MOBILE = "mobile";
    public static final String PHOTO_URL = "photoUrl";
    //签名
    public static final String TICK = "tick";
    //平台
    public static final String PLATFORM = "platform";


    private DBHelper() {
        super(ImoocApplication.getInstance(), DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static synchronized DBHelper getInstance() {
        if (dbHelper == null) {
            //用全局Application即可
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            creataAllTables(db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    private void creataAllTables(SQLiteDatabase db) {
    }

    public void closeDatabase(SQLiteDatabase db) {
    }

    public synchronized void insert(final String table, final String nullColumnHack, final ContentValues contentValue) {
    }

    public synchronized void insert(final String table, final String nullColumnHack, final ArrayList<ContentValues> contentValues) {
    }

    public int delete(final String table,final String whereClause,final String[] whereArgs){
        return 0;
    }

    public int update(final String table,final ContentValues contentValues,final String whereClause,final String[] whereArgs){
        return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
