package com.openclassrooms.entrevoisins.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;

import org.hamcrest.Matcher;

/**
 * Created by Nelfdesign at 01/09/2019
 * com.openclassrooms.entrevoisins.utils
 */
public class DetailsNeighbourAction implements ViewAction {
    //field
    private String name;

    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Description of the neighbour";
    }

    @Override
    public void perform(UiController uiController, View view) {
        //TextView of the NeighbourFragment with the name
        TextView textView = view.findViewById(R.id.item_list_name);
        name = textView.getText().toString();
        view.performClick();
    }

    /**
     * Getter
     * @return the name
     */
    public String getName(){ return this.name ;}
}
