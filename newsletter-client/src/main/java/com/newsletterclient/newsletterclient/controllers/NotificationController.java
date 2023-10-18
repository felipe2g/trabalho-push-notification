package com.newsletterclient.newsletterclient.controllers;

import com.newsletterclient.newsletterclient.models.NotificationMessage;
import com.newsletterclient.newsletterclient.services.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @PostMapping
    public ResponseEntity<?> sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) {
        return firebaseMessagingService.sendNotification(notificationMessage);
    }
}
