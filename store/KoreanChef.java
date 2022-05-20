package org.zerock.sp1.store;

import org.springframework.stereotype.Repository;

@Repository
public class KoreanChef implements Chef{
    
    @Override
    public String cook() {
        return "불고기";
    }
}
