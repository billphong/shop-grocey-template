package com.grocery.service.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grocery.service.GrocerApplication;
import com.grocery.service.R;



/****************************************************************************
 * WelcomeActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for intro-screen .
 ***************************************************************************/


public class WelcomeActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;

    private Button btnSkip;
    private Button btnStartShoping;

    private TextView tvTitleOne;
    private TextView tvTitleTwo;
    private TextView tvTitleThree;

    private TextView[] dots;
    private int[] layouts;

    private TextView tvDesOne;
    private TextView tvDesTwo;
    private TextView tvDesThree;

    private Animation slide_up;
    private Animation slide_down;
    private Animation zoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);

        /**
         * Init component
         */


        initComponet();
        initAnimation();
        initViewPager();


    }

    private void initViewPager() {

        addBottomDots(0);
        changeStatusBarColor();


        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        btnStartShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
    }


    /**
     * Init Slid-Up & Slide-down & Zoom Animation
     */


    private void initAnimation() {

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_up);
    }


    /**
     * Launch HomeScreen and Save isWelcome PreferenceData
     */


    private void launchHomeScreen() {


        GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_isWelcom), true);
        Intent mMenuIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        mMenuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mMenuIntent);
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
        finish();
    }

    /**
     * Add Bottom Dotson viewPager
     */

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    /**
     * Change statusbar color
     */

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
    }


    /**
     * SetUp viewPager Listener
     */


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            initComponet();
            switch (position) {
                case 0:
                    //imgChat.startAnimation(slide_down);
                    tvTitleOne.startAnimation(zoom);
                    tvDesOne.startAnimation(slide_up);
                    break;
                default:
                    break;
            }

        }


        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btnStartShoping.setVisibility(View.VISIBLE);
                btnStartShoping.setText(getString(R.string.start_shoping));
                dotsLayout.setVisibility(View.GONE);
                btnSkip.setVisibility(View.GONE);

            } else {
                dotsLayout.setVisibility(View.VISIBLE);
                btnStartShoping.setVisibility(View.GONE);
                btnSkip.setVisibility(View.VISIBLE);
                btnSkip.setText(getString(R.string.skip));
            }

            initComponet();


            switch (position) {
                case 0:
                    tvTitleOne.startAnimation(zoom);
                    tvDesOne.startAnimation(slide_up);
                    break;
                case 1:
                    tvTitleTwo.startAnimation(zoom);
                    tvDesTwo.startAnimation(slide_up);
                    break;
                case 2:
                    tvTitleThree.startAnimation(zoom);
                    tvDesThree.startAnimation(slide_up);


                    break;
                default:
                    break;


            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    /**
     * SetUp viewPager Listener
     */



    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

    /**
     * initComponet
     */

    public void initComponet() {


        viewPager = findViewById(R.id.viewpager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnStartShoping = findViewById(R.id.btn_start_shop);
        layouts = new int[]{R.layout.welcome_slide1, R.layout.welcome_slide2, R.layout.welcome_slide3};

        tvTitleOne =(TextView) findViewById(R.id.tvTitleOne);
        tvTitleTwo = findViewById(R.id.tvTitleTwo);
        tvTitleThree = findViewById(R.id.tvTitleThree);

        tvDesOne =(TextView) findViewById(R.id.tvDesOne);
        tvDesTwo = findViewById(R.id.tvDesTwo);
        tvDesThree = findViewById(R.id.tvDesThree);

        myViewPagerAdapter = new MyViewPagerAdapter();




    }


}
