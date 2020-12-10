package edu.cnm.deepdive.tunefull.controller;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.tunefull.R;

/**
 * Currently unimplemented
 */
public class NestedFragment extends Fragment {

  /**
   * Inflates nothing currently.
   *
   * @param inflater           A {@code LayoutInflater}.
   * @param container          A {@code ViewGroup}.
   * @param savedInstanceState A {@code Bundle}.
   * @return A {@code View}.
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_nested, container, false);
  }
}