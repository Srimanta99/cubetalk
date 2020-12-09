package local.impactlife.cubetalk.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceIdService extends FirebaseInstanceIdService {
    public InstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        //sends this token to the server
        System.out.println("token "+token);
        sendToServer(token);
    }

    private void sendToServer(String token) {

    }
    }