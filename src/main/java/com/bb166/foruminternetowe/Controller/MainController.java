package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Entities.Section;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
public class MainController {
    private SessionFactory sessionFactory;

    @Autowired
    public MainController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping("/{sectionid}")
    public String sectionView(@PathVariable String sectionid, Model model) {
        Session session = sessionFactory.openSession();
        Section section = (Section) session.createQuery("from Section s where s.name like '" + sectionid + "'").getResultList().get(0);
        model.addAttribute("sectionList",
                section
                        .getSectionSet()
                        .stream()
                        .collect(Collectors
                                .toList()));
        model.addAttribute("topicList",
                section
                        .getTopics()
                        .stream()
                        .collect(Collectors
                                .toList()));
        session.close();
        model.addAttribute("sectionId",sectionid);
        model.addAttribute("isMainPage",false);
        return "mainPage";
    }

    @RequestMapping("/")
    public String mainPage(Model model) {
        Session session = sessionFactory.openSession();
        model.addAttribute("sectionList",
                session.createQuery("from Section s where s.section is null").getResultList());
        session.close();
        model.addAttribute("isMainPage",true);
        return "mainPage";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "loginPage";
    }
}