package org.zerock.sp1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BoardDTO {

    private Integer bno;
    private String title;
    private String content;
    private String writer;
    private int replyCount;
    //mainImage의 섬네일의 링크
    private String mainImage;

    private List<UploadResultDTO> uploads = new ArrayList<>();

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public String getMain(){
        if(mainImage == null){
            return null;
        }

        int idx = mainImage.indexOf("s_");
        String first = mainImage.substring(0, idx );
        String second = mainImage.substring(idx+2);

        return first+second;
    }
}
