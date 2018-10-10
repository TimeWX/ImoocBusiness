package cn.com.megait.imoocbusiness.db.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.com.megait.imoocbusiness.module.BaseModel;

/**
 * @author lenovo
 * @function
 * @date 2018/10/9
 */
public class DBDataHelper {
    private static DBDataHelper dbDataHelper = null;
    private DBHelper dbHelper = null;
    private static final String SELECT = "select ";
    private static final String FROM = " from ";
    private static final String ORDER_BY = " order by ";
    private static final String WHERE = " where ";


    private DBDataHelper() {
        dbHelper = DBHelper.getInstance();
    }

    public static DBDataHelper getInstance() {
        if (dbDataHelper == null) {
            dbDataHelper = new DBDataHelper();
        }
        return dbDataHelper;
    }

    /**
     * 根据条件数据库查询,单条件查询
     *
     * @param tableName     表名
     * @param showColumns   所要显示的列
     * @param selection     条件列名 ,如 id
     * @param selectionArgs 条件值   ,如 跟上述selection对应, 1111
     * @param orderBy       排序参数
     * @param cls
     * @return
     */
    public ArrayList<BaseModel> select(String tableName, String showColumns, String selection, String selectionArgs
            , String orderBy, Class<?> cls) {
        synchronized (dbHelper) {
            ArrayList<BaseModel> modules = new ArrayList<>();
            SQLiteDatabase readableDatabase = null;
            try {
                readableDatabase = dbHelper.getReadableDatabase();
                StringBuffer sb = new StringBuffer();
                sb.append(SELECT);
                sb.append(TextUtils.isEmpty(showColumns) ? "*" : showColumns);
                sb.append(FROM).append(tableName);
                if (!TextUtils.isEmpty(selection) && !TextUtils.isEmpty(selectionArgs)) {
                    sb.append(WHERE).append(selection).append(" = ").append(selectionArgs);
                }
                if (!TextUtils.isEmpty(orderBy)) {
                    sb.append(ORDER_BY).append(orderBy);
                }
                Cursor cursor = readableDatabase.rawQuery(sb.toString(), null);
                changeToList(cursor, modules, cls);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbHelper.closeDatabase(readableDatabase);
            }
            return modules;
        }

    }

    /**
     * 多条件查询
     *
     * @param tableName     表名
     * @param selection     条件名,如 id>? and name <>?   不需要可设置为null
     * @param selectionArgs 条件值,跟selection对应   new String[]{"111","jack"}
     * @param orderBy       排序参数
     * @param cls
     * @return
     */
    public ArrayList<BaseModel> select(String tableName, String selection, String[] selectionArgs
            , String orderBy, Class<?> cls) {
        ArrayList<BaseModel> modules = new ArrayList<>();
        SQLiteDatabase readableDatabase = null;
        Cursor cursor;
        synchronized (dbHelper) {
            try {
                readableDatabase = dbHelper.getReadableDatabase();
                cursor = readableDatabase.query(tableName, null, selection, selectionArgs, null, null, orderBy);
                changeToList(cursor, modules, cls);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbHelper.closeDatabase(readableDatabase);
            }
            return modules;
        }
    }

    /**
     * 将结果转换为数据模型
     *
     * @param cursor
     * @param modules
     * @param cls
     */
    private void changeToList(Cursor cursor, List<BaseModel> modules, Class<?> cls) {

        int count = cursor.getCount();
        BaseModel module;
        cursor.moveToFirst();
        synchronized (dbHelper) {
            try {
                for (int i = 0; i < count; i++) {
                    module = changeToModule(cursor, cls);
                    modules.add(module);
                    cursor.moveToNext();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
    }

    /**
     * 转化为Module一个实例
     *
     * @param cursor
     * @param cls
     * @return
     */
    private BaseModel changeToModule(Cursor cursor, Class<?> cls) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        synchronized (dbHelper) {
            String[] columnNames = cursor.getColumnNames();
            int columnNamesCount = columnNames.length;
            BaseModel module = (BaseModel) cls.newInstance();
            String filedValue;
            Field field;
            for (int i = 0; i < columnNamesCount; i++) {
                field = cls.getField(columnNames[i]);
                filedValue = cursor.getString(i);
                if (filedValue != null) {
                    field.set(module, filedValue);
                }
            }
            return module;
        }
    }

    public long insert(String tableName, BaseModel module) {
        synchronized (dbHelper) {
            ContentValues contentValues = moduleToContentValues(module);
            return dbHelper.insert(tableName,null,contentValues);
        }
    }

    public boolean insert(String tableName, ArrayList<BaseModel> modules) {
        synchronized (dbHelper){
            ArrayList<ContentValues> arrs=moduleToContentValues(modules);
            return dbHelper.insert(tableName,null,arrs);
        }
    }

    private ArrayList<ContentValues> moduleToContentValues(ArrayList<BaseModel> modules) {
        ArrayList<ContentValues> values = new ArrayList<>();
        for (BaseModel module : modules) {
            values.add(moduleToContentValues(module));
        }
        return values;
    }

    private ContentValues moduleToContentValues(BaseModel module) {
        ContentValues contentValues = new ContentValues();
        Field[] fields = module.getClass().getFields();
        String fieldName;
        String fieldValue;
        Integer fieldValueForInt = -1;
        try {
            for (Field field : fields) {
                fieldName = field.getName();
                Object obj = field.get(module);
                if (obj instanceof String) {
                    fieldValue = (String) field.get(module);
                    if (!TextUtils.isEmpty(fieldValue)) {
                        contentValues.put(fieldName, fieldValue);
                    } else {
                        contentValues.put(fieldName, "");

                    }
                } else if (obj instanceof Integer) {
                    fieldValueForInt = (Integer) field.get(module);
                    if (fieldValueForInt != null) {
                        contentValues.put(fieldName, fieldValueForInt);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return contentValues;
    }

    public int delete(String tableName, String whereClause, String[] whereArgs) {
        synchronized (dbHelper){
            return  dbHelper.delete(tableName,whereClause,whereArgs);
        }
    }

    public int update(String tableName, String whereClause, String[] whereArgs, BaseModel module) {
        synchronized (dbHelper){
            return dbHelper.update(tableName,moduleToContentValues(module),whereClause,whereArgs);
        }
    }

}
