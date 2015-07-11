// Copyright (c) 2015 Entropass Authors. All rights reserved.
package com.rampantlogic.entropass.core;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

import com.rampantlogic.entropass.utils.Base85Encoder;

public class DefaultPasswordGenerator implements PasswordGenerator {

	private static int DEFAULT_INTERATION_COUNT = 100;
	private String privateKeyHash;
	private PKCS5S2ParametersGenerator paramGenerator;
	
	public DefaultPasswordGenerator() throws NoSuchAlgorithmException {
		paramGenerator = new PKCS5S2ParametersGenerator(new SHA512Digest());
		
	}
	
	@Override
	public String generateKeyWithSymbols(String passphrase, String username, String domain, int resetCount, int keyLength) throws EntropassException {
		try {

			byte[] key = generatePBKDF2Key(passphrase, username, domain, resetCount);
			
			String encodedKey = Base85Encoder.encodeToString(key);
			return encodedKey.substring(0, keyLength);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new EntropassException();
		}
	}

	@Override
	public String generateKeyWithoutSymbols(String passphrase, String username, String domain, int resetCount, int keyLength) throws EntropassException {
		try {
			byte[] key = generatePBKDF2Key(passphrase, username, domain, resetCount);
			String encodedKey = Base64.encodeToString(key, 0, key.length, Base64.DEFAULT).replaceAll("[_\\W]", "");
			//FIXME: potential arrayOutOfBound
			return encodedKey.substring(0, keyLength);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new EntropassException();
		}
	}


	protected byte[] generatePBKDF2Key(String passphrase, String username, String domain, int resetCount) throws InvalidKeySpecException {
		StringBuilder secretBuilder = new StringBuilder();
		secretBuilder.append(passphrase);
		secretBuilder.append(resetCount == 0 ? "" : resetCount);
		secretBuilder.append(privateKeyHash == null ? "" : privateKeyHash);
		paramGenerator.init(secretBuilder.toString().getBytes(Charset.forName("UTF-8")), domain.getBytes(Charset.forName("UTF-8")), DEFAULT_INTERATION_COUNT);
		byte[] key = ((KeyParameter) paramGenerator.generateDerivedParameters(512)).getKey();
		return key;
	}

	@Override
	public void setPrivateHash(String hash) {
		this.privateKeyHash = hash;		
	}
	
}
