package com.wzp.nflj.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.wzp.nflj.config.SpringContextUtil;
import com.wzp.nflj.model.Admin;
import com.wzp.nflj.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/23 9:41
 */
@Slf4j
public class EasyExcelReadService extends AnalysisEventListener<Admin> {

    /**
     * 每隔3000条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;


    List<Admin> list = new ArrayList<>();


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param admin
     * @param context
     */
    @Override
    public void invoke(Admin admin, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(admin));
        list.add(admin);
        if (list.size() >= BATCH_COUNT) {
            saveData();
        }
    }


    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }


    /**
     * 存储数据到数据库
     */
    private void saveData() {
        log.info("共有{}条数据，开始存储数据库！", list.size());
        AdminRepository adminRepository = SpringContextUtil.getBean(AdminRepository.class);
        //for 循环组装数据然后存储数据库
        adminRepository.saveAll(list);
        list.clear();
        log.info("存储数据库成功！");
    }


}
