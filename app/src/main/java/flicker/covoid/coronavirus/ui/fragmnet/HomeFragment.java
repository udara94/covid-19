package flicker.covoid.coronavirus.ui.fragmnet;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import flicker.covoid.coronavirus.BaseApplication;
import com.covoid.coronavirus.R;
import flicker.covoid.coronavirus.common.CommonUtils;
import flicker.covoid.coronavirus.common.constants.ApplicationConstants;
import flicker.covoid.coronavirus.domain.CoronaService;
import flicker.covoid.coronavirus.domain.CoronaServiceImpl;
import flicker.covoid.coronavirus.model.dto.Data;
import flicker.covoid.coronavirus.model.dto.Hospital_data;
import flicker.covoid.coronavirus.model.entities.response.CoronaResponse;
import flicker.covoid.coronavirus.model.rest.CoronaAPIService;
import flicker.covoid.coronavirus.mvp.presenters.CoronaPresenter;
import flicker.covoid.coronavirus.mvp.presenters.CoronaPresenterImpl;
import flicker.covoid.coronavirus.mvp.views.CoronaView;
import flicker.covoid.coronavirus.ui.activity.MainActivity;
import flicker.covoid.coronavirus.utils.AppScheduler;
import flicker.covoid.coronavirus.utils.BaseBackPressedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements BaseBackPressedListener.OnBackPressedListener, CoronaView {

    final String TAG = HomeFragment.this.getClass().getSimpleName();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public static HomeFragment homeFragment;
    private List<Hospital_data> mHospitalDataList = new ArrayList<>();

    @BindView(R.id.txt_local_active_cases) TextView txtLocalActive;
    @BindView(R.id.txt_local_deaths) TextView txtLocalDeath;
    @BindView(R.id.txt_local_new_cases) TextView txtLocalNewCases;
    @BindView(R.id.txt_local_new_deaths) TextView txtLocalNewDeaths;
    @BindView(R.id.txt_local_recovered) TextView txtLocalRecovered;
    @BindView(R.id.txt_local_total_cases) TextView txtLocalTotal;
    @BindView(R.id.txt_local_total_number_of_individuals_in_hospitals) TextView txtIndividualInHospitals;
    @BindView(R.id.txt_global_deaths) TextView txtGlobalDeaths;
    @BindView(R.id.txt_global_new_cases) TextView txtGlobalNewCases;
    @BindView(R.id.txt_global_new_deaths) TextView txtGlobalNewDeaths;
    @BindView(R.id.txt_global_recovered) TextView txtGlobalRecovered;
    @BindView(R.id.txt_global_total_cases) TextView txtGlobalTotalCases;
    @BindView(R.id.btn_hospital) Button btnHospital;
    @BindView(R.id.txt_date) TextView txtDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            Log.d(TAG, "onCreateView");
            ButterKnife.bind(this, rootView);
            homeFragment = this;
            ((MainActivity) getActivity()).setOnBackPressedListener(this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: " + e.toString());
        }
        return rootView;
    }

    @Override
    protected void initializePresenter() {
        CoronaService mJobService = new CoronaServiceImpl(new CoronaAPIService());
        presenter = new CoronaPresenterImpl(getActivity(), mJobService, new AppScheduler());
        presenter.attachView(HomeFragment.this);
        presenter.onCreate();
    }

    @Override
    protected void setUpUI() {
        performCoronaDetailsRequest();
        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mHospitalDataList != null && mHospitalDataList.size() > 0)
                    gotoHospitalList();
            }
        });
    }

    @Override
    protected void setUpToolBar() {
        if (Build.VERSION.SDK_INT > 17) {
            View mCustomView = getLayoutInflater().inflate(R.layout.custom_action_bar_home, null);
            TextView title = (TextView) mCustomView.findViewById(R.id.title);
            mToolBar.addView(mCustomView);

            title.setTypeface(CommonUtils.getInstance().getFont(getActivity(), ApplicationConstants.FONT_ROBOTO_BOLD));
            title.setText(R.string.app_name);
            Toolbar parent = (Toolbar) mCustomView.getParent();
            parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
            parent.setContentInsetsAbsolute(0, 0);
        }
    }

    @Override
    public void doBack() {

    }

    private void populateData(Data data){

        Hospital_data[] hospital_data = data.getHospital_data();


        if(hospital_data.length > 0){
            mHospitalDataList = Arrays.asList(hospital_data);
        }


        if(data.getUpdate_date_time() != null && !data.getUpdate_date_time().isEmpty()){
            txtDate.setText(data.getUpdate_date_time());
        }
        if(data.getLocal_new_cases() != null){
            txtLocalNewCases.setText(data.getLocal_new_cases()+"");
        }
        if(data.getLocal_total_cases() != null){
            txtLocalTotal.setText(data.getLocal_total_cases());
        }
        if(data.getLocal_total_number_of_individuals_in_hospitals() != null){
            txtIndividualInHospitals.setText(data.getLocal_total_number_of_individuals_in_hospitals());
        }
        if(data.getLocal_deaths() != null){
            txtLocalDeath.setText(data.getLocal_deaths());
        }
        if(data.getLocal_new_deaths() != null){
            txtLocalNewDeaths.setText(data.getLocal_new_deaths());
        }
        if(data.getLocal_recovered() != null){
            txtLocalRecovered.setText(data.getLocal_recovered());
        }
        if(data.getLocal_active_cases() != null){
            txtLocalActive.setText(data.getLocal_active_cases());
        }
        if(data.getGlobal_new_cases() != null){
            txtGlobalNewCases.setText(data.getGlobal_new_cases());
        }
        if(data.getGlobal_total_cases() != null){
            txtGlobalTotalCases.setText(data.getGlobal_total_cases());
        }
        if(data.getGlobal_deaths() != null){
            txtGlobalDeaths.setText(data.getGlobal_deaths());
        }
        if(data.getGlobal_new_deaths() != null){
            txtGlobalNewDeaths.setText(data.getGlobal_new_deaths());
        }
        if(data.getGlobal_recovered() != null){
            txtGlobalRecovered.setText(data.getGlobal_recovered());
        }

    }

    private void gotoHospitalList(){
        if (!BaseApplication.getBaseApplication().isLoadHospitalListScreen()) {
            BaseApplication.getBaseApplication().setLoadHospitalListScreen(true);
            ((MainActivity) getActivity()).addFragment(new HospitalListFragment().newInstance(), HospitalListFragment.getTAG());

        }
    }

    public List<Hospital_data> getmHospitalDataList(){
        return mHospitalDataList;
    }

    private void performCoronaDetailsRequest() {
        if (CommonUtils.getInstance().isNetworkConnected()) {
            setProgressDialog(true);
            ((CoronaPresenter) presenter).getCoronaDetails();
        } else {
            showAlertDialog(false, ApplicationConstants.WARNING,
                    ApplicationConstants.ERROR_MSG_CONNECTION_LOST, null);
        }

    }
    @Override
    public void showCoronaResponse(CoronaResponse coronaResponse) {
        setProgressDialog(false);
        if ((!isAdded() || !isVisible())) return;
        if (coronaResponse.isSuccess()) {
            populateData(coronaResponse.getData());
        } else {
            if (coronaResponse.isTokenExpired()) {
                return;
            } else if (coronaResponse.isAPIError() && coronaResponse.getMessage() != null) {
               // System.out.println("API Error");
            } else {
                showTopSnackBar(coronaResponse.getMessage(), getResources().getColor(R.color.colorAccent));
            }
        }
    }
}
