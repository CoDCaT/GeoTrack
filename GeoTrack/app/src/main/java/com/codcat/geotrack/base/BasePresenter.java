package com.codcat.geotrack.base;


public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {


    private V mvpView;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        this.mvpView = null;
    }

    public boolean isViewAttached(){
        return this.mvpView != null;
    }

    public V getMvpView(){
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call GeneralActivityPresenter.onAttach(MvpView) before" + " requesting data to the GeneralActivityPresenter");
        }
    }

}
