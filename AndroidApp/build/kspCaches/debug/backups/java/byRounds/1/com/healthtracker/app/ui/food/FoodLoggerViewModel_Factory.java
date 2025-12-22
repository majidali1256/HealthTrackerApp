package com.healthtracker.app.ui.food;

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
public final class FoodLoggerViewModel_Factory implements Factory<FoodLoggerViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public FoodLoggerViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public FoodLoggerViewModel get() {
    return newInstance(healthRepositoryProvider.get(), firebaseAuthProvider.get());
  }

  public static FoodLoggerViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new FoodLoggerViewModel_Factory(healthRepositoryProvider, firebaseAuthProvider);
  }

  public static FoodLoggerViewModel newInstance(HealthRepository healthRepository,
      FirebaseAuth firebaseAuth) {
    return new FoodLoggerViewModel(healthRepository, firebaseAuth);
  }
}
