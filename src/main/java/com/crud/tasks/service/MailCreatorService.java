package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class MailCreatorService {

    private static final String MAIL_BUILD = "build";
    private static final String MAIL_INFO = "info";

    @Autowired
    private DbService dbService;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private ITemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://kaminskimarcin.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye","Best regards!" );
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("preview_message", "Trello Card Created" );
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String informUserAboutNumberOfTasks(String message) {
        int tasksQuantity = dbService.getAllTasks().size();

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://kaminskimarcin.github.io/");
        context.setVariable("quantity", tasksQuantity);
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye","Best regards!" );
        context.setVariable("preview_message", "Tasks Status" );
        return templateEngine.process("mail/information-tasks-quantity-mail", context);
    }

    public MimeMessagePreparator createMimeMessage(final Mail mail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo((mail.getMailTo()));
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(chooseMailType(mail), true);
        };
        return mimeMessagePreparator;
    }

    private String chooseMailType(final Mail mail) {
        if(Objects.equals(mail.getMailTo(), MAIL_BUILD)) {
            return buildTrelloCardEmail(mail.getMessage());
        }else if(Objects.equals(mail.getMailType(), MAIL_INFO)) {
            return informUserAboutNumberOfTasks(mail.getMessage());
        }else {
            return "not applicable";
        }
    }
}