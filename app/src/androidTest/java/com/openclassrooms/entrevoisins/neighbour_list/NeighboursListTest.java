
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.ui.neighbour_list.Activity.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.SelectViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private static int POSITION_ITEM = 0;

    private ListNeighbourActivity mActivity;
    private List<Neighbour> mNeighbourList = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Click on a item then go to details Page
     */
    @Test
    public void myNeighboursList_clickOnView_shouldGoToDetailPage(){
        //Given : click on the item
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Then : go to page details
        onView(withId(R.id.activity_details))
                .check(matches(isDisplayed()));
    }

    /**
     * Click on a item and attent to have the name in the TextView
     */
    @Test
    public void detailPageTextView_LoadNameTextView_shouldShowTheName(){
        //Given : create a neighbour for test at the position 0
        Neighbour neighbour = this.mNeighbourList.get(POSITION_ITEM);
        //When : click on a item of neighbour list at position 0
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, new SelectViewAction()));
        //Then : attent to have the name of the neighbour in the TextView
        onView(withId(R.id.nameDetails)).check(matches(withText(neighbour.getName())));
    }

    /**
     * Chek if the list of favorite given only favorite neighbours
     */
    @Test
    public void Given_two_favorites_When_clickOnFavoriteButton_Then_AttentToShowThem(){
        //When : Check if the View is Displayed
        onView(withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        for (int i = 0; i < 2; i++){
            //click on the first neighbour to show detail
            onView(withId(R.id.list_neighbours))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,new SelectViewAction()));
            //click on the fab button to add it to favorite
            onView(withId(R.id.fab)).perform(click());
            //return back
            pressBack();
        }
        //scroll to the favorite page in the container
        onView(withId(R.id.container)).perform(scrollRight());
        //Check if favorite list has 2 items
        onView(withId(R.id.list_favorite)).check(withItemCount(2));
        //Then : assert that the page is displayed
        onView(withId(R.id.list_favorite)).check(matches(isDisplayed()));
    }

    /**
     * assert that the delete button remove a favorite in the list
     */
    @Test
    public void Given_aFavorite_When_clickOnDelete_Then_deleteIt() {
        //When : Check if the View is Displayed
        onView(withId(R.id.list_neighbours)).check(matches(isDisplayed()));
        for (int i = 0; i < 1; i++){
            //click on the first neighbour to show detail
            onView(withId(R.id.list_neighbours))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,new SelectViewAction()));
            //click on the fab button to add it to favorite
            onView(withId(R.id.fab)).perform(click());
            //return back
            pressBack();
        }

        //scroll to the favorite page in the container
        onView(withId(R.id.container)).perform(scrollRight());
        //Then : assert that the page is displayed
        onView(withId(R.id.list_favorite)).check(matches(isDisplayed()));
        //check if the favorite count is 1
        onView(withId(R.id.list_favorite)).check(withItemCount(1));
        // When perform a click on a delete icon
        onView(withId(R.id.list_favorite))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 0
        onView(withId(R.id.list_favorite)).check(withItemCount(0));
    }
}