package org.example.services;

import org.example.dto.EmailMessageDto;
import org.example.dto.FilterRouteDto;
import org.example.dto.RouteDto;
import org.example.dto.UserDto;
import org.example.email.EmailSender;
import org.example.entities.EmailTemplate;
import org.example.entities.User;
import org.example.entities.enums.EmailCode;
import org.example.repository.IEmailTemplateRepository;
import org.example.repository.IUserRepository;
import org.example.restclient.interfaces.RouteClient;
import org.example.security.custom.CustomUser;
import org.example.services.factory.EmailTemplateFactory;
import org.example.services.iService.IRouteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService implements IRouteService {

    private final RouteClient routeClient;
    private final IUserRepository userRepository;
    private final IEmailTemplateRepository emailTemplateRepository;
    private final EmailSender emailSender;
    private final EmailTemplateFactory emailTemplateFactory;

    public RouteService(RouteClient routerClient,
                        IUserRepository userRepository,
                        IEmailTemplateRepository emailTemplateRepository,
                        EmailSender emailSender,
                        EmailTemplateFactory emailTemplateFactory) {
        this.routeClient = routerClient;
        this.userRepository = userRepository;
        this.emailTemplateRepository = emailTemplateRepository;
        this.emailSender = emailSender;
        this.emailTemplateFactory = emailTemplateFactory;
    }

    public List<RouteDto> getRoutes() {
        return this.routeClient.getAllRoutes().collectList().block();
    }

    public List<RouteDto> filterRoute(FilterRouteDto filterDto) {
        return this.routeClient.getRouterByFilter(filterDto);
    }

    public RouteDto registerUserRoute(CustomUser customUser, String routeId) {
        User user = userRepository.getReferenceById(customUser.getId());

        UserDto userDto = UserDto.fromUser(user);
        RouteDto routeDto = this.routeClient.registerUserRoute(userDto, routeId).block();

        EmailMessageDto emailMessageDto = EmailMessageDto.builder().routeDto(routeDto).userDto(userDto).build();

        sendTemplatedEmail(EmailCode.CONFIRMATION_USER_ROUTE, emailMessageDto, List.of(user));

        return routeDto;
    }

    @Override
    public void sendNotificationNewRoute(RouteDto routeDto) {
        List<User> users = userRepository.findAll();
        EmailMessageDto emailMessageDto = EmailMessageDto.builder().routeDto(routeDto).build();

        sendTemplatedEmail(EmailCode.NEW_ROUTE_NOTIFICATION, emailMessageDto, users);

    }

    private void sendTemplatedEmail(EmailCode emailCode, EmailMessageDto emailMessageDto, List<User> users) {
        Optional<EmailTemplate> emailTemplateOpt = emailTemplateRepository.findByCode(emailCode);

        if (emailTemplateOpt.isPresent()) {
            EmailTemplate emailTemplate = emailTemplateOpt.get();

            String body = emailTemplateFactory.buildEmailBody(emailCode, emailMessageDto, emailTemplate.getHtmlContent());
            String subject = emailTemplateFactory.buildEmailSubject(emailCode, emailMessageDto, emailTemplate.getSubject());

            users.forEach(user -> emailSender.sendEmail(user.getPersonalInformation().getEmail(), subject, body));
        }

    }
}
