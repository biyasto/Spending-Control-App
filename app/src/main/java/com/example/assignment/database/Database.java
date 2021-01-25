package com.example.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) { super(context, "QUANLYCHITIEUDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE THUCHI(" +
                "maKhoan integer PRIMARY KEY AUTOINCREMENT," +
                "tenKhoan text," +
                "loaiKhoan integer)";
        db.execSQL(sql);
        //0 là thu, 1 là chi
        sql = "INSERT INTO THUCHI VALUES(null,'Lương',0)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Lãi ngân hàng',0)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Tiền thưởng',0)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Tiền sinh hoạt',1)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Tiền ốm đau',1)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Mua sắm',1)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Lãi kinh doanh',0)";
        db.execSQL(sql);
        sql = "INSERT INTO THUCHI VALUES(null,'Xui xẻo',1)";
        db.execSQL(sql);
        //Tạo bảng giao dịch, cho maGd tự tăng lên
        sql = "CREATE TABLE GIAODICH(" +
                "maGd integer PRIMARY KEY AUTOINCREMENT," +
                "moTaGd text," +
                "ngayGd date," +
                "soTien integer, " +
                "maKhoan integer REFERENCES thuchi(maKhoan))";
        db.execSQL(sql);




        sql = "CREATE TABLE taiKhoan(tenTaiKhoan text primary key, matKhau text)";
        db.execSQL(sql);
        sql = "INSERT INTO taiKhoan VALUES('admin','admin')";
        db.execSQL(sql);
        sql = "INSERT INTO taiKhoan VALUES('admin1','admin1')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS thuchi");
        db.execSQL("DROP TABLE IF EXISTS giaodich");
        onCreate(db);
    }
}
