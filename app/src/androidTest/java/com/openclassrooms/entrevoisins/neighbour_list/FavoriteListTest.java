package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewActionFavorite;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Nelfdesign at 01/09/2019
 * com.openclassrooms.entrevoisins.neighbour_list
 */
@RunWith(AndroidJUnit4.class)
public class FavoriteListTest {

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void myFavouritesList_deleteAction_shouldRemoveItem() {
        int favouritesCount = 0;
        // When perform a click on a delete icon
        onView(withId(R.id.list_favorite))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewActionFavorite()));
        // Then : the number of element is favouritesCount - 1
        onView(withId(R.id.list_favorite)).check(withItemCount(favouritesCount - 1));
    }
}
