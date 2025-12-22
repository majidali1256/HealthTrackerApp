package com.healthtracker.app.ui.trends;

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
public final class TrendsViewModel_Factory implements Factory<TrendsViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  public TrendsViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
  }

  @Override
  public TrendsViewModel get() {
    return newInstance(healthRepositoryProvider.get());
  }

  public static TrendsViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider) {
    return new TrendsViewModel_Factory(healthRepositoryProvider);
  }

  public static TrendsViewModel newInstance(HealthRepository healthRepository) {
    return new TrendsViewModel(healthRepository);
  }
}
