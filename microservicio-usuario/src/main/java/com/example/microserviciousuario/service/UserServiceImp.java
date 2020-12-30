package com.example.microserviciousuario.service;

import com.example.microserviciousuario.dto.UserRegistrationDto;
import com.example.microserviciousuario.entity.Role;
import com.example.microserviciousuario.entity.User;
import com.example.microserviciousuario.entity.UsersRoles;
import com.example.microserviciousuario.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImp  implements  UserService{

    User currentUser;
    
    @Autowired
    UserRepo repo;

    @Autowired
    RoleService roleService;

    @Autowired
    UsersRolesService userRolesService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepo repo){
        this.repo   = repo;
        this.currentUser = new User();
    }
    
    public User getCurrentUser(){return this.currentUser;}

    @Override
    public User save(UserRegistrationDto entity) {
        User user = new User();
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        System.out.println("SELECTED ROLE = "+entity.getRole());
        User result = repo.save(user);

        //getting role

        Role r = roleService.findByName(entity.getRole());
        if(r!= null){

            UsersRoles usrRoles = new UsersRoles();
            usrRoles.setUser_id(result.getId());
            usrRoles.setRole_id(r.getId());

            userRolesService.save(usrRoles);

        }




        return user;

    }

    public User findByEmail(String email){
        return this.repo.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        
        this.currentUser = user;
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));


    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
