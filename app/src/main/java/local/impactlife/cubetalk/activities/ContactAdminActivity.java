package local.impactlife.cubetalk.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityContactAdminBinding;
import local.impactlife.cubetalk.databinding.ContentContactAdminBinding;

public class ContactAdminActivity extends AppCompatActivity {
    private ActivityContactAdminBinding activityContactAdminBinding;
    private ContentContactAdminBinding contentContactAdminBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityContactAdminBinding=ActivityContactAdminBinding.inflate(LayoutInflater.from(this));
        contentContactAdminBinding=activityContactAdminBinding.contentContactAdmin;
        setContentView(activityContactAdminBinding.getRoot());
        Toolbar toolbar=activityContactAdminBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.action_contact_admin));
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
