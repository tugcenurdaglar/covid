package com.tugcenurdaglar.retro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tugcenurdaglar.retro.retrofit.CovidInterface;
import com.tugcenurdaglar.retro.models.CovidModel;
import com.tugcenurdaglar.retro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CovidAdapter extends RecyclerView.Adapter<CovidAdapter.CovidCardObj> {
    private Context mContext;
    private List<CovidModel> covidModelList;

    private CovidInterface covidInterface;

    public CovidAdapter(Context mContext, List<CovidModel> covidModelList, CovidInterface covidInterface) {
        this.mContext = mContext;
        this.covidModelList = covidModelList;
        this.covidInterface = covidInterface;
    }


    @NonNull
    @Override
    public CovidCardObj onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.covid_layout, parent, false);
        return new CovidCardObj(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCardObj holder, int position) {
        holder.loadData();
    }


    public void updateList(List<CovidModel> covidList) {
        covidModelList = covidList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return covidModelList.size();
    }

    public class CovidCardObj extends RecyclerView.ViewHolder {
        public CardView cardId;
        public TextView casesId;
        public TextView dateId;
        public TextView countryId;

        public CovidCardObj(@NonNull View view) {
            super(view);

            cardId = view.findViewById(R.id.cardId);
            casesId = view.findViewById(R.id.casesId);
            dateId = view.findViewById(R.id.dateId);
            countryId = view.findViewById(R.id.countryId);
        }

        public void loadData() {

            CovidModel covidModel = covidModelList.get(getAdapterPosition());


            countryId.setText(covidModel.getCountry());
            casesId.setText(covidModel.getCases().toString());
            dateId.setText(covidModel.getDate());

        }
    }


}

