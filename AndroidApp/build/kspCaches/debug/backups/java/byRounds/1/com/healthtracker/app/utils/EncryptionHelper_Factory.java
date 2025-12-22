package com.healthtracker.app.utils;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class EncryptionHelper_Factory implements Factory<EncryptionHelper> {
  private final Provider<Context> contextProvider;

  public EncryptionHelper_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public EncryptionHelper get() {
    return newInstance(contextProvider.get());
  }

  public static EncryptionHelper_Factory create(Provider<Context> contextProvider) {
    return new EncryptionHelper_Factory(contextProvider);
  }

  public static EncryptionHelper newInstance(Context context) {
    return new EncryptionHelper(context);
  }
}
