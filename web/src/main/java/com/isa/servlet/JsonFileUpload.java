package com.isa.servlet;

import com.isa.auth.UserAuthenticationService;
import com.isa.config.TemplateProvider;
import com.isa.domain.entity.UserType;
import com.isa.service.FileUploadProcessor;
import com.isa.service.domain.EventService;
import com.isa.service.domain.OrganizersService;
import com.isa.service.domain.PlaceService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
@WebServlet ("/admin/upload")
public class JsonFileUpload extends HttpServlet {

    @Inject
    private FileUploadProcessor fileUploadProcessor;

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private EventService eventService;

    @Inject
    private OrganizersService organizersService;

    @Inject
    private PlaceService placeService;

    @Inject
    UserAuthenticationService userAuthenticationService;


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("Session id: " + req.getSession().getId());
        setEncoding(req, resp);

        Template template = templateProvider.getTemplate(getServletContext(), "json_file_uploader.ftlh");
        Map<String, Object> model = new HashMap<>();

        final String googleId = (String) req.getSession().getAttribute("googleId");
        final String googleEmail = (String) req.getSession().getAttribute("googleEmail");
        final UserType userType = (UserType) req.getSession().getAttribute("userType");
        logger.info("Google email set to {}", googleEmail);

        if (googleId != null && !googleId.isEmpty()) {
            model.put("logged", "yes");
            model.put("googleEmail", googleEmail);
            model.put("userType", userType);
        } else {
            model.put("logged", "no");
            model.put("loginUrl", userAuthenticationService.buildLoginUrl());
        }

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            logger.error(e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, UnsupportedEncodingException {

        setEncoding(req, resp);

        logger.info("Session id: " + req.getSession().getId());
        logger.info("doPost invoked");

        PrintWriter writer = null;
        Part eventsJson = null;
        Part placesJson = null;
        Part organizersJson = null;
        try {
            writer = resp.getWriter();

            eventsJson = req.getPart("events");
            placesJson = req.getPart("places");
            organizersJson = req.getPart("organizers");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String eventsFilePath = "";
        String placesFilePath = "";
        String organizersFilePath = "";

        try {
            assert eventsJson != null;
            eventsFilePath = fileUploadProcessor.uploadFile(eventsJson).getName();
            assert placesJson != null;
            placesFilePath = fileUploadProcessor.uploadFile(placesJson).getName();
            assert organizersJson != null;
            organizersFilePath = fileUploadProcessor.uploadFile(organizersJson).getName();


            organizersService.setRelationsFromFile(organizersFilePath);
            placeService.setRelationsFromFile(placesFilePath);
            eventService.mapApiToEntityFromFile(eventsFilePath);

            Template template = templateProvider.getTemplate(getServletContext(), "upload_success.ftlh");
            Map<String, Object> model = new HashMap<>();

            model.put("success", "Pliki zostały załadowane");

            final String googleId = (String) req.getSession().getAttribute("googleId");
            final String googleEmail = (String) req.getSession().getAttribute("googleEmail");
            final UserType userType = (UserType) req.getSession().getAttribute("userType");
            logger.info("Google email set to {}", googleEmail);

            if (googleId != null && !googleId.isEmpty()) {
                model.put("logged", "yes");
                model.put("googleEmail", googleEmail);
                model.put("userType", userType);
            } else {
                model.put("logged", "no");
                model.put("loginUrl", userAuthenticationService.buildLoginUrl());
            }

            try {
                template.process(model, resp.getWriter());
            } catch (TemplateException e) {
                logger.error(e.getMessage());
            }


        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Events.json file path set to: " + eventsFilePath);
        logger.info("Organizer.json file path set to: " + organizersFilePath);
        logger.info("Places.json file path set to: " + placesFilePath);

    }

    private void setEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }
}
