package com.healthtracker.app.ui.medications;

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
public final class MedicationsActivity_MembersInjector implements MembersInjector<MedicationsActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public MedicationsActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<MedicationsActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new MedicationsActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(MedicationsActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.medications.MedicationsActivity.firebaseAuth")
  public static void injectFirebaseAuth(MedicationsActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
