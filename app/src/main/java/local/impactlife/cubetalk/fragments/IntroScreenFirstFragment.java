package local.impactlife.cubetalk.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import local.impactlife.cubetalk.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class IntroScreenFirstFragment extends Fragment {

    public IntroScreenFirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro_screen_first_fragment, container, false);
    }
}
