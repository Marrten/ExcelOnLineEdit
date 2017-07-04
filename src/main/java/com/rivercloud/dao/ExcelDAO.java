package com.rivercloud.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.rivercloud.utils.ReadExcelUtils;
import org.bson.Document;

import java.io.File;
import java.util.*;

/**
 * Created by lq on 2016/12/15/0015.
 */
public class ExcelDAO {

    MongoClient client = new MongoClient("localhost:27017");
    MongoDatabase db = client.getDatabase("test");
    private  MongoCollection collection = null;

    public void insert(String filename,File file){

        if (db.getCollection(filename) ==null ){
            db.createCollection(filename);
        }
         collection = db.getCollection(filename);
        ReadExcelUtils excelReader = new ReadExcelUtils(file);
        Map<Integer, Object[]> map = null;
        try {
            map = excelReader.readExcelContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[] title = map.get(0);//得到excel的列标题

        for (int i = 0;i < map.size();i++ ){
            Document document  = new Document();
            for (int j = 0;j < title.length;j++){
                if (title[j].toString() != null){
                    document.append(title[j].toString(),map.get(i)[j]);
                }
            }
            collection.insertOne(document);
        }
    }

    public Object[] getCollectionNames(){
        List list = new ArrayList();
        MongoIterable<String> collectionNames = db.listCollectionNames();
        Iterator it = collectionNames.iterator();
        while (it.hasNext()){
            list.add(it.next());
        }
        Object[] CollectionNames = new Object[list.size()];
        for (int i = 0;i < list.size();i++){
            CollectionNames[i] = list.get(i);
        }
        return CollectionNames;
    }

    public Map find(String filename) {
        collection = db.getCollection(filename);
        FindIterable iterable = collection.find();
        Iterator it = iterable.iterator();
        Set set = null;
        List<Document> dou = new ArrayList();
        while (it.hasNext()) {
            Document document = (Document) it.next();
            set = document.keySet();
            dou.add(document);
        }
        List data = new ArrayList();
        for (Object str : set) {
            if (!"_id".equals(str)) {
                data.add(str);
            }
        }
        Object[] title = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            title[i] = data.get(i);
        }
        Map<Integer, Object[]> map = new HashMap<>();

        for (int i = 0; i < dou.size(); i++) {
            Object[] cellValue = new Object[title.length];
            for (int j = 0; j < title.length; j++) {
                //System.out.println(dou.get(i).get(title[j]));
                cellValue[j] = dou.get(i).get(title[j]);
            }
            map.put(0, title);
            map.put(i, cellValue);
        }
        return map;
    }

    public void updata(Map<Integer,String[]> map,String filename){

        db.getCollection(filename).drop();
        db.createCollection(filename);
        collection = db.getCollection(filename);
        String[] title = map.get(0);//得到excel的列标题
        for (int i = 0;i < map.size();i++ ){
            Document document  = new Document();
            for (int j = 0;j < title.length;j++){
                if (title[j].toString() != null){
                    document.append(title[j],map.get(i)[j]);
                    System.out.println(title[j].toString()+"===="+map.get(i)[j]);
                }
            }
            collection.insertOne(document);
        }

    }

    public void delete(String filename){
        collection = db.getCollection(filename);
        collection.drop();
    }

}
