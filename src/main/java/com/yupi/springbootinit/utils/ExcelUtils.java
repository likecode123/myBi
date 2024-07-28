package com.yupi.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class ExcelUtils {
    /**
     * 使用接受到的MultipartFile对象解析Excel文件，并返回解析结果csv
     */
    public static  String excelToCsv(MultipartFile multipartFile)  {
        // 解析Excel文件并生成CSV文件
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:网站数.xlsx");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("解析Excel文件失败", e);
        }

        //转换为csv
        //读取标头
        if (CollUtil.isEmpty(list)){
            return "";
        }
//        Map<Integer, String> headerMap = list.get(0);//此处不使用map  使用有序的LinkHashMap
        LinkedHashMap<Integer,String> headerMap = (LinkedHashMap) list.get(0);//此处不使用map  使用有序的LinkHashMap
        //过滤
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");

       // System.out.println(StringUtils.join(headerList, ","));
        //上面是表头  下面是数据
        for (int i = 1;i<list.size();i++){
            LinkedHashMap<Integer,String> dataMap  = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            stringBuilder.append(StringUtils.join(dataList, ",")).append("\n");
           // System.out.println(StringUtils.join(dataList, ","));
        }


       // System.out.println(list);
        return stringBuilder.toString();
    }


    public  static  void main(String[] args) throws FileNotFoundException {
        excelToCsv(null);
    }
}
