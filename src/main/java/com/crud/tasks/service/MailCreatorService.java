package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", "Informacja o dodaniu nowego zadania");
        context.setVariable("goodbye_message", "Dziękuję za uwagę");
        context.setVariable("company_name", "Nazwa Firmy " + companyConfig.getCompanyName());
        context.setVariable("company_goal", "Nasze cele " + companyConfig.getCompanyGoal());
        context.setVariable("company_email", "Email " + companyConfig.getCompanyEmail());
        context.setVariable("company_phone", "Numer Telefonu " + companyConfig.getCompanyPhone());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}