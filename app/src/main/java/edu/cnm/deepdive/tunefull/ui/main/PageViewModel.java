package edu.cnm.deepdive.tunefull.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

  private MutableLiveData<Integer> index = new MutableLiveData<>();
  private LiveData<String> text = Transformations.map(index,
      input -> "Hello world from section: " + input);

  public void setIndex(int index) {
    this.index.setValue(index);
  }

  public LiveData<String> getText() {
    return text;
  }
}