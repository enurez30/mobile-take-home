package com.straus.airports.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.straus.airports.R;
import com.straus.airports.adapter.TripFoundAdapter;
import com.straus.airports.activity.MasterActivity;
import com.straus.airports.datasource.AirportDataSource;
import com.straus.airports.objects.Airport;
import com.straus.airports.objects.Route;
import com.straus.airports.task.RouteFindTask;
import com.straus.airports.utils.DialogUtils;
import com.straus.airports.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, View.OnTouchListener, /*SearchView.OnQueryTextListener,*/ android.support.v7.widget.SearchView.OnQueryTextListener {

    private boolean isStart = true;
    private GoogleMap mMap;
    private MapView mMapView;
    private MarkerOptions markerOptions;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout llBottomSheet;
    private LinearLayout detailsLL;
    private LinearLayout tripDirectionLL;
    private TextView originTV;
    private TextView destinationTV;
    private ImageView backArrowIMG;
    protected RecyclerView recyclerView;
    protected LinearLayoutManager mLayoutManager;
    private TripFoundAdapter tripFoundAdapter;
    private android.support.v7.widget.SearchView searchView;
    private Button startBtn;
    private Airport origin = null;
    private Airport destination = null;
    private View rootView;
    private Marker marker;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        setHasOptionsMenu(true);
        generateView(rootView);
        setListeners();
        generateSearchView();
        markerOptions = new MarkerOptions();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        tripFoundAdapter = new TripFoundAdapter(getActivity(), this);
        recyclerView.setAdapter(tripFoundAdapter);
        rootView.requestFocus();
        return rootView;
    }

    private void generateSearchView() {
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
    }

    private void generateView(View rootView) {
        llBottomSheet = rootView.findViewById(R.id.bottom_sheet);
        detailsLL = rootView.findViewById(R.id.detailsLL);
        tripDirectionLL = rootView.findViewById(R.id.tripDirectionLL);
        originTV = rootView.findViewById(R.id.originTV);
        destinationTV = rootView.findViewById(R.id.destinationTV);
        backArrowIMG = rootView.findViewById(R.id.backArrowIMG);
        recyclerView = rootView.findViewById(R.id.recycleView);
        searchView = rootView.findViewById(R.id.searchView);
        startBtn = rootView.findViewById(R.id.startBtn);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
    }

    private void setListeners() {
        originTV.setOnTouchListener(this);
        destinationTV.setOnTouchListener(this);
        bottomSheetBehavior.setHideable(false);
        detailsLL.setOnClickListener(this);
        backArrowIMG.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int state) {
                if (state == BottomSheetBehavior.STATE_EXPANDED) {
                    searchView.requestFocus();
                    Utils.forceOpenKeyboard();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Utils.forceCloseKeyboard(searchView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.airport));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrowIMG:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Utils.forceCloseKeyboard(searchView);
                break;
            case R.id.startBtn:
                if (isStart) {
                    if (!validate()) {
                        setButtonState(false);
                        closeTripDirection();
                        RouteFindTask routeFindTask = new RouteFindTask(getActivity(), this, origin, destination);
                        routeFindTask.execute();
                    }
                } else {
                    setButtonState(true);
                    showTripDirection();
                }
                break;
        }
    }

    private void setButtonState(boolean isStart) {
        this.isStart = isStart;
        if (isStart) {
            startBtn.setText(getResources().getString(R.string.start));
        } else {
            startBtn.setText(getResources().getString(R.string.change_trip));
        }
    }

    private void closeTripDirection() {
        tripDirectionLL.animate()
                .translationY(-tripDirectionLL.getHeight())
                .alpha(0.0F)
                .setDuration(300);
    }

    public void executePath() {
        rootView.requestFocus();
        mMap.clear();
        ArrayList<LatLng> points = new ArrayList<>();
        points.add(new LatLng(origin.getLatitude(), origin.getLongitude()));
        points.add(new LatLng(destination.getLatitude(), destination.getLongitude()));
        mMap.addMarker(markerOptions.position(points.get(0)).title(origin.getName()));
        mMap.addMarker(markerOptions.position(points.get(1)).title(destination.getName()));

        addPolyline(points);
    }

    public void executeMultiplePath(List<Airport> list) {
        rootView.requestFocus();
        if (list == null) {
            showTripException();
            return;
        }
        list.add(0, origin);
        mMap.clear();
        ArrayList<LatLng> points = new ArrayList<>();
        for (Airport airport : list) {
            points.add(new LatLng(airport.getLatitude(), airport.getLongitude()));
            mMap.addMarker(markerOptions.position(new LatLng(airport.getLatitude(), airport.getLongitude())).title(airport.getName()));
        }
        addPolyline(points);
    }

    private void showTripException() {
        DialogUtils.showTripErrorDialog(getActivity());
    }

    private void addPolyline(ArrayList<LatLng> points) {
        PolylineOptions polyLineOptions = new PolylineOptions();
        polyLineOptions.width(6);
        polyLineOptions.geodesic(true);
        polyLineOptions.color(getResources().getColor(R.color.colorPrimaryDark));
        polyLineOptions.addAll(points);
        Polyline polyline = mMap.addPolyline(polyLineOptions);
        polyline.setGeodesic(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(points.get(0)));
    }

    private boolean validate() {
        boolean errors = false;
        StringBuilder errorDescription = new StringBuilder();
        if (TextUtils.isEmpty(originTV.getText())) {
            errors = true;
            errorDescription.append("origin is empty");
            errorDescription.append(System.getProperty("line.separator"));
        }
        if (TextUtils.isEmpty(destinationTV.getText())) {
            errors = true;
            errorDescription.append("destination is empty");
        }

        if (errors) {
            DialogUtils.showErrorDialog(getActivity(), errorDescription);
        }

        return errors;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.originTV:
                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        tripFoundAdapter.start(TripFoundAdapter.ORIGIN_MOD, tripFoundAdapter.getItemCount() == 0);
                        searchView.setQuery(origin != null ? origin.getCity() : "", true);
                    }
                    break;
                case R.id.destinationTV:
                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        tripFoundAdapter.start(TripFoundAdapter.DESTINATION_MOD, tripFoundAdapter.getItemCount() == 0);
                        searchView.setQuery(destination != null ? destination.getCity() : "", true);
                    }
                    break;
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (!s.equals("")) {
            tripFoundAdapter.search(AirportDataSource.getInstance().getAirportsList(s));
        } else {
            tripFoundAdapter.search(getAllSuggestions());
        }
        return false;
    }

    public void addItem(Airport item, int mode) {
        Utils.forceCloseKeyboard(searchView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        if (mode == TripFoundAdapter.ORIGIN_MOD) {
            origin = item;
            originTV.setText(origin.getCity());
        } else {
            destination = item;
            destinationTV.setText(destination.getCity());
        }
    }

    public void showTripDirection() {
        tripDirectionLL.animate().translationY(0);
        tripDirectionLL.setAlpha(1.0F);
    }

    private List<Airport> getAllSuggestions() {
        return AirportDataSource.getInstance().loadAll();
    }

    public void backPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            ((MasterActivity) Objects.requireNonNull(getActivity())).submitExit();
        }
    }

}
