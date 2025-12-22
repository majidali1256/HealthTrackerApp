package com.healthtracker.app.ui.sleep;

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
public final class SleepAnalysisActivity_MembersInjector implements MembersInjector<SleepAnalysisActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public SleepAnalysisActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<SleepAnalysisActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new SleepAnalysisActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(SleepAnalysisActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.sleep.SleepAnalysisActivity.firebaseAuth")
  public static void injectFirebaseAuth(SleepAnalysisActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
