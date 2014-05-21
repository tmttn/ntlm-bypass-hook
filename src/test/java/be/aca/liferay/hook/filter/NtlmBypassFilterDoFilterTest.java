package be.aca.liferay.hook.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NtlmBypassFilterDoFilterTest {

	private static final String NTLM_REMOTE_USER_ATTRIBUTE_KEY = "NTLM_REMOTE_USER";
	public static final String NTLM_BYPASS_REQUEST_PARAMETER = "ntlmBypass";
	public static final String NTLM_BYPASS_ENABLED_VALUE = "true";

	@Mock private ServletRequest request;
	@Mock private ServletResponse response;
	@Mock private FilterChain chain;

	@InjectMocks private NtlmBypassFilter filter;

	@Test
	public void ntlmIsBypassedWhenRequestParameterTrue() throws IOException, ServletException {
		when(request.getParameter(NTLM_BYPASS_REQUEST_PARAMETER)).thenReturn(NTLM_BYPASS_ENABLED_VALUE);
		filter.doFilter(request, response, chain);
		verify(request).removeAttribute(NTLM_REMOTE_USER_ATTRIBUTE_KEY);
	}

	@Test
	public void ntlmIsNotByPassed() throws IOException, ServletException {
		filter.doFilter(request, response, chain);
		verify(request, never()).removeAttribute(NTLM_REMOTE_USER_ATTRIBUTE_KEY);
	}

	@Test
	public void filterIsChained() throws IOException, ServletException {
		filter.doFilter(request, response, chain);
		verify(chain).doFilter(request, response);
	}
}
