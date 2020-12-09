package local.impactlife.cubetalk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.adapters.recycler.CreditshellAdapter;
import local.impactlife.cubetalk.databinding.ActivityCreditShellBinding;
import local.impactlife.cubetalk.databinding.ContentCreditShellBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.creditshell.CreditShellResponse;
import local.impactlife.cubetalk.models.user_privacy_policy_and_terms.UserPrivacyPolicyAndTermsResponse;
import local.impactlife.cubetalk.services.api.CreditShellService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.services.api.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditShellActivity extends AppCompatActivity {
    private ActivityCreditShellBinding activityCreditShellBinding;
    private ContentCreditShellBinding contentCreditShellBinding;
    private SharedPreferences mSharedPreferences;
    private static final String TAG = CreditShellActivity.class.getSimpleName();
    String userId="";
    String creditCellAmount="";
    CreditshellAdapter creditshellAdapter;
    RecyclerView rec_credit_shell;
    List<CreditShellResponse.Creditshell> creditshell=new ArrayList<>();
    TextView tv_inramount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreditShellBinding=ActivityCreditShellBinding.inflate(LayoutInflater.from(this));
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getString(User.ID, "");
        setContentView(activityCreditShellBinding.getRoot());
        contentCreditShellBinding=activityCreditShellBinding.contentCreditshell;
        Toolbar toolbar=activityCreditShellBinding.toolbar;
        rec_credit_shell=contentCreditShellBinding.recCreditShell;
        tv_inramount=contentCreditShellBinding.tvInramount;
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.action_credit_shell));
        }

        creditshellAdapter= new CreditshellAdapter(this,creditshell);
        rec_credit_shell.setAdapter(creditshellAdapter);
        callApiForCreditshell();
    }

    private void callApiForCreditshell() {

        CreditShellService creditShellService = ServiceBuilder.buildService(CreditShellService.class);
        Call<CreditShellResponse> createRequest = creditShellService.getcreditShell(mSharedPreferences.getString(User.TOKEN, ""),userId);
        createRequest.enqueue(new Callback<CreditShellResponse>() {
            @Override
            public void onResponse(Call<CreditShellResponse> call, Response<CreditShellResponse> response) {
                Log.d(TAG, "onResponse: responseshell: " + response.toString());
                if (response.body().getSuccess()){
                    creditCellAmount=response.body().getTotalShellBalance().toString();
                    tv_inramount.setText("INR "+creditCellAmount);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(User.CREDIT_AMOUNT, creditCellAmount);
                    editor.apply();
                    creditshell.addAll(response.body().getCreditshells());
                    creditshellAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<CreditShellResponse> call, Throwable t) {
                //Log.d(TAG, "onResponse: response: " + response.toString());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
