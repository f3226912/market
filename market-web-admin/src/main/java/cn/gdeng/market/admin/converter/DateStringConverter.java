package cn.gdeng.market.admin.converter;


import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateStringConverter extends DateConverterBase implements Converter<Date, String> {

    @Override
    public String convert(Date source) {
        if (source == null){
            return "";
        }
        return getDateFormat().format(source);
    }

}
