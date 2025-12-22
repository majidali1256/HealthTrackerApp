package com.healthtracker.app.ui.sleep;

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
public final class SleepAnalysisViewModel_Factory implements Factory<SleepAnalysisViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  public SleepAnalysisViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
  }

  @Override
  public SleepAnalysisViewModel get() {
    return newInstance(healthRepositoryProvider.get());
  }

  public static SleepAnalysisViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider) {
    return new SleepAnalysisViewModel_Factory(healthRepositoryProvider);
  }

  public static SleepAnalysisViewModel newInstance(HealthRepository healthRepository) {
    return new SleepAnalysisViewModel(healthRepository);
  }
}
