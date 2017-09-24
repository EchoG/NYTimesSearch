package com.codepath.nytimessearch.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.models.Filter;

import java.util.Calendar;

/**
 * Created by chenrangong on 9/23/17.
 */

public class FilterDialogFragment extends DialogFragment {

    public interface EditSaveListener{
        public void onEditSave(Filter filterQuery, FilterDialogFragment editItemDialogFragment);
    }

    Button saveBtw;
    public EditSaveListener editSaveListener;
    static Filter fragmentFilter;
    DatePicker datePicker;
    Spinner sort;
    CheckBox arts;
    CheckBox fashion;
    CheckBox sports;


    public FilterDialogFragment(){

    }

    public static FilterDialogFragment newInstance(Filter filter){
        FilterDialogFragment frag = new FilterDialogFragment();
        fragmentFilter = filter;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_filter_item, container);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        sort = (Spinner) view.findViewById(R.id.sortSpinner);
        arts = (CheckBox) view.findViewById(R.id.checkbox_arts);
        fashion = (CheckBox) view.findViewById(R.id.checkbox_Fashion);
        sports = (CheckBox) view.findViewById(R.id.checkbox_sports);

        // Set values
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fragmentFilter.beginDate);
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        if(fragmentFilter.sortOrder != null){
            if(fragmentFilter.sortOrder.equals("oldest")){
                sort.setSelection(0);
            }else if(fragmentFilter.sortOrder.equals("newest")){
                sort.setSelection(1);
            }else{
                sort.setSelection(0);
            }
        }else{
            sort.setSelection(0);
        }

        arts.setChecked(fragmentFilter.arts);
        fashion.setChecked(fragmentFilter.fashion);
        sports.setChecked(fragmentFilter.sports);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        saveBtw = (Button) view.findViewById(R.id.saveBtn);
        saveBtw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                fragmentFilter.beginDate = calendar.getTime();

                fragmentFilter.sortOrder  = sort.getSelectedItem().toString().toLowerCase();

                fragmentFilter.arts = arts.isChecked();
                fragmentFilter.fashion = fashion.isChecked();
                fragmentFilter.sports = sports.isChecked();

                editSaveListener.onEditSave(fragmentFilter, FilterDialogFragment.this);
            }
        });
    }
}
