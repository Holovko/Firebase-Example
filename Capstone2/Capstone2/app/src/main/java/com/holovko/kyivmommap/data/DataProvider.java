package com.holovko.kyivmommap.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Data provider
 * Created by Andrey Holovko on 7/26/16.
 */
public class DataProvider implements IDataProvider {
    private static final String TAG = "DataProvider";
    private static final String LANGUAGE_ENG = "eng";
    private static final String CATEGORY_NODE = "category";
    private static DataProvider sInstance;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(LANGUAGE_ENG);

    private  DataProvider() {}

    public static synchronized DataProvider getInstance() {
        if(sInstance ==null) sInstance = new DataProvider();
        return sInstance;
    }

    @Override
    public Query getPlacesByType(@Constant.RubricType int type) {
        return mDatabase.child(CATEGORY_NODE).child(Constant.getNodeByType(type));
    }


    public void getListPlacesByType(@Constant.RubricType int type, final OnGetPLacesListener pLacesListener){
        getPlacesByType(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Place> placeList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Place place = postSnapshot.getValue(Place.class);
                    placeList.add(place);
                }
                pLacesListener.onGetPlaces(placeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new DatabaseException("Error with database");
            }
        });
    }

}
