package com.tripplannerai.mapper.image;

import com.tripplannerai.entity.image.Image;

public class ImageFactory {

    public static Image of(String url){
        return Image.builder()
                .url(url)
                .build();
    }
}
