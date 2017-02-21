package com.bb166.foruminternetowe.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@Configuration
public class CaptchaGeneratorConfiguration {
    @Bean
    public Random random(){
        return new Random();
    }

    @Bean
    public BufferedImage bufferedImage() {
        return  new BufferedImage(100,23,BufferedImage.TYPE_INT_ARGB);
    }

    @Bean
    public Font font() {
        return new Font(Font.MONOSPACED,Font.BOLD,20);
    }

    @Bean
    public ByteArrayOutputStream byteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }
}
