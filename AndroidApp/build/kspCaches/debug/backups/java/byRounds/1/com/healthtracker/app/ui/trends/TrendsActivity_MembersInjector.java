package com.healthtracker.app.ui.trends;

import com.google.firebase.auth.FirebaseAuth;
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
public final class TrendsActivity_MembersInjector implements MembersInjector<TrendsActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TrendsActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<TrendsActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TrendsActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(TrendsActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.trends.TrendsActivity.firebaseAuth")
  public static void injectFirebaseAuth(TrendsActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
