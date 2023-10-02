package com.web.controller;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.model.Login;
import com.web.model.Otp;
import com.web.model.Pro;
import com.web.model.Register;
import com.web.model.Register1;
import com.web.repo.OtpRepo;
import com.web.repo.ProRepo;
import com.web.repo.Register1Repo;
import com.web.repo.RegisterRepo;
import com.web.service.Register1Imp;
import com.web.service.RegisterImp;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {
	@Autowired
	private RegisterImp service;
	@Autowired
	private Register1Imp service1;

	@Autowired
	private RegisterRepo repo7;

	@Autowired
	private Register1Repo repo8;

	@Autowired
	private ProRepo repo1;

	@Autowired
	private OtpRepo otprepo;
	@Autowired
	private RegisterRepo regrepo;

	@PostMapping("/register")
	public String addForm11(@RequestBody

	Register customer, ModelMap model, HttpSession session) {
		String email = customer.getEmail();
		String ename = customer.getEname();
		String mob = customer.getMob();
		String password = customer.getPassword();
		String cnpassword = customer.getCnpassword();
		model.put("ename", ename);

		Register reg = repo7.findByEmailAndMob(email, mob);

		if (reg != null) {
			return "regfail";
		} else {
			Register newRegister = new Register();
			newRegister.setEmail(email);
			newRegister.setEname(ename);
			newRegister.setMob(mob);
			newRegister.setPassword(password);
			newRegister.setCnpassword(cnpassword);

			// Generate OTP
			String otp = generateOTP();
			String otpId = UUID.randomUUID().toString();

			// Save the OTP to the database using the Otp class

			Otp otpEntity = new Otp(otpId, otp);
			otprepo.save(otpEntity);

			try {
				// Send OTP via email
				sendEmail(email, "OTP Verification",
						"Hello " + ename + ",\n\nYour OTP is: " + otp + " and it is valid for 5 minutes.");
				System.out.println("Email sent successfully.");
			} catch (MessagingException e) {
				// Handle any exceptions that occurred during email sending
				e.printStackTrace();
				return "otperror";
			}

			return "otp1";
		}
	}

	// otp for applicant

	@PostMapping("/otp1")
	public String submitOTP1(@RequestParam String otp, ModelMap model) {

		if (otp == null) {
			System.out.println("otp is null1");
		} else {
			System.out.println("otp is Entered" + otp);
		}

		Otp otp2 = otprepo.findByOtpValue(otp);
		if (otp2 == null) {
			return "inotp";
		}
		String otp3 = otp2.getOtpValue();
		if (otp != null) {
			System.out.println("Stored otp is not null" + otp3);
		}

		// Check if newRegister object is found in the session and OTP is correct
		if (otp != null && otp.equals(otp3)) {
			// Save the newRegister object using the repository

			otprepo.delete(otp2);
			return "regsucess";
		} else {
			// newRegister object not found in session or OTP is incorrect, return
			// registration failure page
			return "inotp";
		}
	}

	@PostMapping("/save")
	public String addEmp(@RequestBody Register emp, ModelMap m) {
		repo7.save(emp);
		return "Details Saved SucessFully";
	}

	@PostMapping("/loginform")
	public String addForm(@RequestBody Login login, @ModelAttribute ModelMap model)

	{
		String email = login.getEmail();
		String password = login.getPassword();

		Register emp1 = repo7.findByEmailAndPassword(email, password);
		if (emp1 == null) {
			return "Invalid Credits";
		}

		model.addAttribute("command", emp1);

		// start

		Register log = service.login(login.getEmail(), login.getPassword());

		if (log != null) {

			return "personaldetails1";

		} else {

			return "delete1";
		}

	}

//admin Start123

	@PostMapping("/adminregister")
	public String adminregister(@RequestBody

	Register1 customer, ModelMap model) {
		String id = customer.getId();
		String email = customer.getEmail();
		String ename = customer.getEname();
		String mob = customer.getMob();
		String password = customer.getPassword();
		String cnpassword = customer.getCnpassword();
		model.put("ename", ename);

		Register1 reg = repo8.findByEmailAndMob(email, mob);

		if (reg != null) {
			return "regfail";
		} else {
			Register1 newRegister = new Register1();
			newRegister.setId(id);
			newRegister.setEmail(email);
			newRegister.setEname(ename);
			newRegister.setMob(mob);
			newRegister.setPassword(password);
			newRegister.setCnpassword(cnpassword);

			// Generate OTP
			String otp = generateOTP();
			String otpId = UUID.randomUUID().toString();

			// Save the OTP to the database using the Otp class

			Otp otpEntity = new Otp(otpId, otp);
			otprepo.save(otpEntity);

			try {
				// Send OTP via email
				sendEmail(email, "OTP Verification",
						"Hello " + ename + ",\n\nYour OTP is: " + otp + " and it is valid for 5 minutes.");
				System.out.println("Email sent successfully.");
			} catch (MessagingException e) {
				// Handle any exceptions that occurred during email sending
				e.printStackTrace();
				return "otperror";
			}

			return "adminotp1";
		}
	}

	// otp for applicant

	// ...

	@PostMapping("/adminotp1")
	public String adminsubmitOTP(@RequestParam String otp, ModelMap model) {

		if (otp == null) {
			System.out.println("otp is null1");
		} else {
			System.out.println("otp is Entered" + otp);
		}

		Otp otp2 = otprepo.findByOtpValue(otp);
		if (otp2 == null) {
			return "inotp";
		}
		String otp3 = otp2.getOtpValue();
		if (otp != null) {
			System.out.println("Stored otp is not null" + otp3);
		}

		// Check if newRegister object is found in the session and OTP is correct
		if (otp != null && otp.equals(otp3)) {
			// Save the newRegister object using the repository

			otprepo.delete(otp2);
			return "adminregsucess";
		} else {
			// newRegister object not found in session or OTP is incorrect, return
			// registration failure page
			return "inotp";
		}
	}

	@PostMapping("/adminsave")
	public String aminsave(@RequestBody Register1 emp, ModelMap m) {
		repo8.save(emp);
		return "adminsaved";
	}

	@PostMapping("/adminloginform")
	public String adminlogin(@RequestBody Login login, @ModelAttribute ModelMap model)

	{
		String email = login.getEmail();
		String password = login.getPassword();
		System.out.println("log not null" + email);
		System.out.println("log not null" + password);
		Register1 emp1 = repo8.findByEmailAndPassword(email, password);
		if (emp1 == null) {
			System.out.println("emp1 is null");
			return "Invalid Credits";

		}
		System.out.println("emp1 not  null");

		// start

		Register1 log = service1.login(login.getEmail(), login.getPassword());

		if (log != null) {
			System.out.println("log not null");
			return "adminlogin";

		} else {
			System.out.println("log  null");
			return "delete1";
		}

	}

	// admin end123

	// start admin access

	@RequestMapping(value = "/register1", method = RequestMethod.POST)
	public String addForm10(@RequestParam String id, @RequestParam String ename, @RequestParam String email,
			@RequestParam String mob, @RequestParam String password, @RequestParam String cnpassword,
			Register1 customer, ModelMap model, HttpSession session) {

		model.put("ename", ename);

		Register1 reg = repo8.findByEmailAndMob(email, mob);

		if (reg != null) {
			return "regfail";
		} else {
			Register1 newRegister = new Register1();
			newRegister.setId(id);
			newRegister.setEname(ename);
			newRegister.setEmail(email);
			newRegister.setMob(mob);
			newRegister.setPassword(password);
			newRegister.setCnpassword(cnpassword);

			// Generate OTP
			String otp = generateOTP();

			// Store the OTP and its expiration time in session
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expirationTime = now.plusMinutes(5); // OTP expires in 1 minute
			session.setAttribute("otp1", otp);
			session.setAttribute("otpExpiration", expirationTime);

			session.setAttribute("register10", newRegister);

			try {
				// Send OTP via email
				sendEmail(email, "OTP Verification", "Hello" + " " + ename + ",\n\nYour OTP is: " + " " + otp + " "
						+ "and Your Otp is  Valid 5 Minutes");
			} catch (MessagingException e) {
				// Handle any exceptions that occurred during email sending
				e.printStackTrace();
				return "otperror";
			}

			return "enterotp";
		}
	}

	private String generateOTP() {
		// Generate a random 6-digit OTP
		int otp = (int) (Math.random() * 900000) + 100000;
		return String.valueOf(otp);
	}

	private void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
		// Replace with your email and password
		String senderEmail = "slrvamsikrishna@gmail.com";
		String senderPassword = "zugweogflidhqcyi";

		// Set properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Create session
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		// Create message
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(senderEmail));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		message.setSubject(subject);
		message.setText(body);

		// Send message
		Transport.send(message);
		System.out.println("Email sent successfully.");
	}

	//

	// otp validatio
	@PostMapping(value = "/otp")
	public String submitOTP(@RequestParam String otp, HttpSession session, ModelMap model) {
		// Get the stored newRegister object from the session
		Register1 newRegister = (Register1) session.getAttribute("register10");
		String reg = (String) session.getAttribute("otp1");

		// Check if newRegister object is found in the session
		if (newRegister != null && reg != null && otp.equals(reg)) {
			// Save the newRegister object using the repository
			Register1 reg2 = repo8.save(newRegister);
			model.put("ename", reg2.getEname());

			// Clear the session after successful registration
			// session.removeAttribute("register1");

			return "reg1sucess";
		} else {
			// newRegister object not found in session or OTP is incorrect, return
			// registration failure page
			return "inotp";
		}
	}

//h2

	@GetMapping("/viewprofessional")
	public Pro addForm17(@RequestParam Long id) {
		Pro emp1 = repo1.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Professional with ID " + id + " not found"));

		return emp1;
	}

	@GetMapping("/reg")
	public Register registerdetails(@RequestParam String email) {

		Register emp1 = regrepo.findByEmail(email);

		return emp1;

	}
}
