package com.healthtracker.app.ui.dashboard;

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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  public DashboardViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(healthRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider) {
    return new DashboardViewModel_Factory(healthRepositoryProvider);
  }

  public static DashboardViewModel newInstance(HealthRepository healthRepository) {
    return new DashboardViewModel(healthRepository);
  }
}
