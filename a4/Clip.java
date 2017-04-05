package a4;

/*
 * This class sets up an AudioClip by accepting the name of the
 * sound file to be encapsulated within a Clip object so that it
 * can be reused if needed.
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Clip implements AudioClip {
	private AudioClip clip;
	private String fileName;
	
	public Clip(String name)
	{
		fileName = "." + File.separator + "sounds" + File.separator + name;
		try {
			File file = new File(fileName);
			if(file.exists())
			{
				clip = Applet.newAudioClip(file.toURI().toURL());
			}
			else
			{
				throw new RuntimeException("Sound: file not found: " + fileName);
			}
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
	}
	
	public void play() { clip.play(); }
	public void loop() { clip.loop(); }
	public void stop() { clip.stop(); }

}
