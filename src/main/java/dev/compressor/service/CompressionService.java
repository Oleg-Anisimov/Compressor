package dev.compressor.service;

import dev.compressor.util.Compressor;
import dev.compressor.util.SVGCompressor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class CompressionService {
    File compressFile(MultipartFile file){
        Compressor compressor = new SVGCompressor();
        try {
           byte[] result = compressor.compress(file.getBytes());
            Files.write(Path.of("/tmp/new"),result);
        } catch (IOException e) {
            log.error("Cannot read bytes from file. {}",e.getMessage());
            throw new RuntimeException(e);
        }
        return new File("/tmp/new");
    }
}
