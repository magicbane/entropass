package com.rampantlogic.entropass.core;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import android.test.suitebuilder.annotation.SmallTest;

import com.rampantlogic.entropass.core.DefaultPasswordGenerator;
import com.rampantlogic.entropass.core.EntropassException;
import com.rampantlogic.entropass.core.PasswordGenerator;

import junit.framework.TestCase;

@RunWith(RobolectricGradleTestRunner.class)
public class DefaultPasswordGeneratorTest extends TestCase {

	private PasswordGenerator pg;
 
	@Before
	public void setUp() throws NoSuchAlgorithmException {
		pg = new DefaultPasswordGenerator();
		
	}
	
	@SmallTest
	public void testGenerateKeyWithSymbols() throws EntropassException {
		String result = pg.generateKeyWithSymbols("foobar", "foobar", "github.com", 0, 16);
		assertEquals("-wW3DflRt&#scJ`_", result);
		result = pg.generateKeyWithSymbols("foobar", "foobar", "github.com", 1, 16);
		assertEquals("EWB#)o4CqtN6^W)S", result);
		
	}
	
	@SmallTest
	public void testGenerateKeyWithoutSymbols() throws EntropassException {	
		String result = pg.generateKeyWithoutSymbols("foobar", "foobar", "github.com", 0, 16);
		assertEquals("3wvW9YFMumTGAuSi", result);
		result = pg.generateKeyWithoutSymbols("foobar", "foobar", "github.com", 1, 16);
		assertEquals("LLxq8Ju4ym1H0Mm3", result);
		
	}
	
	@SmallTest
	public void testGenerateKeyWithSymbolsWithPrivateKey() throws EntropassException{
		pg.setPrivateHash("b13beae89bbbc4f1c162241a513e709b0517d8d0ba5e70d9fe85f75a07fc491d527eec2863d0731819a56aa0d49eeefff7c1421afa5b8a8483d41968d0795653");
		String result = pg.generateKeyWithSymbols("foobar", "foobar", "github.com", 0, 16);
		assertEquals("Q2YTNIP6TQbo?8ln", result);
		
	}
	
	@SmallTest
	public void testGenerateKeyWithoutSymbolsWithPrivateKey() throws EntropassException {	
		pg.setPrivateHash("b13beae89bbbc4f1c162241a513e709b0517d8d0ba5e70d9fe85f75a07fc491d527eec2863d0731819a56aa0d49eeefff7c1421afa5b8a8483d41968d0795653");
		String result = pg.generateKeyWithoutSymbols("foobar", "foobar", "github.com", 0, 16);
		assertEquals("UPwBHjjsTKh0Buim", result);
	}
	
	@SmallTest
	public void testGeneratePBKDF2Key() throws InvalidKeySpecException {
		byte[] key = ((DefaultPasswordGenerator) pg).generatePBKDF2Key("foobar", "foobar", "github.com", 0);
		assertEquals(key, "");
	}

}
