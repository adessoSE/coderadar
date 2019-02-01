package org.wickedsource.coderadar.job.scan.file;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class Hasher {

	/**
	* Creates a SHA-1 hash of the given content.
	*
	* @param content the bytes to create a hash for.
	* @return SHA-1 hash value as hex string.
	*/
	public String hash(byte[] content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			return DatatypeConverter.printHexBinary(digest.digest(content));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
}
