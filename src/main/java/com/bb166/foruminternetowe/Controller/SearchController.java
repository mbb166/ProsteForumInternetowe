package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Entities.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {
    private SessionFactory sessionFactory;

    @Autowired
    public SearchController(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping(value = "/search/{tags}", method = RequestMethod.GET)
    public String searchGetVar(@PathVariable String tags, Model model) {
        String[] tagsSplit = tags.split("_");
        Session session = sessionFactory.openSession();
        Set<Topic> resutlts = new HashSet<>();

        for(String string: tagsSplit){
            @SuppressWarnings("unchecked")
            List<Topic> res = session.createQuery("from Topic t where t.title like '%"+string+"%'").getResultList();
            resutlts.addAll(res);
        }

        model.addAttribute("resultList", resutlts);
        model.addAttribute("tags", tags.replace('_',' '));
        session.close();
        return "searchPage";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchGet() {
        return "searchPage";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchPost(@RequestParam String tags) {
        return "redirect:/search/" + tags.replace(' ', '_');
    }
}