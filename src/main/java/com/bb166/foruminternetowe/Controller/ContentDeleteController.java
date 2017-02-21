package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Entities.Message;
import com.bb166.foruminternetowe.Entities.Post;
import com.bb166.foruminternetowe.Entities.Section;
import com.bb166.foruminternetowe.Entities.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ContentDeleteController {
    private SessionFactory sessionFactory;

    @Autowired
    public ContentDeleteController(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping(value = "/deleteSection/{name}", method = RequestMethod.GET)
    public String deleteSection(@PathVariable String name){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Section section = (Section) session.createQuery("from Section s where s.name like '"+name+"'").getResultList().get(0);
        session.delete(section);
        transaction.commit();
        session.close();

        return "redirect:/"+(section.getSection() == null?"":section.getSection().getName());
    }

    @RequestMapping(value = "/deleteTopic/{title}", method =RequestMethod.GET)
    public String deleteTopic(@PathVariable String title){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Topic topic = (Topic) session.createQuery("from Topic t where t.title like '"+title+"'").getResultList().get(0);
        Section section = topic.getSection();
        section.getTopics().remove(topic);
        session.delete(topic);
        transaction.commit();
        session.close();

        return "redirect:/";
    }

    @RequestMapping(value = "/deletePost", method=RequestMethod.GET)
    public String deletePost(@RequestParam String topicName, @RequestParam int number) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Topic topic = (Topic) session.createQuery("from Topic t where t.title like '" + topicName + "'").getResultList().get(0);
        Post post = topic
                .getPostSet()
                .stream()
                .filter(p -> p.getNumber() == number)
                .findFirst()
                .get();


        for (Post post1:topic.getPostSet()){
            if (number < post1.getNumber())
                post1.setNumber(post1.getNumber()-1);

        }

        topic.getPostSet().remove(post);
        session.delete(post);
        session.update(topic);
        transaction.commit();
        session.close();

        return "redirect:/topic/"+topicName;
    }

    @RequestMapping(value = "/deleteMessage/{messageId}",method = RequestMethod.GET)
    public String deletePost(@PathVariable int messageId, Principal principal){
        Session session = sessionFactory.openSession();
        Message message = session.get(Message.class,messageId);
        if (message.getOwner().getUsername().equals(principal.getName())){
            Transaction transaction = session.beginTransaction();
            session.delete(message);
            transaction.commit();
        }
        session.close();
        return "redirect:/messages";
    }
}
