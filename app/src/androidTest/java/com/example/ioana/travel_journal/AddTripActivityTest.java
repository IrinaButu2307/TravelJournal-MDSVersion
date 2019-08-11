package com.example.ioana.travel_journal;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class AddTripActivityTest {

    @Rule
    public ActivityTestRule<AddTripActivity> mActivityTestRule =
            new ActivityTestRule<AddTripActivity>(AddTripActivity.class);
    //'launch' my main act
    private AddTripActivity mActivity = null;

    // set a monitor on destination activity

    Instrumentation.ActivityMonitor monitor =
            getInstrumentation().addMonitor(MenuActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

    }

    @Test
    public void testLaunchOnSaveClicked(){
        assertNotNull(mActivity.findViewById(R.id.button_save));
        onView(withId(R.id.button_save)).perform(click());

        Activity menuActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(menuActivity);
        menuActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
        mActivity =null;
    }
}