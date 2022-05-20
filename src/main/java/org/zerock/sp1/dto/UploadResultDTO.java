package org.zerock.sp1.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UploadResultDTO {

    private String uuid;
    private String fileName;
    private String savePath;
    private boolean img;

    public UploadResultDTO(){

    }

    public String getLink(){
        return savePath+"/"+uuid+"_"+fileName;
    }
    public String getThumbnail(){
        return savePath+"/s_"+uuid+"_"+fileName;
    }

}
