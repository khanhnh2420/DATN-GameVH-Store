package com.fourTL.controller.site.LoginAndRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.AuthorityDAO;
import com.fourTL.dao.RoleDAO;
import com.fourTL.entities.Account;
import com.fourTL.entities.Authority;
import com.fourTL.entities.Role;
import com.fourTL.service.UserService;

@Controller
public class LoginController {

	@RequestMapping("/login/form")
	public String form() {
		return "site/login";
	}

	@RequestMapping("/login/success")
	public String success() {
		return "redirect:/";
	}

	@RequestMapping("/login/error")
	public String error(Model model) {
		model.addAttribute("messageFailed", "Sai thông tin đăng nhập!");
		return "forward:/login/form";
	}

	@RequestMapping("/login/access/denied")
	public String denied(Model model) {
		model.addAttribute("messageFailed", "Bạn không có quyền truy xuất!");
		return "forward:/login/form";
	}

	// OAuth2

	@Autowired
	UserService userService;
	@Autowired
	RoleDAO rolesDAO;
	@Autowired
	AccountDAO accountsDAO;
	@Autowired
	AuthorityDAO authoritiesDAO;
	@Autowired
	BCryptPasswordEncoder pe;

	@RequestMapping("/oauth2/login/success")
	public String oauth2Success(OAuth2AuthenticationToken oauth2, Model model) {
		try {
			if (!accountsDAO.existsById(oauth2.getPrincipal().getAttribute("email"))) {
				Account account = new Account();
				account.setUsername(oauth2.getPrincipal().getAttribute("email"));
				account.setPassword(pe.encode("123"));
				account.setFullname(oauth2.getPrincipal().getAttribute("name"));
				account.setEmail(oauth2.getPrincipal().getAttribute("email"));
				account.setAddress("");
				account.setPhoto("user.png");
				Role role = rolesDAO.findById("CUST").get();

				Authority authorities = new Authority();
				authorities.setAccount(account);
				authorities.setRole(role);

				accountsDAO.save(account);
				authoritiesDAO.save(authorities);
			}

			userService.loginFromOAuth2(oauth2);
			return "forward:/login/success";
		} catch (Exception e) {
			model.addAttribute("messageFailed", "Đăng nhập thất bại!");
			return "forward:/login/form";
		}

	}
}
