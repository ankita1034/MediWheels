package com.ogre.slide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import com.ogre.slide.models.HealthProduct;
import com.ogre.slide.models.User;
import com.ogre.slide.models.Cart;
import com.ogre.slide.models.Doctor;
import com.ogre.slide.models.Medicine;
import com.ogre.slide.models.Appointment;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static String TAG="DataBaseHelper";

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "AndroidDatabase";

    //Table Names
    private static final String TABLE_USER = "user";
    private static final String TABLE_MEDICINE="medicine";
    private static final String TABLE_HEALTH="health";
    private static final String TABLE_APP="appointment";
    private static final String TABLE_CART="cart";
    private static final String TABLE_DOCTOR="doctor";

    //User Table Columns
    private static final String USER_ID = "userid";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PHONE = "phone";

    //Appointment Table Columns
    private static final String APP_ID = "appid";
    private static final String APP_DOCTOR_ID = "doctorid";
    private static final String APP_TIME = "time";
    private static final String APP_DATE = "date";

    //Cart Table Columns
    private static final String CART_ID = "cartid";
    private static final String CART_MED_NAME = "medname";
    private static final String CART_PRODUCT_NAME = "productname";
    private static final String CART_QUANTITY = "quantity";
    private static final String CART_COST = "cost";

    //Doctor Table Columns
    private static final String DOCTOR_ID = "doctorid";
    private static final String DOCTOR_NAME = "name";
    private static final String DOCTOR_SPL = "specialization";

    //Medicine Table Columns
    private static final String MED_ID = "medid";
    private static final String MED_NAME = "name";
    private static final String MED_POWER = "power";
    private static final String MED_COST = "cost";

    //Products Table Columns
    private static final String HEALTH_ID = "healthid";
    private static final String HEALTH_NAME = "name";
    private static final String HEALTH_COST = "cost";

    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE="CREATE TABLE "+TABLE_USER+"("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                USER_NAME+" TEXT, "+USER_PASSWORD+" TEXT, "+USER_EMAIL+" TEXT, "+USER_PHONE+" TEXT"+")";
        db.execSQL(CREATE_USER_TABLE);
        Log.i(TAG,"User Table Created");

        String CREATE_APP_TABLE="CREATE TABLE "+TABLE_APP+"("+APP_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                APP_DOCTOR_ID+" INTEGER, "+APP_DATE+" TEXT, "+APP_TIME+" TEXT"+")";
        db.execSQL(CREATE_APP_TABLE);
        Log.i(TAG,"Appointment Table Created");

        String CREATE_CART_TABLE="CREATE TABLE "+TABLE_CART+"("+CART_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CART_MED_NAME+" TEXT, "+CART_PRODUCT_NAME+" TEXT, "+CART_QUANTITY+" INTEGER,"+CART_COST+" INTEGER"+")";
        db.execSQL(CREATE_CART_TABLE);
        Log.i(TAG,"Cart Table Created");

        String CREATE_DOCTOR_TABLE="CREATE TABLE "+TABLE_DOCTOR+"("+DOCTOR_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DOCTOR_NAME+" TEXT,"+DOCTOR_SPL+" TEXT "+")";
        db.execSQL(CREATE_DOCTOR_TABLE);
        Log.i(TAG, "Doctor Table Created");

        String CREATE_HEALTH_TABLE="CREATE TABLE "+TABLE_HEALTH+"("+HEALTH_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                HEALTH_NAME+" TEXT,"+HEALTH_COST+" INTEGER"+")";
        db.execSQL(CREATE_HEALTH_TABLE);
        Log.i(TAG, "Health Table Created");

        String CREATE_MEDICINES_TABLE="CREATE TABLE "+TABLE_MEDICINE+"("+MED_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                MED_NAME+" TEXT,"+MED_POWER+" INTEGER,"+MED_COST+" INTEGER"+")";
        db.execSQL(CREATE_MEDICINES_TABLE);
        Log.i(TAG, "Medicine Table Created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_APP);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HEALTH);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MEDICINE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DOCTOR);
        onCreate(db);
    }

    public void addUser(User user)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_PHONE, user.getPhone());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public List<User> getAllUsers()
    {
        List<User> userslist=new ArrayList<User>();
        String selectQuery="SELECT * FROM "+TABLE_USER;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                User user=new User();
                user.setUserid(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(3));
                user.setPassword(cursor.getString(2));
                user.setPhone(cursor.getString(4));

                userslist.add(user);
            }while (cursor.moveToNext());
        }
        return userslist;
    }

    public User getUser(int userid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] { USER_ID,
                        USER_NAME, USER_EMAIL,USER_PASSWORD,USER_PHONE }, USER_ID + "=?",
                new String[] { String.valueOf(userid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return contact
        return user;
    }

    public void addApp(Appointment app)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(APP_DOCTOR_ID,app.getDoctorid());
        values.put(APP_DATE,app.getDate());
        values.put(APP_TIME,app.getTime());
        db.insert(TABLE_APP, null, values);
        db.close();
    }

    public List<Appointment> getAllApps()
    {
        List<Appointment> appList=new ArrayList<Appointment>();
        String selectQuery="SELECT * FROM "+TABLE_APP;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                Appointment app=new Appointment();
                app.setAppid(Integer.parseInt(cursor.getString(0)));
                app.setDoctorid(Integer.parseInt(cursor.getString(1)));
                app.setDate(cursor.getString(2));
                app.setTime(cursor.getString(3));
                appList.add(app);
            }while (cursor.moveToNext());
        }
        return appList;
    }

    public void addCart(Cart cart)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CART_MED_NAME,cart.getMedname());
        values.put(CART_PRODUCT_NAME,cart.getProductname());
        values.put(CART_QUANTITY,cart.getQuantity());
        values.put(CART_COST,cart.getCost());
        db.insert(TABLE_CART, null, values);
        db.close();
    }


    public List<Cart> getCart()
    {
        List<Cart> cartList=new ArrayList<Cart>();
        String selectQuery="SELECT * FROM "+TABLE_CART;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                Cart cart = new Cart();
                cart.setCartid(Integer.parseInt(cursor.getString(0)));
                cart.setMedname((cursor.getString(1)));
                cart.setProductname(cursor.getString(2));
                cart.setQuantity(Integer.parseInt(cursor.getString(3)));
                cart.setCost(Integer.parseInt(cursor.getString(4)));
                cartList.add(cart);
            }while (cursor.moveToNext());
        }
        return cartList;
    }

    public void addDoctor(Doctor doctor)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DOCTOR_NAME,doctor.getName());
        values.put(DOCTOR_SPL,doctor.getSpecializtion());
        db.insert(TABLE_DOCTOR, null, values);
        db.close();
        Log.i(TAG,"Doctors Added");
    }

    public List<Doctor> getAllDoctors()
    {
        List<Doctor> docList=new ArrayList<Doctor>();
        String selectQuery="SELECT * FROM "+TABLE_DOCTOR;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                Doctor doctor = new Doctor();
                doctor.setDoctorid(Integer.parseInt(cursor.getString(0)));
                doctor.setName(cursor.getString(1));
                doctor.setSpecializtion(cursor.getString(2));
                docList.add(doctor);
            }while (cursor.moveToNext());
        }
        return docList;
    }


    public void addProduct(HealthProduct product)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(HEALTH_NAME,product.getName());
        values.put(HEALTH_COST, product.getCost());
        db.insert(TABLE_HEALTH, null, values);
        db.close();
    }

    public List<HealthProduct> getAllProducts()
    {
        List<HealthProduct> productList=new ArrayList<HealthProduct>();
        String selectQuery="SELECT * FROM "+TABLE_HEALTH;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                HealthProduct product=new HealthProduct();
                product.setHealthid(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setCost(Integer.parseInt(cursor.getString(2)));
                productList.add(product);
            }while (cursor.moveToNext());
        }
        return productList;
    }

    public void addMedicine(Medicine medicine)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MED_NAME, medicine.getName());
        values.put(MED_POWER, medicine.getPower());
        values.put(MED_COST, medicine.getCost());
        db.insert(TABLE_MEDICINE, null, values);
        db.close();
    }

    public List<Medicine> getAllMedicine()
    {
        List<Medicine> medicineList=new ArrayList<Medicine>();
        String selectQuery="SELECT * FROM "+TABLE_MEDICINE;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                Medicine medicine=new Medicine();
                medicine.setMedid(Integer.parseInt(cursor.getString(0)));
                medicine.setName(cursor.getString(1));
                medicine.setPower(Integer.parseInt(cursor.getString(2)));
                medicine.setCost(Integer.parseInt(cursor.getString(3)));
                medicineList.add(medicine);
            }while (cursor.moveToNext());
        }
        return medicineList;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        db.delete(TABLE_CART,null,null);

        db.delete(TABLE_DOCTOR,null,null);
        db.execSQL("delete from "+ TABLE_CART);
        db.execSQL("delete from "+ TABLE_MEDICINE);
        db.execSQL("delete from "+ TABLE_HEALTH);
        db.execSQL("delete from "+ TABLE_DOCTOR);
        db.execSQL("delete from "+ TABLE_APP);
        Log.i(TAG,"All Tables deleted");
        db.close();
    }

}
