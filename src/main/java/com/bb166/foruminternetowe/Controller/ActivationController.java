package com.bb166.foruminternetowe.Controller;

import com.bb166.foruminternetowe.Component.ActivateKeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class ActivationController {
    private ActivateKeyGenerator activateKeyGenerator;
    private DriverManagerDataSource driverManagerDataSource;

    @Autowired
    public ActivationController(ActivateKeyGenerator activateKeyGenerator,
                                DriverManagerDataSource driverManagerDataSource) {
        this.activateKeyGenerator = activateKeyGenerator;
        this.driverManagerDataSource = driverManagerDataSource;
    }

    @RequestMapping( value = "/activateUser",method = RequestMethod.GET)
    public synchronized String activate(@RequestParam String username, @RequestParam String key) {
        if (username != null && key != null) {
            try {
                try (PreparedStatement preparedStatement =
                             driverManagerDataSource.getConnection().prepareStatement("SELECT active from user where username =?")){
                    preparedStatement.setString(1,username);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.getBoolean(0)) {
                        rs.close();
                        return "redirect:/login?activateError";
                    }
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String requestKey = activateKeyGenerator.generateActiavtionKey(username);
            if (requestKey.equals(key)) {
                try {
                    try (PreparedStatement preparedStatement =
                            driverManagerDataSource.getConnection().prepareStatement("UPDATE user set active = true where username = ?")) {
                        preparedStatement.setString(1, username);
                        preparedStatement.execute();
                    }
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
                return "redirect:/login?activateSuccess";
            }
        }
        return "redirect:/login?activateError";
    }
}
