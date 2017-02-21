package com.bb166.foruminternetowe.Tool;

public final class Captcha {
    private final String captchaImage;
    private final String captchString;

    public Captcha(String captchaImage, String captchString) {
        this.captchaImage = captchaImage;
        this.captchString = captchString;
    }

    public String getCaptchaImage() {
        return captchaImage;
    }

    public String getCaptchString() {
        return captchString;
    }
}
