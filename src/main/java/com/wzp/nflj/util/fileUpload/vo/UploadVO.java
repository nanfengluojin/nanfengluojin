package com.wzp.nflj.util.fileUpload.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("文件上传VO")
public class UploadVO implements Serializable {

    private static final long serialVersionUID = -1682840830908187239L;

    /***
     * 文件id WU_FILE_0
     *
     */
    private String id;
    /**
     * 文件名称 Beyond Compare.rar
     */
    @ApiModelProperty(value = "文件名称", required = true)
    private String name;
    /**
     * 类型 application/octet-stream
     */
    private String type;
    /**
     * 文件大小
     */
    private Long size;

    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;

    /**
     * 分片片数
     */
    @ApiModelProperty(value = "分片片数", required = true)
    private Long chunks;

    /**
     * 当前分片标识
     */
    @ApiModelProperty(value = "当前分片标识", required = true)
    private Long chunk;

    /**
     * 分片设置大小
     */
    @ApiModelProperty(value = "分片设置大小", required = true)
    private Long chunkSize;

    /**
     * 表单数据
     */
    private String formData;

    /**
     * 文件Md5(文件的唯一标识)
     */
    @ApiModelProperty(value = "文件md5值", required = true)
    private String fileMd5;

    @ApiModelProperty(value = "文件名后缀", required = true)
    public String getSuffix() {
        return name.substring(name.lastIndexOf(".") + 1);
    }
}
