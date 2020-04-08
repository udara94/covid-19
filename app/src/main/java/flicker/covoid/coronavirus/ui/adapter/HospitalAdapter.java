package flicker.covoid.coronavirus.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covoid.coronavirus.R;
import flicker.covoid.coronavirus.model.dto.Hospital_data;
import flicker.covoid.coronavirus.ui.fragmnet.HospitalListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HospitalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Hospital_data> hospitalDataList = new ArrayList<>();

    public HospitalAdapter(Context mContext, List<Hospital_data> hospitalDataList) {
        this.mContext = mContext;
        this.hospitalDataList = hospitalDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_hospital, parent, false);
        HospitalAdapter.ItemRowHolder itemRowHolder = new HospitalAdapter.ItemRowHolder(inflate);
        return itemRowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HospitalAdapter.ItemRowHolder){
            final HospitalAdapter.ItemRowHolder itemRowHolder = (HospitalAdapter.ItemRowHolder) holder;
            final Hospital_data item = hospitalDataList.get(position);

            if(item != null){
                if (item.getHospital().getName() != null && !item.getHospital().getName().isEmpty())
                    itemRowHolder.txtNameEn.setText(item.getHospital().getName());

                if (item.getHospital().getName_si() != null && !item.getHospital().getName_si().isEmpty())
                    itemRowHolder.txtNameSi.setText(item.getHospital().getName_si());

                if (item.getHospital().getName_ta() != null && !item.getHospital().getName_ta().isEmpty())
                    itemRowHolder.txtNameTamil.setText(item.getHospital().getName_ta());


                itemRowHolder.parentContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(HospitalListFragment.hospitalListFragment != null){
                            HospitalListFragment.hospitalListFragment.gotoHospitalDetailsScreen(item);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != hospitalDataList ? hospitalDataList.size() : 0);
    }

    public void updateData(List<Hospital_data> results, int flag) {
        if(flag == 0){
            for (int i = 0; i < results.size(); i++) {
                hospitalDataList.add(results.get(i));
                notifyItemInserted(getItemCount());
            }
        }else {
            hospitalDataList.clear();
            notifyDataSetChanged();
        }

    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name) TextView txtNameEn;
        @BindView(R.id.txt_name_si) TextView txtNameSi;
        @BindView(R.id.txt_name_tamil) TextView txtNameTamil;
        @BindView(R.id.parent_container_main) RelativeLayout parentContainer;

        public ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
