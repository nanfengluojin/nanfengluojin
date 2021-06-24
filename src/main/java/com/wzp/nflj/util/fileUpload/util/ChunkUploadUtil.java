package com.wzp.nflj.util.fileUpload.util;

import com.wzp.nflj.config.CustomConfig;
import com.wzp.nflj.enums.ResultCodeEnum;
import com.wzp.nflj.util.FileUtil;
import com.wzp.nflj.util.Result;
import com.wzp.nflj.util.fileUpload.vo.CheckMd5FileVO;
import com.wzp.nflj.util.fileUpload.vo.FileVO;
import com.wzp.nflj.util.fileUpload.vo.UploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/7 10:16
 */
public class ChunkUploadUtil {

    private static final String DELIMITER = "-";

    private static String savePath = CustomConfig.fileSave;


    private static String checkFileSavePath() {
        String fileSavePath = savePath;
        File file = new File(fileSavePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileSavePath;
    }


    public static Result upload(MultipartFile file, UploadVO uploadVO) {
        Long chunk = uploadVO.getChunk();
        // 没有分片
        if (chunk == null) {
            return unChunkUpload(file, uploadVO);
        }
        return ChunkUpload(file, uploadVO);
    }


    /**
     * 文件校验
     *
     * @param md5FileVO
     * @return FileVO
     */
    public static Result check(CheckMd5FileVO md5FileVO) {
        if (md5FileVO.getType() == null || md5FileVO.getChunk() == null || md5FileVO.getFileMd5() == null ||
                md5FileVO.getSuffix() == null || md5FileVO.getFileName() == null) {
            return Result.error(ResultCodeEnum.LACK_NEEDS_PARAM);
        }
        Integer type = md5FileVO.getType();
        Long chunk = md5FileVO.getChunk();
        String fileName = md5FileVO.getFileMd5() + "." + md5FileVO.getSuffix();
        Long fileSize = md5FileVO.getFileSize();
        String fileSavePath = checkFileSavePath();
        // 未分片校验
        if (type == 0) {
            String destFilePath = fileSavePath + File.separator + fileName;
            File destFile = new File(destFilePath);
            if (destFile.exists() && destFile.length() == fileSize) {
                return Result.ok(new FileVO(fileName, fileSize));
            } else {
                return Result.error(ResultCodeEnum.FILE_NOT_EXISTS);
            }
        } else {// 分片校验
            String fileMd5 = md5FileVO.getFileMd5();
            String destFileDir = fileSavePath + File.separator + fileMd5;
            String destFileName = chunk + DELIMITER + fileName;
            String destFilePath = destFileDir + File.separator + destFileName;
            File destFile = new File(destFilePath);
            if (destFile.exists() && destFile.length() == fileSize) {
                return Result.error(ResultCodeEnum.CHUNK_EXISTS);
            } else {
                return Result.error(ResultCodeEnum.CHUNK_NOT_EXISTS);
            }
        }
    }


    /**
     * 未分片上传
     *
     * @param file
     * @param uploadVO
     * @return FileVO
     */
    public static Result unChunkUpload(MultipartFile file, UploadVO uploadVO) {
        String suffix = uploadVO.getSuffix();
        String fileName = uploadVO.getFileMd5() + "." + suffix;
        //判断文件存放目录是否存在
        String fileSavePath = checkFileSavePath();
        // 文件上传
        File destFile = new File(fileSavePath + File.separator + fileName);
        if (file != null && !file.isEmpty()) {
            // 上传目录
            File fileDir = new File(fileSavePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            if (destFile.exists()) {
                destFile.delete();
            }
            try {
                file.transferTo(destFile);
                return Result.ok(new FileVO(fileName));
            } catch (Exception e) {
                return Result.error(ResultCodeEnum.FILE_UPLOAD_ERROR);
            }
        }
        return Result.error(ResultCodeEnum.UPLOAD_FAIL);
    }


    /**
     * 分片上传
     *
     * @param file
     * @param uploadVO
     * @return FileVO
     */
    public static Result ChunkUpload(MultipartFile file, UploadVO uploadVO) {
        String fileMd5 = uploadVO.getFileMd5();
        String fileName = fileMd5 + "." + uploadVO.getSuffix();
        Long chunk = uploadVO.getChunk();// 当前片
        Long chunks = uploadVO.getChunks();// 总共多少片
        //判断文件存放目录是否存在
        String fileSavePath = checkFileSavePath();
        // 分片目录创建
        String chunkDirPath = fileSavePath + File.separator + fileMd5;
        File chunkDir = new File(chunkDirPath);
        if (!chunkDir.exists()) {
            chunkDir.mkdirs();
        }
        // 分片文件上传
        String chunkFileName = chunk + DELIMITER + fileName;
        String chunkFilePath = chunkDir + File.separator + chunkFileName;
        File chunkFile = new File(chunkFilePath);
        try {
            file.transferTo(chunkFile);
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.CHUNK_UPLOAD_ERROR);
        }
        // 合并分片
        Long chunkSize = uploadVO.getChunkSize();
        long seek = chunkSize * chunk;
        String destFilePath = fileSavePath + File.separator + fileName;
        File destFile = new File(destFilePath);
        if (chunkFile.length() > 0) {
            try {
                ChunkFileUtil.randomAccessFile(chunkFile, destFile, seek);
            } catch (IOException e) {
                return Result.error(ResultCodeEnum.CHUNK_MERGE_FAIL);
            }
        }
        if (chunk == chunks - 1) {
            // 删除分片文件夹
            FileUtil.deleteFolder(new File(chunkFilePath));
            return Result.ok(new FileVO(fileName));
        } else {
            return Result.error(ResultCodeEnum.UPLOADING);
        }
    }


}
