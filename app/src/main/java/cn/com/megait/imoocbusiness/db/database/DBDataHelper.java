package cn.com.megait.imoocbusiness.db.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author lenovo
 * @function
 * @date 2018/10/9
 */
public class DBDataHelper {
    private static DBDataHelper dbDataHelper=null;
    private DBHelper dbHelper=null;
    private static final String SELECT="select ";
    private static final String FROM=" from ";
    private static final String ORDER_BY=" order by";
    private static final String WHERE=" where ";


    private DBDataHelper(){
        dbHelper=DBHelper.getInstance();
    }

    public static DBDataHelper getInstance(){
        if(dbDataHelper==null){
            dbDataHelper=new DBDataHelper();
        }
        return dbDataHelper;
    }

    public ArrayList<BaseModel> select(String tableName,String showColumns,String selction,String selectionArgs
    ,String orderBy,Class<?> cls){
        return null;
    }

    public ArrayList<BaseModel> select(String tableName,String showColumns,String selction,String[] selectionArgs
            ,String orderBy,Class<?> cls){
        return null;
    }

    /**
     * 将结果转换为数据模型
     * @param cursor
     * @param modules
     * @param cls
     */
    private void changeToList(Cursor cursor, List<BaseModel> modules,Class<?> cls){}

    /**
     * 转化为Module一个实例
     * @param cursor
     * @param cls
     * @return
     */
    private BaseModel changeToModule(Cursor cursor,Class<?> cls){
        BaseModel baseModel=null;
        return baseModel;
    }

    public long insert(String tableName,BaseModel module){
        long result=-1;
        return result;
    }

    public long insert(String tableName,ArrayList<BaseModel> modules){
        long result=-1;
        return result;
    }

    private ArrayList<ContentValues> moduleToContentValues(ArrayList<BaseModel> modules){
        ArrayList<ContentValues> values=new ArrayList<>();
        return values;
    }

    private ContentValues moduleToContentValues(BaseModel module){
        ContentValues contentValues=new ContentValues();
        return contentValues;
    }

    public int delete(String tableName,String whereClause,String[] whereArgs ){
        int result=-1;
        return  result;
    }
    public int update(String tableName,String whereClause,String[] whereArgs,BaseModel module){
        return 0;
    }

}
