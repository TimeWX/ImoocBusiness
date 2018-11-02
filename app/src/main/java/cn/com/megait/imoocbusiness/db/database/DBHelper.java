package cn.com.megait.imoocbusiness.db.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
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
    private static final String INTEGER_TYPE = " integer";//整型
    private static final String TEXT_TYPE = " TEXT";//文本
    public static final String DESC = " DESC";//降序

    /**
     * 表公共字段
     */
    public static final String ID = "_id";
    public static final String TIME = "time";

    /**
     * 货物记录表
     */
    public static final String GOODS_LIST_TABLE = "goodsListTable";
    public static final String GOODS_CODE = "goodscode";//商品编码
    public static final String ABBREV = "abbrev";//缩写
    public static final String SPELL = "spell";//拼写
    public static final String GOODS_TYPE = "type";//类型

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                //此处不应该有break; 保证版本跳跃时也可以全部更新，例如:由oldVersion为1直接到version 5
            case 2:
                //老版本为2时,说明一定执行过了老版本为1时的更新,再执行以后所有版本的更新即可,不需要使用网上的那种循环的方式。
            case 3:
            default:
                break;
        }

    }

    private void creataAllTables(SQLiteDatabase sqLiteDatabase) {
        String[] goodsColunms = new String[]{GOODS_CODE + TEXT_TYPE,
                ABBREV + TEXT_TYPE,
                SPELL + TEXT_TYPE,
                GOODS_TYPE + TEXT_TYPE};
        //创建商品列表
        createTable(sqLiteDatabase, GOODS_LIST_TABLE, goodsColunms);
        //创建商品历史浏览列表
        createTable(sqLiteDatabase, GOODS_BROWER_TABLE, goodsColunms);
        String[] userColunms = new String[]{USER_ID + TEXT_TYPE,
                USER_NAME + TEXT_TYPE,
                MOBILE + TEXT_TYPE,
                PHOTO_URL + TEXT_TYPE};
        //创建第三方登录用户信息列表
        createTable(sqLiteDatabase, THRID_LOGIN_TABLE, userColunms);
    }

    /**
     * 创建表
     *
     * @param sqLiteDatabase
     * @param tableName      表名
     * @param columns        列名类型数组.如"columnName TEXT",列名+列名类型
     */
    private void createTable(SQLiteDatabase sqLiteDatabase, String tableName, String[] columns) {
        String createTable = "create table if not exists ";
        String primaryKey = " Integer primary key autoincrement";
        String text = " text";
        char leftBracket = '(';
        char rightBracket = ')';
        char comma = ',';
        int stringBufferSize = 170;
        StringBuffer sb = new StringBuffer(stringBufferSize);
        sb.append(createTable).append(tableName).append(leftBracket).append(ID)
                .append(primaryKey).append(comma);
        for (String column : columns) {
            sb.append(column);
            sb.append(comma);
        }
        sb.append(TIME).append(text).append(rightBracket);
        try {
            sqLiteDatabase.execSQL(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabase(SQLiteDatabase sqLiteDatabase) {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }

    }

    public synchronized long insert(final String table, final String nullColumnHack, final ContentValues contentValue) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            return sqLiteDatabase.insert(table, nullColumnHack, contentValue);
        } catch (Exception e) {
            return -1;
        } finally {
            closeDatabase(sqLiteDatabase);
        }
    }

    public synchronized boolean insert(final String table, final String nullColumnHack, final ArrayList<ContentValues> contentValues) {
        boolean result = true;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.beginTransaction();
            for (ContentValues value : contentValues) {
                if (sqLiteDatabase.insert(table, nullColumnHack, value) < 0) {
                    result = false;
                    break;
                }
            }
            if (result) {
                sqLiteDatabase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            return false;
        } finally {
            sqLiteDatabase.endTransaction();
            closeDatabase(sqLiteDatabase);
        }
        return result;
    }

    public int delete(final String table, final String whereClause, final String[] whereArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            return sqLiteDatabase.delete(table, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeDatabase(sqLiteDatabase);
        }

    }

    public int update(final String table, final ContentValues contentValues, final String whereClause, final String[] whereArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = getWritableDatabase();
            return sqLiteDatabase.update(table, contentValues, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeDatabase(sqLiteDatabase);
        }
    }


}
