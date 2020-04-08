package flicker.covoid.coronavirus.ui.fragmnet;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import flicker.covoid.coronavirus.BaseApplication;
import com.covoid.coronavirus.R;
import flicker.covoid.coronavirus.common.CommonUtils;
import flicker.covoid.coronavirus.common.constants.ApplicationConstants;
import flicker.covoid.coronavirus.model.dto.Hospital_data;
import flicker.covoid.coronavirus.ui.activity.MainActivity;
import flicker.covoid.coronavirus.ui.adapter.HospitalAdapter;
import flicker.covoid.coronavirus.utils.BaseBackPressedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HospitalListFragment extends BaseFragment implements BaseBackPressedListener.OnBackPressedListener {


    final String TAG = HospitalListFragment.this.getClass().getSimpleName();
    public static String getTAG() {
        return "HospitalListFragment";
    }
    private static String BUNDLE_EXTRA = "BUNDLE_EXTRA";

    public static HospitalListFragment newInstance() {
        HospitalListFragment fragment = new HospitalListFragment();
        Bundle args = new Bundle();
        //args.putParcelable(BUNDLE_EXTRA, Parcels.wrap(hospitalDataList));
        fragment.setArguments(args);
        return fragment;

    }

    RecyclerView mRecyclerView;
    private HospitalAdapter mHospitalAdapter;

    public static HospitalListFragment hospitalListFragment;
    private List<Hospital_data> mHospitalDataList;
    private boolean isPullToRefreshCall = false;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mHospitalDataList = Parcels.unwrap(getArguments().getParcelable(BUNDLE_EXTRA));
            if(HomeFragment.homeFragment != null){
                mHospitalDataList = HomeFragment.homeFragment.getmHospitalDataList();
            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_hospital_list, container, false);
            Log.d(TAG, "onCreateView");
            ButterKnife.bind(this, rootView);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            hospitalListFragment = this;
            ((MainActivity) getActivity()).setOnBackPressedListener(this);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: " + e.toString());
        }
        return rootView;
    }

    private  void initRecyclerViwe(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mHospitalAdapter = new HospitalAdapter(getActivity(), new ArrayList<Hospital_data>());
        mRecyclerView.setAdapter(mHospitalAdapter);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int fVPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
//                if (fVPosition == 0 || fVPosition == -1)
//                    mSwipeRefreshLayout.setEnabled(true);
//                else
//                    mSwipeRefreshLayout.setEnabled(false);
//            }
//        });
    }

    private void initData(){
        initRecyclerViwe();
        if(mHospitalDataList.size() > 0){
            mHospitalAdapter.updateData(mHospitalDataList, 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseApplication.getBaseApplication().setLoadHospitalListScreen(false);
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void setUpUI() {

        initData();
    }



    public void refreshView(){
//        resetRecyclerView();
//        if(mJobField != null && !mJobField.isEmpty())
//            performJobTypeRequest();
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
            title.setText(R.string.title_hospital_list);
            Toolbar parent =(Toolbar) mCustomView.getParent();
            parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
            parent.setContentInsetsAbsolute(0,0);
        }
    }

    public  void gotoHospitalDetailsScreen(Hospital_data hospitalData){
        if (!BaseApplication.getBaseApplication().isLoadHospitalDetailsScreen()) {
            BaseApplication.getBaseApplication().setLoadHospitalDetailsScreen(true);
            ((MainActivity) getActivity()).addFragment(new HospitalDetailsFragment().newInstance(hospitalData), HospitalDetailsFragment.getTAG());

        }
    }

    @Override
    public void doBack() {
        getFragmentManager().popBackStack();
    }
}
