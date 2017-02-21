package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Entities.Section;
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

@Controller
public class CreateSectionController {
    private SessionFactory sessionFactory;

    @Autowired
    public CreateSectionController(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping(value = "/createSection/{section}", method= RequestMethod.GET)
    public String createSectionGet(@PathVariable String section, Model model) {
        model.addAttribute("sectionId",section);
        return "createSectionsPage";
    }

    @RequestMapping(value = "/createSection", method = RequestMethod.GET)
    public String createSection(){
        return "createSectionsPage";
    }

    @RequestMapping(value = "/createSection", method = RequestMethod.POST)
    public String createSectionPost(@RequestParam String title, @RequestParam String parentSectionName){
        if (title.length() > 3) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Section section = new Section();
            section.setName(title);
            if (parentSectionName.length() != 0) {
                Section parentSection = (Section) session.createQuery("from Section s where s.name = '" + parentSectionName + "'").getResultList().get(0);
                section.setSection(parentSection);
            } else section.setSection(null);
            session.persist(section);
            transaction.commit();
            session.close();
            return "redirect:/" + parentSectionName;
        }
        return "redirect:/";
    }
}
