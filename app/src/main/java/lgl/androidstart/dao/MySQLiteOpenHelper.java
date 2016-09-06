package lgl.androidstart.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.InputStream;

import lgl.androidstart.MyApplication;
import lgl.androidstart.file.FileHelper;
import lgl.androidstart.file.IOHelper;
import lgl.androidstart.tool.L;

/**
 * Created by LGL on 2016/8/16.
 */
//开发者稍微做了一点修改
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    String createSql = "create table if not exists Student(" +
            "_id integer primary key autoincrement," +
            "userId integer not null unique," +
            "name varchar(10) not null default 不详," +
            "age integer not null default 18 check(age>18)," +
            "address char(50)" +
            ")";
    String mDBname;
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mDBname=name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskDAO.createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        L.i("==========","我数据库执行了版本升级       "+newVersion+"------》"+newVersion);
        //因为升级的时候有可能添加一些东西或修改一些，所以需要把数据备份到另一张表中  升级好了之后再存回来
        try {
            // // 备份数据库到SD卡的/aDBTest/DBTest.db
            // CopyDB2SD();
            for (int i = oldVersion; i < newVersion; i++) {//假如用户一直使用很老的APP版本   而数据库已经更新了好几个版本   所以这里要这样来循环跟新
                switch (i) {
                    case 1:
                        UpgradedVersion1To2(db);
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void UpgradedVersion1To2(SQLiteDatabase db){

        try {
            db.execSQL("alter table user rename to temp_user");//改表，名

            db.execSQL("drop table if exists user");//确保之前的表不存在

            db.execSQL("create table if not exists user(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(10), remark varchar(50), age varchar(10))");//新建表

            db.execSQL("insert into user select id, name, remark, 'age_lala' from temp_user");//数据放回

            db.execSQL("drop table if exists temp_user");//删除新表
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库备份到SD卡中去
     * @return
     */
    public boolean CopyDB2SD() {

        File dbFile = MyApplication.getContext().getDatabasePath(mDBname);
        File file =FileHelper.getDiskCacheDir(MyApplication.getContext(),"copy");

        InputStream in=IOHelper.getInputStream4File(dbFile.getAbsolutePath());
        IOHelper.WirteFile(in,file+File.separator+mDBname);

        return false;
    }
    /**
     * 各种SQL语句
     *
     * 建表  create table if not exists User(
     *          id integer  PRIMARY KEY  AUTOINCREMENT  NOT NULL,             主键（一个或多个）  自增 （自增只能是integer类型  而且当设置了PRIMARY KEY 的时候就默认自增    AUTOINCREMENT必须添加在约束之前   也就是说  约束是必须放在最后面的）
     *          userid int not null UNIQUE                                   唯一约束，不能有两个相同的userId  在一个表中可以有多个 UNIQUE 列
     *          name text TEXT not null default 未知，
     *          age int int not null default 18  check(age > 18),               检查约束   age必须大于18
     *          address char(50),
     *
     *          );
     *
     *
     *
     *
     *
     */
    //      将一个表中数据复制到另一个表中[可以加条件]
    //      insert into user2 (name, user_age) select name,user_age from user1  [WHERE condition]

    //      插入数据   没有太多的解释   就是这样一种写法
    //      insert into user1 (name,age) values('卢桂林',18)


    //      delete from user2  删除表中所有的数据
    //    delete from user2 where id=8
    //      drop table if exists user1 如果存在user1表则删除表

    //    查询语句
    //    select id,name,age from user
    //    select *  from user where age not in(18,20)
    //    select *  from user where age not in(18,20) and age>18 使用AND
    //    select *  from user where age in(18,20) or age=55   使用OR
    //    select '连接'||'字符串'
    //    select count(*) from user   以后多少条数据
    //    SELECT FROM table_name WHERE column like '%XXXX%'
    //    SELECT FROM table_name WHERE column like '_XXXX_'
    //    SELECT FROM table_name WHERE column like '2_%_%'	查找以 2 开头，且长度至少为 3 个字符的任意值


    //    select * from (select * from user2 where user_age<200)  子查询


    //    UPDATE table_name
    //    SET column1 = value1, column2 = value2...., columnN = valueN
    //    WHERE [condition];
    //    eg：   update user2 set user_age=111 where id=3  更新


    // LIMIT 子句用于限制由 SELECT 语句返回的数据数量    就是说取多少行数据
//    SELECT column1, column2, columnN FROM table_name  LIMIT 10

//     OFFSET 偏移行   从这个值一下所有的行
//    select * from user2 limit 3  offset 1  偏移一行   从第二行开始取数据   取前三个  （此处可以明显看出  sql语句是从后往前执行的）

//    SELECT * FROM COMPANY ORDER BY SALARY [ASC | DESC];     order by  排序  升序或者降序


//     分组   GROUP BY 子句必须放在 WHERE 子句中的条件之后，必须放在 ORDER BY 子句之前。

    //视图的创建和删除（临时表）

//    create view if not exists view_name as  select * from user2 where user_age=111
//    drop view if exists view_name
//    select * from user where user_age in(select user_age from view_name)
}
