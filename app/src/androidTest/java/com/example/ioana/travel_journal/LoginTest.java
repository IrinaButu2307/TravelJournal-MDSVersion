package com.example.ioana.travel_journal;

import android.util.Log;
import android.widget.Button;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class LoginTest {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<Login>(Login.class);
    private Login mLogin = null;

    @Before
    public void setUp() throws Exception {
        mLogin = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
     mLogin = null;
    }

    @Test
    public void onCreate() {
        Button button = mLogin.findViewById(R.id.button_signin);
        assertNotNull(button);
    }
}