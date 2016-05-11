package lbs.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class LBSUtils {
	
	public static String getRequestMsg(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(request.getInputStream()));

			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();	
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return sb.toString();
	}
}
