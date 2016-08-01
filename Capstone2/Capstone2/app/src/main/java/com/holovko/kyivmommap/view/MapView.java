package com.holovko.kyivmommap.view;

import com.holovko.kyivmommap.model.Place;

/**
 * Created by Andrey Holovko on 7/26/16.
 */
public interface MapView {
    void initView();
    void showAllOnMaps();
    void fillMapMarkerPLace(double latitude, double longitude, Place place);
}
