package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailCreatorService {
    private final AdminConfig adminConfig;
    private final CompanyConfig companyConfig;
    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;
    private final TaskRepository taskRepository;

    @Autowired
    public MailCreatorService(AdminConfig adminConfig, CompanyConfig companyConfig, TemplateEngine templateEngine, TaskRepository taskRepository) {
        this.adminConfig = adminConfig;
        this.companyConfig = companyConfig;
        this.templateEngine = templateEngine;
        this.taskRepository = taskRepository;
    }

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://miloszsierakowski.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("preview_message", "Informacja o dodaniu nowego zadania");
        context.setVariable("goodbye_message", "Dziękuję za uwagę");
        context.setVariable("company_name", "Nazwa Firmy " + companyConfig.getCompanyName());
        context.setVariable("company_goal", "Nasze cele " + companyConfig.getCompanyGoal());
        context.setVariable("company_email", "Email " + companyConfig.getCompanyEmail());
        context.setVariable("company_phone", "Numer Telefonu " + companyConfig.getCompanyPhone());
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
    public String buildSecondTrelloCardEmail(String message) {

        List<String> functionality = taskRepository.findAll().stream().map(Task::getContent).collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://miloszsierakowski.github.io/");
        context.setVariable("button", "Visit our website");
        context.setVariable("gender", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("preview_message", "Aktualnie dostępne zadania");
        context.setVariable("goodbye_message", "Do usłyszenia");
        context.setVariable("company_name", "Nazwa Firmy " + companyConfig.getCompanyName());
        context.setVariable("company_goal", "Nasze cele " + companyConfig.getCompanyGoal());
        context.setVariable("company_email", "Email " + companyConfig.getCompanyEmail());
        context.setVariable("company_phone", "Numer Telefonu " + companyConfig.getCompanyPhone());
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-second-trello-card-mail", context);
    }



}