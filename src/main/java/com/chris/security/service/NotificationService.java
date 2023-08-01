package com.chris.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chris.security.modal.Notification;
import com.chris.security.modal.ResponseObj;
import com.chris.security.repository.NotificationRepository;

@Service
public class NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	private NotificationRepository notificationRepository;

	public ResponseEntity<ResponseObj> sendNotification(Notification notification) {
		ResponseObj body = new ResponseObj();
		body.setStatusCode(200);
		body.setData(notificationRepository.save(notification));
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	public ResponseEntity<ResponseObj> getNotifications(String deviceId, String notificationType) {
		ResponseObj body = new ResponseObj();

		List<Notification> notifications = notificationRepository.findNotificationByDeviceId(deviceId);
		notifications = notifications.stream()
				.filter(n -> (n.getNotificationType().equals(notificationType) && n.getStatus().equals("pending")))
				.toList();

		if (notifications != null && !notifications.isEmpty()) {
			body.setStatusCode(200);
			body.setData(notifications);
		} else {
			body.setStatusCode(400);
			body.setMessage("No notifications");
		}
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	public ResponseEntity<ResponseObj> sendSms(Object smsObject) {
		return null;
	}

	public ResponseEntity<ResponseObj> sendEmail(Object emailObject) {
		return null;
	}

}
