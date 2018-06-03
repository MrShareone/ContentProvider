package com.example.mr_shareone.contentprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    @BindView(R.id.teledit)
    EditText editText;
    @BindView(R.id.tel)
    Button button;
    @BindView(R.id.showconector)
    Button show;
    Context context;
//    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();
        ButterKnife.bind(this);

//        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);
//        SQLiteDatabase db = dbHelper.getWritableDatabase(); // 新建bookstore数据库，包含两张表
//        ContentValues values = new ContentValues();
//
//        values.put("name","the da vinci code");
//        values.put("author","dan brown");
//        values.put("pages","454");
//        values.put("price","16.96");
//
//        db.insert("Book",null,values);
//        values.clear();
//
//        values.put("name","xxxxxxxxx");
//        values.put("author","xxxxxxx");
//        values.put("pages","454");
//        values.put("price","16.96");
//
//        db.insert("Book",null,values);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    @OnClick({R.id.tel, R.id.teledit, R.id.showconector})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tel:
                call();
                break;
            case R.id.teledit:
                break;
            case R.id.showconector:
                readContacts();
                break;
            default:
                break;
        }
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(1)
    public void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            if (EasyPermissions.hasPermissions(context, Manifest.permission.CALL_PHONE)) {
                Log.e("call", "can call,just do it");
                startActivity(intent);
            } else {
                EasyPermissions.requestPermissions(this, "我需要获取手机拨号权限", 1, Manifest.permission.CALL_PHONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //这样做将会把用户带到权限设置界面
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, EasyPermissions.hasPermissions(context, Manifest.permission.CALL_PHONE) ? "yes" : "no", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    @AfterPermissionGranted(2)
    public void readContacts() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
            Cursor cursor = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null);
            } else {
                Log.e("contacts", "版本过低");
            }

            try {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String tel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.e("contacts", "name:" + name + "tel:" + tel + "\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }else {
            EasyPermissions.requestPermissions(this, "我需要读取手机号码列表", 2, Manifest.permission.READ_CONTACTS);
        }
    }
}
