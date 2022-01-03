package com.example.madcamp1;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class FragmentContact extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<Contact> contactList;
    private List<ContactItem> contactItemList;
    private FloatingActionButton fab_main;
    private FloatingActionButton fab_add;
    private FloatingActionButton fab_search;
    private TextView fab_add_text;
    private TextView fab_search_text;
    boolean fabVisible = false;
    private EditText emailAddress = null;
    private EditText phoneNumber = null;

    public FragmentContact() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_contact, container, false);
        contactItemList = getContactItemList();

        myrecyclerview = v.findViewById(R.id.contact_recyclerview);
        RecyclerViewAdapter2 recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(), contactItemList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerViewAdapter2);
        fab_main = v.findViewById(R.id.fab);
        fab_add = v.findViewById(R.id.fab_add);
        fab_search = v.findViewById(R.id.fab_search);
        fab_add_text = v.findViewById(R.id.fab_add_text);
        fab_search_text = v.findViewById(R.id.fab_search_text);
        fab_add.setVisibility(View.GONE);
        fab_search.setVisibility(View.GONE);
        fab_add_text.setVisibility(View.GONE);
        fab_search_text.setVisibility(View.GONE);


        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fabVisible) {
                    fab_add.show();
                    fab_search.show();
                    fab_add_text.setVisibility(View.VISIBLE);
                    fab_search_text.setVisibility(View.VISIBLE);
                    fabVisible = true;
                } else {
                    fab_add.hide();
                    fab_search.hide();
                    fab_add_text.setVisibility(View.GONE);
                    fab_search_text.setVisibility(View.GONE);
                    fabVisible = false;
                }
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivity(intent);
            }
        });

        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ArrayList<ContactItem> getContactItemList() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.RawContacts.CONTACT_ID
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, sortOrder);
        LinkedHashSet<ContactItem> hashlist = new LinkedHashSet<>();
        if (cursor.moveToFirst()) {
            do {
                long photo_id = cursor.getLong(2);
                long person_id = cursor.getLong(3);
                ContactItem contactItem = new ContactItem();
                contactItem.setPhone(cursor.getString(0));
                contactItem.setName(cursor.getString(1));
                contactItem.setPhoto_id(photo_id);
                contactItem.setPerson_id(person_id);
                hashlist.add(contactItem);
            } while (cursor.moveToNext());
        }

        ArrayList<ContactItem> contactItems = new ArrayList<>(hashlist);
        for (int i = 0; i < contactItems.size(); i++) {
            contactItems.get(i).setId(i + 1);
        }

        if (cursor != null) {
            cursor.close();
        }
        return contactItems;
    }
/*
    public void InitializeContact() {
        contactList = new ArrayList<>();
        String json;
        json = getJsonString();
        parseJson(json);
    }


    private String getJsonString() {
        String json = "";
        try {
            AssetManager am = getResources().getAssets();
            InputStream is = getActivity().getAssets().open("contact.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("Contact");

            for (int i = 0; i < contactArray.length(); i++) {
                JSONObject contactObject = contactArray.getJSONObject(i);
                Contact contact = new Contact();
                contact.setName(contactObject.getString("name"));
                contact.setPhone(contactObject.getString("phone_number"));
                contactList.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
 */
}