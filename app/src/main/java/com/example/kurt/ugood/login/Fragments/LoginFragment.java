package com.example.kurt.ugood.login.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kurt.ugood.ForgotPasswordActivity;
import com.example.kurt.ugood.login.LoginActivity;
import com.example.kurt.ugood.main.MainActivity;
import com.example.kurt.ugood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText userEmail, userPassword;
    private ProgressBar loadingProgress;
    private TextView forgotPass, errorMessage;
    private Button loginButton;

    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == loginButton)
        {
            if (userEmail.getText() != null && userEmail.getText().toString().length() == 0 ||
                    userPassword.getText() != null && userPassword.getText().toString().length() == 0)
                errorMessage.setText(getString(R.string.errorMessageNEEDTOFILL));
            else
                LogAttempt();
        }
        else if (v == forgotPass)
            GoToForgotPassword();
    }

    private void GoToForgotPassword()
    {
        final Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void LogAttempt()
    {
        if (getActivity() == null)
            return;

        final Intent intent = new Intent(getActivity(), MainActivity.class);
        String emailFinal = userEmail.getText().toString().trim();
        String passFinal = userPassword.getText().toString().trim();

        fbAuth.signInWithEmailAndPassword(emailFinal, passFinal)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            loadingProgress.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.INVISIBLE);
                            errorMessage.setText(getString(R.string.logSuccess));

                            startActivity(intent);
                        }
                        else
                        {
                             try {
                                 throw task.getException();
                             } catch(FirebaseAuthInvalidUserException e) {
                                 errorMessage.setText(getString(R.string.errorMessageUSERDOESNOTEXIST));
                             } catch(FirebaseAuthInvalidCredentialsException e) {
                                 errorMessage.setText(getString(R.string.errorMessageINVALIDEMAILORPASSWORD));
                             } catch(Exception e) {
                                 Log.e("ERROR: ", e.getMessage());
                             }
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment_login, container, false);

        loginButton = view.findViewById(R.id.loginButton);
        userEmail = view.findViewById(R.id.emailText);
        userPassword = view.findViewById(R.id.passText);
        forgotPass = view.findViewById(R.id.forgotPass);
        errorMessage = view.findViewById(R.id.errorMessage);

        loadingProgress = view.findViewById(R.id.progressBar);
        loadingProgress.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(this);
        forgotPass.setOnClickListener(this);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
