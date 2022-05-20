package org.zerock.sp1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.sp1.dto.UploadResultDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UploadController {


    @GetMapping("/view")
    public ResponseEntity<byte[]> viewFile(String fileName){
        log.info("============================");
        log.info("fileName.........." + fileName);

        File targetFile = new File("C:\\upload\\"+ fileName);

        log.info(targetFile);

        try {
            String mimeType = Files.probeContentType(targetFile.toPath());

            log.info("========================");
            log.info(mimeType);

            byte[] data = FileCopyUtils.copyToByteArray(targetFile);

            return ResponseEntity.ok().header("Content-Type",mimeType)
                    .body(data);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }


    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> deleteFile(String fileName){

        int idx = fileName.lastIndexOf("/");
        String path = fileName.substring(0,idx);
        String name = fileName.substring(idx+1); //uuid_fileName
        String uuid = name.substring(0,name.indexOf("_"));

        log.info("path: "+ path);
        log.info("name: " + name);


        File targetFile = new File("C:\\upload\\" + fileName);

        boolean result = targetFile.delete();

        //thumbnail
        if(result){
            File thumbFile = new File("C:\\upload\\"+path+"\\s_"+name);
            thumbFile.delete();
        }


        return Map.of("result", "success");
    }

    @PostMapping("/upload1")
    @ResponseBody
    public List<UploadResultDTO> upload1(MultipartFile[] files){

        log.info("=========================");

        log.info(files);

        List<UploadResultDTO> list = new ArrayList<>();

        //업로드된 파일이 있다고 가정
        for (MultipartFile file:files) {

            String originalFileName = file.getOriginalFilename();

            boolean img = file.getContentType().startsWith("image");

            String uuid = UUID.randomUUID().toString();

            String saveName = uuid +"_"+ originalFileName;

            log.info(file.getResource());
            String saveFolder = makeFolders();

            File saveFile = new File("C:\\upload\\" +saveFolder+"\\"+saveName);

            try (InputStream in = file.getInputStream();
                 FileOutputStream fos = new FileOutputStream(saveFile);
            ){
                FileCopyUtils.copy(in, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(img){
                //saveName = UUID+"_"+fileName
                String thumbFileName = saveFolder+"\\s_"+saveName;
                File thumbFile = new File("C:\\upload\\" +thumbFileName);

                try {
                    Thumbnails.of(saveFile)
                            .size(200,200)
                            .toFile(thumbFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            UploadResultDTO uploadResultDTO = UploadResultDTO.builder()
                    .fileName(originalFileName)
                    .uuid(uuid)
                    .img(img)
                    .savePath(saveFolder)
                    .build();


            list.add(uploadResultDTO);


            log.info("--------------------------");

        }//end for

        return list;
    }


    private String makeFolders(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        String str = sdf.format(new java.util.Date());

        File folderPath = new File("C:\\upload\\" + str);

        if(!folderPath.exists()){
            folderPath.mkdirs();
        }

        return str;

    }

}
