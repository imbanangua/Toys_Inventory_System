package ca.sheridancollege.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import ca.sheridancollege.databases.DatabaseAccess;

@Component
public class EmailServiceImpl {
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private DatabaseAccess da;
	
	public void sendMailWithThymeleaf(String to, String subject) 
				throws MessagingException{
		
		//Prepare evaluation context
		final Context ctx = new Context();
		ctx.setVariable("toyList", da.getToys());
		
		//Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setSubject(subject);
		message.setFrom("enan.song.imbanangua@gmail.com");
		message.setTo(to);
		
		//Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("emailTemplate.html", ctx);
		message.setText(htmlContent, true);  //true = isHtml
		
		//Send email
		this.emailSender.send(mimeMessage);
		
	}

}
