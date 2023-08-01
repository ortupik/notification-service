package com.chris.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.security.modal.Notification;
import com.chris.security.modal.ResponseObj;
import com.chris.security.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("")
	public ResponseEntity<ResponseObj> sendNotification(@RequestBody Notification notification) {
		return notificationService.sendNotification(notification);
	}

	@GetMapping(value = "/{notificationType}/{deviceId}")
	public ResponseEntity<ResponseObj> getNotifications(@PathVariable String notificationType, @PathVariable String deviceId) {
		return notificationService.getNotifications(deviceId, notificationType);
	}

	@PostMapping("/sms")
	public ResponseEntity<ResponseObj> sendSms(@RequestBody Object smsObject) {
		return notificationService.sendSms(smsObject);
	}

	@PostMapping("/email")
	public ResponseEntity<ResponseObj> sendEmail(@RequestBody Object emailObject) {
		return notificationService.sendEmail(emailObject);
	}
}
