package com.kapp.happinessindex;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.kapp.happinessindex.data.HashtagResult;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAB_TYPE = "tab type";
    private static final String ARG_PARAM2 = "param2";

    private final int TAB_BUTTONS_KEY = 1;
    private final int TAB_GRAPH_KEY = 2;

    private int mTabType;
    private HashtagResult mHashTag;

    private OnFragmentInteractionListener mListener;

    public TabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tabType the type of Tab to be inflated
     * @return A new instance of fragment TabFragment.
     */
    public static TabFragment newInstance(int tabType) {

        Log.e("DEBUG", "newInstance in TabFragment called");

        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_TYPE, tabType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.e("DEBUG", "onCreate in TabFragment called");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabType = getArguments().getInt(TAB_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view;

        if (mTabType == TAB_BUTTONS_KEY) {
            view = inflater.inflate(R.layout.tab_colorfields_fragment, container, false);
            TextView greenField = (TextView) view.findViewById(R.id.green_button);
            TextView orangeField = (TextView) view.findViewById(R.id.orange_button);
            TextView redField = (TextView) view.findViewById(R.id.red_button);
            TextView totalVotes = (TextView) view.findViewById(R.id.total_votes);

            if (mHashTag == null) {
                return view;
            }
            int totalAmount = mHashTag.getTotalAmount();
            totalVotes.setText("Total: " + totalAmount + " votes");
            int greenAmountinPercentage = (int) (((double) mHashTag.getGreenAmount() / (double) totalAmount) * 100);
            greenField.setText(getResources().getString(R.string.amountInPercentage, greenAmountinPercentage));
            int orangeAmountinPercentage = (int) (((double) mHashTag.getOrangeAmount() / (double)totalAmount) * 100);
            orangeField.setText(getResources().getString(R.string.amountInPercentage, orangeAmountinPercentage));
            int redAmountinPercentage = (int) (((double) mHashTag.getRedAmount() / (double) totalAmount) * 100);
            redField.setText(getResources().getString(R.string.amountInPercentage, redAmountinPercentage));

            setButtonSizeAccordingtoPercentage(greenField, greenAmountinPercentage);
            setButtonSizeAccordingtoPercentage(orangeField, orangeAmountinPercentage);
            setButtonSizeAccordingtoPercentage(redField, redAmountinPercentage);

            //set button size and percentage text on Buttons

        } else if (mTabType == TAB_GRAPH_KEY) {
            view = inflater.inflate(R.layout.tab_graph_fragment, container, false);
            GraphView statsGraph = (GraphView) view.findViewById(R.id.stats_graph);

            //TODO: set Graphparameters

        } else {
            throw new RuntimeException("Tab with key " + mTabType + " is not implemented.");
        }
        return view;
    }

    /*
    resize the Textview according to the percentage so that green, orange and red fields give a graphical feedback about vote
     */
    private void setButtonSizeAccordingtoPercentage(TextView textViewToResize, int percentage) {
        int hundredPercent = (int) (getResources().getDimension(R.dimen.button_size) * 3);
        int newHeight = (int) ((double) hundredPercent * ((double) percentage / 100));

        ViewGroup.LayoutParams params = textViewToResize.getLayoutParams();
        params.height = newHeight;
        textViewToResize.setLayoutParams(params);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void setData(HashtagResult hashTag) {
        mHashTag = hashTag;
        Log.e("DEBUG", "enter setData in TabFragment.");
    }
}
