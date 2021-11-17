package com.patternkeyboard.android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.patternkeyboard.android.adapter.SliderPageAdapter;
import com.patternkeyboard.android.utils.PreferenceManager;
import com.patternkeyboard.android.utils.Utils;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout mPageIndicatorLL;
    private int[] sliderLayoutIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (!PreferenceManager.getInstance(this).isFirstTimeLaunch())
            launchHomeScreen();

        sliderLayoutIds = new int[]{R.layout.activity_welcome_slide1, R.layout.activity_welcome_slide2, R.layout.activity_welcome_slide3};

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mPageIndicatorLL = (LinearLayout) findViewById(R.id.pageIndicatorLL);
        Button btnSkip = (Button) findViewById(R.id.skipBtn);
        Button btnNext = (Button) findViewById(R.id.nextBtn);

        updatePageIndicator(0);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int currentPageIndex = mViewPager.getCurrentItem() + 1;
                if (currentPageIndex < sliderLayoutIds.length) {
                    // move to next screen
                    mViewPager.setCurrentItem(currentPageIndex);
                } else {
                    launchHomeScreen();
                }
            }
        });

        // utils hide notification bar
        mViewPager.setAdapter(new SliderPageAdapter(this, sliderLayoutIds));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updatePageIndicator(position);
                // changing the next button text 'NEXT' / 'GOT IT'
                if (position == sliderLayoutIds.length - 1) {
                    // last page. make button text to GOT IT
                    btnNext.setText(getString(R.string.got_it));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    // still pages are left
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updatePageIndicator(int currentPage) {

        int colorsActive = getResources().getColor(R.color.screen_active_color);
        int colorsInactive = getResources().getColor(R.color.screen_inactive_color);

        mPageIndicatorLL.removeAllViews();

        for (int i = 0; i < sliderLayoutIds.length; i++) {
            TextView dots = new TextView(this);
            dots.setText(Html.fromHtml("&#8226;"));
            dots.setTextSize(35);
            dots.setTextColor(colorsInactive);
            mPageIndicatorLL.addView(dots);
        }

        if (sliderLayoutIds.length > 0) {
            TextView txv = (TextView) mPageIndicatorLL.getChildAt(currentPage);
            txv.setTextColor(colorsActive);
        }
    }

    private void launchHomeScreen() {
        PreferenceManager.getInstance(this).setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    public void enablePatternKeyboard(View view) {
        Utils.openKeyboardSettings(this);
    }

}