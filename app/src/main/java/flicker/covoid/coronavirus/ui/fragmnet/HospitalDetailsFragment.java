package flicker.covoid.coronavirus.ui.fragmnet;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import flicker.covoid.coronavirus.BaseApplication;
import com.covoid.coronavirus.R;
import flicker.covoid.coronavirus.common.CommonUtils;
import flicker.covoid.coronavirus.common.constants.ApplicationConstants;
import flicker.covoid.coronavirus.model.dto.Hospital_data;
import flicker.covoid.coronavirus.ui.activity.MainActivity;
import flicker.covoid.coronavirus.utils.BaseBackPressedListener;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HospitalDetailsFragment extends BaseFragment implements BaseBackPressedListener.OnBackPressedListener {

    final String TAG = HospitalDetailsFragment.this.getClass().getSimpleName();

    public static String getTAG() {
        return "HospitalDetailsFragment";
    }

    private static String BUNDLE_EXTRA = "BUNDLE_EXTRA";

    public static HospitalDetailsFragment newInstance(Hospital_data hospitalData) {
        HospitalDetailsFragment fragment = new HospitalDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_EXTRA, Parcels.wrap(hospitalData));
        fragment.setArguments(args);
        return fragment;
    }

    private Hospital_data mHospital_data;
    public static HospitalDetailsFragment hospitalDetailsFragment;


    @BindView(R.id.txt_cumulative_foreign) TextView txtCumForeign;
    @BindView(R.id.txt_cumulative_local) TextView txtCumLocal;
    @BindView(R.id.txt_treatment_foreign) TextView txtTreatForeign;
    @BindView(R.id.txt_treatment_local) TextView txtTreatLocal;
    @BindView(R.id.hospital_name_sin) TextView txtHospitalNameSin;
    @BindView(R.id.hospital_name_en) TextView txtHospitalNameEn;
    @BindView(R.id.hospital_name_tamil) TextView txtHospitalNameTamil;
    @BindView(R.id.txt_cumulative_total) TextView txtCumTotal;
    @BindView(R.id.txt_treatment_total) TextView txtTreatTotal;
    @BindView(R.id.txt_date) TextView txtdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHospital_data = Parcels.unwrap(getArguments().getParcelable(BUNDLE_EXTRA));
//            if(HomeFragment.homeFragment != null){
//                mHospitalDataList = HomeFragment.homeFragment.getmHospitalDataList();
//            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_hospital_details, container, false);
            Log.d(TAG, "onCreateView");
            ButterKnife.bind(this, rootView);
            hospitalDetailsFragment = this;
            ((MainActivity) getActivity()).setOnBackPressedListener(this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: " + e.toString());
        }
        return rootView;
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseApplication.getBaseApplication().setLoadHospitalDetailsScreen(false);
    }

    @Override
    protected void setUpUI() {

        if (mHospital_data != null) {

            if(mHospital_data.getHospital().getUpdated_at() != null && !mHospital_data.getHospital().getUpdated_at().isEmpty()){
                txtdate.setText(mHospital_data.getHospital().getUpdated_at());
            }

            if (mHospital_data.getCumulative_local() != null) {
                txtCumLocal.setText(mHospital_data.getCumulative_local() + "");
            }
            if (mHospital_data.getTreatment_local() != null) {
                txtTreatLocal.setText(mHospital_data.getTreatment_local() + "");
            }
            if (mHospital_data.getCumulative_foreign() != null) {
                txtCumForeign.setText(mHospital_data.getCumulative_foreign() + "");
            }
            if (mHospital_data.getTreatment_foreign() != null) {
                txtTreatForeign.setText(mHospital_data.getTreatment_foreign() + "");
            }
            if (mHospital_data.getCumulative_total() != null) {
                txtCumTotal.setText(mHospital_data.getCumulative_total() + "");
            }
            if (mHospital_data.getTreatment_total() != null) {
                txtTreatTotal.setText(mHospital_data.getTreatment_total() + "");
            }

            if (mHospital_data.getHospital() != null &&
                    mHospital_data.getHospital().getName_si() != null &&
                    !mHospital_data.getHospital().getName_si().isEmpty()) {
                txtHospitalNameSin.setText(mHospital_data.getHospital().getName_si());
            }
            if (mHospital_data.getHospital() != null &&
                    mHospital_data.getHospital().getName() != null &&
                    !mHospital_data.getHospital().getName().isEmpty()) {
                txtHospitalNameEn.setText(mHospital_data.getHospital().getName() + "");
            }
            if (mHospital_data.getHospital() != null &&
                    mHospital_data.getHospital().getName_ta() != null &&
                    !mHospital_data.getHospital().getName_ta().isEmpty()) {
                txtHospitalNameTamil.setText(mHospital_data.getHospital().getName_ta());
            }
        }
    }

    @Override
    protected void setUpToolBar() {
        if(Build.VERSION.SDK_INT > 17) {
            View mCustomView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
            TextView title = (TextView) mCustomView.findViewById(R.id.title);
            mToolBar.addView(mCustomView);
            mCustomView.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });
            title.setTypeface(CommonUtils.getInstance().getFont(getActivity(), ApplicationConstants.FONT_ROBOTO_BOLD));
            title.setText(R.string.title_hospital_details);
            Toolbar parent =(Toolbar) mCustomView.getParent();
            parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
            parent.setContentInsetsAbsolute(0,0);
        }
    }

    @Override
    public void doBack() {
        getFragmentManager().popBackStack();
    }
}
