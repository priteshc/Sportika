package com.client.sportika;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.client.sportika.Interface.Productview;
import com.client.sportika.Interface.Registerview;
import com.client.sportika.Presenter.AddProductPresenter;
import com.client.sportika.Presenter.ProductPresenter;
import com.client.sportika.adapter.Item_Adapter;
import com.client.sportika.model.AddProductPojo;
import com.client.sportika.model.Product;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by HP on 12/12/2017.
 */

public class Purchase extends AppCompatActivity implements Registerview,Productview {

    TextView add,view;

    Dialog dialog;
    private Toolbar toolbar;
    private EditText totalamt;
    private MaterialSpinner payment,tax;

    private AddProductPresenter addProductPresenter;

    private Context context = Purchase.this;

    private SweetAlertDialog pDialog;

    private ProductPresenter productPresenter;

    private ArrayList<String> prodname = new ArrayList<>();

    private ArrayList<Integer> purchprice = new ArrayList<>();



    private ArrayList<String> brandname = new ArrayList<>();
    private ArrayList<String> flavrname = new ArrayList<>();
    private ArrayList<String> weight1 = new ArrayList<>();


    private ArrayList<String> paymentype = new ArrayList<>();

    private BottomSheetBehavior bottomSheetBehavior;

    private ImageView down;

    private SportikaDatabase sportikaDatabase;

    private  Cursor cursor,cursor1;

    private RecyclerView recyclerView;

    ArrayList<String>proname = new ArrayList<>();
    ArrayList<String>probrand = new ArrayList<>();
    ArrayList<String>proflvr = new ArrayList<>();
    ArrayList<String>proweight = new ArrayList<>();
    ArrayList<String>proquant = new ArrayList<>();

    ArrayList<String>alltax = new ArrayList<>();

    private Item_Adapter itemAdapter;

    private ImageView imageView,imageView1;
    private Button takeimg;
    private static final int CAMERA_REQUEST = 1888;

    private static final int CAMERA_REQUEST1 = 10;

    private TextView taxcal,totalamount;

    private ArrayList<AddProductPojo>addProductPojos = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.purchase);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#CD282F"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        addProductPresenter = new AddProductPresenter(this);

        productPresenter = new ProductPresenter(this);


        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        takeimg = (Button) findViewById(R.id.takepic);

        imageView = (ImageView) findViewById(R.id.imageView1);

        add = (TextView) findViewById(R.id.add);

        view = (TextView) findViewById(R.id.view);

        down = (ImageView) findViewById(R.id.down);

        totalamt= (EditText) findViewById(R.id.total);

        payment  = (MaterialSpinner) findViewById(R.id.payment);

        tax  = (MaterialSpinner) findViewById(R.id.tax);

        taxcal = (TextView) findViewById(R.id.taxvalue);

        totalamount = (TextView) findViewById(R.id.totalamt);


        tax.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {


                int i,j,tot;
                float d = 0f;

                if(position == 0){

                    i = Integer.parseInt(totalamt.getText().toString());

                    taxcal.setText("0");

                    totalamount.setText(String.valueOf(i));


                }

                if(position > 0 && !totalamt.getText().toString().equals("")&& !totalamt.getText().toString().equals("0")){

                    i = Integer.parseInt(totalamt.getText().toString());

                    if(position == 1){

                       d = (i*18)/100;

                        j = Math.round(d);

                        taxcal.setText(String.valueOf(j));

                        tot = i + j;

                        totalamount.setText(String.valueOf(tot));


                    }

                    if(position == 2){

                        d = (i*9)/100;

                        j = Math.round(d);

                        taxcal.setText(String.valueOf(j));

                        tot = i + j;

                        totalamount.setText(String.valueOf(tot));

                    }

                    if(position == 3){

                        d = (i*9)/100;

                        j = Math.round(d);

                        taxcal.setText(String.valueOf(j));

                        tot = i + j;

                        totalamount.setText(String.valueOf(tot));
                    }

                }
            }
        });


        sportikaDatabase = new SportikaDatabase(this);

        sportikaDatabase.open();

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rec1);

        LinearLayoutManager llm = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prodname.clear();
                probrand.clear();
                proflvr.clear();
                proweight.clear();
                proquant.clear();

                cursor1 = sportikaDatabase.getaddProduct();
                if(cursor1.getCount()>0){

                    for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {

                     String  proname = cursor1.getString(cursor1.getColumnIndex(sportikaDatabase.KEY_INAME));
                      String  probrand = cursor1.getString(cursor1.getColumnIndex(sportikaDatabase.KEY_IBRAND));
                       String  proflvr =cursor1.getString(cursor1.getColumnIndex(sportikaDatabase.KEY_IFLVR));
                       String proweight =cursor1.getString(cursor1.getColumnIndex(sportikaDatabase.KEY_IWEIGHT));
                       String proquant = cursor1.getString(cursor1.getColumnIndex(sportikaDatabase.KEY_IQUNT));

                        AddProductPojo productPojo = new AddProductPojo(proname,probrand,proflvr,proweight,proquant);

                        addProductPojos.add(productPojo);

                    }


                    itemAdapter = new Item_Adapter(context,addProductPojos);

                    recyclerView.setAdapter(itemAdapter);

                }

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });


        paymentype.add(0,"select payment");
        paymentype.add("cash");
        paymentype.add("check");
        payment.setItems(paymentype);


        alltax.add(0,"Tax");
        alltax.add("IGST-18%");
        alltax.add("CGST-9%");
        alltax.add("SGST-9%");
        tax.setItems(alltax);


        payment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(position == 2){

                    takeimg.setVisibility(View.VISIBLE);

                }
                else {

                    takeimg.setVisibility(View.GONE);
                }
            }
        });

        takeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenPopUp();

            }
        });

        productPresenter.getProduct();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);
        }

        if (requestCode == CAMERA_REQUEST1 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView1.setImageBitmap(photo);
            imageView1.setVisibility(View.VISIBLE);
        }


    }

    private void OpenPopUp() {
        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        dialog = new Dialog(Purchase.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  dialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;

        dialog.setContentView(R.layout.add_transaction_item);

        final AutoCompleteTextView proname;

        proname = dialog.findViewById(R.id.name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,prodname);

        proname.setThreshold(1);//will start working from first character
        proname.setAdapter(adapter);

         int b,f,w;
        final MaterialSpinner brand,flavr,weight;

        final EditText price,quantity;
        final TextView subtot;

        final  Button save;

        save = dialog.findViewById(R.id.log1);

        price = dialog.findViewById(R.id.price);
        quantity = dialog.findViewById(R.id.quantity);
        subtot = dialog.findViewById(R.id.subtot);

        brand = dialog.findViewById(R.id.brand);

        flavr = dialog.findViewById(R.id.flavr);

        weight = dialog.findViewById(R.id.weight);

        brandname.add(0,"select brand");

        flavrname.add(0,"select flavr");

        weight1.add(0,"select weight");

        brand.setItems(brandname);

        flavr.setItems(flavrname);

        weight.setItems(weight1);

        sportikaDatabase.open();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(proname.getText().toString().equals("")){

                    Toast.makeText(context,"plz enter product name",Toast.LENGTH_SHORT).show();
                }
                else {

                    if(brand.getSelectedIndex() == 0){

                        Toast.makeText(context,"plz select brand name",Toast.LENGTH_SHORT).show();

                    }
                    else {

                        if ((flavr.getSelectedIndex()== 0)){

                            Toast.makeText(context,"plz select flvr",Toast.LENGTH_SHORT).show();

                        }
                        else {

                            if (weight.getSelectedIndex() == 0){

                                Toast.makeText(context,"plz select weight",Toast.LENGTH_SHORT).show();

                            }
                            else {

                               if (quantity.getText().toString().equals("")){

                                   Toast.makeText(context,"plz select quantity",Toast.LENGTH_SHORT).show();

                               }
                               else {

                                  if(price.getText().toString().equals("")){

                                      Toast.makeText(context,"plz enter product price",Toast.LENGTH_SHORT).show();

                                  }
                                   else {

                                      int b = brand.getSelectedIndex();
                                      int f = flavr.getSelectedIndex();
                                      int w = weight.getSelectedIndex();

                                      sportikaDatabase.purchasetentry(proname.getText().toString(),brandname.get(b),flavrname.get(f),weight1.get(w),quantity.getText().toString(),price.getText().toString(),subtot.getText().toString());

                                      Toast.makeText(context,"item added successfully",Toast.LENGTH_SHORT).show();

                                      cursor1 = sportikaDatabase.getaddProduct();
                                      if(cursor1.getCount()>0) {

                                          purchprice.clear();

                                          for (cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()) {


                                              purchprice.add(Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(sportikaDatabase.KEY_IOTAL))));

                                          }

                                          int tot = 0;
                                          for (int i =0; i<purchprice.size();i++ ){

                                              tot = tot + purchprice.get(i);

                                          }

                                          totalamt.setText(String.valueOf(tot));

                                          totalamount.setText(String.valueOf(tot));
                                      }

                                      dialog.dismiss();
                                  }
                               }
                            }
                        }
                    }
                }
            }
        });


        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().equals("") && !editable.toString().equals("0") && !price.getText().toString().equals("") && !price.getText().toString().equals("0") ) {

                    int q = Integer.parseInt(quantity.getText().toString());

                    int p = Integer.parseInt(price.getText().toString());

                    int tot = q * p;

                    subtot.setText(String.valueOf(tot));

                }
                else {

                    subtot.setText("0");

                }


            }
        });


        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().equals("") && !editable.toString().equals("0") && !quantity.getText().toString().equals("") && !quantity.getText().toString().equals("0") ) {

                int q = Integer.parseInt(quantity.getText().toString());

                    int p = Integer.parseInt(price.getText().toString());

                    int tot = q * p;

                    System.out.println("cot:" + tot);

                    subtot.setText(String.valueOf(tot));


                    //      subtot.setText(tot);


                }
                else {

                    subtot.setText("0");


                }

                }
        });


        brand.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(position != 0){

                   if(flavr.getSelectedIndex() !=0 && weight.getSelectedIndex() !=0 && !proname.getText().toString().equals("")){

                    int   f = flavr.getSelectedIndex();
                     int  w = weight.getSelectedIndex();

                    cursor = sportikaDatabase.getdata(proname.getText().toString(),brandname.get(position).trim(),flavrname.get(f).trim(),weight1.get(w).trim());

                       int i = cursor.getCount();

                       if(i > 0) {

                           for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {


                               String pr = cursor.getString(cursor.getColumnIndex(sportikaDatabase.KEY_PSALEPRICE));


                               price.setText(pr);
                           }
                       }
                           else {

                               price.setText("0");
                           }

                   }
                }
            }
        });


        flavr.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(position != 0){

                    if(brand.getSelectedIndex() !=0 && weight.getSelectedIndex() !=0 && !proname.getText().toString().equals("")){

                        int   b = brand.getSelectedIndex();
                        int  w = weight.getSelectedIndex();

                        cursor = sportikaDatabase.getdata(proname.getText().toString(),brandname.get(b).trim(),flavrname.get(position).trim(),weight1.get(w).trim());
                        int i = cursor.getCount();

                        if(i > 0) {

                            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                                String pr = cursor.getString(cursor.getColumnIndex(sportikaDatabase.KEY_PSALEPRICE));


                                price.setText(pr);

                            }
                        }
                            else {

                                price.setText("0");
                            }
                        }

                }
            }
        });

        weight.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(position != 0){

                    if(flavr.getSelectedIndex() !=0 && brand.getSelectedIndex() !=0 && !proname.getText().toString().equals("")){

                        int   f = flavr.getSelectedIndex();
                        int  b = brand.getSelectedIndex();

                        cursor = sportikaDatabase.getdata(proname.getText().toString(),brandname.get(b).trim(),flavrname.get(f).trim(),weight1.get(position).trim());


                        int i = cursor.getCount();

                        if(i > 0) {

                            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                                String pr = cursor.getString(cursor.getColumnIndex(sportikaDatabase.KEY_PSALEPRICE));

                                price.setText(pr);
                            }
                        }
                            else {

                                price.setText("0");
                            }
                        }
                    }
                }

        });


        ImageView add = dialog.findViewById(R.id.addpro);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                OpenPopUp1();
            }
        });

        // Include dialog.xml file




        // Set dialog title
//            dialog.setTitle("Scan this code to redeem offer");

        // set values for custom dialog components - text, image and button
        dialog.show();


    }




    private void OpenPopUp1() {
        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        dialog = new Dialog(Purchase.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  dialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;

        dialog.setContentView(R.layout.add_product);

        final EditText proname,proflvr,probrand,proweight,propurschase,prosale;

        Button save,takepic;

        // Include dialog.xml file

        proname = dialog.findViewById(R.id.proname);
        proflvr = dialog.findViewById(R.id.proflvr);
        probrand = dialog.findViewById(R.id.proband);
        proweight = dialog.findViewById(R.id.proweight);
        propurschase = dialog.findViewById(R.id.purchaseprice);
        prosale = dialog.findViewById(R.id.saleprice);

        imageView1 = dialog.findViewById(R.id.imageView2);

        takepic = dialog.findViewById(R.id.takepic);

        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST1);

            }
        });

        save = dialog.findViewById(R.id.log);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        if(proname.getText().toString().equals("")){

            Toast.makeText(context,"plz enter product name",Toast.LENGTH_SHORT).show();

        }
        else {

            if(proflvr.getText().toString().equals("")){

                Toast.makeText(context,"plz enter product flvr",Toast.LENGTH_SHORT).show();

            }
            else {

                if(probrand.getText().toString().equals("")){

                    Toast.makeText(context,"plz enter product brand",Toast.LENGTH_SHORT).show();

                }
                else {

                    if(proweight.getText().toString().equals("")){

                        Toast.makeText(context,"plz enter product weight",Toast.LENGTH_SHORT).show();

                    }
                    else {

                        if(propurschase.getText().toString().equals("")){

                            Toast.makeText(context,"plz enter product purchase price",Toast.LENGTH_SHORT).show();

                        }
                        else {

                            if(prosale.getText().toString().equals("")){

                                Toast.makeText(context,"plz enter product sale price",Toast.LENGTH_SHORT).show();

                            }
                            else {


                                addProductPresenter.addPro(proname.getText().toString(),proflvr.getText().toString(),probrand.getText().toString(),proweight.getText().toString(),propurschase.getText().toString(),prosale.getText().toString());

                            }


                        }



                    }

                }

            }


        }

            }
        });


        // Set dialog title
//            dialog.setTitle("Scan this code to redeem offer");

        // set values for custom dialog components - text, image and button
        dialog.show();


    }

    @Override
    public void showRErrorMeassage() {


        Toast.makeText(context,"server error",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showRLoginSuccessMsg(int success) {

        if(success == 1){

            Toast.makeText(context,"product added successfully",Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            productPresenter.getProduct();


        }
        else {


            Toast.makeText(context,"Doesn't added",Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void showPErrorMeassage() {

        Toast.makeText(context,"server error",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void showPLoginSuccessMsg(List<Product> PList) {

          if(PList.size()>0) {

              sportikaDatabase.open();
              sportikaDatabase.deleteitm1();

              for (int i = 0; i < PList.size(); i++) {

                  prodname.add(PList.get(i).getItemName());
                  brandname.add(PList.get(i).getBrandName());
                  flavrname.add(PList.get(i).getItemFlvr());
                  weight1.add(PList.get(i).getItemWeight());
                  sportikaDatabase.productentry(PList.get(i).getItemName(),PList.get(i).getBrandName(),PList.get(i).getItemFlvr(),PList.get(i).getItemWeight(),PList.get(i).getItemPurchasePrice(),PList.get(i).getItemSalePrice());

              }
          }

    }

    @Override
    public void showprogress() {

        pDialog.show();
    }

    @Override
    public void hideprogress() {

        pDialog.hide();
    }



  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.addicon, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(Purchase.this,AddParty.class);
        startActivity(intent);
        finish();

        return super.onOptionsItemSelected(item);

    }*/


}
