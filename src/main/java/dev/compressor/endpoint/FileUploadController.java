package dev.compressor.endpoint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import dev.compressor.service.CompressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class FileUploadController {


    @Autowired
    private CompressionService compressionService;



    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public  String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                compressionService.compressFile(file,name);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
                return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }
    }

}