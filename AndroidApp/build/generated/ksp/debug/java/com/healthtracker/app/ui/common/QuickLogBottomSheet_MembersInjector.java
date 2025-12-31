package com.healthtracker.app.ui.common;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.repository.HealthRepository;
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
public final class QuickLogBottomSheet_MembersInjector implements MembersInjector<QuickLogBottomSheet> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public QuickLogBottomSheet_MembersInjector(Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<QuickLogBottomSheet> create(
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new QuickLogBottomSheet_MembersInjector(healthRepositoryProvider, firebaseAuthProvider);
  }

  @Override
  public void injectMembers(QuickLogBottomSheet instance) {
    injectHealthRepository(instance, healthRepositoryProvider.get());
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.common.QuickLogBottomSheet.healthRepository")
  public static void injectHealthRepository(QuickLogBottomSheet instance,
      HealthRepository healthRepository) {
    instance.healthRepository = healthRepository;
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.common.QuickLogBottomSheet.firebaseAuth")
  public static void injectFirebaseAuth(QuickLogBottomSheet instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
