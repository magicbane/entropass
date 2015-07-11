// Copyright (c) 2015 Entropass Authors. All rights reserved.
package com.rampantlogic.entropass.core;

/**
 *
 * @author George W. (magicbane@engineer.com)
 *
 */
public interface PasswordGenerator {

public String generateKeyWithSymbols(String passphrase,
		String username,
		String domain,
		int resetCount,
		int keyLength) throws EntropassException;

public String generateKeyWithoutSymbols(String passphrase,
		String username,
		String domain,
		int resetCount,
		int keyLength) throws EntropassException;

public void setPrivateHash(String string);

}

