package com.straus.airports.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.straus.airports.R;
import com.straus.airports.datasource.AirportDataSource;
import com.straus.airports.fragment.MapFragment;
import com.straus.airports.objects.Airport;

import java.util.ArrayList;
import java.util.List;

public class TripFoundAdapter extends RecyclerView.Adapter<TripFoundAdapter.ViewHolder> {

    public static final int ORIGIN_MOD = 100;
    public static final int DESTINATION_MOD = 200;
    private Context context;
    private Airport airportItem;
    private List<Airport> airportList;
    private int mode;
    private MapFragment parent;

    public TripFoundAdapter(Context context, MapFragment mapFragment) {
        this.context = context;
        this.parent = mapFragment;
        if (airportList == null) {
            airportList = new ArrayList<>();
        }
    }

    @Override
    public TripFoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.trip_adapter_single_line, parent, false);
        return new TripFoundAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripFoundAdapter.ViewHolder holder, final int position) {
        airportItem = airportList.get(position);
        holder.bind(airportItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.addItem(airportList.get(position), mode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return airportList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView topTV;
        TextView bottomTV;

        public ViewHolder(final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            topTV = itemView.findViewById(R.id.topTV);
            bottomTV = itemView.findViewById(R.id.bottomTV);
        }

        private void bind(Airport item) {
            if (mode == DESTINATION_MOD) {
                image.setBackgroundResource(R.drawable.airplane_landing);
            } else {
                image.setBackgroundResource(R.drawable.airplane_takeoff);
            }
            topTV.setText(item.getCity());
            bottomTV.setText(item.getName() + ", " + item.getCountry() + ", " + item.getIATA());
        }
    }

    public void start(int mode, boolean generateList) {
        this.mode = mode;

            if (generateList) {
                airportList = AirportDataSource.getInstance().loadAll();
            }

        notifyDataSetChanged();
    }

    public void search(List<Airport> list) {
        airportList.clear();
        airportList.addAll(list);
        notifyDataSetChanged();
    }

}
