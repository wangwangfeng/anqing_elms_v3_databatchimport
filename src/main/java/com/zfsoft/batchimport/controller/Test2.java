package com.zfsoft.batchimport.controller;

import com.zfsoft.batchimport.service.AsynAttaFileService;
import com.zfsoft.batchimport.service.CommonService;
import com.zfsoft.batchimport.service.impl.AsynTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * @author: kkfan
 * @create: 2020-02-26 12:49:20
 * @description:
 */
@Controller
public class Test2 {

    @Autowired
    private CommonService commonService;

    @Autowired
    private AsynTaskServiceImpl asynTaskService;

    @Autowired
    private AsynAttaFileService asynAttaFileService;

    @RequestMapping("/test1")
    @ResponseBody
    public String test1(String downloadFilePath) {
        File file = new File(downloadFilePath);
        if (!file.exists()) {
            return "文件不存在";
        }
        return "success";
    }


    @ResponseBody
    @RequestMapping("/test")
    public String test() throws Exception{
        asynAttaFileService.test();
        return "success";
    }

//    @RequestMapping("/test2")
//    @ResponseBody
//    public String test2() {
//        String fileId = "2c287ca770c1fb450170c2d7388a003b";
//        InputStream ins = null;
//        try {
//            ReadSheet sheet = new ReadSheet(1);
////            ins = FileInterfaceUtil.downLoadFile(fileId);
//            File file = new File("D:\\Excel-超限运输车辆通行证证照导入模板_1000.xls");
//            ins = new FileInputStream(file);
//            CommonCreateTableDto commonCreateTableDto = createTableFiled();
////            EasyExcel.read(ins, new VehiclePermitDataListener(commonService, commonCreateTableDto)).sheet().doRead();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "success";
//    }
//
//    @RequestMapping("/test3")
//    @ResponseBody
//    public String test3() {
//        String fileId = "2c287ca770c1fb450170c2d7388a003b";
//        InputStream ins = null;
//        try {
////            ins = FileInterfaceUtil.downLoadFile(fileId);
//            File file = new File("D:\\Excel-超限运输车辆通行证证照导入模板_10000.xls");
//            ins = new FileInputStream(file);
//            CommonCreateTableDto commonCreateTableDto = this.createTableFiled();
//            EasyExcel.read(ins, new VehiclePermitDataListener(commonService, asynTaskService, "4028820d70c7f3c70170c80fd723000e", commonCreateTableDto)).readCacheSelector(new SimpleReadCacheSelector(1, 100)).sheet().doRead();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "success";
//    }
//
//    @RequestMapping("/test4")
//    @ResponseBody
//    public String test4() {
////        this.vehiclePermitService.test();
//        System.out.println(this.commonService.existTable("t_vehicle_permit"));
//        System.out.println(this.commonService.existTable("t_vehicle_permit1"));
//        return "success";
//    }
//
//    private static CommonCreateTableDto createTableFiled() {
//        CommonCreateTableDto commonCreateTableDto = new CommonCreateTableDto();
//        commonCreateTableDto.setTableName("t_vehicle_permit");
//        commonCreateTableDto.setTableDesc("excel数据批量导入管理");
//        List<CommonTableFieldDto> commonTableFieldDtoList = Lists.newArrayList();
//        CommonTableFieldDto commonTableFieldDto = new CommonTableFieldDto();
//        commonTableFieldDto.setFieldName("证照编号");
//        commonTableFieldDto.setFieldCode("license_code");
//        commonTableFieldDto.setFieldLength("10");
//        commonTableFieldDto.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(commonTableFieldDto);
//        CommonTableFieldDto commonTableFieldDto1 = new CommonTableFieldDto();
//        commonTableFieldDto1.setFieldName("持证主体名称");
//        commonTableFieldDto1.setFieldCode("license_name");
//        commonTableFieldDto1.setFieldLength("64");
//        commonTableFieldDto1.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(commonTableFieldDto1);
//
//        CommonTableFieldDto commonTableFieldDto2 = new CommonTableFieldDto();
//        commonTableFieldDto2.setFieldName("证照等级");
//        commonTableFieldDto2.setFieldCode("license_grade");
//        commonTableFieldDto2.setFieldLength("1");
//        commonTableFieldDto2.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(commonTableFieldDto2);
//
//        CommonTableFieldDto c3 = new CommonTableFieldDto();
//        c3.setFieldName("联系电话");
//        c3.setFieldCode("contact_number");
//        c3.setFieldLength("11");
//        c3.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c3);
//
//        CommonTableFieldDto c4 = new CommonTableFieldDto();
//        c4.setFieldName("持有者代码类型");
//        c4.setFieldCode("certificate_type");
//        c4.setFieldLength("40");
//        c4.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c4);
//
//        CommonTableFieldDto c5 = new CommonTableFieldDto();
//        c5.setFieldName("持证人证件号码");
//        c5.setFieldCode("id_card_number");
//        c5.setFieldLength("18");
//        c5.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c5);
//
//        CommonTableFieldDto c6 = new CommonTableFieldDto();
//        c6.setFieldName("审核日期");
//        c6.setFieldCode("review_date");
//        c6.setFieldLength("25");
//        c6.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c6);
//
//        CommonTableFieldDto c7 = new CommonTableFieldDto();
//        c7.setFieldName("有效期范围");
//        c7.setFieldCode("validity_range");
//        c7.setFieldLength("25");
//        c7.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c7);
//
//        CommonTableFieldDto c8 = new CommonTableFieldDto();
//        c8.setFieldName("有效期起");
//        c8.setFieldCode("validity_begin");
//        c8.setFieldLength(null);
//        c8.setFieldType("datetime");
//        commonTableFieldDtoList.add(c8);
//
//        CommonTableFieldDto c9 = new CommonTableFieldDto();
//        c9.setFieldName("有效期止");
//        c9.setFieldCode("validity_end");
//        c9.setFieldLength(null);
//        c9.setFieldType("datetime");
//        commonTableFieldDtoList.add(c9);
//
//        CommonTableFieldDto c10 = new CommonTableFieldDto();
//        c10.setFieldName("是否需要年检");
//        c10.setFieldCode("need_annual_inspection");
//        c10.setFieldLength("25");
//        c10.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c10);
//
//        CommonTableFieldDto c11 = new CommonTableFieldDto();
//        c11.setFieldName("提前年检天数");
//        c11.setFieldCode("annual_inspection_begin_days");
//        c11.setFieldLength("25");
//        c11.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c11);
//
//        CommonTableFieldDto c12 = new CommonTableFieldDto();
//        c12.setFieldName("超期是否自动注销");
//        c12.setFieldCode("expire_auto");
//        c12.setFieldLength("20");
//        c12.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c12);
//
//        CommonTableFieldDto c13 = new CommonTableFieldDto();
//        c13.setFieldName("超期自动注销天数");
//        c13.setFieldCode("expire_automatically_days");
//        c13.setFieldLength("11");
//        c13.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c13);
//
//        CommonTableFieldDto c14 = new CommonTableFieldDto();
//        c14.setFieldName("水印内容");
//        c14.setFieldCode("watermark_content");
//        c14.setFieldLength("40");
//        c14.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c14);
//
//        CommonTableFieldDto c15 = new CommonTableFieldDto();
//        c15.setFieldName("许可证号");
//        c15.setFieldCode("permit_number");
//        c15.setFieldLength("20");
//        c15.setFieldType("VARCHAR");
//        commonTableFieldDtoList.add(c15);
//
//        CommonTableFieldDto c16 = new CommonTableFieldDto();
//        c16.setFieldName("有效范围");
//        c16.setFieldCode("permit_range");
//        c16.setFieldLength(null);
//        c16.setFieldType("datetime");
//        commonTableFieldDtoList.add(c16);
//
//        CommonTableFieldDto c17 = new CommonTableFieldDto();
//        c17.setFieldName("通行日期");
//        c17.setFieldCode("pass_date");
//        c17.setFieldLength(null);
//        c17.setFieldType("datetime");
//        commonTableFieldDtoList.add(c17);
//
//        CommonTableFieldDto c18 = new CommonTableFieldDto();
//        c18.setFieldName("通行次数");
//        c18.setFieldCode("pass_number");
//        c18.setFieldLength("11");
//        c18.setFieldType("int");
//        commonTableFieldDtoList.add(c18);
//
//        CommonTableFieldDto c19 = new CommonTableFieldDto();
//        c19.setFieldName("承运单位");
//        c19.setFieldCode("transport_unit");
//        c19.setFieldLength("50");
//        c19.setFieldType("varchar");
//        commonTableFieldDtoList.add(c19);
//
//        CommonTableFieldDto c20 = new CommonTableFieldDto();
//        c20.setFieldName("牵引车车号牌");
//        c20.setFieldCode("tractor_number");
//        c20.setFieldLength("20");
//        c20.setFieldType("varchar");
//        commonTableFieldDtoList.add(c20);
//
//        CommonTableFieldDto c21 = new CommonTableFieldDto();
//        c21.setFieldName("挂车车号牌");
//        c21.setFieldCode("trailer_number");
//        c21.setFieldLength("20");
//        c21.setFieldType("varchar");
//        commonTableFieldDtoList.add(c21);
//
//        CommonTableFieldDto c22 = new CommonTableFieldDto();
//        c22.setFieldName("货物名称");
//        c22.setFieldCode("goods_name");
//        c22.setFieldLength("50");
//        c22.setFieldType("varchar");
//        commonTableFieldDtoList.add(c22);
//
//        CommonTableFieldDto c23 = new CommonTableFieldDto();
//        c23.setFieldName("货物质量");
//        c23.setFieldCode("goods_weight");
//        c23.setFieldLength("10, 0");
//        c23.setFieldType("decimal");
//        commonTableFieldDtoList.add(c23);
//
//        CommonTableFieldDto c24 = new CommonTableFieldDto();
//        c24.setFieldName("整备质量");
//        c24.setFieldCode("curb_weight");
//        c24.setFieldLength("10, 0");
//        c24.setFieldType("decimal");
//        commonTableFieldDtoList.add(c24);
//
//        CommonTableFieldDto c25 = new CommonTableFieldDto();
//        c25.setFieldName("车货总质量");
//        c25.setFieldCode("cargo_tital_quality");
//        c25.setFieldLength("10, 0");
//        c25.setFieldType("decimal");
//        commonTableFieldDtoList.add(c25);
//
//        CommonTableFieldDto c26 = new CommonTableFieldDto();
//        c26.setFieldName("轴荷分布");
//        c26.setFieldCode("load_distribution");
//        c26.setFieldLength("10, 0");
//        c26.setFieldType("decimal");
//        commonTableFieldDtoList.add(c26);
//
//        CommonTableFieldDto c27 = new CommonTableFieldDto();
//        c27.setFieldName("长");
//        c27.setFieldCode("length");
//        c27.setFieldLength("10, 0");
//        c27.setFieldType("decimal");
//        commonTableFieldDtoList.add(c27);
//
//        CommonTableFieldDto c28 = new CommonTableFieldDto();
//        c28.setFieldName("宽");
//        c28.setFieldCode("width");
//        c28.setFieldLength("10, 0");
//        c28.setFieldType("decimal");
//        commonTableFieldDtoList.add(c28);
//
//        CommonTableFieldDto c29 = new CommonTableFieldDto();
//        c29.setFieldName("高");
//        c29.setFieldCode("height");
//        c29.setFieldLength("10, 0");
//        c29.setFieldType("decimal");
//        commonTableFieldDtoList.add(c29);
//
//        CommonTableFieldDto c30 = new CommonTableFieldDto();
//        c30.setFieldName("通行线路");
//        c30.setFieldCode("traffic_routes");
//        c30.setFieldLength("50");
//        c30.setFieldType("varchar");
//        commonTableFieldDtoList.add(c30);
//
//        CommonTableFieldDto c31 = new CommonTableFieldDto();
//        c31.setFieldName("备注");
//        c31.setFieldCode("note");
//        c31.setFieldLength(null);
//        c31.setFieldType("blob");
//        commonTableFieldDtoList.add(c31);
//
//        CommonTableFieldDto c32 = new CommonTableFieldDto();
//        c32.setFieldName("发证单位");
//        c32.setFieldCode("issuing_unit");
//        c32.setFieldLength("50");
//        c32.setFieldType("varchar");
//        commonTableFieldDtoList.add(c32);
//
//        CommonTableFieldDto c33 = new CommonTableFieldDto();
//        c33.setFieldName("签发人");
//        c33.setFieldCode("signatory");
//        c33.setFieldLength("50");
//        c33.setFieldType("varchar");
//        commonTableFieldDtoList.add(c33);
//
//        CommonTableFieldDto c34 = new CommonTableFieldDto();
//        c34.setFieldName("发证日期");
//        c34.setFieldCode("issuing_date");
//        c34.setFieldLength(null);
//        c34.setFieldType("datetime");
//        commonTableFieldDtoList.add(c34);
//
//        CommonTableFieldDto c35 = new CommonTableFieldDto();
//        c35.setFieldName("打印日期");
//        c35.setFieldCode("print_time");
//        c35.setFieldLength(null);
//        c35.setFieldType("datetime");
//        commonTableFieldDtoList.add(c35);
//
//        CommonTableFieldDto c36 = new CommonTableFieldDto();
//        c36.setFieldName("修改时间");
//        c36.setFieldCode("MODIFY_DATE");
//        c36.setFieldLength(null);
//        c36.setFieldType("datetime");
//        commonTableFieldDtoList.add(c36);
//        commonCreateTableDto.setCommonTableFieldDtoList(commonTableFieldDtoList);
//
//        return commonCreateTableDto;
//    }
//
////    public static void main(String[] args) {
////        String fileId = "2c287ca770c1fb450170c2d7388a003b";
////        InputStream ins = null;
////        try {
////            // File file = new File("D:\\Excel-超限运输车辆通行证证照导入模板_2000.xls");
////            File file = new File("D:\\1.xlsx");
////            ins = new FileInputStream(file);
////            Long beginTime = System.currentTimeMillis();
////            CommonCreateTableDto commonCreateTableDto = createTableFiled();
////            // List<LinkedHashMap<Integer, Object>> objects = EasyExcel.read(ins).sheet().doReadSync();
////            List<List<String>> head = Lists.newArrayList();
////            head.add(Lists.newArrayList("持证主体名称1"));
////            // List<LinkedHashMap<Integer, Object>> objects = EasyExcel.read(ins).sheet().head(head).doReadSync();
////            ReadSheet objects = EasyExcel.read(ins).sheet().build();
////            objects.getHead();
////            System.out.println("用时: " + (System.currentTimeMillis() - beginTime));
////            System.out.println("1");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    public static void main(String[] args) {
////        InputStream ins = null;
////        try {
////            // File file = new File("D:\\Excel-超限运输车辆通行证证照导入模板_2000.xls");
////            File file = new File("D:\\1.xlsx");
////            ins = new FileInputStream(file);
////            Long beginTime = System.currentTimeMillis();
////            ExcelReader excelReader = EasyExcel.read(ins).build();
////            List<List<String>> head = Lists.newArrayList();
////            head.add(Lists.newArrayList("持证主体名称1"));
////            ReadSheet readSheet1 =
////                    EasyExcel.readSheet(0).head(head).build();
////            excelReader.read(readSheet1);
////            excelReader.finish();
////            System.out.println("用时: " + (System.currentTimeMillis() - beginTime));
////            System.out.println("1");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//        System.out.println(createOrderId());
//    }
//
//    public static String createOrderId() {
//        int machineId = 1;//最大支持1-9个集群机器部署
//        int hashCodeV = UUID.randomUUID().toString().hashCode();
//        if(hashCodeV < 0) {//有可能是负数
//            hashCodeV = - hashCodeV;
//        }
//        // 0 代表前面补充0
//        // 4 代表长度为4
//        // d 代表参数为正数型
//        return machineId+String.format("%015d", hashCodeV);
//    }
}



