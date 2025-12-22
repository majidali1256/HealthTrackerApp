package com.healthtracker.app.ui.symptoms;

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
public final class SymptomCheckerActivity_MembersInjector implements MembersInjector<SymptomCheckerActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public SymptomCheckerActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<SymptomCheckerActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new SymptomCheckerActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(SymptomCheckerActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.symptoms.SymptomCheckerActivity.firebaseAuth")
  public static void injectFirebaseAuth(SymptomCheckerActivity instance,
      FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
