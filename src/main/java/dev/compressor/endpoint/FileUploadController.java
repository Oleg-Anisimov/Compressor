package dev.compressor.endpoint;

import java.io.*;

import javax.servlet.http.HttpServletResponse;

import dev.compressor.service.LinkingService;
import org.apache.commons.io.IOUtils;
import dev.compressor.service.CompressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class FileUploadController {


    @Autowired
    private CompressionService compressionService;

    @Autowired
    private LinkingService linkingService;

    @RequestMapping(value = "/download/{key}",method = RequestMethod.GET)
    public ResponseEntity returnCompressedFile(@PathVariable(name = "key",required = true) String key){
        try {
            File file = linkingService.getByKey(key);
            String filePath = file.getAbsolutePath();
            String fileName = file.getName();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_PDF);
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            return new ResponseEntity<FileSystemResource>(new FileSystemResource(filePath), header, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public  String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                File compressedFile = compressionService.compressFile(file,name);
               String fileURL = linkingService.createLinkForFile(compressedFile);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "Ссылка: "+ fileURL;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
                return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }
    }
}