package com.laptrinhoop.service.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import com.laptrinhoop.agent.SendGripAgent;
import com.laptrinhoop.converter.Jksonizer;
import com.laptrinhoop.dto.SendGripDto;
import com.laptrinhoop.properties.TemplateProperties;
import com.laptrinhoop.utils.DataEncryptor;
import com.laptrinhoop.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.laptrinhoop.service.IMailService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import retrofit2.Call;
import retrofit2.Response;

@Service
@Slf4j
public class MailService implements IMailService {
	@Autowired
	JavaMailSender sender;

	@Autowired
	private SendGripAgent sendGripAgent;

	@Autowired
	private TemplateProperties templateProperties;

	/**
	 * JavaMailSender interface con cua MailSender , hỗ trợ tin nhắn kiểu MiME
	 * Gửi email
	 * @param to      email người nhận
	 * @param subject tiêu đề email
	 * @param body    nội dung email
	 * @param others  các thông số còn lại theo thứ tự sau
	 * from: email người gửi
	 * cc: chuỗi chứa danh sách email những người đồng nhận, cáchnhau dấu phẩy
	 * bcc: chuỗi chứa danh sách email những người đồng nhận bí mật, cách nhau dấu phẩy
	 * bcc: chuỗi chứa danh sách đường dẫn file đính kèm, cách  nhau dấu phẩy
	 * @return true nếu gửi mail thành công, ngược lại là false
	 */
	public boolean send(String to, String subject, String body, String... others) {
		try {
			MimeMessage mail = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			// Người gửi
			String from = "Kỹ thuật lập trình hướng đối tượng <tuanvuplbp@gmail.com>";
			if (others.length > 0 && others[0] != null) {
				from = String.format("%s <%s>", others[0], others[0]);
			}
			helper.setReplyTo(from);
			helper.setFrom(from);

			// Người nhận CC
			if (others.length > 1 && others[1] != null && others[1].length() > 0) {
				String[] cc = others[1].split("[,; ]+");
				helper.setCc(cc);
			}

			// Người nhận BCC
			if (others.length > 2 && others[2] != null && others[2].length() > 0) {
				String[] bcc = others[2].split("[,; ]+");
				helper.setBcc(bcc);
			}

			// File đính kèm
			if (others.length > 3 && others[3] != null && others[3].length() > 0) {
				String[] paths = others[3].split("[,; ]+");
				for (String path : paths) {
					File file = new File(path);
					helper.addAttachment(file.getName(), file);
				}
			}
			// Send
			sender.send(mail);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public SendGripDto.SendGripResponse send(SendGripDto.SendGripRequest requestBody) {
		log.info("[SENDGRIP] request body: {}",Jksonizer.toJson(requestBody));
		Call<ResponseBody> data = sendGripAgent.sendEmail(
				new StringBuilder("Bearer ")
				.append(DataEncryptor.decrypt(templateProperties.getApiKey(),templateProperties.getSecretKey()))
				.toString(),
				requestBody
		);
		try{
			Response<ResponseBody> excuseData =  data.execute();
			if (excuseData.code() == HttpStatus.SC_OK || excuseData.code() == HttpStatus.SC_ACCEPTED) {
				log.info("[End] sendMailWithTemplate success");
				return SendGripDto.SendGripResponse.successWith();
			}
			String body = ObjectUtils.isEmpty(excuseData.errorBody()) ? null : excuseData.errorBody().toString();
			log.info("[End] sendMailWithTemplate fail:{}");
			return SendGripDto.SendGripResponse.failedWith(excuseData.code(), body);
		} catch (ResourceAccessException e) {
			log.error("Error request timeout sendMailWithTemplate", e);
			return SendGripDto.SendGripResponse.failedWith(HttpStatus.SC_REQUEST_TIMEOUT, e.getMessage());
		} catch (HttpClientErrorException e) {
			log.error("Error with client sendMailWithTemplate", e);
			return SendGripDto.SendGripResponse.failedWith(e.getStatusCode().value(), e.getMessage());
		} catch (Exception e) {
			log.error("Error not handle sendMailWithTemplate", e);
			return SendGripDto.SendGripResponse.failedWith(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
