package com.weatherservice.utilities;

import com.weatherservice.exceptions.HttpRequestException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

public class HttpRequestUtil {
  public static String doQuery(String url, HashMap<String, String> params, java.nio.charset.Charset charset) throws HttpRequestException {

    String charsetStr = charset.name();
    String query = "";
    Boolean firstParam = true;

    try {
      Set<String> paramKeys = params.keySet();
      for (String paramKey : paramKeys) {
        query += paramKey + "=" + URLEncoder.encode(params.get(paramKey), charsetStr);
        if (firstParam) {
          query += "&";
          firstParam = false;
        }
      }

      System.out.println(url + "?" + query);

      URLConnection connection = new URL(url + "?" + query).openConnection();
      connection.setRequestProperty("Accept-Charset", charsetStr);
      InputStream response = connection.getInputStream();

      return IOUtils.toString(response, charset);

    } catch(UnsupportedEncodingException uee) {
      throw new HttpRequestException(uee, "Encoding error when making HTTP request for url: " + url + " and parameters: " + query);
    } catch(MalformedURLException mie) {
      throw new HttpRequestException(mie, "URL error when making HTTP request for url: " + url + " and parameters: " + query);
    } catch(IOException ioe) {
      throw new HttpRequestException(ioe, "I/O error when making HTTP request for url: " + url + " and parameters: " + query);
    }
  }

  public static String doQuery(String url, HashMap<String, String> params) throws HttpRequestException {
    return doQuery(url, params, StandardCharsets.UTF_8);
  }
}
