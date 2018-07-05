package com.kuliner_di_bekasi;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;


public class TabsViewPagerFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabsViewPagerFragmentActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;
	
	private class TabInfo {
		 private String tag;
         @SuppressWarnings("unused")
		private Class<?> clss;
         @SuppressWarnings("unused")
		private Bundle args;
         @SuppressWarnings("unused")
		private Fragment fragment;
         TabInfo(String tag, Class<?> clazz, Bundle args) {
        	 this.tag = tag;
        	 this.clss = clazz;
        	 this.args = args;
         }
	}
	
	class TabFactory implements TabContentFactory {
		private final Context mContext;
	    public TabFactory(Context context) {
	        mContext = context;
	    }

	    public View createTabContent(String tag) {
	        View v = new View(mContext);
	        v.setMinimumWidth(0);
	        v.setMinimumHeight(0);
	        return v;
	    }

	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullScreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.pencarian_tablayout);
		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
		this.intialiseViewPager();
	}
	
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); 
        super.onSaveInstanceState(outState);
    }
      
    private void intialiseViewPager() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, PencarianTab.class.getName()));
		this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
		this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
    }

	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("PENCARIAN").setIndicator("PENCARIAN"), ( tabInfo = new TabInfo("Search", PencarianTab.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        
        mTabHost.setOnTabChangedListener(this);
	}


	private static void AddTab(TabsViewPagerFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
	}
	
	public void onTabChanged(String tag) {
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
    }
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		this.mTabHost.setCurrentTab(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}
}
