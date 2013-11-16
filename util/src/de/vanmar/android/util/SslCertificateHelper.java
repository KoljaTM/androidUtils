package de.vanmar.android.util;

import android.content.Context;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SslCertificateHelper {

	/**
	 * Trust every server - dont check for any certificate
	 */
	public static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			@Override
			public void checkClientTrusted(final X509Certificate[] chain,
			                               final String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(final X509Certificate[] chain,
			                               final String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[]{};
			}
		}};

		// Install the all-trusting trust manager
		try {
			final SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/** See http://stackoverflow.com/questions/2642777/trusting-all-certificates-using-httpclient-over-https/16022833#16022833
	 * for information on how to get the certificate file
	 */
	public static void trustGeotrustCertificate(final Context context) throws GeneralSecurityException, IOException {
		final KeyStore trustStore = KeyStore.getInstance("BKS");
		final InputStream in = context.getResources().openRawResource(
				R.raw.geotrust_cert);
		trustStore.load(in, null);

		final TrustManagerFactory tmf = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trustStore);

		final SSLContext sslCtx = SSLContext.getInstance("TLS");
		sslCtx.init(null, tmf.getTrustManagers(),
				new java.security.SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx
				.getSocketFactory());
	}
}
