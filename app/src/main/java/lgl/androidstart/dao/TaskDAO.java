package lgl.androidstart.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 作者: LGL on 2016/8/17.
 * 邮箱: 468577977@qq.com
 */
public class TaskDAO<T> {
    SQLiteDatabase mReadableDatabase;//因为读入的Database对象一样可以写（先获取一个  RW  对象，如果不能写就获取一个 R 的）
    //    SQLiteDatabase mWritableDatabase;

    public TaskDAO(Context context) {
        //if (readOrwrita.length>0)
//        mReadableDatabase = MyApplication.getSqliteHelper().getReadableDatabase();
    }

    //建表的语句
    public static final String createSql = "create table if not exists Task(" +
            "       _id integer primary key autoincrement," +
            "       venue_item_id integer," +
            "       status  integer, " +
            "       task_id integer not null default 0," +
            "       user_id integer not null default 0," +
            "       task_answer_id integer default 0," +
            "       [type] integer not null default 0," +
            "       createby_name varchar(50) default ''," +
            "       cover varchar(255) default ''," +
            "       title  varchar(50) default ''," +
            "       degree  varchar(50) default ''," +
            "       item_name  varchar(50) default ''," +
            "       web_introduce  varchar(50) default ''," +
            "       create_time  varchar(50) default ''" +
            ");";

    /**
     * 插入一条数据
     */
//    public void InsertTask(Task task) {
//        //String insertSql = "insert into Task(venue_item_id,status,task_id,user_id,task_answer_id,[type],createby_name,cover,title,degree,item_name,web_introduce,create_time)  " + "values(?,?,?,?,?,?,'?','?','?','?','?','?','?')";
//        //Object[] objects = new Object[]{task.getVenue_item_id(), task.getStatus(), task.getTask_id(), task.getUser_id(), task.getTask_answer_id(), task.getType(), task.getCreateby_name(), task.getCover(), task.getTitle(), task.getDegree(), task.getItem_name(), task.getWeb_introduce(), task.getCreate_time()};
//        //mWritableDatabase.execSQL(insertSql, objects);
//        String insertSql = "insert into Task(venue_item_id,status,task_id,user_id,task_answer_id,[type],createby_name,cover,title,degree,item_name,web_introduce,create_time)  " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
//        Object[] objects = new Object[]{task.getVenue_item_id(), task.getStatus(), task.getTask_id(), MyApplication.userInfo.getUser_id(), task.getTask_answer_id(), task.getType(), task.getCreateby_name(), task.getCover(), task.getTitle(), task.getDegree(), task.getItem_name(), task.getWeb_introduce(), task.getCreate_time()};
//        mReadableDatabase.execSQL(insertSql, objects);
//    }

    /**
     * 根据条件删除
     */
//    public void DeleteTaskByUser() {
//        String deleteSql = "delete from task where user_id=?";
//        mReadableDatabase.beginTransaction();
//        try {
//            mReadableDatabase.execSQL(deleteSql, new Object[]{MyApplication.userInfo.getUser_id()});
//            mReadableDatabase.setTransactionSuccessful();
//        } catch (SQLException e) {
//            Log.e("=====sqlE===", e.getMessage());
//        } finally {
//            mReadableDatabase.endTransaction();
//        }
//    }

//    public List<T> SelectTaskAllByUser() {
//        String selectSql = "select * from task where user_id=?;";
//        List<T> tasks = new ArrayList<>();
//        Cursor cursor = mReadableDatabase.rawQuery(selectSql, new String[]{MyApplication.userInfo.getUser_id() + ""});
//        if (cursor==null||cursor.getCount() == 0) return tasks;
//        cursor.moveToFirst();
//        do {
//            T task = new T();
//            task.setVenue_item_id(cursor.getInt(cursor.getColumnIndex("venue_item_id")));
//            task.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
//            task.setTask_id(cursor.getInt(cursor.getColumnIndex("task_id")));
//            task.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
//            task.setTask_answer_id(cursor.getInt(cursor.getColumnIndex("task_answer_id")));
//            task.setType(cursor.getInt(cursor.getColumnIndex("type")));
//            task.setDegree(cursor.getColumnIndex("degree"));
//            task.setCreateby_name(cursor.getString(cursor.getColumnIndex("createby_name")));
//            task.setCover(cursor.getString(cursor.getColumnIndex("cover")));
//            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
//            task.setItem_name(cursor.getString(cursor.getColumnIndex("item_name")));
//            task.setWeb_introduce(cursor.getString(cursor.getColumnIndex("web_introduce")));
//            task.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
//            tasks.add(task);
//        } while (cursor.moveToNext());
//        return tasks;
//    }

    /**
     * 更新这个有一个坑（忘了是啥）
     */
//    public void UpdateTaskByUser(String task_id) {
//        String updateSql = "update Task set task_answer_id=99 where task_id=? and user_id=?;";
//        mReadableDatabase.beginTransaction();
//        try {
//            mReadableDatabase.execSQL(updateSql, new Object[]{task_id, MyApplication.userInfo.getUser_id()});
//            mReadableDatabase.setTransactionSuccessful();
//        } catch (SQLException e) {
//            Log.e("=====sqlE===", e.getMessage());
//        } finally {
//            mReadableDatabase.endTransaction();
//        }
//    }
}
