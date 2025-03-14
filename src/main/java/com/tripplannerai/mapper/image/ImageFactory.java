package com.tripplannerai.mapper.image;

import com.tripplannerai.dto.request.member.SignUpRequest;
import com.tripplannerai.entity.image.Image;
import com.tripplannerai.entity.member.Member;

public class ImageFactory {

    public static Image of(String url){
        return Image.builder()
                .url(url)
                .build();
    }
}
