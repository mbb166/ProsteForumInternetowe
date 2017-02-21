package com.bb166.foruminternetowe.Component;

import com.bb166.foruminternetowe.Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@Component
public class ActivateKeyGenerator {
    private SessionFactory sessionFactory;

    @Autowired
    public ActivateKeyGenerator(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String generateActiavtionKey(String nickname) {
        Session session = sessionFactory.openSession();
        String md5code = null;
        @SuppressWarnings("unchecked")
        List<User> list = session.createQuery("from User u where u.username like '" + nickname + "'").getResultList();
        if (list != null) {
            User user = list.get(0);
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(Integer.toString(user.hashCode()).getBytes(Charset.forName("UTF8")));
                byte[] resultByte = messageDigest.digest();
                md5code = new BigInteger(1,resultByte).toString(16);
                return md5code;
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
        return md5code;
    }
}