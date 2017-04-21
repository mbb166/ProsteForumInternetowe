package com.bb166.foruminternetowe.Component;

import com.bb166.foruminternetowe.Tool.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Component
public class CaptchaGenerator {
    private Random random;
    private BufferedImage bufferedImage;
    private Font font;
    private ByteArrayOutputStream byteArrayOutputStream;
    @Autowired
    public CaptchaGenerator(Random random,
                            BufferedImage bufferedImage,
                            Font font,
                            ByteArrayOutputStream byteArrayOutputStream){
        this.random = random;
        this.bufferedImage = bufferedImage;
        this.font = font;
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public Captcha generateCaptcha(){
        String captchaString = this.generateCaptchaCode();
        return new Captcha(this.generateCaptchaImage(captchaString),captchaString);
    }

    private String generateCaptchaImage(String captchaCode){
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,
                bufferedImage.getWidth(),
                bufferedImage.getHeight());
        graphics.setFont(font);
        graphics.setColor(Color.black);
        graphics.drawString(captchaCode,12,22);
        byteArrayOutputStream.reset();
        try {
            ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    private String generateCaptchaCode(){
        return Integer.toString(random.nextInt(983039)+65536,16);
    }
}
