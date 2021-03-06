package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.PhotoPickerContract;
import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.interactor.photo.PhotoPickerBucketUseCase;
import com.crazysunj.domain.interactor.photo.PhotoPickerMediaUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2018/9/17 下午2:32
 * description:
 */
@ActivityScope
public class PhotoPickerPresenter extends BasePresenter<PhotoPickerContract.View> implements PhotoPickerContract.Presenter {
    private PhotoPickerBucketUseCase mPhotoPickerBucketUseCase;
    private PhotoPickerMediaUseCase mPhotoPickerMediaUseCase;

    @Inject
    public PhotoPickerPresenter(PhotoPickerBucketUseCase photoPickerBucketUseCase, PhotoPickerMediaUseCase photoPickerMediaUseCase) {
        mPhotoPickerBucketUseCase = photoPickerBucketUseCase;
        mPhotoPickerMediaUseCase = photoPickerMediaUseCase;
    }

    @Override
    public void getMediaList(int imageOffset, int videoOffset, String bucketId) {
        mPhotoPickerMediaUseCase.execute(PhotoPickerMediaUseCase.Params.get(imageOffset, videoOffset, bucketId), new BaseSubscriber<MediaEntity.MediaResponseData>() {
            @Override
            public void onNext(MediaEntity.MediaResponseData data) {
                mView.showMediaList(data.imageOffset, data.videoOffset, data.mediaList);
            }
        });
    }

    @Override
    public void getBucketList() {
        mPhotoPickerBucketUseCase.execute(new BaseSubscriber<List<BucketEntity>>() {
            @Override
            public void onNext(List<BucketEntity> bucketEntityList) {
                mView.showBucketList(bucketEntityList);
            }
        });
    }
}
