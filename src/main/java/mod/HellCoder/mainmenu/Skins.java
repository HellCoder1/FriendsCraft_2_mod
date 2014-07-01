package mod.HellCoder.mainmenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import net.minecraft.client.Minecraft;

public class Skins
{
	private Minecraft mc;
	
    private String sendSessionRequest(String par1Str, String par2Str, String par3Str)
    {
        try
        {
            URL url = new URL("http://friendscraft2.ru/minecraft/joinserver.jsp?user=" + urlEncode(par1Str) + "&sessionId=" + urlEncode(par2Str) + "&serverId=" + urlEncode(par3Str));
            InputStream inputstream = url.openConnection(this.mc.getProxy()).getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            String s3 = bufferedreader.readLine();
            bufferedreader.close();
            return s3;
        }
        catch (IOException ioexception)
        {
            return ioexception.toString();
        }
    }
    
    private static String urlEncode(String par0Str) throws IOException
    {
        return URLEncoder.encode(par0Str, "UTF-8");
    }
}
