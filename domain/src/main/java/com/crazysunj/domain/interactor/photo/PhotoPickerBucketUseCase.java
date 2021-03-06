package com.crazysunj.domain.interactor.photo;

import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: sunjian
 * created on: 2018/9/17 下午3:38
 * description:
 */
public class PhotoPickerBucketUseCase extends UseCase<List<BucketEntity>, Void> {
    private final PhotoPickerRepository mPhotoPickerRepository;

    @Inject
    public PhotoPickerBucketUseCase(PhotoPickerRepository photoPickerRepository) {
        mPhotoPickerRepository = photoPickerRepository;
    }

    /**
     * 根据修改时间排序，好像跟微信的没对上，微信应该想的更多
     */
    @Override
    protected Flowable<List<BucketEntity>> buildUseCaseObservable(Void aVoid) {
        return mPhotoPickerRepository.getBucketList()
                .observeOn(Schedulers.io())
                .flatMap(Flowable::fromIterable)
                .toSortedList(BucketEntity::compareTo)
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
