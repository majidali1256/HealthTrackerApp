package com.healthtracker.app.ui.dashboard;

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
public final class DashboardActivity_MembersInjector implements MembersInjector<DashboardActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public DashboardActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<DashboardActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new DashboardActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(DashboardActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.dashboard.DashboardActivity.firebaseAuth")
  public static void injectFirebaseAuth(DashboardActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
