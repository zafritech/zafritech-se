/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.converters.DaoToUserConverter;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.dao.ClaimDao;
import org.zafritech.core.data.dao.UserDao;
import org.zafritech.core.data.dao.generic.ImageItemDao;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.FileIOService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  
    @Value("${zafritech.paths.static-resources}")
    private String static_resources;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DaoToUserConverter daoToUser;
    
    @Autowired
    private FileIOService fileIOService;
    
    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private ClaimService claimService;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override 
    public List<User> allUser() {
        
        return new ArrayList(userRepository.findAll());
    }
    
    @Override
    public User loggedInUser() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String name = userDetails.getUsername();
        
        User user = userRepository.findByUserName(name);
        
        return user;
    }
 
    @Override
    public boolean hasRole(String roleName) {
        
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return false;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        
        return authentication.getAuthorities().stream().anyMatch((auth) -> (roleName.equals(auth.getAuthority())));
    }
 
    @Override
    public boolean hasRole(String roleName, User user) {
       
        return user.getUserRoles().stream().anyMatch((role) -> (role.getRoleName().equalsIgnoreCase(roleName)));
    }
    
    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        List<User> sanitizedUsers = new ArrayList<>();

        userRepository.findAll().forEach(users::add);
        
        users.forEach(user->{
            
            user.setUserName(null); 
            user.setPassword(null); 
            sanitizedUsers.add(user);
            
        });

        return sanitizedUsers;
    }

    @Override
    public List<User> findOrderByFirstName() {

        List<User> users = new ArrayList<>();
        List<User> sanitizedUsers = new ArrayList<>();

        userRepository.findAllByOrderByFirstNameAsc().forEach(users::add);
        
        users.forEach(user->{
            
            user.setUserName(null); 
            user.setPassword(null); 
            sanitizedUsers.add(user);
            
        });

        return sanitizedUsers;
    }
    
    @Override
    public List<User> findOrderByFirstName(int pageSize, int pageNumber) {
        
        List<User> users = new ArrayList<>();
        List<User> sanitizedUsers = new ArrayList<>();
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "firstName");
        
        userRepository.findAll(request).forEach(users::add);
        
        users.forEach(user->{
            
            user.setUserName(null); 
            user.setPassword(null); 
            sanitizedUsers.add(user);
            
        });
        
        return sanitizedUsers;
    }
    

    @Override
    public User findByUserName(String name) {

        return userRepository.findByEmail(name);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user == null) {

            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(user);
    }

    @Override
    public User findById(Long id) {

        return userRepository.findOne(id);
    }

    @Override
    public User findByUuId(String uuid) {

        return userRepository.findByUuId(uuid);
    }

    @Override
    public User saveUser(User user) {

        if (userExists(user.getEmail())) {
            return null;
        }

        User newUser = new User(user.getEmail(), user.getPassword(), (HashSet<Role>) user.getUserRoles());

        return userRepository.save(newUser);
    }

    @Override
    public User saveDao(UserDao userDao) {

        if (userDao != null) {

            return userRepository.save(daoToUser.convert(userDao));
        }

        return null;
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.delete(userRepository.findOne(id));
    }

    @Override
    public boolean passwordAndConfirmationMatch(UserDao userDao) {

        return userDao.getPassword().equals(userDao.getConfirmPassword());
    }

    @Override
    public boolean userExists(String email) {

        return userRepository.findByEmail(email) != null;
    }

    @Override
    public boolean passwordMatches(String rawPassword, String encodedPassword) {

        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword); 
    }

    @Override
    public User changePasswordTo(User user, String password) {

        user.setPassword(new BCryptPasswordEncoder().encode(password)); 
        userRepository.save(user);
        
        return user;
    }
    
    @Override
    public User updateUserProfilePhoto(ImageItemDao dao) throws IOException, ParseException {
        
        String images_dir = static_resources + "images/";
                
        if (!dao.getImageFile().isEmpty()) {
            
            User user = userRepository.findOne(dao.getItemId());
            
            // Already has a logo set
            if (user.getPhotoPath() != null) {
                
                // Remove uploaded file
                File file = new File(images_dir + user.getPhotoPath());
                if (file.exists()) {

                    file.delete();
                }
            }
            
            // Upload and move Reference Image file
            String imageFileExtension = FilenameUtils.getExtension(dao.getImageFile().getOriginalFilename());
            String imageRelPath = "user/avatars/profile_" + user.getUuId() + "." + imageFileExtension;
            String imageFullPath = images_dir + imageRelPath;
            List<String> imageFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getImageFile()));
            FileUtils.moveFile(FileUtils.getFile(imageFiles.get(0)), FileUtils.getFile(imageFullPath)); 
            
            user.setPhotoPath(imageRelPath);
            
            // Remove uploaded file
            File file = new File(imageFiles.get(0));
            if (file.exists()) {
                
                file.delete();
            }
            
            userRepository.save(user);
            
            return user;
            
        } else {
        
            return null;
        }
    }
    
    @Override
    public UserClaim createClaim(ClaimDao claimDao) {
        
        User user = userRepository.findByUuId(claimDao.getUserUuId());
        ClaimType claimType = claimTypeRepository.findByTypeName(claimDao.getUserClaimType());
        
        return claimService.updateUserClaim(user, claimType, claimDao.getUserClaimValue());
    }

    @Override
    public List<UserClaim> findUserClaims(User user) {

        List<UserClaim> claims = userClaimRepository.findByUser(user);
   
        return claims;
    }

    @Override
    public List<UserClaim> findUserClaims(User user, String type) {
        
        List<UserClaim> claims = new ArrayList<>();
        List<ClaimType> claimTypes = claimTypeRepository.findByEntityType(type);
        
        claimTypes.stream().map((claimType) -> userClaimRepository.findByUserAndClaimClaimType(user, claimType)).forEachOrdered((userClaims) -> {
            userClaims.forEach((userClaim) -> {
                claims.add(userClaim);
            });
        });
        
        return claims;
    }
}
