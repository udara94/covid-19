package flicker.covoid.coronavirus.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.covoid.coronavirus.R;
import flicker.covoid.coronavirus.ui.fragmnet.HomeFragment;
import flicker.covoid.coronavirus.utils.BaseBackPressedListener;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity {

    final String TAG = MainActivity.this.getClass().getSimpleName();
    public static MainActivity mainActivity;

    protected BaseBackPressedListener.OnBackPressedListener onBackPressedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        if (findViewById(R.id.fragment_container) != null) {
            setFragment(HomeFragment.newInstance());
        }
    }

    public void setFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment, String TAG) {
        if (fragment == null) return;
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);
        fragTransaction.add(R.id.fragment_container, fragment, TAG);
        fragTransaction.addToBackStack(TAG);
        fragTransaction.commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

//        if (onBackPressedListener != null){
//            System.out.println("==========>back presss");
//            onBackPressedListener.doBack();
//            System.out.println("==========>back presss22");
//            if(!popFragment()){
//                finish();
//            }
//        }
        super.onBackPressed();
    }

    public boolean popFragment() {
        Log.e("test", "pop fragment: " + getSupportFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getSupportFragmentManager().popBackStack();
        }
        return isPop;
    }

    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void setOnBackPressedListener(BaseBackPressedListener.OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
