package br.com.marcosmilitao.idativosandroid.Processos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Idutto07 on 21/12/2016.
 */

public class ProcessosOSActivityFragment extends Fragment {
    public ProcessosOSActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_processos_os, container, false);
    }
}
