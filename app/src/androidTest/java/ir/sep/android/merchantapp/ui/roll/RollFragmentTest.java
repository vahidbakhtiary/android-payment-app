package ir.sep.android.merchantapp.ui.roll;

import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.ui.MainActivity;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;


public class RollFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =new ActivityTestRule<>(MainActivity.class);


    SharedPreferencesHelper sharedPreferMock;


    @Before
    public void setUp() {
//get fragment
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

         sharedPreferMock=new SharedPreferencesHelper(mActivityRule.getActivity());

        sharedPreferMock.insert(Const.SHARED_PREF_Merchant_GUID_KEY,"74775dd8-dd27-4409-bf53-4f83797d6435");
        sharedPreferMock.insert(Const.SHARED_PREF_APP_GUID_KEY,"3e02b54e-6fab-4a61-8059-128b3b05f9e1");

    }

    @Test
    public void test_rollFragment() throws InterruptedException {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_menu),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        Thread.sleep(3000);
        onView(withId(R.id.btn_send))
                .check(matches(isDisplayed()));

    }


    @Test
    public void test_rollFragment_disconnect_network() throws InterruptedException {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_menu),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

      //  Thread.sleep(3000);


        onView(withText(startsWith("Clicked:"))).
        inRoot(withDecorView(
                not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).
                check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }




}

