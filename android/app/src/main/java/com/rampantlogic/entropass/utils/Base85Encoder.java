package com.rampantlogic.entropass.utils;

import com.github.libxjava.io.Base85;
import com.github.libxjava.io.BaseX;

/**
 * 
 * This Base85 encoder follows RFC 1924
 * 
 * @author George W. (magicbane@engineer.com)
 *
 */
public class Base85Encoder {

	public static String encodeToString(byte[] buffer) {
		BaseX base85 = new Base85();
		byte[] resultByteArray = base85.encode(buffer, 0, buffer.length);

		return new String(resultByteArray);
		
	}

}
