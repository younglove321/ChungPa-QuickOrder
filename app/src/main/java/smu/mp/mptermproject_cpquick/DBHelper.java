package smu.mp.mptermproject_cpquick;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "CPQuickDB.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_CART (cafe TEXT, menu TEXT, hotice TEXT, price int, count int)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  TABLE_LOG (cafe TEXT, stamp int, visit_cnt int)");
        db.execSQL("INSERT INTO TABLE_LOG VALUES('블루베리 카페', 0, 0);");
        db.execSQL("INSERT INTO TABLE_LOG VALUES('순헌관 카페', 0, 0);");
        db.execSQL("INSERT INTO TABLE_LOG VALUES('본솔', 0, 0);");
        db.execSQL("INSERT INTO TABLE_LOG VALUES('청파맨션', 0, 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS TABLE_CART");
//        db.execSQL("DROP TABLE IF EXISTS TABLE_LOG");
//        onCreate(db);
    }
}

