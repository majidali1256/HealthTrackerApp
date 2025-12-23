package com.healthtracker.app.ui.vitals;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.repository.HealthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LogVitalsViewModel_Factory implements Factory<LogVitalsViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public LogVitalsViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public LogVitalsViewModel get() {
    return newInstance(healthRepositoryProvider.get(), firebaseAuthProvider.get());
  }

  public static LogVitalsViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new LogVitalsViewModel_Factory(healthRepositoryProvider, firebaseAuthProvider);
  }

  public static LogVitalsViewModel newInstance(HealthRepository healthRepository,
      FirebaseAuth firebaseAuth) {
    return new LogVitalsViewModel(healthRepository, firebaseAuth);
  }
}
