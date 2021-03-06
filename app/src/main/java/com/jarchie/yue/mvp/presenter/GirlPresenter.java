package com.jarchie.yue.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jarchie.common.base.impl.BasePresenterImpl;
import com.jarchie.yue.R;
import com.jarchie.yue.api.Api;
import com.jarchie.yue.mvp.model.GirlBean;
import com.jarchie.yue.constant.HostType;
import com.jarchie.yue.mvp.contract.GirlContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jarchie on 2018\1\26.
 * 妹子模块的中间处理类
 */

public class GirlPresenter extends BasePresenterImpl<GirlContract.View> implements GirlContract.presenter {

    public GirlPresenter(GirlContract.View view) {
        super(view);
    }

    @Override
    public void requestGirlData(Context mContext, int pageSize, int pageNum) {
        view.showLoading(mContext.getString(R.string.loading));
        Api.getDefault(HostType.GANK_GIRL_PHOTO).requestGirlData(pageSize,pageNum).enqueue(new Callback<GirlBean>() {
            @Override
            public void onResponse(@NonNull Call<GirlBean> call, @NonNull Response<GirlBean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        view.setData(response.body());
                        view.stopLoading();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GirlBean> call, @NonNull Throwable t) {
                view.stopLoading();
                view.showErrorTip(t.getMessage());
            }
        });
    }
}
