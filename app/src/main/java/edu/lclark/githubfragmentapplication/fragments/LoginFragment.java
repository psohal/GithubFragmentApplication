package edu.lclark.githubfragmentapplication.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.GithubUserAsyncTask;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.models.GithubUser;




/**
 * Created by parulsohal on 3/2/16.
 */
public class LoginFragment extends Fragment implements GithubUserAsyncTask.OnUserNotFoundListener {


    GithubUser mUser;
    @Bind(R.id.fragment_login_edit_text)
    EditText mEditText;
    @Bind(R.id.fragment_login_button)
    Button mLoginButton;

   GithubUserAsyncTask mATask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.fragment_login_button)
    public void onFindUserClick() {
        ConnectivityManager manager =(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = manager.getActiveNetworkInfo();
        if(network == null || !network.isConnected()){
            Toast.makeText(getContext(), "You do not have Internet!", Toast.LENGTH_SHORT).show();
        }else {
            mATask = new GithubUserAsyncTask(this,
                    (GithubUserAsyncTask.OnUserFoundListener) getActivity(),
                    mEditText.getText().toString());
            mATask.execute(mEditText.getText().toString());
            mLoginButton.setEnabled(false);
        }
        View v = getActivity().getCurrentFocus();
        if(v != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    @Override
    public void onUserNotFound() {

    }
}
