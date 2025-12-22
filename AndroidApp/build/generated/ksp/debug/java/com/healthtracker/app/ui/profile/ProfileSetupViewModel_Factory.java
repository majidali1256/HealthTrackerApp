package com.healthtracker.app.ui.profile;

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
public final class ProfileSetupViewModel_Factory implements Factory<ProfileSetupViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  public ProfileSetupViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
  }

  @Override
  public ProfileSetupViewModel get() {
    return newInstance(healthRepositoryProvider.get());
  }

  public static ProfileSetupViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider) {
    return new ProfileSetupViewModel_Factory(healthRepositoryProvider);
  }

  public static ProfileSetupViewModel newInstance(HealthRepository healthRepository) {
    return new ProfileSetupViewModel(healthRepository);
  }
}
