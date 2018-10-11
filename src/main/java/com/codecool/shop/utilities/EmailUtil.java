package com.codecool.shop.utilities;


import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmailUtil{


        private static void sendEmail(String email, String msg){

            // Sender's email ID needs to be mentioned
            String from = "chernobyl@codecool.com";
            final String username = "thejawas2018";//change accordingly
            final String password = "CCShop18";//change accordingly

            // Assuming you are sending email through relay.jangosmtp.net
            String host = "smtp.gmail.com";

            Properties props = getProperties();
            Session session = getSession(username, password, props);


            send(email, msg, from, session);
        }

        private static void send(String email,  String msg, String from, Session session) {
            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));

                // Set Subject: header field
                message.setSubject("Order");

                // Now set the actual message
                message.setText(msg);

                // Send message
                Transport.send(message);

                System.out.println("Sent message successfully....");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        private static Session getSession(String username, String password, Properties props) {
            // Get the Session object.
            return Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
        }

        private static Properties getProperties() {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            return props;
        }

        public static void sendVerificationEmail(String email, String name) {
            ShoppingCart shoppingCart = ShoppingCart.getInstance();
            List<Product> cart = shoppingCart.getAll();

            double total = cart.stream().mapToDouble(Product::getDefaultPrice).sum();

            Map<Product, Long> products = cart.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            StringBuilder message = new StringBuilder();

            message.append("Name: ").append(name).append("\n");
            message.append("Email: ").append(email).append("\n");
            message.append("Items:\n\t");

            products.forEach((product, count) -> message.append(product.getName())
                    .append("     x ")
                    .append(count)
                    .append("\n\t"));

            message.append("\n");
            message.append("Total: ").append(total).append(" USD");

            sendEmail(email, message.toString());
        }

    }
