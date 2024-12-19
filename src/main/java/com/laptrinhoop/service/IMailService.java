package com.laptrinhoop.service;

import com.laptrinhoop.dto.SendGripDto;

public interface IMailService {
	boolean send(String to, String subject, String body, String... others);

	SendGripDto.SendGripResponse send(SendGripDto.SendGripRequest requestbody);

}
