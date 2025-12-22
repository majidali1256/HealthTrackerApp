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
public final class SignUpActivity_MembersInjector implements MembersInjector<SignUpActivity> {
  private final Provider<GoogleSignInClient> googleSignInClientProvider;

  public SignUpActivity_MembersInjector(Provider<GoogleSignInClient> googleSignInClientProvider) {
    this.googleSignInClientProvider = googleSignInClientProvider;
  }

  public static MembersInjector<SignUpActivity> create(
      Provider<GoogleSignInClient> googleSignInClientProvider) {
    return new SignUpActivity_MembersInjector(googleSignInClientProvider);
  }

  @Override
  public void injectMembers(SignUpActivity instance) {
    injectGoogleSignInClient(instance, googleSignInClientProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.auth.SignUpActivity.googleSignInClient")
  public static void injectGoogleSignInClient(SignUpActivity instance,
      GoogleSignInClient googleSignInClient) {
    instance.googleSignInClient = googleSignInClient;
  }
}
