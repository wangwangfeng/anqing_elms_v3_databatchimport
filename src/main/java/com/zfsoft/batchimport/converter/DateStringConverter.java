package com.zfsoft.batchimport.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * @author: kkfan
 * @create: 2020-03-10 15:29:13
 * @description: 日期字符串转化
 */
public class DateStringConverter implements Converter<Date> {
    private static final Logger logger = LoggerFactory.getLogger(DateStringConverter.class);

    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                  GlobalConfiguration globalConfiguration) throws ParseException {
        try {
            String tempDateStr = StringUtils.isEmpty(cellData.getStringValue()) ? new BigDecimal(cellData.toString()).toPlainString() : cellData.getStringValue();
            return DateUtils.parseDate(tempDateStr, "yyyyMMdd");
        } catch (Exception e) {
            logger.error("数据转化失败", e);
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(Date value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData(DateUtils.format(value, null));
        } else {
            return new CellData(DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }
}
