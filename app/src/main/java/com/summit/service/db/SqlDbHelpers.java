package com.summit.service.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.summit.service.model.order.ProductOrderModel;
import com.summit.service.model.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class SqlDbHelpers extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // Table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_PRODUCT_ORDER = "productorder";
    private static final String TABLE_ORDER = "ordertb";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_ADDRESS = "user_address";

    // Product Order Table columns
    private static final String COLUMN_PRODUCT_ORDER_USER_ID = "product_user_id";
    private static final String COLUMN_PRODUCT_ORDER_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_ORDER_NUMBER = "product_number"; //so luong mua
    private static final String COLUMN_PRODUCT_ORDER_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_ORDER_PRODUCT_IMG = "product_img";
    private static final String COLUMN_PRODUCT_ORDER_PRODUCT_OLDPRICE = "product_old_price";
    private static final String COLUMN_PRODUCT_ORDER_PRODUCT_DISCOUNT = "product_discount";

    // Order Table columns
    private static final String COLUMN_ORDER_PHONE = "order_phone";
    private static final String COLUMN_ORDER_ADDRESS = "order_address";
    private static final String COLUMN_ORDER_INFO = "order_info";
    private static final String COLUMN_ORDER_USER_ID = "order_user_id";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT,"
            + COLUMN_USER_ADDRESS + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    //create table product order
    private String CREATE_PRODUCT_ORDER_TABLE = "CREATE TABLE " + TABLE_PRODUCT_ORDER + "("
            + COLUMN_PRODUCT_ORDER_USER_ID + " INTEGER,"
            + COLUMN_PRODUCT_ORDER_PRODUCT_ID + " INTEGER,"
            + COLUMN_PRODUCT_ORDER_NUMBER + " INTEGER,"
            + COLUMN_PRODUCT_ORDER_PRODUCT_NAME + " TEXT,"
            + COLUMN_PRODUCT_ORDER_PRODUCT_IMG + " TEXT,"
            + COLUMN_PRODUCT_ORDER_PRODUCT_DISCOUNT + " INTEGER,"
            + COLUMN_PRODUCT_ORDER_PRODUCT_OLDPRICE + " INTEGER" + ")";

    private String DROP_PRODUCT_ORDER_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT_ORDER;

    //create table
    private String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + "("
            + COLUMN_ORDER_USER_ID + " INTEGER,"
            + COLUMN_ORDER_PHONE + " TEXT,"
            + COLUMN_ORDER_INFO + " TEXT,"
            + COLUMN_ORDER_ADDRESS + " TEXT" + ")";

    private String DROP_ORDER_TABLE = "DROP TABLE IF EXISTS " + TABLE_ORDER;

    String[] ProductOrderColumns = {
            COLUMN_PRODUCT_ORDER_USER_ID ,
            COLUMN_PRODUCT_ORDER_PRODUCT_ID ,
            COLUMN_PRODUCT_ORDER_NUMBER ,
            COLUMN_PRODUCT_ORDER_PRODUCT_NAME ,
            COLUMN_PRODUCT_ORDER_PRODUCT_IMG ,
            COLUMN_PRODUCT_ORDER_PRODUCT_DISCOUNT,
            COLUMN_PRODUCT_ORDER_PRODUCT_OLDPRICE
    };

    /**
     * Constructor
     *
     * @param context
     */
    public SqlDbHelpers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_ORDER_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUCT_ORDER_TABLE);
        db.execSQL(DROP_ORDER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.getID());
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public UserModel getFirstUser(){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_PHONE,
                COLUMN_USER_ADDRESS
        };
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                null,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);
        if(cursor.getCount() > 0) {
            if(cursor.moveToFirst()) {
                UserModel userModel = userFromCursor(cursor);
                cursor.close();
                return userModel;
            }
        }
        cursor.close();
        return null;
    }

    public UserModel getUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_PHONE,
                COLUMN_USER_ADDRESS
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);
        if(cursor.getCount() > 0) {
            if(cursor.moveToFirst()) {
                UserModel userModel = userFromCursor(cursor);
                cursor.close();
                return userModel;
            }
        }
        cursor.close();
        return null;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<UserModel> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_PHONE,
                COLUMN_USER_ADDRESS
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<UserModel> userList = new ArrayList<UserModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel user = userFromCursor(cursor);
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }

    //delete all row
    public void deleteAllUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    private UserModel userFromCursor(Cursor cursor){
        UserModel user = new UserModel();
        user.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
        user.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)));
        return user;
    }


    /*table product order*/
    public void addProductOrder(ProductOrderModel productOrderModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = contentValuesFromProductModel(productOrderModel);

        // Inserting Row
        db.insert(TABLE_PRODUCT_ORDER, null, values);
        db.close();
    }

    public void updateProductOrder(ProductOrderModel productOrderModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = contentValuesFromProductModel(productOrderModel);

        // updating row
        db.update(TABLE_PRODUCT_ORDER, values, COLUMN_PRODUCT_ORDER_USER_ID + " = ? AND " + COLUMN_PRODUCT_ORDER_PRODUCT_ID + " =? ",
                new String[]{String.valueOf(productOrderModel.getUserID()), String.valueOf(productOrderModel.getProductID())});
        db.close();
    }

    public ProductOrderModel getProductOrder(int userId, int productId) {
        ProductOrderModel prOr = null;
        // sorting orders
        String sortOrder =
                COLUMN_PRODUCT_ORDER_USER_ID + " ASC";
        String selection = COLUMN_PRODUCT_ORDER_USER_ID + " = ? AND " + COLUMN_PRODUCT_ORDER_PRODUCT_ID + " =? ";
        String[] selectionArgs = {Integer.toString(userId), Integer.toString(productId)};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT_ORDER, //Table to query
                ProductOrderColumns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            prOr = productOrderFromCursor(cursor);
        }
        cursor.close();
        db.close();

        // return user list
        return prOr;
    }

    public List<ProductOrderModel> getProductOrderByUserId(int userId) {

        // sorting orders
        String sortOrder =
                COLUMN_PRODUCT_ORDER_USER_ID + " ASC";
        String selection = COLUMN_PRODUCT_ORDER_USER_ID + " = ?";
        String[] selectionArgs = {Integer.toString(userId)};

        List<ProductOrderModel> prOrList = new ArrayList<ProductOrderModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT_ORDER, //Table to query
                ProductOrderColumns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductOrderModel prOr = productOrderFromCursor(cursor);
                // Adding user record to list
                prOrList.add(prOr);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return prOrList;
    }

    public void deleteProductOrder(int userId, int productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT_ORDER, COLUMN_PRODUCT_ORDER_USER_ID + " = ? AND " + COLUMN_PRODUCT_ORDER_PRODUCT_ID + " =? "
                , new String[]{Integer.toString(userId), Integer.toString(productId)});
        db.close();
    }

    public void deleteAllProductOrder(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT_ORDER, null, null);
        db.close();
    }

    private ProductOrderModel productOrderFromCursor(Cursor cursor){
        ProductOrderModel user = new ProductOrderModel();
        user.setUserID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_USER_ID))));
        user.setImg(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_PRODUCT_IMG)));
        user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_PRODUCT_NAME)));
        user.setProductID(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_PRODUCT_ID)));
        int discount = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_PRODUCT_DISCOUNT));
        int oldPrice = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_PRODUCT_OLDPRICE));
        user.setDiscount(discount);
        user.setOldPrice(oldPrice);
        if(discount > 0){
            user.setPrice(oldPrice * (100 - discount) / 100);
        }else{
            user.setPrice(oldPrice);
        }
        user.setNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ORDER_NUMBER)));
        return user;
    }

    private ContentValues contentValuesFromProductModel(ProductOrderModel productOrderModel){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ORDER_USER_ID, productOrderModel.getUserID());
        values.put(COLUMN_PRODUCT_ORDER_PRODUCT_ID, productOrderModel.getProductID());
        values.put(COLUMN_PRODUCT_ORDER_NUMBER, productOrderModel.getNumber());
        values.put(COLUMN_PRODUCT_ORDER_PRODUCT_DISCOUNT, productOrderModel.getDiscount());
        values.put(COLUMN_PRODUCT_ORDER_PRODUCT_IMG, productOrderModel.getImg());
        values.put(COLUMN_PRODUCT_ORDER_PRODUCT_NAME, productOrderModel.getName());
        values.put(COLUMN_PRODUCT_ORDER_PRODUCT_OLDPRICE, productOrderModel.getOldPrice());
        return values;
    }
}
