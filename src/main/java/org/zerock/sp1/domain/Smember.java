package org.zerock.sp1.domain;

import lombok.*;
import org.zerock.sp1.domain.Sauth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Smember {

    private String mid,mpw,mname,nickname;

    private LocalDateTime regDate,updateDate;

    private List<Sauth> sauthList;

}





