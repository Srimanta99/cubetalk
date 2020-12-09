package local.impactlife.cubetalk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import local.impactlife.cubetalk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    public DemoObjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demo_object, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        ((TextView) view.findViewById(R.id.text1))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));

    }
}
