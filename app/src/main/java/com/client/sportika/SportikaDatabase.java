package com.client.sportika;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pritesh on 6/15/2017.
 */

public class SportikaDatabase {



    private static final String DATABASE_NAME = "sportika";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_PRODUCT = "product";

    public static final String KEY_PID = "id";

    public static final String KEY_PNAME = "name";
    public static final String KEY_PBRAND = "brand";
    public static final String KEY_PWEIGHT = "weight";
    public static final String KEY_PFLVR = "flvr";
    public static final String KEY_PPRICE = "price";
    public static final String KEY_PSALEPRICE = "sale";




    private static final String DATABASE_PURCHASEITEM = "purchase";

    public static final String KEY_ID = "cid";

    public static final String KEY_INAME = "iname";
    public static final String KEY_IBRAND = "ibrand";
    public static final String KEY_IFLVR = "iflvr";
    public static final String KEY_IWEIGHT = "iweight";
    public static final String KEY_IQUNT = "quantity";
    public static final String KEY_ISALEPRICE = "saleprice";
    public static final String KEY_IOTAL = "total";




    private Dbhelper oHelper;
    private final Context ocontext;
    private SQLiteDatabase oDtabase;



    private static class Dbhelper extends SQLiteOpenHelper {

        public Dbhelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(" CREATE TABLE " + DATABASE_PRODUCT + " (" + KEY_PID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PNAME + " TEXT, " + KEY_PBRAND + " TEXT, " + KEY_PFLVR + " TEXT, " + KEY_PWEIGHT + " TEXT, " + KEY_PPRICE + " TEXT, " + KEY_PSALEPRICE + " TEXT);"
            );


            db.execSQL(" CREATE TABLE " + DATABASE_PURCHASEITEM + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_INAME + " TEXT, " + KEY_IBRAND + " TEXT, " + KEY_IFLVR + " TEXT, " + KEY_IWEIGHT + " TEXT, " + KEY_IQUNT + " TEXT, " + KEY_ISALEPRICE + " TEXT, " + KEY_IOTAL + " TEXT);"
            );



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXIST" + DATABASE_PRODUCT);
            onCreate(db);


        }
    }

    public SportikaDatabase(Context c) {
        ocontext = c;
        oHelper = new Dbhelper(c);
        oDtabase = oHelper.getWritableDatabase();
    }

    public SportikaDatabase open() {
        oHelper = new Dbhelper(ocontext);
        oDtabase = oHelper.getWritableDatabase();
        oDtabase = oHelper.getReadableDatabase();
        return this;

    }

    public void close() {
        oHelper.close();

    }



    public long productentry (String name, String brand,String flvr,String weight,String price,String salepr){



        ContentValues lcv = new ContentValues();

        lcv.put(KEY_PNAME,name);
        lcv.put(KEY_PBRAND,brand);
        lcv.put(KEY_PFLVR,flvr);
        lcv.put(KEY_PWEIGHT,weight);
        lcv.put(KEY_PPRICE,price);
        lcv.put(KEY_PSALEPRICE,salepr);



        return oDtabase.insert(DATABASE_PRODUCT, null, lcv);


    }


    public long purchasetentry (String name, String brand,String flvr,String weight,String quantity ,String price,String total){



        ContentValues lcv = new ContentValues();

        lcv.put(KEY_INAME,name);
        lcv.put(KEY_IBRAND,brand);
        lcv.put(KEY_IFLVR,flvr);
        lcv.put(KEY_IWEIGHT,weight);
        lcv.put(KEY_IQUNT,quantity);
        lcv.put(KEY_ISALEPRICE,price);
        lcv.put(KEY_IOTAL,total);



        return oDtabase.insert(DATABASE_PURCHASEITEM, null, lcv);


    }


/*
    public long contactentry (String customername, String contactid){


       // deleteitm();

        ContentValues lcv = new ContentValues();

        lcv.put(KEY_NAME,customername);
        lcv.put(KEY_CSTID,contactid);

        return oDtabase.insert(DATABASE_CONTACT, null, lcv);


    }
*/

    public Cursor getdata(String name,String brand,String flvr,String weight) {

        Cursor c = oDtabase.rawQuery(
                "SELECT * FROM " + DATABASE_PRODUCT + " WHERE "
                        + KEY_PNAME + "='" + name +"'AND " + KEY_PBRAND + "='" + brand +"'AND " + KEY_PFLVR + "='" + flvr +"'AND " + KEY_PWEIGHT + "='" + weight +"'" , null);

        return c;
    }



  /*  public Cursor getcontact(String custid) {

        Cursor c1 = oDtabase.rawQuery(
                "SELECT * FROM " + DATABASE_CONTACT + " WHERE "
                        + KEY_CSTID + "='" + custid +"'" , null);
        return c1;
    }



    public void deleteitm(){


        SQLiteDatabase db = this.oHelper.getWritableDatabase();

        db.execSQL("delete from " + DATABASE_CUSTOMER);

        db.close();
    }
*/

    public void deleteitm1(){

        oDtabase.execSQL("delete from " + DATABASE_PRODUCT);
    }


    public Cursor getaddProduct() {
        // TODO Auto-generated method stub

        Cursor ssyll1 = oDtabase.query(DATABASE_PURCHASEITEM, null, null, null, null, null,
                null);


        return ssyll1;
    }

}
