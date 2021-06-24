package com.wzp.nflj.util.fileUpload.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel("文件MD5校验VO")
public class CheckMd5FileVO implements Serializable {

    private static final long serialVersionUID = 5714726223010887827L;
    /**
     * 0：不分片，1：分片
     */
    @ApiModelProperty(value = "分片状态 0：不分片，1：分片", required = true)
    private Integer type;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", required = true)
    private String fileName;

    /**
     * 文件Md5（文件唯一表示）
     */
    @ApiModelProperty(value = "文件Md5（文件唯一表示）", required = true)
    private String fileMd5;

    /**
     * 当前分片下标
     */
    @ApiModelProperty(value = "当前分片下标", required = true)
    private Long chunk;

    /**
     * 文件大小（如果分片了，则是分片文件大小）
     */
    @ApiModelProperty(value = "文件大小 如果分片了，则是分片文件大小", required = true)
    private Long fileSize;

    private String formData;

    @ApiModelProperty(value = "文件后缀名", required = true)
    public String getSuffix() {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
