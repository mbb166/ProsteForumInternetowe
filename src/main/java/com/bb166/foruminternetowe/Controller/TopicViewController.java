package com.bb166.foruminternetowe.Controller;


import com.bb166.foruminternetowe.Entities.Post;
import com.bb166.foruminternetowe.Entities.Topic;
import com.bb166.foruminternetowe.Entities.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
public class TopicViewController {
    SessionFactory sessionFactory;

    @Autowired
    public TopicViewController(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping(value = "/topic/{topicid}", method = RequestMethod.GET)
    public String topicViewGet(@PathVariable String topicid, Model model,Principal principal){
        Session session = sessionFactory.openSession();
        Topic topic = (Topic) session.createQuery("from Topic t where t.title like '"+topicid+"'").getResultList().get(0);
        model.addAttribute("postList", topic
                .getPostSet()
                .stream()
                .sorted()
                .collect(Collectors
                        .toList()));
        model.addAttribute("topic",topicid);
        model.addAttribute("loggedUserName",principal!=null?principal.getName():"");
        session.close();
        return "topicViewPage";
    }
    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public String topicViewPost(@RequestParam String topicName, @RequestParam String text, Principal principal){
        Session session = sessionFactory.openSession();
        Topic topic = (Topic) session.createQuery("from Topic t where t.title like '"+topicName+"'").getResultList().get(0);
        User author = (User) session.createQuery("from User u where u.username like '"+principal.getName()+"'").getResultList().get(0);
        Post post = new Post();
        post.setDate(new Date());
        post.setAuthor(author);
        post.setTopic(topic);
        post.setNumber(topic.getPostSize());
        post.setText(text);
        Transaction transaction = session.beginTransaction();
        session.persist(post);
        transaction.commit();
        session.close();

        return "redirect:/topic/"+topicName;
    }
}
