package be.aca.liferay.hook.filter;

import javax.servlet.*;
import java.io.IOException;

public class NtlmBypassFilter implements Filter {

	private static final String NTLM_REMOTE_USER_ATTRIBUTE_KEY = "NTLM_REMOTE_USER";
	public static final String NTLM_BYPASS_REQUEST_PARAMETER = "ntlmBypass";
	public static final String NTLM_BYPASS_ENABLED_VALUE = "true";


	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean isNtlmBypass = checkNtlmBypass(request);

		if(isNtlmBypass) {
			request.removeAttribute(NTLM_REMOTE_USER_ATTRIBUTE_KEY);
		}

		chain.doFilter(request, response);
	}

	private boolean checkNtlmBypass(ServletRequest request) {
		String bypassParameter = request.getParameter(NTLM_BYPASS_REQUEST_PARAMETER);
		return NTLM_BYPASS_ENABLED_VALUE.equals(bypassParameter);
	}

	public void destroy() {

	}
}
