package greenteam.dungeoncraft.Engine.Scene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {

	private int texWidth;
	private int texHeight;
	ByteBuffer buf;

	public Texture() {
	}

	/* loads a texture from a file location kept in a byteBuffer for later use */
	public void load(String relativePath) {

		buf = null;

		try {
			InputStream stream = new FileInputStream(relativePath);
			PNGDecoder decoder = new PNGDecoder(stream);
			texWidth = decoder.getWidth();
			texHeight = decoder.getHeight();
			buf = ByteBuffer.allocateDirect(4 * texWidth * texHeight);
			decoder.decode(buf, texWidth * 4, Format.RGBA);
			buf.flip();
			stream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load texture from stream.");
			System.exit(-1);
		}
	}

	public int getTexWidth() {
		return texWidth;
	}

	public void setTexWidth(int texWidthIn) {
		this.texWidth = texWidthIn;
	}

	public int getTexHeight() {
		return texHeight;
	}

	public void setTexHeight(int texHeightIn) {
		this.texHeight = texHeightIn;
	}

	public ByteBuffer getBuf() {
		return buf;
	}

}
