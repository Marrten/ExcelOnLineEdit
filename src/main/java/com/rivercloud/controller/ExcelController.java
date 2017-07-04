package com.rivercloud.controller;

import com.rivercloud.dao.ExcelDAO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lq on 2016/11/29/0029.
 */
@At("/excel")
@Fail("http:500")
public class ExcelController {

    @Inject
    private   ExcelDAO excelDAO = new ExcelDAO();

    @At("/upload")
    @Ok("redirect:/index.jsp")
    @AdaptBy(type = UploadAdaptor.class, args = {  })
    public void uploadExcel(HttpServletRequest request, HttpServletResponse response,@Param("file") TempFile tempFile)
            throws ServletException, IOException{
        File file = tempFile.getFile();             //保存的临时文件
        FieldMeta meta = tempFile.getMeta();        //原本的文件信息
        String filename = meta.getFileLocalName();  //文件名称
        excelDAO.insert(filename,file);
    }

    @At("/download")
    @Ok("redirect:/index.jsp")
    public void downloadExcel(HttpServletResponse response,HttpServletRequest request)
            throws IOException {
        String filename = request.getParameter("filename");
        Map<Integer,Object[]> map = excelDAO.find(filename);
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        for (int i = 0;i<map.size();i++){
                String[] value = new String[map.get(0).length];
                for (int j = 0;j<map.get(0).length;j++ ){
                        value[j] = map.get(i)[j].toString();
                    }
                Row row = workbook.getSheet("sheet1").createRow(i);//创建第一行
                for (int k = 0;k < value.length;k++ ){
                    Cell cell = row.createCell(k);
                    cell.setCellValue(value[k].trim());
                }
            }
        FileOutputStream out = new FileOutputStream("D:/"+filename);
        workbook.write(out);
        out.close();
    }

    //可编辑列表
    @At("/list")
    @Ok("jsp:jsp.list")
    @Fail("jsp:jsp.500")
    public void listExcel(HttpServletRequest request){
        Object[] fileNames = excelDAO .getCollectionNames();
        request.setAttribute("fileNames", fileNames);
    }

    //只读的列表
    @At("/scan")
    @Fail("jsp:jsp.500")
    @Ok("jsp:jsp.lists")
    public void listExcels(HttpServletRequest request) {
        Object[] fileNames = excelDAO .getCollectionNames();
        request.setAttribute("fileNames", fileNames);
    }

    //可编辑的展示
    @At("/show")
    @Ok("json")
    public Map readExcel(HttpServletRequest request){
        String filename = request.getParameter("filename");
        Map<Integer,Object[]> map = excelDAO.find(filename);
        return map;
    }

    //只读的展示
    @At("/read")
    @Ok("jsp:jsp.show")
    public void readExcels(HttpServletRequest request){
        String filename = request.getParameter("filename");
        Map<Integer,Object[]> map = excelDAO.find(filename);
        Object[] title = map.get(0);//得到Excel的列标题
        List data = new ArrayList();//得到Excel的内容
        for (int i = 0;i < map.size();i++ ){
            Object[] obj = map.get(i+1);
            data.add(obj);
        }
        request.setAttribute("title",title );
        request .setAttribute("data",data);
    }

    //更新excel到数据库中
    @At("/write")
    public void writeExcel(HttpServletRequest request){
        String json =  request.getParameter("json");
        String filename = request.getParameter("filename");
        NutMap map = Json.fromJson(NutMap.class,json);
        Map<Integer,String[]> data = new HashMap();
        for (Map.Entry entry:map.entrySet()){
            String str= entry.getValue().toString();
            String str1 = str.substring(1,str.indexOf("]"));
            String[] value = str1.split(",");
                System.out.println(entry.getKey().getClass());
                System.out.println(Integer.parseInt(entry.getKey().toString()));
            int i = Integer.parseInt(entry.getKey().toString());
            data.put(i,value);
        }
        excelDAO.updata(data,filename);
    }

    //删除excel
    @At("/delete")
    @Ok("json")
    public Map deleteExcel(HttpServletRequest request) {
        String filename = request.getParameter("filename");
        Map map = new HashMap();
        excelDAO.delete(filename);
        map.put("true",true);
        return map;
    }
}




