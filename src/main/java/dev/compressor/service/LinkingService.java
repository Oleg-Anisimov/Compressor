package dev.compressor.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LinkingService {
   private Map<String,File> map = new HashMap<>();

   @Value("${service.URL}")
   private String serviceURL;

   private final static String URLprefix = "download/";


   public String createLinkForFile(File file){
        String key = "733";
        map.put(key,file);
        return serviceURL + URLprefix +key;
    }
    public File getByKey(String key){
       return map.get(key);
    }
}
