package com.example.listenmusic.login_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.listenmusic.Login_Activity;
import com.example.listenmusic.MainActivity;
import com.example.listenmusic.Models.User;
import com.example.listenmusic.R;
import com.example.listenmusic.Service.APIservice;
import com.example.listenmusic.Service.Dataservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_4_2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_4_2Fragment extends Fragment {
    private Button btnLogin;
    private EditText edtemail,edtpass;
    private List<User> listuser;
    private User myUser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_4_2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_4_2Fragment newInstance(String param1, String param2) {
        Login_4_2Fragment fragment = new Login_4_2Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login_4_2, container, false);

        btnLogin = rootView.findViewById(R.id.btnLogin);
        edtemail = rootView.findViewById(R.id.etEmail);
        edtpass = rootView.findViewById(R.id.etPassword);
        getlisUser();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickLogin();

            }


        });
        return rootView;
    }
    private void ClickLogin() {
        String Email = edtemail.getText().toString().trim();
        String Password = edtpass.getText().toString().trim();
        if(listuser==null ||listuser.isEmpty()){
            return;
        }
        boolean isHasUser= false;
        for(User user: listuser){
            if(Email.equals(user.getEmail()) && Password.equals(user.getPass())){
                isHasUser = true;
                myUser = user;
                break;
            }
        }
        if(isHasUser){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_user", myUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),"Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
        }
    }
    private void getlisUser() {
        Dataservice dataservice = APIservice.getService();
        Call<List<User>> callback = dataservice.GetUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                listuser=response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
