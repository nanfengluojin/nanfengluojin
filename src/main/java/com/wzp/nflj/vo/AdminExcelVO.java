package com.wzp.nflj.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * @author zp.wei
 * @date 2021/6/17 9:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 22)
public class AdminExcelVO {

    @ExcelProperty("id")
    @ColumnWidth(5)
    private Long id;

    @ExcelProperty("账号")
    @ColumnWidth(20)
    private String username;

    @ExcelProperty("密码")
    @ColumnWidth(70)
    private String password;


}
