package com.example.kekec.authentication;


import com.example.kekec.model.enums.Provider;
import com.example.kekec.model.enums.UserType;
import com.example.kekec.model.jpa.User;
import com.example.kekec.model.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by ristes on 3/15/16.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private Provider provider;
  private UserType defaultUserType;
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  private UserRepository userRepository;

  private User user;

  public LoginSuccessHandler(Provider provider, UserType defaultUserType) {
    this.provider = provider;
    this.defaultUserType = defaultUserType;
  }

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse,
    Authentication authentication
  ) throws IOException, ServletException {

    HttpSession session = httpServletRequest.getSession();
    User user = getUser(authentication);
    session.setAttribute("user", user);

    //super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    handle(httpServletRequest, httpServletResponse, authentication);
  }

  public User getUser(Authentication authentication) {
    user = userRepository.findByUsername(authentication.getName());
    if (user == null) {
      user = createUserFromProvider(authentication);
    }
    return user;
  }

  private User createUserFromProvider(Authentication authentication) {
    user = new User();
    user.username = authentication.getName();
    user.type = defaultUserType;
    user.provider = provider;
    userRepository.save(user);
    return user;
  }


  // IMPL

  protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
    final String targetUrl = determineTargetUrl(authentication);

    if (response.isCommitted()) {
      logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(final Authentication authentication) {
    boolean isUser = false;
    boolean isAdmin = false;
    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    for (final GrantedAuthority grantedAuthority : authorities) {
      if (grantedAuthority.getAuthority().equals("ROLE_CUSTOMER")) {
        isUser = true;
        break;
      } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
        isAdmin = true;
        break;
      }
    }

    if (isUser) {
      return "/user/"+ user.id;
//      return "/user";
    } else if (isAdmin) {
      return "/admin";
    } else {
      throw new IllegalStateException();
    }
  }
}
