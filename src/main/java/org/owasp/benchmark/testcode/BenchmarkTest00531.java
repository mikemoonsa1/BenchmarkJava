/**
* OWASP Benchmark Project v1.2beta
*
* This file is part of the Open Web Application Security Project (OWASP)
* Benchmark Project. For details, please see
* <a href="https://www.owasp.org/index.php/Benchmark">https://www.owasp.org/index.php/Benchmark</a>.
*
* The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
* of the GNU General Public License as published by the Free Software Foundation, version 2.
*
* The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
* even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* @author Nick Sanidas <a href="https://www.aspectsecurity.com">Aspect Security</a>
* @created 2015
*/

package org.owasp.benchmark.testcode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BenchmarkTest00531")
public class BenchmarkTest00531 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	
		String param = "";
		boolean flag = true;
		java.util.Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements() && flag) {
			String name = (String) names.nextElement();		    	
			String[] values = request.getParameterValues(name);
			if (values != null) {
				for(int i=0;i<values.length && flag; i++){
					String value = values[i];
					if (value.equals("vector")) {
						param = name;
					    flag = false;
					}
				}
			}
		}
		
		
		String bar = "safe!";
		java.util.HashMap<String,Object> map17333 = new java.util.HashMap<String,Object>();
		map17333.put("keyA-17333", "a Value"); // put some stuff in the collection
		map17333.put("keyB-17333", param); // put it in a collection
		map17333.put("keyC", "another Value"); // put some stuff in the collection
		bar = (String)map17333.get("keyB-17333"); // get it back out
		
		
		java.security.Provider[] provider = java.security.Security.getProviders();
		java.security.MessageDigest md;

		try {
			if (provider.length > 1) {

				md = java.security.MessageDigest.getInstance("SHA1", provider[0]);
			} else {
				md = java.security.MessageDigest.getInstance("SHA1", "SUN");
			}
			byte[] input = { (byte)'?' };
			Object inputParam = bar;
			if (inputParam instanceof String) input = ((String) inputParam).getBytes();
			if (inputParam instanceof java.io.InputStream) {
				byte[] strInput = new byte[1000];
				int i = ((java.io.InputStream) inputParam).read(strInput);
				if (i == -1) {
					response.getWriter().println("This input source requires a POST, not a GET. Incompatible UI for the InputStream source.");
					return;
				}
				input = java.util.Arrays.copyOf(strInput, i);
			}
			md.update(input);
			
			byte[] result = md.digest();
			java.io.File fileTarget = new java.io.File(
					new java.io.File(org.owasp.benchmark.helpers.Utils.testfileDir),"passwordFile.txt");
			java.io.FileWriter fw = new java.io.FileWriter(fileTarget,true); //the true will append the new data
			    fw.write("hash_value=" + org.owasp.esapi.ESAPI.encoder().encodeForBase64(result, true) + "\n");
			fw.close();
			response.getWriter().println("Sensitive value '" + org.owasp.esapi.ESAPI.encoder().encodeForHTML(new String(input)) + "' hashed and stored<br/>");
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println("Problem executing hash - TestCase java.security.MessageDigest.getInstance(java.lang.String,java.security.Provider)");
            throw new ServletException(e);
		} catch (java.security.NoSuchProviderException e) {
			System.out.println("Problem executing hash - TestCase java.security.MessageDigest.getInstance(java.lang.String,java.security.Provider)");
            throw new ServletException(e);
		}

		response.getWriter().println("Hash Test java.security.MessageDigest.getInstance(java.lang.String,java.security.Provider) executed");
	}
}