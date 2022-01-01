package com.example.madcamp1;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public FragmentContact() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_contact, container, false);

        myrecyclerview = v.findViewById(R.id.contact_recyclerview);
        RecyclerViewAdapter2 recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(), contactItemList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerViewAdapter2);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeContact();
        contactItemList = getContactItemList();
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
}