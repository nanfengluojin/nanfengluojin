package com.wzp.nflj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.wzp.nflj.config.CustomConfig;
import com.wzp.nflj.model.Admin;
import com.wzp.nflj.repository.AdminRepository;
import com.wzp.nflj.service.EasyExcelWriteService;
import com.wzp.nflj.util.FileUtil;
import com.wzp.nflj.util.excel.EasyExcelWriteUtil;
import com.wzp.nflj.vo.AdminExcelVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zp.wei
 * @date 2021/6/17 9:31
 */
@Service
public class EasyExcelWriteServiceImpl implements EasyExcelWriteService {

    @Resource
    private AdminRepository adminRepository;


    private static final String EXCEL_SAVE_PATH = CustomConfig.excelSavePath;

    /**
     * 默认每次从数据库取一万的数据
     */
    private static final Integer EXCEL_ROWS = 10000;


    /**
     * admin 用户数据导出到Excel
     *
     * @param totalNum 总量
     * @param fileName excel表名
     * @return
     */
    @Override
    public boolean adminExcelExport(Long totalNum, String fileName) {
        FileUtil.fileExist(EXCEL_SAVE_PATH);
        EasyExcelWriteUtil easyExcelUtil = new EasyExcelWriteUtil(EXCEL_SAVE_PATH);
        ExcelWriter excelWriter = null;
        try {
            excelWriter = easyExcelUtil.create(fileName, totalNum, AdminExcelVO.class);
            List<AdminExcelVO> list = new ArrayList<>();
            //根据数据总数据量和每次拿的数据量计算出需要拿几次数据
            Long number = (totalNum % EXCEL_ROWS) > 0 ? (totalNum / EXCEL_ROWS) + 1 : (totalNum / EXCEL_ROWS);
            int count = 0;
            for (int i = 0; i <= number-1; i++) {
                Page<Admin> page = adminRepository.findAll(PageRequest.of(i, EXCEL_ROWS));
                page.getContent().forEach(admin -> {
                    list.add(new AdminExcelVO(admin.getId(), admin.getUsername(), admin.getPassword()));
                });
                //count 将控制插入哪一个sheet
                count += list.size();
                easyExcelUtil.write(excelWriter, list, count);
                list.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (null != excelWriter) {
                easyExcelUtil.finish(excelWriter);
            }
        }
        return true;
    }


}
