package com.bb166.foruminternetowe.Controller;


import com.bb166.foruminternetowe.Entities.Message;
import com.bb166.foruminternetowe.Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
public class MessageController {
    private SessionFactory sessionFactory;

    @Autowired
    public MessageController(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String messagesGet(Model model, Principal principal) {

        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("from User u where u.username like '" + principal.getName() + "'").getResultList().get(0);
        model.addAttribute("messageList",
                user
                        .getMessageSet()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList()));
        session.close();
        return "messageListPage";

    }
    @RequestMapping(value = "/viewMessage/{messageId}",method = RequestMethod.GET)
    public String viewMessage(@PathVariable int messageId,Principal principal,Model model){
        Session session = sessionFactory.openSession();
        Message message = session.get(Message.class, messageId);
        if (message.getOwner().getUsername().equals(principal.getName())){
            model.addAttribute("authorName",message.getAuthor());
            model.addAttribute("title",message.getTitle());
            model.addAttribute("date",message.getDate());
            model.addAttribute("text",message.getText());
            session.close();
            return "messageViewPage";
        }
        session.close();
        return "redirect:/";
    }

    @RequestMapping(value = "/sendMessage/{user}", method=RequestMethod.GET)
    public String sendMessageGet(@PathVariable String user,Model model){
        model.addAttribute("user",user);
        return "sendMessagePage";
    }

    @RequestMapping(value = "/sendMessage", method=RequestMethod.GET)
    public String sendMessage(){
        return "sendMessagePage";
    }

    @RequestMapping(value="/sendMessage", method=RequestMethod.POST)
    public String sendMessagePost(@RequestParam String userId, @RequestParam String title,
                                  @RequestParam String text, Principal principal){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.createQuery("from User u where u.username like '"+userId+"'").getResultList().get(0);
        Message message = new Message();
        message.setText(text);
        message.setOwner(user);
        message.setDate(new Date());
        message.setAuthor(principal.getName());
        message.setTitle(title);
        session.persist(message);
        transaction.commit();

        return "redirect:/messages";
    }

}
