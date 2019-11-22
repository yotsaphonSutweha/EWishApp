package nci.yo.ewishapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import nci.yo.ewishapp.Helper.LocalHelper;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        final Button thBtn = view.findViewById(R.id.thButtonId);
        final Button engBtn = view.findViewById(R.id.engButtonId);

        thBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage(view, "th");
                refresh();
            }
        });

        engBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage(view, "en");
                refresh();
            }
        });

        return view;
    }

    private void changeLanguage(View view, String lang) {
        LocalHelper.setLocale(view.getContext(), lang);
    }

    private void refresh(){
        Intent intent = new Intent(getActivity(), getActivity().getClass());
        startActivity(intent);
    }
}
