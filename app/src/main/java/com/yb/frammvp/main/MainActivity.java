package com.yb.frammvp.main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yb.frammvp.R;
import com.yb.frammvp.common.BaseActivity;
import com.yb.frammvp.main.HomeFragment.HomeFListener;
import com.yb.ilibray.presenter.Presenter;

import static com.yb.frammvp.main.DashboradFragment.DashboradListener;

public class MainActivity extends BaseActivity implements DashboradListener,HomeFListener,MultitaskDownload.MultitaskDownloadListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d("abc");
        Logger.d("abc: %s","abc");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        addFragmentAndShow(R.id.content, HomeFragment.newInstance(),"home");
    }
    private void addFragmentAndShow(int content, Fragment home, String tag) {
        addFragmentAndShow(content,home,tag,false);
    }
    @SuppressLint("RestrictedApi")
    private void addFragmentAndShow(int content, Fragment home, String tag, boolean isAddBack) {
        FragmentTransaction fmanager = getSupportFragmentManager().beginTransaction();
        Fragment fragment=getSupportFragmentManager().findFragmentByTag(tag);
       if ( getSupportFragmentManager().getFragments()!=null &&  getSupportFragmentManager().getFragments().size()>0){
           for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
               Fragment oledFragment=getSupportFragmentManager().getFragments().get(i);
               if (oledFragment!=null &&!oledFragment.isHidden() && !oledFragment.equals(fragment))
                fmanager.hide(oledFragment);
           }
       }
       if (isAddBack){
           if (fragment!=null){
               if (fragment.isHidden())
                   fmanager.show(fragment).commit();
           }else {
               fmanager.add(content,home,tag).addToBackStack(tag).show(home).commit();
           }
       }else {
           if (fragment!=null){
               if (fragment.isHidden())
                   fmanager.show(fragment).commit();
           }else {
               fmanager.add(content,home,tag).show(home).commit();
           }
       }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        addFragmentAndShow(R.id.content, HomeFragment.newInstance(),"home");
                        return true;
                    case R.id.navigation_dashboard:
                            addFragmentAndShow(R.id.content, DashboradFragment.newInstance(),"dashborad");
                        return true;
                    case R.id.navigation_notifications:
//                        String url2 = "http://192.168.29.168:8080/download/ssr-android.apk";
//                        DownloadEntity downloadEntity2 = new DownloadEntity().setUrl(url2).setMethod("GET");
//                        if (DownLoadManager.getInstance(this).exist(downloadEntity2)){
//                            DownLoadManager.getInstance(this).pause(downloadEntity2);
//                        }else {
//                            DownLoadManager.getInstance(this).addDownLoad(downloadEntity2);
//                            addFragmentAndShow(R.id.content,DashboradFragment.newInstance(),"dashborad");
//                        }
                        Toast.makeText(MainActivity.this, getString(R.string.title_notifications), Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            };


    @Override
    protected Presenter getPresenter() {
        return null;
    }
    @Override
    public void dashboradAction(Uri uri) {
        if (uri.compareTo(Uri.parse("goto://Multitask"))==0){
            addFragmentAndShow(R.id.content,MultitaskDownload.newInstance(),"multitask",true);
        }
    }

    @Override
    public void homeAction(Uri uri) {

    }
}
