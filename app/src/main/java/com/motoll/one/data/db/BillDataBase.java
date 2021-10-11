package com.motoll.one.data.db;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.xuexiang.xormlite.AppDataBaseTable;
import com.xuexiang.xormlite.db.DataBaseUtils;
import com.xuexiang.xormlite.db.IDatabase;
import com.xuexiang.xormlite.logs.DBLog;

import java.sql.SQLException;

/**
 * <pre>
 *     desc   : 应用内部数据库 实现接口
 *     author : xuexiang
 *     time   : 2018/5/9 下午11:52
 * </pre>
 */
public class BillDataBase implements IDatabase {
    /**
     * 数据库创建
     *
     * @param database         SQLite数据库
     * @param connectionSource 数据库连接
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            DataBaseUtils.createTablesByClassNames(connectionSource, AppDataBaseTable.getTables());
        } catch (SQLException e) {
            DBLog.e(e);
        }
    }

    /**
     * 数据库升级和降级操作
     *
     * @param database         SQLite数据库
     * @param connectionSource 数据库连接
     * @param oldVersion       旧版本
     * @param newVersion       新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        DBLog.i("数据库旧版本:" + oldVersion);
        DBLog.i("数据库新版本:" + newVersion);
    }
}