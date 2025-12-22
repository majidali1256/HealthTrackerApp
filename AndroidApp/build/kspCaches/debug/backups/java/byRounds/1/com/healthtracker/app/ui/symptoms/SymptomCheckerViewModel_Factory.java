package com.healthtracker.app.ui.symptoms;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SymptomCheckerViewModel_Factory implements Factory<SymptomCheckerViewModel> {
  @Override
  public SymptomCheckerViewModel get() {
    return newInstance();
  }

  public static SymptomCheckerViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SymptomCheckerViewModel newInstance() {
    return new SymptomCheckerViewModel();
  }

  private static final class InstanceHolder {
    private static final SymptomCheckerViewModel_Factory INSTANCE = new SymptomCheckerViewModel_Factory();
  }
}
