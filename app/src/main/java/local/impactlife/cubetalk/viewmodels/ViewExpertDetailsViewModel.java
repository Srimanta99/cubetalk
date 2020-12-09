package local.impactlife.cubetalk.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.user_info.UserInfoFetchResponse;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.services.api.UserInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewExpertDetailsViewModel extends ViewModel {
    private static final String TAG = ViewExpertDetailsViewModel.class.getSimpleName();

    private MutableLiveData<UserInfoFetchResponse.Data> mExpert = new MutableLiveData<>();

    public LiveData<UserInfoFetchResponse.Data> getExpert() {
        return mExpert;
    }

    public void fetchExpertDetails(String expertId, String token) {
        UserInfoService userInfoService = ServiceBuilder.buildService(UserInfoService.class);

        Call<UserInfoFetchResponse> createRequest = userInfoService.getUserInfo(expertId,token);

        createRequest.enqueue(new Callback<UserInfoFetchResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserInfoFetchResponse> call, @NotNull Response<UserInfoFetchResponse> response) {
                Log.d(TAG, "onResponse: response: " + response.body().toString());
                if (response.isSuccessful()) {
                    UserInfoFetchResponse userInfoFetchResponse = response.body();
                    mExpert.setValue(userInfoFetchResponse.getData());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserInfoFetchResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }
}
