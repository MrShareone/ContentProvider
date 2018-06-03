package com.example.mr_shareone.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Autor：created by MR-SHAREONE on 2018/6/3 18 44
 * Emain:13437105740@163.com
 */
public class MyProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;
    private static UriMatcher uriMatcher; //这还是可以理解为一个键值对，通配符uri对应常量

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.app.provider", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.app.provider", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.app.provider", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.app.provider", "table2/#", TABLE2_ITEM);
    }

    /**
     * @return 初始化的时候调用（创建和升级）
     * contentprovider尝试初始化的时候会调用
     */
    @Override
    public boolean onCreate() {
        return false;
    }

    /**
     * @param uri           确定查询哪一张表
     * @param projection    用于确认查询哪些列
     * @param selection     用于约束查哪一行
     * @param selectionArgs 用于约束查哪一行
     * @param sortOrder     对查询结果进行排序
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                //查询table1表中的所有数据
                break;
            case TABLE1_ITEM:
                //查询table1表中的单条数据
                break;
            case TABLE2_DIR:
                //查询table2表中的所有数据
                break;
            case TABLE2_ITEM:
                //查询table1表中的单条数据
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * @param uri
     * @return根据传入的uri来返回相应MIME
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table2";
            case TABLE2_ITEM:
                return  "vnd.android.cursor.item/vnd.com.example.app.provider.table2";
            default:
                break;

        }
        return null;
    }

    /**
     * @param uri    指定表
     * @param values 待添加数据
     * @return 返回一个uri表示这条最新加入的数据
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    /**
     * @param uri           数据表的地址
     * @param selection     用于约束删除哪一行
     * @param selectionArgs 用于约束删除哪一行
     * @return 返回被删除的行数
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 更新
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
