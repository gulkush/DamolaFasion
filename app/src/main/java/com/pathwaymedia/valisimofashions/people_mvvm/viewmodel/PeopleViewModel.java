/**
 * Copyright 2016 Erik Jhordan Rey. <p/> Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at <p/> http://www.apache.org/licenses/LICENSE-2.0 <p/> Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.pathwaymedia.valisimofashions.people_mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import com.pathwaymedia.valisimofashions.people_mvvm.PeopleApplication;
import com.pathwaymedia.valisimofashions.people_mvvm.R;
import com.pathwaymedia.valisimofashions.people_mvvm.data.PeopleResponse;
import com.pathwaymedia.valisimofashions.people_mvvm.data.PeopleService;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PeopleViewModel implements PeopleViewModelContract.ViewModel {

  public ObservableInt peopleProgress;
  public ObservableInt peopleList;
  public ObservableInt peopleLabel;
  public ObservableField<String> messageLabel;

  private PeopleViewModelContract.MainView mainView;
  private Context context;
  private Subscription subscription;

  public PeopleViewModel(@NonNull PeopleViewModelContract.MainView mainView,
      @NonNull Context context) {

    this.mainView = mainView;
    this.context = context;
    peopleProgress = new ObservableInt(View.GONE);
    peopleList = new ObservableInt(View.GONE);
    peopleLabel = new ObservableInt(View.VISIBLE);
    messageLabel = new ObservableField<>(context.getString(R.string.default_loading_people));
  }

  public void onClickFabLoad(View view) {
    initializeViews();
    fetchPeopleList();
  }

  //It is "public" to show an example of test
  public void initializeViews() {
    peopleLabel.set(View.GONE);
    peopleList.set(View.GONE);
    peopleProgress.set(View.VISIBLE);
  }

  private void fetchPeopleList() {

    final String URL = "http://api.randomuser.me/?results=10&nat=en";
    unSubscribeFromObservable();
    PeopleApplication peopleApplication = PeopleApplication.create(context);
    PeopleService peopleService = peopleApplication.getPeopleService();
    subscription = peopleService.fetchPeople(URL)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(peopleApplication.subscribeScheduler())
        .subscribe(new Action1<PeopleResponse>() {
          @Override public void call(PeopleResponse peopleResponse) {
            peopleProgress.set(View.GONE);
            peopleLabel.set(View.GONE);
            peopleList.set(View.VISIBLE);

            if (mainView != null) {
              mainView.loadData(peopleResponse.getPeopleList());
            }
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            throwable.printStackTrace();
            messageLabel.set(context.getString(R.string.error_loading_people));
            peopleProgress.set(View.GONE);
            peopleLabel.set(View.VISIBLE);
            peopleList.set(View.GONE);
          }
        });
  }

  @Override public void destroy() {
    reset();
  }

  private void unSubscribeFromObservable() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  private void reset() {
    unSubscribeFromObservable();
    subscription = null;
    context = null;
    mainView = null;
  }
}
