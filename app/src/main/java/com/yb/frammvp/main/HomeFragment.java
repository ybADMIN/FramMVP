package com.yb.frammvp.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.frammvp.R;
import com.yb.frammvp.common.BaseFragment;
import com.yb.frammvp.main.model.UserModel;
import com.yb.ilibray.utils.data.assist.Check;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements MainView {
    UserPresenter mUserPresenter;
    private HomeFListener mListener;
    private TextView mMessage;
    private EditText mEtUser;
    private Button mButton;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        setPresenter();
        return view;
    }

    private void setPresenter() {
        mUserPresenter = new UserPresenter();
        mUserPresenter.setView(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView(View view) {
        if (view==null)return;
        mMessage= (TextView)view.findViewById(R.id.message);
        mEtUser= (EditText)view.findViewById(R.id.et_user);
        mButton= (Button)view.findViewById(R.id.btn_getUser);
        mButton.setOnClickListener(v -> {
            if (Check.isEmpty(mEtUser.getText())){
                Toast.makeText(context(), "ID不能为空", Toast.LENGTH_SHORT).show();
            }else {
                mUserPresenter.getUser(Integer.parseInt(mEtUser.getText().toString()));
            }
        });
        mMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
        mMessage.setOnClickListener(v -> mUserPresenter.getUserList());
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.homeAction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFListener) {
            mListener = (HomeFListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mUserPresenter.destroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void showUserList(List<UserModel> models) {
        StringBuilder builder=new StringBuilder();
        for (UserModel mod :
                models) {
            builder.append(mod.toString());
            builder.append("\n");
        }
        mMessage.setText(builder);
    }

    @Override
    public void showUser(UserModel models) {
        mMessage.setText(models.toString());
    }

    public interface HomeFListener {
        void homeAction(Uri uri);
    }
}
