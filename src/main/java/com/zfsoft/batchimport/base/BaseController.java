package com.zfsoft.batchimport.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kkfan
 * @apiNote baseController
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        final String[] dateFormat = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyyMMddHHmmss"};
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (StringUtils.isNotBlank(text)) {
                    try {
                        setValue(DateUtils.parseDate(text, dateFormat));
                    } catch (ParseException e) {
                        logger.warn("转换时间格式错误，转换前的时间文本为：{}", text);
                    }
                }
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? sdf.format(value) : "";
            }
        });
    }
}
