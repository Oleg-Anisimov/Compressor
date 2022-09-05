package dev.compressor.service;

import dev.compressor.util.Compressor;
import dev.compressor.util.SVGCompressor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
public class CompressionService {
    public File compressFile(MultipartFile file, String inputFileName){
        Compressor compressor = new SVGCompressor();
        try {
           byte[] result = compressor.compress(file.getBytes());
           String outputFileName = inputFileName +"_compressed.svg";
           Files.write(Path.of(outputFileName),result, StandardOpenOption.CREATE);
        } catch (IOException e) {
            log.error("Cannot read bytes from file. {}",e.getMessage());
            throw new RuntimeException(e);
        }
        return new File("/tmp/new");
    }
}
