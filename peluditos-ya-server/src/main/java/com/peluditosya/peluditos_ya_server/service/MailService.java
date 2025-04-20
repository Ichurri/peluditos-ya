package com.peluditosya.peluditos_ya_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String name, String role) {
        String subject = "¡Bienvenido a la plataforma de adopciones!";
        String message;

        if ("SHELTER".equalsIgnoreCase(role)) {
            message = "Hola " + name + ",\n\nGracias por registrar tu hogar de adopción en nuestra plataforma. Ahora podrás ayudar a conectar mascotas con nuevos hogares llenos de amor.";
        } else {
            message = "Hola " + name + ",\n\nGracias por registrarte como adoptante. Esperamos que encuentres una mascota ideal y le des un nuevo hogar lleno de cariño.";
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }

    // Método para enviar un correo sobre el estado de la solicitud
    public void sendStatusEmail(String toEmail, String status) {
        String subject = "Estado de la solicitud";
        String message;

        if ("APPROVED".equalsIgnoreCase(status)) {
            message = "¡Felicidades! Tu solicitud ha sido aprobada y ahora puedes comenzar a registrar tu hogar de adopción en nuestra plataforma.";
        } else if ("REJECTED".equalsIgnoreCase(status)) {
            message = "Lamentablemente, tu solicitud ha sido rechazada. Por favor, revisa los requisitos y vuelve a intentarlo.";
        } else {
            message = "El estado de tu solicitud ha sido actualizado a: " + status;
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
