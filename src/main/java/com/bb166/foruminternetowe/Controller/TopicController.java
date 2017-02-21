package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Entities.Post;
import com.bb166.foruminternetowe.Entities.Section;
import com.bb166.foruminternetowe.Entities.Topic;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

@Controller
public class TopicController {
    SessionFactory sessionFactory;

    @Autowired
    public TopicController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping(value = "/createTopic/{section}", method = RequestMethod.GET)
    public String createTopicGet(@PathVariable String section, Model model){
        model.addAttribute("section", section);
        return "createTopicPage";
    }

    @RequestMapping(value = "/createTopic",  method = RequestMethod.POST)
    public String createTopicPost(@RequestParam String title, @RequestParam String sectionName,
                                  @RequestParam String text, Principal principal){
        Session session = sessionFactory.openSession();
        Topic topic = new Topic();
        topic.setTitle(title);
        Transaction transaction = session.beginTransaction();
        topic.setSection((Section) session.createQuery("from Section s where s.name like '"+sectionName+"'").getResultList().get(0));
        User author = (User) session.createQuery("from User u where u.username like '"+principal.getName()+"'").getResultList().get(0);
        topic.setAuthor(author);
        Post post = new Post();
        post.setAuthor(author);
        post.setDate(new Date());
        post.setText(text);
        post.setNumber(0);
        post.setTopic(topic);
        session.persist(topic);
        session.persist(post);
        transaction.commit();
        session.close();
        return "redirect:/topic/"+topic.getTitle();
    }
    @RequestMapping(value = "/editPost", method = RequestMethod.GET)
    public String editTopicGet(@RequestParam String topicName,@RequestParam int number,
                               Principal principal,HttpServletRequest httpServletRequest,
                               Model model) {
        Session session = sessionFactory.openSession();
        Topic topic = (Topic) session.createQuery("from Topic t where t.title like '" + topicName + "'").getResultList().get(0);
        Post post = topic
                .getPostSet()
                .stream()
                .filter(p -> p.getNumber() == number)
                .findFirst()
                .get();

        if (principal.getName().equals(post.getAuthorName()) || httpServletRequest.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("topicName", topicName);
            model.addAttribute("number", number);
            model.addAttribute("text",post.getText());
            session.close();
            return "editPostPage";
        }
        session.close();
        return "redirect:/topic/"+topicName;
    }

    @RequestMapping(value="/editPost", method = RequestMethod.POST)
    public String editTopicPost(@RequestParam String topicName,@RequestParam int number,
                                @RequestParam String text, HttpServletRequest httpServletRequest,
                                Principal principal){

        Session session = sessionFactory.openSession();
        Topic topic = (Topic) session.createQuery("from Topic t where t.title like '" + topicName + "'").getResultList().get(0);
        Post post = topic
                .getPostSet()
                .stream()
                .filter(p -> p.getNumber() == number)
                .findFirst()
                .get();
        if (principal.getName().equals(post.getAuthorName()) || httpServletRequest.isUserInRole("ROLE_ADMIN")) {
            Transaction transaction = session.beginTransaction();
            post.setText(text);
            session.update(post);
            transaction.commit();
        }
        session.close();
        return "redirect:/topic/"+topicName;
    }
}
