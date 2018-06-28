/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.PwdDao;
import org.zafritech.core.data.dao.RoleDao;
import org.zafritech.core.data.dao.UserDao;
import org.zafritech.core.data.dao.UserEditDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.CountryRepository;
import org.zafritech.core.data.repositories.RoleRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.UserService;


/**
 *
 * @author LukeS
 */
@RestController
public class UserRestController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
 
    @Autowired
    private CountryRepository countryRepository;
    
    @RequestMapping("/api/login/check")
    public ResponseEntity<String> checkUserLogin(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        return new ResponseEntity<>(userName, HttpStatus.OK);
    }
        
    @RequestMapping("/api/users/list")
    public ResponseEntity<List<User>> getUserList(Model model) {
        
        List<User> users = userService.findOrderByFirstName();
        
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
        
    @RequestMapping(value = "/api/users/create/new", method = POST)
    public ResponseEntity<User> createNewUser(@RequestBody UserDao userDao) {
        
        if (userService.userExists(userDao.getEmail())) {
            
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            
        } else if (!userDao.getPassword().equals(userDao.getConfirmPassword())) {
        
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        } else {

            User user = userService.saveDao(userDao); 

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
   
    @RequestMapping(value = "/api/user/password/change", method = POST)
    public ResponseEntity<User> getUserByUuId(@RequestBody PwdDao pwdDao) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        
        User user = userRepository.findByUserName(userName);

        if (user != null && pwdDao.getNewPassword().equals(pwdDao.getNewPasswordConfirm()) 
                         && userService.passwordMatches(pwdDao.getCurrentPassword(), user.getPassword())) {
            
            userService.changePasswordTo(user, pwdDao.getNewPassword());
            
        } else {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = {"/api/user/profile/photo/update"})
    public ResponseEntity<?> getUpdateUserProfilePhoto(ImageItemDao imageDao) throws IOException, ParseException {
      
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        
        User loggedUser = userRepository.findByUserName(userName);
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");

        if (!Objects.equals(loggedUser.getId(), imageDao.getItemId()) && !isAdmin) {
            
            return new ResponseEntity("Account security error!", HttpStatus.OK);
        }
        
        if (imageDao.getImageFile().isEmpty()) {

            return new ResponseEntity("Please select a image file!", HttpStatus.OK);
        }
        
        User user = userService.updateUserProfilePhoto(imageDao);
        
        return new ResponseEntity("Error updating profile photo!", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/user/byuuid/{uuid}", method = GET)
    public ResponseEntity<User> getUserByUuId(@PathVariable(value = "uuid") String uuid) {
        
        User user = userService.findByUuId(uuid); 
        user.setPassword(null); 
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/update/{uuid}", method = POST)
    public ResponseEntity<User> getUserUpdate(@RequestBody UserEditDao userDao, 
                                              @PathVariable(value = "uuid") String uuid) {
        
        User user = userService.findByUuId(uuid); 
        
        if (userDao.getFirstName() != null && !userDao.getFirstName().isEmpty()) { user.setFirstName(userDao.getFirstName()); }
        if (userDao.getLastName() != null && !userDao.getLastName().isEmpty()) { user.setLastName(userDao.getLastName()); }
        if (userDao.getMainRole() != null && !userDao.getMainRole().isEmpty()) { user.setMainRole(userDao.getMainRole()); }
        if (userDao.getPhoneNumber() != null && !userDao.getPhoneNumber().isEmpty()) { user.getContact().setPhone(userDao.getPhoneNumber()); }
        if (userDao.getMobileNumber() != null && !userDao.getMobileNumber().isEmpty()) { user.getContact().setMobile(userDao.getMobileNumber()); }
        if (userDao.getAddress() != null && !userDao.getAddress().isEmpty()) { user.getContact().setAddress(userDao.getAddress()); }
        if (userDao.getCountryId() != null) { user.getContact().setCountry(countryRepository.findOne(userDao.getCountryId())); }
        if (userDao.getState() != null && !userDao.getState().isEmpty()) { user.getContact().setState(userDao.getState()); }
        if (userDao.getCity() != null && !userDao.getCity().isEmpty()) { user.getContact().setCity(userDao.getCity()); }
        if (userDao.getPostCode()!= null && !userDao.getPostCode().isEmpty()) { user.getContact().setPostCode(userDao.getPostCode()); }
        
        userRepository.save(user);
        
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/user/roles/{uuid}", method = GET)
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable(value = "uuid") String uuid) {
        
        User user = userService.findByUuId(uuid); 
        @SuppressWarnings({ "rawtypes", "unchecked" })
		List<Role> roles = new ArrayList(user.getUserRoles());
        
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/roles/all", method = GET)
    public ResponseEntity<List<Role>> getAllRoles() {
        
        Iterable<Role> rolesIterable = roleRepository.findAll();
        List<Role> roles = new ArrayList<>();
        rolesIterable.forEach(roles::add);
        
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/api/users/roles/add/{uuid}", method = POST)
    public ResponseEntity<User> addUserRoles(@RequestBody List<RoleDao> roleDaos, @PathVariable(value = "uuid") String uuid) {
        
        List<Role> newRoles = new ArrayList<>();
        
        roleDaos.forEach((roleDao) -> {
            
            newRoles.add(roleRepository.findByRoleName(roleDao.getRoleName()));
        });
        
        User user = userService.findByUuId(uuid); 
        user.setUserRoles(new HashSet(newRoles)); 
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
