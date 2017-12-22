package com.client.sportika;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 12/12/2017.
 */

public class AddParty extends AppCompatActivity {


    AutoCompleteTextView autoCompleteTextView;

    private ArrayList<Map<String, String>> arrayListMap;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addparty);



        arrayListMap = new ArrayList<Map<String, String>>();


        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        simpleAdapter = new SimpleAdapter(this, arrayListMap, R.layout.custcontview,
                new String[] { "Name", "Phone", "Type" }, new int[] {
                R.id.ccontName, R.id.ccontNo, R.id.ccontType });
        autoCompleteTextView.setAdapter(simpleAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View arg1, int index,
                                    long arg3) {
                Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

                String name  = map.get("Name");
                String number = map.get("Phone");
                autoCompleteTextView.setText(""+name+"<"+number+">");

            }
        });
        PopulatePeopleList();


    }


    public void PopulatePeopleList()
    {

        arrayListMap.clear();

        Cursor people = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (people.moveToNext())
        {
            String contactName = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

            String contactId = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts._ID));
            String hasPhone = people.getString(people.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ((Integer.parseInt(hasPhone) > 0))
            {

                // You know have the number so now query it like this
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                        null, null);
                while (phones.moveToNext()) {

                    //store numbers and display a dialog letting the user select which.
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                    String numberType = phones.getString(phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TYPE));

                    Map<String, String> NamePhoneType = new HashMap<String, String>();

                    NamePhoneType.put("Name", contactName);
                    NamePhoneType.put("Phone", phoneNumber);

                    if(numberType.equals("0"))
                        NamePhoneType.put("Type", "Work");
                    else
                    if(numberType.equals("1"))
                        NamePhoneType.put("Type", "Home");
                    else if(numberType.equals("2"))
                        NamePhoneType.put("Type",  "Mobile");
                    else
                        NamePhoneType.put("Type", "Other");

                    //Then add this map to the list.
                    arrayListMap.add(NamePhoneType);
                }
                phones.close();
            }
        }
        people.close();

        startManagingCursor(people);
    }

}
