package com.ogre.slide.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ogre.slide.DatabaseHandler;
import com.ogre.slide.JSONParser;
import com.ogre.slide.R;
import com.ogre.slide.adapters.MedCustomAdapter;
import com.ogre.slide.adapters.ProductCustomAdapter;
import com.ogre.slide.models.Appointment;
import com.ogre.slide.models.Cart;
import com.ogre.slide.models.Doctor;
import com.ogre.slide.models.HealthProduct;
import com.ogre.slide.models.ListModel;
import com.ogre.slide.models.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";

    Calendar calendar;
    int date, month, year;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int hour, minutes;
    String appdate;
    String apptime;
    String doctorname;
    Boolean exit = false;

    int total=0;
    TextView aboutUs;
    EditText quantityview;
    TextView orderText;
    List<Doctor> doctorList;
    DatabaseHandler db;
    Doctor doctorObj;
    int doctorid;
    TextView headerText;
    TextView appText;
    ImageView imageView;
    JSONParser parser = new JSONParser();
    List<Cart> cartList;
    public static final String PREFS_NAME = "MyPrefsFile";


    ListView list;
    MedCustomAdapter medadapter;
    ProductCustomAdapter productadapter;
    public MainActivity activity = null;
    public ArrayList<Medicine> medcustomListViewValues = new ArrayList<>();
    public ArrayList<HealthProduct> productcustomListViewValues = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if(!hasLoggedIn)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        imageView = (ImageView) findViewById(R.id.imageView2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        headerText = (TextView) header.findViewById(R.id.textView);
        headerText.setText("Anshika");

    }

    @Override
    public void onBackPressed() {
        db=new DatabaseHandler(this);
        db.deleteAll();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (exit) {
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_medicine) {

            if(orderText!=null)
                orderText.setVisibility(View.INVISIBLE);
            if(appText!=null)
                appText.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(), "Medicine", Toast.LENGTH_SHORT).show();
            imageView.setVisibility(View.INVISIBLE);
            activity = this;
            setMedData();

            Resources resources = getResources();
            list = (ListView) findViewById(R.id.list);
            list.setVisibility(View.VISIBLE);
            medadapter = new MedCustomAdapter(activity, medcustomListViewValues, resources);
            list.setAdapter(medadapter);


        }
        else if (id == R.id.nav_book)
        {

            if(orderText!=null)
                orderText.setVisibility(View.INVISIBLE);
            if(appText!=null)
                appText.setVisibility(View.INVISIBLE);

            doctors();
            if(orderText!=null)
            orderText.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setTitle("Select Doctor");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    MainActivity.this,
                    android.R.layout.select_dialog_singlechoice);


            db = new DatabaseHandler(MainActivity.this);
            doctorList = db.getAllDoctors();

            for (Doctor doctor : doctorList) {
                Log.i(TAG, String.valueOf((doctor.getDoctorid())));
                arrayAdapter.add(doctor.getName() + "-" + doctor.getSpecializtion());

            }
            builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doctorname = arrayAdapter.getItem(which);
                            doctorid = which + 1;
                            Log.i(TAG, String.valueOf(doctorid));
                            Toast.makeText(MainActivity.this, "Select Appointment Date", Toast.LENGTH_SHORT).show();
                            showDialog(1111);
                            showDialog(999);
                        }
                    });
            builderSingle.show();
            calendar = Calendar.getInstance();




        } else if (id == R.id.nav_products) {


            if(orderText!=null)
                orderText.setVisibility(View.INVISIBLE);
            if(appText!=null)
                appText.setVisibility(View.INVISIBLE);
            if(appText!=null)
                appText.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Products", Toast.LENGTH_SHORT).show();
            imageView.setVisibility(View.INVISIBLE);
            activity = this;
            setProductData();

            Resources resources = getResources();
            list = (ListView) findViewById(R.id.list);
            list.setVisibility(View.VISIBLE);
            productadapter = new ProductCustomAdapter(activity, productcustomListViewValues, resources);
            list.setAdapter(productadapter);

        } else if (id == R.id.nav_status) {

            imageView.setVisibility(View.INVISIBLE);
            if(list!=null)
                list.setVisibility(View.INVISIBLE);

            appText = (TextView) findViewById(R.id.textView2);
            if(doctorname==null)
            {
                appText.setText("You dont have an appointment booked");
            }
            else {
                appText.setText("Appointment Done at " + apptime + " on " + appdate + " with Dr." + doctorname);
            }


        } else if (id == R.id.nav_about) {

            if(orderText!=null)
                orderText.setVisibility(View.INVISIBLE);
            if(appText!=null)
                appText.setVisibility(View.INVISIBLE);
            aboutUs=(TextView)findViewById(R.id.abouUs);
            imageView.setVisibility(View.INVISIBLE);
            aboutUs.setText("This app is mainly for a person to perform multiple operations at a flip od his eyes which contains " +
                    "shopping of medicines and health products");

        } else if (id == R.id.nav_send) {
            if(orderText!=null)
                orderText.setVisibility(View.INVISIBLE);
            if(appText!=null)
                appText.setVisibility(View.INVISIBLE);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            datePickerDialog = new DatePickerDialog(this, myDateListener, year, month, date);
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            return datePickerDialog;
        }
        if (id == 1111) {
            timePickerDialog = new TimePickerDialog(this, mytimelistener, hour, minutes, false);
            return timePickerDialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            year = arg1;
            month = arg2;
            date = arg3;
            appdate = String.valueOf(date) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
            Toast.makeText(MainActivity.this, "Select Appointment Time", Toast.LENGTH_SHORT).show();
            Log.i(TAG, appdate);
        }
    };


    private TimePickerDialog.OnTimeSetListener mytimelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            minutes = minute;
            if ((hourOfDay > 10 &&hourOfDay<14)||(hourOfDay > 16 &&hourOfDay<20)) {
                Toast.makeText(MainActivity.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                apptime = String.valueOf(hour) + ":" + String.valueOf(minute);
                Log.i(TAG, apptime);
                db = new DatabaseHandler(MainActivity.this);
                db.addApp(new Appointment(doctorid, apptime, appdate));
                Log.i(TAG, "Appointment Done at" + apptime + " on " + appdate+" with Dr."+doctorname);
                imageView.setVisibility(View.INVISIBLE);
                if(list!=null)
                    list.setVisibility(View.INVISIBLE);
                appText = (TextView) findViewById(R.id.textView2);
                appText.setText("Appointment Done at " + apptime + " on " + appdate + " with Dr." + doctorname);

            } else {
                Toast.makeText(MainActivity.this, "Please select between 10 AM to 2 PM and 4 PM to 8PM", Toast.LENGTH_LONG).show();
                showDialog(1111);

            }

        }

    };


    public void doctors() {
        HashMap<String, String> doctors = parser.getDoctors();
        Iterator it = doctors.entrySet().iterator();
        db = new DatabaseHandler(MainActivity.this);
        while ((it.hasNext())) {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String doctor = (String) entry.getKey();
            String spec = (String) entry.getValue();
            doctorObj = new Doctor(doctor, spec);
            Log.i(TAG, "Doctors-" + doctor + " " + spec);

            db.addDoctor(doctorObj);

        }
    }

    public void setMedData() {
        Log.i(TAG, "Inside setListData");


        String medicineslist[] = {"Combiflame", "Disprin", "Paracetamol", "Citrazin", "Acarbose", "Accolate", "Almita", "Alinia", "Alkeran", "Allegra", "Alli", "Alocril", "Alpha", "Alrex", "Altace"};
        int power[] = {5, 10, 5, 10, 50, 20, 25, 25, 5, 25, 60, 100, 50, 25, 10};
        int cost[] = {10, 20, 4, 8, 14, 15, 63, 24, 23, 75, 23, 65, 33, 21, 43};
        db = new DatabaseHandler(MainActivity.this);
        for (int i = 0; i < 15; i++) {
            Medicine med = new Medicine(medicineslist[i], power[i], cost[i]);
            medcustomListViewValues.add(med);
            db.addMedicine(med);
        }

        List<Cart> carts=db.getCart();
        for(Cart cart : carts)
        {
            String log=cart.getMedname()+" "+cart.getProductname()+" "+cart.getQuantity()+" "+cart.getCost();
            Log.d("User ",log);
        }
        Log.i(TAG, "Medicines Added");
    }

    public void setProductData() {
        String healthlist[]={"Soap","Shampoo","Cream","Vitamin","Comb","handwash","facewash","Protein Shake","Thermometer","Weighing Scale","ToothPaste","ToothBrush","Conditioner"};
        int cost[]={20,35,50,40,40,20,100,200,230,320,150,50,60};
        db = new DatabaseHandler(MainActivity.this);
        for (int i = 0; i < 12; i++) {
            HealthProduct product=new HealthProduct(healthlist[i],cost[i]);
            productcustomListViewValues.add(product);
            db.addProduct(product);
        }
        Log.i(TAG, "Medicines Added");
    }

    public void onMedClick(int mposition) {

        final Medicine medicine =medcustomListViewValues.get(mposition);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            // set dialog icon
            alertDialog.setIcon(android.R.drawable.stat_notify_error)
                    // set Dialog Title
                    .setTitle("Select Quantity")
                    .setMessage(medicine.getName());
            quantityview = new EditText(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
            quantityview.setLayoutParams(lp);
            quantityview.setInputType(InputType.TYPE_CLASS_NUMBER);
            alertDialog.setView(quantityview);
            // positive button
            alertDialog.setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if (quantityview.getText().toString().trim().length() > 0) {
                        db = new DatabaseHandler(MainActivity.this);
                        db.addCart(new Cart(medicine.getName(), Integer.parseInt(quantityview.getText().toString()), medicine.getCost()));
                        Log.i(TAG, medicine.getName() + " added to cart");
                    }
                }
            }).setNeutralButton("Go To Cart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                    builderSingle.setTitle("Cart Items");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            MainActivity.this,
                            android.R.layout.simple_expandable_list_item_1);


                    db = new DatabaseHandler(MainActivity.this);
                    cartList = db.getCart();

                    if(cartList.isEmpty())
                    {
                        Toast.makeText(MainActivity.this,"Please select items",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (Cart cart : cartList) {
                        Log.i(TAG, String.valueOf((cart.getMedname())));
                        arrayAdapter.add(cart.getMedname() + "-" + cart.getQuantity());
                        total=total+cart.getQuantity()*cart.getCost();
                    }
                    builderSingle.setNegativeButton(
                            "Buy",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                                    list.setVisibility(View.INVISIBLE);
                                    orderText = (TextView) findViewById(R.id.order);
                                    orderText.setText("Your Order has been placed");
                                }
                            });
                    builderSingle.setPositiveButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builderSingle.setNeutralButton(
                            "Total Rs." + String.valueOf(total),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builderSingle.setAdapter(
                            arrayAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builderSingle.show();



                }
            });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            alertDialog.show();
        }

    public void onProductClick(int mposition) {

        final HealthProduct product = (HealthProduct) productcustomListViewValues.get(mposition);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        // set dialog icon
        alertDialog.setIcon(android.R.drawable.stat_notify_error)
                // set Dialog Title
                .setTitle("Select Quantity")
                .setMessage(product.getName());
        quantityview = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        quantityview.setLayoutParams(lp);
        quantityview.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(quantityview);
        // positive button
        alertDialog.setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (quantityview.getText().toString().trim().length() > 0){
                db=new DatabaseHandler(MainActivity.this);
                db.addCart(new Cart(product.getName(), Integer.parseInt(quantityview.getText().toString()),product.getCost()));
                Log.i(TAG,product.getName()+" added to cart");
                }
            }
        }).setNeutralButton("Go To Cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                builderSingle.setTitle("Cart Items");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_expandable_list_item_1);


                db = new DatabaseHandler(MainActivity.this);
                cartList = db.getCart();

                if(cartList.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please select items,cart is empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Cart cart : cartList) {
                    Log.i(TAG, String.valueOf((cart.getMedname())));
                    arrayAdapter.add(cart.getMedname() + "-" + cart.getQuantity());
                    total=total+cart.getQuantity()*cart.getCost();
                }
                builderSingle.setNegativeButton(
                        "Buy",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                                list.setVisibility(View.INVISIBLE);
                                orderText=(TextView)findViewById(R.id.order);
                                orderText.setText("Your Order has been placed");
                            }
                        });
                builderSingle.setPositiveButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builderSingle.setNeutralButton(
                        "Total Rs." + String.valueOf(total),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builderSingle.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builderSingle.show();
                total=0;

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }


    }



