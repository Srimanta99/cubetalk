package local.impactlife.cubetalk.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnboardingBinding mBinding = ActivityOnboardingBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());


    }

}
