package com.example.pcconfigproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ConfigItemAdapter extends RecyclerView.Adapter<ConfigItemAdapter.ViewHolder> {
    private ArrayList<ConfigItem> mConfigItemsData;
    //private ArrayList<ConfigItem> mConfigItemsDataAll; // For filter, no important
    private Context mContext;
    private int lastPosition = -1;
    ConfigItemAdapter(Context context, ArrayList<ConfigItem> itemsData) {
        this.mConfigItemsData = itemsData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.config_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConfigItem currentItem = mConfigItemsData.get(position);
        holder.bindTo(currentItem);
        
        if(holder.getAdapterPosition() > 200) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mConfigItemsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mConfigName;
        private TextView mMobo;
        private TextView mCpu;
        private TextView mRam;
        private TextView mGpu;
        private TextView mPsu;
        private TextView mCase;
        private TextView mSsd;
        private ImageView mItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mConfigName = itemView.findViewById(R.id.configNameTitle);
            mMobo = itemView.findViewById(R.id.moboTitle);
            mCpu = itemView.findViewById(R.id.cpuTitle);
            mRam = itemView.findViewById(R.id.ramTitle);
            mGpu = itemView.findViewById(R.id.gpuTitle);
            mPsu = itemView.findViewById(R.id.psuTitle);
            mCase = itemView.findViewById(R.id.caseTitle);
            mSsd = itemView.findViewById(R.id.ssdTitle);
            mItemImage = itemView.findViewById(R.id.configImage);

        }

        public void bindTo(ConfigItem currentItem) {
            mConfigName.setText(currentItem.getName());
            mMobo.setText(currentItem.getMobo());
            mCpu.setText(currentItem.getCpu());
            mRam.setText(currentItem.getRam());
            mGpu.setText(currentItem.getGpu());
            mPsu.setText(currentItem.getPsu());
            mCase.setText(currentItem.getPcCase());
            mSsd.setText(currentItem.getSsd());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);

            itemView.findViewById(R.id.deleteConfigButton).setOnClickListener(v -> ((ConfigListActivity)mContext).deleteConfig(currentItem));
            itemView.findViewById(R.id.editConfig).setOnClickListener(v -> ((ConfigListActivity)mContext).updateItem(currentItem));
        }
    }
}


