package com.example.toshiba.myapplication.Helper;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ContactHelper {


    public static void deleteContact(ContentResolver contentResolver,String number)
    {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        String[] args = new String[]{String.valueOf(getContactID(contentResolver,number))};
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
        .withSelection(ContactsContract.RawContacts.CONTACT_ID+" = ?", args)
        .build());

        try
        {
            contentResolver.applyBatch(ContactsContract.AUTHORITY,ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private static long getContactID(ContentResolver contentResolver, String number) {

        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));
        String[] projection = {ContactsContract.PhoneLookup._ID};
        Cursor cur = null;
        try
        {
            cur = contentResolver.query(contactUri,projection ,null ,null,null);
            if (cur.moveToFirst())
            {
                int personId = cur.getColumnIndex(ContactsContract.PhoneLookup._ID);
                return cur.getLong(personId);
            }
            return  -1;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (cur!=null) {
                cur.close();
                cur = null;
            }
        }

        return -1;

    }
}
