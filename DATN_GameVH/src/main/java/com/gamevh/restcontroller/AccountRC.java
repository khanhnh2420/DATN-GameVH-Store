package com.gamevh.restcontroller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gamevh.dto.AccountDTO;
import com.gamevh.entities.Account;
import com.gamevh.mapper.AccountMapper;
import com.gamevh.service.AccountService;
import com.gamevh.service.AuthorityService;
import com.gamevh.service.EncryptionService;
import com.gamevh.service.GoogleDriveService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/account")
public class AccountRC {
	@Autowired
	AccountService accountService;

	@Autowired
	AuthorityService authorityService;

	@Autowired
	AccountMapper accountMapper;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	private GoogleDriveService driveService;

	@GetMapping("{username}")
	public ResponseEntity<AccountDTO> getAccountLogin(@PathVariable("username") Optional<String> username) {
		Optional<String> optionalUsername = username;

		if (optionalUsername.isPresent() && !optionalUsername.get().trim().equals("")) {
			List<Account> accounts = accountService.findByUsername(optionalUsername.get());
			if (!accounts.isEmpty()) {
				AccountDTO accountDTO = accountMapper.modelToDto(accounts.get(0));
				if (accountDTO != null) {
					if (authorityService.findByAccountAndRole(accountDTO.getId(), "CUST") != null) {
						accountDTO.setRole("CUST");
					}
				}
				return ResponseEntity.ok(accountDTO);
			}
		}

		return null;
	}
	
	@GetMapping("email/{email}")
	public ResponseEntity<AccountDTO> getAccountByEmail(@PathVariable("email") Optional<String> email) {
		Optional<String> optionalEmail = email;

		if (optionalEmail.isPresent() && !optionalEmail.get().trim().equals("")) {
			List<Account> accounts = accountService.findByEmail(optionalEmail.get());
			if (!accounts.isEmpty()) {
				AccountDTO accountDTO = accountMapper.modelToDto(accounts.get(0));
				if (accountDTO != null) {
					if (authorityService.findByAccountAndRole(accountDTO.getId(), "CUST") != null) {
						accountDTO.setRole("CUST");
					}
				}
				return ResponseEntity.ok(accountDTO);
			}
		}

		return null;
	}

	@PostMapping("create")
	public ResponseEntity<Account> getAllAccounts(@RequestBody Account account) {
		if(accountService.findByUsername(account.getUsername()).isEmpty() && accountService.findByEmail(account.getEmail()).isEmpty()) {
			account.setPassword(encryptionService.encrypt(account.getPassword()));
			accountService.add(account);
		}
		return ResponseEntity.ok(account);
	}
	
	

	@PostMapping("upload")
	public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile image)
	        throws GeneralSecurityException {
	    try {
	        if (!image.isEmpty()) {
	        	
	            String fileName = image.getOriginalFilename();
	            String mimeType = image.getContentType();
	            String folderId = "1mMOXDZOQvQVs2MvJJF77UUpACbkfp5sv"; // ID của thư mục trên Google Drive để lưu file
	            // URL example:
	            // https://drive.google.com/drive/folders/10VLW7dddQHqi4-f4ddSTqxjN9YmLFZWi
	            String fileId = driveService.uploadFile(image, fileName, mimeType, folderId);
//	            System.out.println(fileId);
	            // Xử lý thành công
	            String responseData = "{\"fileId\": \"" + fileId + "\"}";
				return ResponseEntity.ok(responseData);
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	        // Xử lý lỗi
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).contentType(MediaType.APPLICATION_JSON).body("Lỗi upload hình: " + e.getMessage());
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).contentType(MediaType.APPLICATION_JSON).body("Lỗi upload hình");
	    
	}
//	
//	@GetMapping("/search/{search}")
//	public ResponseEntity<List<Account>> search(Model model, @PathVariable("search") String search) {
//		return ResponseEntity.ok(accountsDAO.findByUsernameContaining(search));
//	}
//	
//	@GetMapping("{id}")
//	public ResponseEntity<Account> getOne(@PathVariable("id") String username) {
//		if(!accountsDAO.existsById(username)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(accountsDAO.findById(username).get());
//	}
//	
//	@DeleteMapping("{id}")
//	public ResponseEntity<Void> delete(@PathVariable("id") String username) {
//		if(!accountsDAO.existsById(username)) {
//			return ResponseEntity.notFound().build();
//		}
//		accountsDAO.deleteById(username);
//		return ResponseEntity.ok().build();
//	}
}
