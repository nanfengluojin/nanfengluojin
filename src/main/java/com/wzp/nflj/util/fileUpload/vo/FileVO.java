package com.wzp.nflj.util.fileUpload.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wq.li
 * @DATE: 2020/8/7 10:25
 */
@Data
@ApiModel("文件VO")
public class FileVO implements Serializable {
    private static final long serialVersionUID = 2777150571045221608L;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    public FileVO() {
    }

    public FileVO(String fileName) {
        this.fileName = fileName;
    }

    public FileVO(String fileName, Long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
}
