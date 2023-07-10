package com.gamevh.restcontroller;

import java.util.List;
import java.util.Optional;

import com.gamevh.dto.RegisterAccountDTO;
import com.gamevh.dto.UpdateAccountDTO;
import com.gamevh.service.impl.AccountServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gamevh.entities.Account;
import com.gamevh.reponsitory.AccountRepository;
import com.gamevh.reponsitory.CategoryRepository;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/account")
public class AccountRC {
	@Autowired
	AccountRepository accountsDAO;

	@Autowired
	CategoryRepository categoriesDAO;

	@Autowired
	AccountServiceImpl accountService;
	
	@GetMapping
	public ResponseEntity<Page<Account>> getAllAccounts(
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size,
			@RequestParam(value = "username", defaultValue = "", required = false) String username,
			@RequestParam(value = "name", defaultValue = "", required = false) String name,
			@RequestParam(value = "roleId", defaultValue = "", required = false) String roleId
			) {
		Page<Account> accounts = accountService.findAll(page, size, username, name, roleId);
		return ResponseEntity.ok(accounts);
	}
	@PostMapping
	public Account registerNewAccount(@RequestPart("account")String  accountDTOString,
									  @RequestPart("image") MultipartFile image){
		return accountService.registerAccount(accountDTOString, image);
	}

	@PutMapping("/status/{username}")
	public Account toggleAccountStatus(@PathVariable("username") String username){
		return accountService.toggleAccountStatus(username);
	}
	@PutMapping("/{username}")
	public Account updateAccount(@PathVariable("username") String username,
								 @RequestPart("account")String  accountDTOString,
								 @RequestPart(value = "image", required = false) MultipartFile image){
		return accountService.updateAccount(username, accountDTOString, image);
	}
	@GetMapping(value = "/image/{username}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getAccountImage(@PathVariable("username")String userName){
		return accountService.getAccountImage(userName);
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<List<Account>> search(Model model, @PathVariable("search") String search) {
		return ResponseEntity.ok(accountsDAO.findByUsernameContaining(search));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Account> getOne(@PathVariable("id") String username) {
		if(!accountsDAO.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accountsDAO.findById(username).get());
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String username) {
		accountService.deleteAccount(username);
	}
}
