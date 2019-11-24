package com.shop.demo.util;

import javax.servlet.http.HttpServletRequest;

import com.shop.demo.exception.EmptyBasketException;
import com.shop.demo.exception.NotAdminException;
import com.shop.demo.exception.NotLoggedInException;
import com.shop.demo.exception.UserException;
import com.shop.demo.model.entity.User;

public class Validation {

	private static final String USER_ATTRIBUTE = "user";

	public static void validateLogIn(HttpServletRequest request) throws NotLoggedInException {
		if(request.getSession().getAttribute(USER_ATTRIBUTE) == null) {
			throw new NotLoggedInException("Please log in!");
		}
	}
	
	public static void validateAdminUser(HttpServletRequest request) throws NotLoggedInException, NotAdminException {
		Validation.validateLogIn(request);
		if(!((User)request.getSession().getAttribute(USER_ATTRIBUTE)).isAdmin()) {
			throw new NotAdminException("You are not admin!");
		}
	}
	
	public static void validateUserPassword(String password, String reEnteredPassword) throws UserException {
		if(!password.equals(reEnteredPassword)) {
			throw new UserException("Invalid re-entered password !!!");
		}
	}
}
