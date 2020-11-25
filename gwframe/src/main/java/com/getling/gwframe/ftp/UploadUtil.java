package com.getling.gwframe.ftp;

import com.blankj.utilcode.util.LogUtils;
import com.getling.gwframe.utils.DateUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @Author: getling
 * @CreateDate: 2019/11/27 11:55
 * @Description: 上传文件到ftp服务器
 */
public class UploadUtil {
    public static FTPUtil ftpUtil;

    private static String initUploadFileName(File file) {
        String fileName = file.getName();
        String[] nameArray = fileName.split("\\.");
        return DateUtil.getDateFromLong(null) + "." + nameArray[nameArray.length - 1];
    }

    /**
     * @param uploadFile 上传文件
     * @return 观察流
     */
    public static Observable<String> uploadFile(File uploadFile, String uploadDirectory) {
        String uploadFileName = initUploadFileName(uploadFile);
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (ftpUtil == null) {
                    ftpUtil = new FTPUtil();
                }
                ftpUtil.uploadSingleFile(uploadFile, uploadFileName, uploadDirectory, new FTPUtil.UploadProgressListener() {
                    @Override
                    public void onUploadProgress(String currentStep, long uploadSize, File file) {
                        if (currentStep.equals(FTPUtil.FTP_CONNECT_FAIL) || currentStep.equals(FTPUtil.FTP_UPLOAD_FAIL)) {
                            emitter.onError(new Throwable(FTPUtil.FTP_UPLOAD_FAIL));
                        } else if (currentStep.equals(FTPUtil.FTP_UPLOAD_SUCCESS)) {
                            emitter.onNext(uploadDirectory + uploadFileName);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> uploadFileList(List<File> uploadFile, String uploadDirectory) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                if (ftpUtil == null) {
                    ftpUtil = new FTPUtil();
                }
                ftpUtil.uploadMultiFile(uploadFile, uploadDirectory, new FTPUtil.UploadProgressListener() {
                    @Override
                    public void onUploadProgress(String currentStep, long uploadSize, File file) {
                        if (currentStep.equals(FTPUtil.FTP_CONNECT_FAIL) || currentStep.equals(FTPUtil.FTP_UPLOAD_FAIL)) {
                            emitter.onError(new Throwable(FTPUtil.FTP_UPLOAD_FAIL));
                        } else if (currentStep.equals(FTPUtil.FTP_UPLOAD_SUCCESS)) {
                            emitter.onNext(file.getName());
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (ftpUtil == null) {
                    ftpUtil = new FTPUtil();
                }
                ftpUtil.deleteSingleFile(filePath, new FTPUtil.DeleteFileProgressListener() {
                    @Override
                    public void onDeleteProgress(String currentStep) {
                        LogUtils.e(currentStep);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
