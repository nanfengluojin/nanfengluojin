package com.wzp.nflj.service;

/**
 * @author zp.wei
 * @date 2021/6/17 9:30
 */

public interface EasyExcelWriteService {

    /**
     * 将表数据导出为excel
     *
     * @param total    数量
     * @param fileName excel表名
     * @return 返回值 true false
     */
    boolean adminExcelExport(Long total, String fileName);

}
