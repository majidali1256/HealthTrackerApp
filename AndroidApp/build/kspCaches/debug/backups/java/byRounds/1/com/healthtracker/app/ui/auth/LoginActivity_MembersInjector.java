package com.healthtracker.app.ui.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class LoginActivity_MembersInjector implements MembersInjector<LoginActivity> {
  private final Provider<GoogleSignInClient> googleSignInClientProvider;

  public LoginActivity_MembersInjector(Provider<GoogleSignInClient> googleSignInClientProvider) {
    this.googleSignInClientProvider = googleSignInClientProvider;
  }

  public static MembersInjector<LoginActivity> create(
      Provider<GoogleSignInClient> googleSignInClientProvider) {
    return new LoginActivity_MembersInjector(googleSignInClientProvider);
  }

  @Override
  public void injectMembers(LoginActivity instance) {
    injectGoogleSignInClient(instance, googleSignInClientProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.auth.LoginActivity.googleSignInClient")
  public static void injectGoogleSignInClient(LoginActivity instance,
      GoogleSignInClient googleSignInClient) {
    instance.googleSignInClient = googleSignInClient;
  }
}
