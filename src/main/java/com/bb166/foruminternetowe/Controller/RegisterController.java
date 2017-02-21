package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Component.ActivateKeyGenerator;
import com.bb166.foruminternetowe.Component.CaptchaGenerator;
import com.bb166.foruminternetowe.Entities.User;
import com.bb166.foruminternetowe.Tool.Captcha;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Controller
public class RegisterController {
    private final String activate = "Aktywuj konto: <a href=\"http://localhost:8080/activateUser?username=";
    private final String key = "&key=";
    private ConcurrentHashMap<Integer, Captcha> captchaList;
    private ScheduledExecutorService executorService;
    private CaptchaGenerator captchaGenerator;
    private SessionFactory sessionFactory;
    private MailSender mailSender;
    private ActivateKeyGenerator activateKeyGenerator;
    private int n = 0;

    @Autowired
    public RegisterController(ConcurrentHashMap<Integer, Captcha> captchaList,
                              ScheduledExecutorService scheduledExecutorService,
                              CaptchaGenerator captchaGenerator,
                              SessionFactory sessionFactory,
                              MailSender mailSender,
                              ActivateKeyGenerator activateKeyGenerator) {
        this.captchaList = captchaList;
        this.executorService = scheduledExecutorService;
        this.captchaGenerator = captchaGenerator;
        this.sessionFactory = sessionFactory;
        this.mailSender = mailSender;
        this.activateKeyGenerator = activateKeyGenerator;
    }

    private class RemoveCapthaFromListThread implements Runnable {
        private int n;

        RemoveCapthaFromListThread(int n) {
            this.n = n;
        }

        public void run() {
            captchaList.remove(this.n);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public synchronized String registerControllerGet(HttpSession httpSession, Model model){
        if (captchaList.get(n)!=null)
            httpSession.setAttribute("captchaId",n);
        else {
            captchaList.put(n, captchaGenerator.generateCaptcha());
            executorService.schedule(new RemoveCapthaFromListThread(n), 3, TimeUnit.MINUTES);
            httpSession.setAttribute("captchaId", n);
        }
        model.addAttribute("captcha", captchaList.get(n).getCaptchaImage());
        n++;
        if (n>Integer.MAX_VALUE-2)
            n=0;
        return "registerPage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public synchronized String registerControllerPost(HttpSession httpSession, @RequestParam String username,
                                                      @RequestParam String email,
                                                      @RequestParam String password,
                                                      @RequestParam String captcha) {
        if (captchaList.get(httpSession.getAttribute("captchaId")) != null) {
            String errors;
            if ((errors = this.validRegisterInformation(username, email, password, captcha, (Integer) httpSession.getAttribute("captchaId"))) == null) {
                Session session = sessionFactory.openSession();

                User registiredUser = new User();
                registiredUser.setUsername(username);
                registiredUser.setPassword(password);
                registiredUser.setActive(false);
                registiredUser.setEmail(email);
                registiredUser.setRole("ROLE_USER");
                Transaction transaction = session.beginTransaction();
                session.persist(registiredUser);
                transaction.commit();
                session.close();
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

                simpleMailMessage.setSubject("Aktywacja konta");
                simpleMailMessage.setFrom("lodowy123@o2.pl");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setText(activate+username+key+activateKeyGenerator.generateActiavtionKey(username)+"\">Aktywuj</a>");
                mailSender.send(simpleMailMessage);
                return "redirect:/register?success";
            } else
                return "redirect:/register?" + errors + "username=" + username + "&email=" + email;
        }
        return "redirect:/register?sessionTimeExpired&username=" + username + "&email=" + email;
    }

    private String validRegisterInformation(String username, String email, String password, String captcha, int captchaId) {
        String validErrors = "";

        if (!captcha.equalsIgnoreCase(captchaList.get(captchaId).getCaptchString()))

            validErrors += "badCaptcha&";

        else {
            Session session = sessionFactory.openSession();
            if (session.createQuery("from User u where u.username like '" + username + "'").getResultList().size() != 0)

                validErrors += "userExist&";

            if (session.createQuery("from User u where u.email like '" + email + "'").getResultList().size() != 0)

                validErrors += "emailExist&";

            session.close();
            if (validErrors.length() == 0) {
                if (username.length() < 4)
                    validErrors += "tooShortUsername&";

                if (password.length() < 6)
                    validErrors += "tooShortPassword&";

                if (!this.isCorrectEmail(email))
                    validErrors += "badEmail&";
            }
        }
        return validErrors.length() == 0 ? null : validErrors;
    }

    private boolean isCorrectEmail(String email) {
        String[] split = email.split("@");
        if (split.length == 2) {
            if (split[1].split(".").length != 2)
                return true;
        }
        return false;
    }
}