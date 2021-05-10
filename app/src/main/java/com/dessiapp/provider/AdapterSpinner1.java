package com.dessiapp.provider;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dessiapp.R;
import com.dessiapp.models.ActivityModel;
import com.dessiapp.models.SpinnerDto;

import java.util.List;


public class AdapterSpinner1 extends RecyclerView.Adapter<AdapterSpinner1.AdapterSpinnerViewHolder> {

    Context context;
    List<ActivityModel.ActivityList> spinnerDtos;
    Dialog dialog1;
    String intent;
    //OnTapAdapterListner onTapAdapter;
    public AdapterSpinner1(Context context, List<ActivityModel.ActivityList> spinnerDtos, Dialog dialog1, String intent) {
        this.context = context;
        this.spinnerDtos = spinnerDtos;
        this.dialog1 = dialog1;
        this.intent = intent;
        //this.onTapAdapter = onTapAdapter;
    }


    public interface OnTapAdapterListner{
        void call(SpinnerDto spinnerDto);
    }

    @NonNull
    @Override
    public AdapterSpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.custom_spin_view,viewGroup,false);
        return new AdapterSpinnerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSpinnerViewHolder holder, int i) {

        ActivityModel.ActivityList spinDto=spinnerDtos.get(i);
        final String text=spinDto.getActivity();
        final String id=spinDto.getSerialno().toString();

        holder.spinView.setText(text);
        holder.idView.setText(id);

        holder.spinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  onTapAdapter.call(spinDto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return spinnerDtos.size();
    }

    class AdapterSpinnerViewHolder extends RecyclerView.ViewHolder{

        TextView spinView,idView;
        public AdapterSpinnerViewHolder(@NonNull View itemView) {
            super(itemView);

            spinView=itemView.findViewById(R.id.spinView);
            idView=itemView.findViewById(R.id.idView);

        }
    }
}
